package com.chen.blog.interfaces.http.controller;

import com.chen.blog.core.article.application.commandservice.ArticleCommandService;
import com.chen.blog.core.article.application.queryservice.ArticleQueryService;
import com.chen.blog.core.article.domain.model.ArticleStatus;
import com.chen.blog.core.article.domain.model.cqrs.command.*;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticlePageQuery;
import com.chen.blog.core.article.domain.model.cqrs.query.ArticleQuery;
import com.chen.blog.core.article.domain.model.cqrs.representation.ArticleRepresentation;
import com.chen.blog.core.hot.application.commandservice.ArticleHotCommandService;
import com.chen.blog.core.hot.application.commandservice.ArticleHotSearchCommandService;
import com.chen.blog.core.hot.application.queryservice.ArticleHotQueryService;
import com.chen.blog.core.hot.application.queryservice.ArticleHotSearchQueryService;
import com.chen.blog.core.hot.doamin.model.cqrs.command.ArticleAccessCommand;
import com.chen.blog.core.hot.doamin.model.cqrs.command.ArticleSearchCommand;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotPageQuery;
import com.chen.blog.core.hot.doamin.model.cqrs.query.ArticleHotSearchPageQuery;
import com.chen.blog.core.sharedkernel.cqrs.Pagination;
import com.chen.blog.core.sharedkernel.trace.Trace;
import com.chen.blog.core.sharedkernel.trace.TraceMonitorLog;
import com.chen.blog.interfaces.http.dto.PageQueryInputDTO;
import com.chen.blog.interfaces.http.dto.input.*;
import com.google.common.collect.Sets;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

/**
 * @author cl
 * @version 1.0
 * @since 2021/9/8 15:24
 */
@Trace
@TraceMonitorLog
@Tag(name = "article-api", description = "文章接口")
@Slf4j
@RestController
@RequestMapping("/api/blog/article")
public class ArticleAppController extends AbstractAppController {


    @Inject
    private ArticleCommandService articleCommandService;
    @Inject
    private ArticleHotSearchCommandService articleHotSearchCommandService;
    @Inject
    private ArticleHotCommandService articleHotCommandService;
    @Inject
    private ArticleQueryService articleQueryService;
    @Inject
    private ArticleHotQueryService articleHotQueryService;
    @Inject
    private ArticleHotSearchQueryService articleHotSearchQueryService;

    @Operation(summary = "保存")
    @PostMapping("")
    public Long save(@RequestBody ArticleSaveAndPublishInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        return articleCommandService.save(ArticleSaveAndPublishCommand.builder().accountId(currentLoggedInAccountId).title(dto.getTitle()).tags(dto.getTags()).content(dto.getContent()).isPublish(dto.getIsPublish()).build());
    }

    @Operation(summary = "更新")
    @PutMapping("")
    public Long update(@RequestBody @Valid ArticleUpdateAndPublishInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        return articleCommandService.update(ArticleUpdateAndPublishCommand.builder()
                .articleId(dto.getArticleId()).accountId(currentLoggedInAccountId).title(dto.getTitle()).tags(dto.getTags()).content(dto.getContent()).isPublish(dto.getIsPublish()).build());
    }


    @Operation(summary = "喜欢")
    @PostMapping("/like")
    public Integer like(@RequestBody ArticleLikeInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        return articleCommandService.like(ArticleLikeCommand.builder().articleId(dto.getArticleId()).accountId(currentLoggedInAccountId).build());
    }

    @Operation(summary = "举报")
    @PostMapping("/report")
    public Integer report(@RequestBody ArticleReportInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        return articleCommandService.report(ArticleReportCommand.builder().articleId(dto.getArticleId()).accountId(currentLoggedInAccountId).remark(dto.getRemark()).build());
    }

    @Operation(summary = "评论")
    @PostMapping("/comment")
    public Long comment(@RequestBody ArticleCommentInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        return articleCommandService.comment(ArticleCommentCommand.builder().articleId(dto.getArticleId()).accountId(currentLoggedInAccountId).content(dto.getContent()).build());
    }

    @Operation(summary = "回复子评论")
    @PostMapping("/replySubComment")
    public Long replySubComment(@RequestBody ArticleReplySubCommentInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();
        return articleCommandService.replySubComment(ArticleReplySubCommentCommand.builder().articleId(dto.getArticleId()).accountId(currentLoggedInAccountId).commentId(dto.getCommentId()).replyCommentId(dto.getReplyCommentId()).content(dto.getContent()).build());
    }


    @Operation(summary = "分页查询")
    @GetMapping("")
    public Pagination<ArticleRepresentation> pageQuery(@ModelAttribute ArticlePageQueryInputDTO dto) {
        boolean isLoggedIn = super.isLoggedIn();
        Long currentLoggedInAccountId = isLoggedIn ? super.getCurrentLoggedInAccountId() : null;

        Pagination<ArticleRepresentation> pagination = articleQueryService.pageQuery(
                ArticlePageQuery.builder()
                        .pageIndex(dto.getPageIndex())
                        .pageSize(dto.getPageSize())
                        .lastValues(dto.getLastValues())
                        .currentAccountId(currentLoggedInAccountId)
                        .searchKeyword(dto.getSearchKeyword())
                        .accountId(dto.getAccountId())
                        .statuses(Sets.newHashSet(ArticleStatus.VIEWABLE))
                        .isOpenPinyin(dto.getIsOpenPinyin())
                        .isOpenSynonym(dto.getIsOpenSynonym())
                        .isOpenCrossLanguage(dto.getIsOpenCrossLanguage())
                        .build());

        // 热搜
        if (StringUtils.isNoneBlank(dto.getSearchKeyword())) {
            articleHotSearchCommandService.hotSearch(ArticleSearchCommand.builder().keywords(dto.getSearchKeyword()).build());
        }

        return pagination;
    }

    @Operation(summary = "账户的文章分页搜索")
    @GetMapping("/account")
    public Pagination<ArticleRepresentation> accountPageQuery(@ModelAttribute ArticleAccountPageQueryInputDTO dto) {
        Long currentLoggedInAccountId = super.getCurrentLoggedInAccountId();

        Pagination<ArticleRepresentation> pagination = articleQueryService.pageQuery(
                ArticlePageQuery.builder()
                        .pageIndex(dto.getPageIndex())
                        .pageSize(dto.getPageSize())
                        .lastValues(dto.getLastValues())
                        .currentAccountId(currentLoggedInAccountId)
                        .searchKeyword(dto.getSearchKeyword())
                        .accountId(currentLoggedInAccountId)
                        .statuses(dto.getStatuses())
                        .isOpenPinyin(dto.getIsOpenPinyin())
                        .isOpenSynonym(dto.getIsOpenSynonym())
                        .isOpenCrossLanguage(dto.getIsOpenCrossLanguage())
                        .build());

        return pagination;
    }

    @Operation(summary = "查询")
    @GetMapping("/{articleId}")
    public ArticleRepresentation query(@PathVariable(name = "articleId") Long articleId) {
        Long currentLoggedInAccountId = super.isLoggedIn() ? super.getCurrentLoggedInAccountId() : null;
        articleHotCommandService.access(ArticleAccessCommand.builder().articleId(articleId).accountId(currentLoggedInAccountId).build());

        return articleQueryService.query(ArticleQuery.builder().currentAccountId(currentLoggedInAccountId).articleId(articleId).build());
    }

    @Operation(summary = "头条分页查询")
    @GetMapping("/headlines")
    public Pagination<ArticleRepresentation> headlinesPageQuery(@ModelAttribute PageQueryInputDTO dto) {
        boolean isLoggedIn = super.isLoggedIn();
        Long currentLoggedInAccountId = isLoggedIn ? super.getCurrentLoggedInAccountId() : null;

        Pagination<ArticleRepresentation> pagination = articleHotQueryService.pageQuery(
                ArticleHotPageQuery.builder()
                        .pageIndex(dto.getPageIndex())
                        .pageSize(dto.getPageSize())
                        .lastValues(dto.getLastValues())
                        .currentAccountId(currentLoggedInAccountId)
                        .build());

        return pagination;
    }

    @Operation(summary = "热搜分页查询")
    @GetMapping("/hot/search")
    public Pagination<String> hotSearchPageQuery(@ModelAttribute PageQueryInputDTO dto) {
        return articleHotSearchQueryService.pageQuery(ArticleHotSearchPageQuery.builder().pageIndex(dto.getPageIndex()).pageSize(dto.getPageSize()).lastValues(dto.getLastValues()).build());
    }


}
