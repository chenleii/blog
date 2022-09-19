import {LikeFilled, LikeOutlined, MessageOutlined, WarningFilled, WarningOutlined} from '@ant-design/icons';
import {PageContainer} from '@ant-design/pro-components';
import {BackTop, Button, Card, Divider, Form, Input, List, Popover, Skeleton, Space, Typography,} from 'antd';
import Avatar from 'antd/lib/avatar/avatar';
import React, {useEffect, useState} from 'react';
import {history, useIntl, useLocation, useSearchParams} from "@@/exports";
import InfiniteScroll from 'react-infinite-scroll-component';
import api from "@/services/api";
import Markdown from "@/components/Markdown";

const ArticleList: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(false);
  let location = useLocation();
  const [searchParams] = useSearchParams();
  let intl = useIntl();
  const [articlePageQueryInputDTO, setArticlePageQueryInputDTO] = useState<API.pageQueryParams>({
    pageIndex: 1,
    pageSize: 10,
    searchKeyword: searchParams.get("searchKeyword") || '',
  });
  const [page, setPage] = useState<API.PaginationArticleResult>({
    pageIndex: 0,
    pageSize: 10,
    list: [],
    total: 0,
  });

  // 区分是不是热门文章查询
  let isHotQuery = location.pathname.includes("/headlines");

  let likeRun = async (item: any) => {
    let res = await api.articleApi.like({articleId: item.id});
    (page.list || [])
      .filter(it => {
        return it?.id === item.id
      })
      .map(it => {
        it.isLiked = !item.isLiked;
        it.likedNumber = res;
        return it;
      });

    let tempPage = {
      ...page,
      list: page.list,
    };
    setPage(tempPage);

  };

  let reportRun = async (item: any, form: any) => {
    let res = await api.articleApi.report({articleId: item.id, remark: form.remark});
    (page.list || [])
      .filter(it => {
        return it?.id === item.id
      })
      .map(it => {
        it.isReported = true;
        it.reportedNumber = res;
        return it;
      });

    let tempPage = {
      ...page,
      list: page.list,
    };
    setPage(tempPage);

  }

  let loadMore = async () => {
    setLoading(true);

    try {
      let res: API.PaginationArticleResult = {};
      if (isHotQuery) {
        res = await api.articleApi.headlinesPageQuery(articlePageQueryInputDTO);
      } else {
        res = await api.articleApi.pageQuery(articlePageQueryInputDTO);
      }
      articlePageQueryInputDTO.pageIndex = (articlePageQueryInputDTO.pageIndex || 0) + 1;
      articlePageQueryInputDTO.lastValues = res.lastValues;
      setArticlePageQueryInputDTO(articlePageQueryInputDTO);

      let tempPage = {
        pageIndex: res.pageIndex,
        pageSize: res.pageSize,
        list: [...page.list || [], ...res.list || []],
        total: res.total,
        lastValues: res.lastValues,
      };
      await setPage(tempPage);

    } finally {

      setLoading(false);
    }
  };

  let refresh = async () => {
    setLoading(true);

    // 重置参数
    articlePageQueryInputDTO.pageIndex = 1;
    articlePageQueryInputDTO.lastValues = [];
    articlePageQueryInputDTO.searchKeyword = searchParams.get("searchKeyword") || '';
    setArticlePageQueryInputDTO(articlePageQueryInputDTO);

    // 重置结果
    page.pageIndex = 1;
    page.list = [];
    page.lastValues = [];
    await setPage(page);

    await loadMore();
  };

  useEffect(() => {
    refresh();

  }, [searchParams]);

  return (
    <PageContainer
      header={{
        title: '',
      }}
    >
      <Card bordered={false} style={{marginBottom: 24}}>
        <InfiniteScroll
          dataLength={page?.list?.length || 0}
          next={loadMore}
          hasMore={(page?.list?.length || 0) < (page?.total || 0)}

          pullDownToRefresh={true}
          refreshFunction={refresh}

          loader={<Skeleton avatar paragraph={{rows: 1}} active/>}
          endMessage={<Divider plain>{intl.formatMessage({id: 'pages.ArticleList.endMessage'})}</Divider>}
        >
          <List
            itemLayout="vertical"
            size="default"
            loading={loading}
            dataSource={page?.list}
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
                  ]
                }
                extra=
                  {item?.cover
                    ? <img
                      width={272}
                      alt="cover"
                      src={item?.cover}
                    />
                    : null
                  }
              >
                <List.Item.Meta
                  avatar={<Avatar src={item?.account?.avatar} size={'large'}/>}
                  title={<a href={item?.account?.avatar}>{item?.account?.name}</a>}
                  description={item?.account?.introduction}
                />

                <div onClick={() => history.push(`/article/${item.id}`)}>
                  <Typography.Title ellipsis={{rows: 1}} level={1}>
                    <Markdown content={item?.title}/>
                  </Typography.Title>
                  <Typography.Paragraph ellipsis={{rows: 5}}>
                    <Markdown content={item?.customContent || ''}/>
                  </Typography.Paragraph>
                </div>
              </List.Item>
            )
            }
          />
        </InfiniteScroll>
      </Card>
      <BackTop/>

    </PageContainer>
  );
};

export default ArticleList;
