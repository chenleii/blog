package com.chen.blog.test.bdd.stepdefinitions;

import com.chen.blog.core.account.domain.model.Account;
import com.chen.blog.core.account.domain.model.repository.AccountRepository;
import com.chen.blog.core.article.application.queryservice.ArticleQueryService;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.interfaces.http.dto.input.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.inject.Inject;
import java.util.Objects;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/15 10:23
 */
public class ArticleStepDefinitions extends SharedStepDefinitions {

    @Inject
    private MockMvc mockMvc;

    @Inject
    private ArticleQueryService articleQueryService;
    @Inject
    private AccountRepository accountRepository;
    @Inject
    private ObjectMapper objectMapper;


    @Given("登录账户 {string}")
    public void 登录账户(String phoneNo) throws Exception {
        {
            AccountSendLoginVerificationCodeInputDTO dto = new AccountSendLoginVerificationCodeInputDTO();
            dto.setPhoneNo(phoneNo);

            final MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/blog/account/sendLoginVerificationCode")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(Serializers.json().toJsonString(dto))
                                    .cookie(super.getCookies())
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        }

        {
            AccountLoginInputDTO dto = new AccountLoginInputDTO();
            dto.setPhoneNo(phoneNo);
            dto.setVerificationCode("000000");

            final MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/blog/account/login")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(Serializers.json().toJsonString(dto))
                                    .cookie(super.getCookies())
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            super.setCookies(mvcResult.getResponse().getCookies());
        }
    }

    @Given("更新登录账户信息 name:{string}, avatar:{string}")
    public void 更新登录账户信息(String name, String avatar) throws Exception {
        AccountUpdateInputDTO dto = new AccountUpdateInputDTO();
        dto.setName(name);
        dto.setAvatar(avatar);
        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/blog/account/update")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(Serializers.json().toJsonString(dto))
                                .cookie(super.getCookies())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        super.setCookies(mvcResult.getResponse().getCookies());
    }

    @When("保存文章 isPublish:{boolean}, title:{string}, tags:{string}, content:{string}")
    public void 保存文章(
            boolean isPublish, String title, String tags, String content) throws Exception {
        ArticleSaveAndPublishInputDTO dto = new ArticleSaveAndPublishInputDTO();
        dto.setTitle(title);
        dto.setTags(Sets.newHashSet(tags.split(",")));
        dto.setContent(content);
        dto.setIsPublish(isPublish);

        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/blog/article")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(Serializers.json().toJsonString(dto))
                                .cookie(super.getCookies())
                )
                .andExpect(MockMvcResultMatchers.status().is(Matchers.greaterThanOrEqualTo(200)))
                .andReturn();
    }

    @Then("登录账户下是否有文章 {boolean}")
    public void 登录账户下是否有文章(boolean isSaved) throws Exception {

        // es有延迟，等一秒。
        Thread.sleep(2000);

        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/blog/article/account")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                .param("pageIndex", "1")
                                .param("pageSize", "11")
                                .cookie(super.getCookies())
                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.list").value(isSaved ? Matchers.hasSize(1) : Matchers.emptyIterable()))
                .andReturn();
    }

    @Then("文章搜索 {boolean}, {string}, {string}")
    public void 文章搜索(boolean isSearched, String searchKeyword,String phoneNo) throws Exception {

        Account account = accountRepository.getByPhoneNo(phoneNo);
        Long accountId = account.getId().getId();

        // es有延迟，等一秒。
        Thread.sleep(2000);

        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/blog/article")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                .param("pageIndex", "1")
                                .param("pageSize", "11")
                                .param("searchKeyword", searchKeyword)
                                .param("accountId", String.valueOf(accountId))
                                .cookie(super.getCookies())
                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.list").value(isSearched ? Matchers.hasSize(1) : Matchers.emptyIterable()))
                .andReturn();
    }


    @When("更新文章，isPublish:{boolean}, title:{string}, tags:{string}, content:{string}")
    public void 更新文章(
            boolean isPublish, String title, String tags, String content
    ) throws Exception {
        Long articleId = null;
        {
            final MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.get("/api/blog/article/account")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(SecurityMockMvcRequestPostProcessors.csrf())
                                    .param("pageIndex", "1")
                                    .param("pageSize", "11")
                                    .cookie(super.getCookies())
                    )
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();


            Pagination<ArticleRepresentation> pagination = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Pagination<ArticleRepresentation>>() {
            });
            if (Objects.nonNull(pagination)) {
                if (CollectionUtils.isNotEmpty(pagination.getList())) {
                    articleId = pagination.getList().get(0).getId();
                }
            }
        }

        ArticleUpdateAndPublishInputDTO dto = new ArticleUpdateAndPublishInputDTO();
        dto.setArticleId(articleId);
        dto.setTitle(title);
        dto.setTags(Sets.newHashSet(tags.split(",")));
        dto.setContent(content);
        dto.setIsPublish(isPublish);

        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/blog/article")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(Serializers.json().toJsonString(dto))
                                .cookie(super.getCookies())
                )
                .andExpect(MockMvcResultMatchers.status().is(Matchers.greaterThanOrEqualTo(200)))
                .andReturn();
    }

}
