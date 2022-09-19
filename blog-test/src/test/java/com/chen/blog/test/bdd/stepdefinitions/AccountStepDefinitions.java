package com.chen.blog.test.bdd.stepdefinitions;

import com.chen.blog.core.sharedkernel.serializer.Serializers;
import com.chen.blog.infrastructure.persistence.repository.dataobject.AccountDO;
import com.chen.blog.interfaces.http.dto.input.AccountLoginInputDTO;
import com.chen.blog.interfaces.http.dto.input.AccountSendLoginVerificationCodeInputDTO;
import com.chen.blog.interfaces.http.dto.input.AccountUpdateInputDTO;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.BDDAssertions;
import org.hamcrest.Matchers;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.inject.Inject;

/**
 * @author cl
 * @version 1.0
 * @since 2021/7/15 10:23
 */
public class AccountStepDefinitions extends SharedStepDefinitions {

    @Inject
    private MockMvc mockMvc;

    @Inject
    private MongoTemplate mongoTemplate;


    @When("发送手机 {string} 验证码")
    public void 发送手机_验证码(String phoneNo) throws Exception {
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

    @When("手机号 {string} 验证码登录")
    public void 手机号_验证码登录(String phoneNo) throws Exception {
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


    @Then("当前登录账户的手机号为 {string}")
    public void 当前登录账户的手机号为(String phoneNo) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/blog/account/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                .cookie(super.getCookies())
                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNo").value(Matchers.is(phoneNo)))
                .andReturn();
    }


    @Then("查询账户手机号为 {string} 有{int}条记录")
    public void 查询账户手机号为_有_条记录(String phoneNo, int count) throws Exception {
        long realCount = mongoTemplate.count(Query.query(Criteria.where("phoneNo").is(phoneNo)), AccountDO.class);

        BDDAssertions.then(realCount)
                .isEqualTo(count);
    }



    @When("当前登录账户 {string} 更新信息，name: {string}， avatar：{string}, introduction:{string}")
    public void 当前登录账户_更新信息(String phoneNo, String name, String avatar,String introduction) throws Exception {
        AccountUpdateInputDTO dto = new AccountUpdateInputDTO();
        dto.setName(name);
        dto.setAvatar(avatar);
        dto.setIntroduction(introduction);
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

    @Then("当前登录账户 {string} 名称为 {string}")
    public void 当前登录账户_名称为(String phoneNo, String name) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/blog/account/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                                .cookie(super.getCookies())
                )
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(Matchers.is(name)))
                .andReturn();
    }



}
