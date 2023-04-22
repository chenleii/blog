import {
  LikeFilled,
  LikeOutlined,
  MessageOutlined,
  ShareAltOutlined,
  WarningFilled,
  WarningOutlined
} from '@ant-design/icons';
import {PageContainer} from '@ant-design/pro-components';
import { Comment } from '@ant-design/compatible';

import {
  Button,
  Card,
  Divider,
  FloatButton,
  Form,
  Input,
  List,
  message,
  Popover,
  Space,
  Tooltip,
  Typography,
} from 'antd';

import Avatar from 'antd/lib/avatar/avatar';
import React, {useEffect, useState} from 'react';
import {history, useAccess, useIntl, useModel, useParams} from "@@/exports";
import api from "@/services/api";
import moment from 'moment';
import TextArea from 'antd/lib/input/TextArea';
import Markdown from '@/components/Markdown';
import copy from "copy-to-clipboard";

const Article: React.FC = () => {
  const {initialState} = useModel('@@initialState');
  const {loggedInAccount} = initialState || {};
  const [loading, setLoading] = useState<boolean>(false);
  const params = useParams();
  const [article, setArticle] = useState<API.ArticleRepresentation>({});
  let intl = useIntl();
  let access = useAccess();


  let likeRun = async (item: any) => {
    let res = await api.articleApi.like({articleId: item.id});
    let tempArticle = {
      ...article,
      isLiked: !article.isLiked,
      likedNumber: res,
    }
    setArticle(tempArticle);
  };

  let reportRun = async (item: any, form: any) => {
    let res = await api.articleApi.report({articleId: item.id, remark: form.remark});
    let tempArticle = {
      ...article,
      isReported: true,
      reportedNumber: res,
    }

    setArticle(tempArticle);
  };

  let commentRun = async (item: any, form: any) => {
    await api.articleApi.comment({
      articleId: item.id,
      content: form.commentContent
    })

    await queryArticle();
  };

  let replySubCommentRun = async (article: any, comment: any, subComment: any, form: any) => {
    await api.articleApi.replySubComment({
      articleId: article?.id,
      commentId: comment?.id,
      replyCommentId: subComment.id,
      content: form.commentContent
    })
    await queryArticle();
  }

  let shareRun = async () => {
    copy(window.location.href);
    message.success(
      intl.formatMessage({id: "pages.ArticleDetails.share.message",}));
  }

  let queryArticle = async () => {
    try {
      let articleId = params.articleId;
      // @ts-ignore
      let article = await api.articleApi.query({articleId: articleId});

      article.comments = article?.comments?.map((item: any) => {
        item?.subComments?.sort((v1: any, v2: any) => (v2?.commentedAt || 0) - (v1?.commentedAt || 0))
        return item;
      }).sort((v1: any, v2: any) => {
        return (v2?.commentedAt || 0) - (v1?.commentedAt || 0)
      });

      setArticle({...article});
    } finally {
      setLoading(false);
    }
  }


  useEffect(() => {
    setLoading(true);
    queryArticle();
  }, [params]);

  useEffect(() => {
    // 获取当前地址栏中的锚点 eg:#评论ID
    const hash = window.location.hash;
    if (hash) {
      const element = document.getElementById(hash);
      if (element) {
        // 滚动到锚点位置
        element.scrollIntoView();
      }
    }
  }, [loading]);


  return (
    (<PageContainer
      header={{
        title: '',
      }}
      loading={loading}
    >
      <Card
        bordered={false}
        style={{marginBottom: 24}}
        loading={loading}
        extra={
          access.canArticleUpdate(article)
            ? <a onClick={() => history.push(`/article/${article?.id}/editor`)}>
              {intl.formatMessage({
                id: 'pages.ArticleDetails.update',
                defaultMessage: '编辑',
              })}
            </a>
            : undefined
        }
      >

        <Space direction={"vertical"} align={'center'} style={{width: '100%'}}
         onClick={() => history.push(`/account/${article.account.id}`)}
         >
          <Avatar src={article?.account?.avatar} alt="" size={128}/>
          <Typography.Title level={3}>
            {article?.account?.name}
          </Typography.Title>
          <Typography.Paragraph>
            {article?.account?.introduction}
          </Typography.Paragraph>
        </Space>

        <Typography.Title ellipsis={{rows: 1}} level={1}>
          <Markdown content={article?.title}/>
        </Typography.Title>
        <Typography.Paragraph>
          <Markdown content={article?.customContent || ''}/>
        </Typography.Paragraph>

        <Space split={<Divider type="vertical"/>}>
          <Space onClick={() => likeRun(article)}>
            {article?.isLiked ? <LikeFilled/> : <LikeOutlined/>}
            <span>{article?.likedNumber}</span>
          </Space>
          <Space>
            <Popover
              title={intl.formatMessage({
                id: "pages.ArticleDetails.report.title",
                defaultMessage: "确认举报吗？",
              })}
              content={
                <Form
                  onFinish={(value) => reportRun(article, value)}
                  layout="vertical">
                  <Form.Item
                    label={intl.formatMessage({
                      id: "pages.ArticleDetails.report.remark",
                      defaultMessage: "举报原因",
                    })}
                    name="remark"
                  >
                    <Input
                      placeholder={intl.formatMessage({
                        id: "pages.ArticleDetails.report.remark",
                        defaultMessage: "举报原因",
                      })}
                    />
                  </Form.Item>
                  <Form.Item>
                    <Button type="primary" htmlType="submit">
                      {intl.formatMessage({
                        id: "pages.ArticleDetails.report.submitButton",
                        defaultMessage: "举报",
                      })}
                    </Button>
                  </Form.Item>
                </Form>
              }
              trigger="click">
              {article?.isReported ? <WarningFilled/> : <WarningOutlined/>}
            </Popover>
            {article?.reportedNumber}
          </Space>
          <Space>
            <MessageOutlined/>
            {article?.comments ? article?.comments.length : 0}
          </Space>
          <Space onClick={() => shareRun()}>
            <ShareAltOutlined/>
            {intl.formatMessage({
              id: "pages.ArticleDetails.share.title",
              defaultMessage: "分享",
            })}
          </Space>
        </Space>
      </Card>
      <Card
        title={intl.formatMessage({
          id: "pages.ArticleDetails.comment.title",
          defaultMessage: "评论",
        })}
        bordered={false} style={{marginBottom: 24}} loading={loading}>
        <Comment
          avatar={<Avatar src={loggedInAccount?.avatar} alt={loggedInAccount?.name}/>}
          content={
            <Form
              onFinish={(value) => commentRun(article, value)}
              layout="vertical">
              <Form.Item name="commentContent">
                <TextArea rows={4}/>
              </Form.Item>
              <Form.Item>
                <Button htmlType="submit" type="primary">
                  {intl.formatMessage({
                    id: "pages.ArticleDetails.comment.submitButton",
                    defaultMessage: "提交评论",
                  })}
                </Button>
              </Form.Item>
            </Form>
          }
        />
        <List
          className="comment-list"
          // header={`${article?.comments?.length} replies`}
          itemLayout="horizontal"
          dataSource={article?.comments}
          renderItem={comment => (
            <li id={"#" + comment.id}>
              <Comment
                actions={[
                  <Popover
                    title={intl.formatMessage({
                      id: "pages.ArticleDetails.replyComment.title",
                      defaultMessage: "回复",
                    })}
                    content={
                      <Form
                        onFinish={(value) => replySubCommentRun(article, comment, {}, value)}
                        layout="vertical">
                        <Form.Item
                          label={intl.formatMessage({
                            id: "pages.ArticleDetails.replyComment.content",
                            defaultMessage: "回复内容",
                          })}
                          name="commentContent"
                        >
                          <TextArea rows={4}/>
                        </Form.Item>
                        <Form.Item>
                          <Button type="primary" htmlType="submit">
                            {intl.formatMessage({
                              id: "pages.ArticleDetails.replyComment.submitButton",
                              defaultMessage: "回复",
                            })}
                          </Button>
                        </Form.Item>
                      </Form>
                    }
                    trigger="click">
                    <span key="comment-nested-reply-to">
                      {intl.formatMessage({
                        id: "pages.ArticleDetails.replyComment.title",
                        defaultMessage: "回复",
                      })}
                    </span>
                  </Popover>
                ]}
                author={<a onClick={() => history.push(`/account/${comment?.account?.id}`)}>{comment?.account?.name}</a>}
                avatar={<Avatar onClick={() => history.push(`/account/${comment?.account?.id}`)} src={comment?.account?.avatar} alt=""/>}
                datetime={
                  <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
                    <span>{moment(comment?.commentedAt, undefined, intl.locale).fromNow()}</span>
                  </Tooltip>
                }
                content={
                  <p>
                    {comment?.content}
                  </p>
                }
              >
                {
                  (comment?.subComments?.length || 0) > 0
                    ? <List
                      className="comment-list"
                      header={`${comment?.subComments?.length} replies`}
                      itemLayout="horizontal"
                      dataSource={comment?.subComments}
                      renderItem={subComment => (
                        <li id={"#" + subComment.id}>
                          <Comment
                            actions={[
                              <Popover
                                title={intl.formatMessage({
                                  id: "pages.ArticleDetails.replyComment.title",
                                  defaultMessage: "回复",
                                })}
                                content={
                                  <Form
                                    onFinish={(value) => replySubCommentRun(article, comment, subComment, value)}
                                    layout="vertical">
                                    <Form.Item
                                      label={intl.formatMessage({
                                        id: "pages.ArticleDetails.replyComment.content",
                                        defaultMessage: "回复内容",
                                      })}
                                      name="commentContent"
                                    >
                                      <TextArea rows={4}/>
                                    </Form.Item>
                                    <Form.Item>
                                      <Button type="primary" htmlType="submit">
                                        {intl.formatMessage({
                                          id: "pages.ArticleDetails.replyComment.submitButton",
                                          defaultMessage: "回复",
                                        })}
                                      </Button>
                                    </Form.Item>
                                  </Form>
                                }
                                trigger="click">
                                <span key="comment-nested-reply-to">
                                  {intl.formatMessage({
                                    id: "pages.ArticleDetails.replyComment.title",
                                    defaultMessage: "回复",
                                  })}
                                </span>
                              </Popover>
                            ]}
                            author={<a onClick={() => history.push(`/account/${subComment?.account?.id}`)}>{subComment?.account?.name}</a>}
                            avatar={<Avatar onClick={() => history.push(`/account/${subComment?.account?.id}`)} src={subComment?.account?.avatar} alt=""/>}
                            datetime={
                              <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
                                <span>{moment(subComment?.commentedAt, undefined, intl.locale).fromNow()}</span>
                              </Tooltip>
                            }
                            content={
                              <p>
                                {subComment?.content}
                              </p>
                            }
                          >

                          </Comment>
                        </li>
                      )}
                    />
                    : <></>
                }
              </Comment>
            </li>
          )}

        />
      </Card>
      <FloatButton.BackTop/>
    </PageContainer>)
  );
};

export default Article;
