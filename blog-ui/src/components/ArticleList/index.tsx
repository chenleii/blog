import {
  LikeFilled,
  LikeOutlined,
  MessageOutlined,
  ShareAltOutlined,
  WarningFilled,
  WarningOutlined
} from '@ant-design/icons';
import {Button, Col, Divider, Form, Input, List, message, Popover, Row, Skeleton, Space, Typography,} from 'antd';
import Avatar from 'antd/lib/avatar/avatar';
import React, {useEffect, useState} from 'react';
import {history, useIntl} from "@@/exports";
import InfiniteScroll from 'react-infinite-scroll-component';
import api from "@/services/api";
import Markdown from "@/components/Markdown";
import copy from "copy-to-clipboard";


export type ArticleListProps = {
  forceUpdate?:string;
  pageQuery: (pageIndex: number) => Promise<API.PaginationArticleRepresentation>;
};

const ArticleList: React.FC<ArticleListProps> = (props: ArticleListProps) => {
  const {
    forceUpdate,
    pageQuery,
  } = props;
  const intl = useIntl();
  const [loading, setLoading] = useState<boolean>(false);
  const [pageIndex, setPageIndex] = useState<number>(0);
  const [articlePage, setArticlePage] = useState<API.PaginationArticleRepresentation>({});

  const likeRun = async (item: any) => {
    const res = await api.articleApi.like({articleId: item.id});

    setArticlePage({
      ...articlePage,
      list: articlePage?.list?.map(it => {
        if (it?.id === item.id) {
          it.isLiked = !item.isLiked;
          it.likedNumber = res;
        }
        return it;
      }),
    });
  }

  const reportRun = async (item: any, form: any) => {
    const res = await api.articleApi.report({articleId: item.id, remark: form.remark});

    setArticlePage({
      ...articlePage,
      list: articlePage?.list?.map(it => {
        if (it?.id === item.id) {
          it.isReported = true;
          it.reportedNumber = res;
        }
        return it;
      }),
    });
  }
  let shareRun = async () => {
    copy(window.location.href);
    message.success(
      intl.formatMessage({id: "pages.ArticleDetails.share.message",}));
  }

  const loadMore = async (isReset = false) => {
    setLoading(true);

    try {
      let currentPageIndex = pageIndex + 1;
      if (isReset) {
        // 重置数据
        currentPageIndex = 1;
        articlePage.list = [];
      }
      setPageIndex(currentPageIndex);
      let res: API.PaginationArticleRepresentation = await pageQuery(currentPageIndex);

      await setArticlePage({
        pageIndex: res.pageIndex,
        pageSize: res.pageSize,
        list: [...(articlePage.list || []), ...(res.list || [])],
        total: res.total,
        lastValues: res.lastValues,
      });

    } finally {
      setLoading(false);
    }
  };

  const refresh = async () => {
    await loadMore(true);
  };

  useEffect(() => {
    refresh();
  }, [forceUpdate]);

  return (
    <InfiniteScroll
      dataLength={articlePage?.list?.length || 0}
      next={loadMore}
      hasMore={(articlePage?.list?.length || 0) < (articlePage?.total || 0)}

      pullDownToRefresh={true}
      refreshFunction={refresh}

      loader={<Skeleton avatar paragraph={{rows: 1}} active/>}
      endMessage={<Divider plain>{intl.formatMessage({id: 'component.endMessage'})}</Divider>}
    >
      <List<API.ArticleRepresentation>
        itemLayout="vertical"
        size="default"
        // loading={loading}
        dataSource={articlePage?.list}
        renderItem={item => (
          <List.Item
            key={item?.id}
            actions={
              [
                <Space onClick={() => likeRun(item)}>
                  {item?.isLiked ? <LikeFilled/> : <LikeOutlined/>}
                  {item?.likedNumber}
                </Space>,
                <Space>
                  <Popover
                    title={intl.formatMessage({
                      id: "pages.ArticleDetails.report.title",
                      defaultMessage: "确认举报吗？",
                    })}
                    content={
                      <Form
                        onFinish={(value) => reportRun(item, value)}
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
                    {item?.isReported ? <WarningFilled/> : <WarningOutlined/>}
                  </Popover>
                  {item?.reportedNumber}
                </Space>,
                <Space>
                  <MessageOutlined/>
                  {item?.comments ? item?.comments.length : 0}
                </Space>,
                <Space onClick={() => shareRun()}>
                  <ShareAltOutlined/>
                  {intl.formatMessage({
                    id: "pages.ArticleDetails.share.title",
                    defaultMessage: "分享",
                  })}
                </Space>,
              ]
            }
            extra=
              {item?.cover
                ?
                // 展示它容易内容溢出，先不展示了。
                // <img
                //   width={'100%'}
                //   alt="cover"
                //   src={item?.cover}
                // />
                null
                : null
              }
          >
            <List.Item.Meta
              avatar={<Avatar src={item?.account?.avatar} size={'large'}/>}
              title={item?.account?.name}
              description={item?.account?.introduction}
              onClick={() => history.push(`/account/${item?.account?.id}`)}
            />

            <Typography onClick={() => history.push(`/article/${item.id}`)}>
              <Typography.Title ellipsis={{rows: 1, tooltip: item?.title}} level={1}  style={{marginTop:0}}>
                {item?.title}
              </Typography.Title>
              <Row>
                <Col lg={item?.cover ? 16 : 24} xs={24}>
                  <Typography.Paragraph ellipsis={{rows: 5}} style={{maxHeight: '200px'}}>
                    <Markdown content={item?.customContent || ''}/>
                  </Typography.Paragraph>
                </Col>
                <Col lg={item?.cover ? 8 : 0} xs={0}>
                  <img
                    width={'100%'}
                    style={{paddingLeft: 16}}
                    alt="cover"
                    src={item?.cover}
                    // 图片加载失败，就把图片设置为null，不展示图片。
                    onError={() => setArticlePage({
                      list: articlePage?.list?.map(i => {
                        if (i?.id === item?.id) {
                          i.cover = undefined;
                        }
                        return i;
                      }),
                      ...articlePage
                    })}
                  />
                </Col>
              </Row>
            </Typography>

          </List.Item>
        )
        }
      />
    </InfiniteScroll>
  );
};

export default ArticleList;
