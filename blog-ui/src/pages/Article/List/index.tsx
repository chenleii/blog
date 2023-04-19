import {LikeFilled, LikeOutlined, MessageOutlined, WarningFilled, WarningOutlined} from '@ant-design/icons';
import {PageContainer} from '@ant-design/pro-components';
import {
  Button,
  Card,
  Col,
  Divider,
  FloatButton,
  Form,
  Input,
  List,
  Popover,
  Row,
  Skeleton,
  Space,
  Typography,
} from 'antd';
import Avatar from 'antd/lib/avatar/avatar';
import React, {useEffect, useState} from 'react';
import {history, useIntl, useLocation, useSearchParams} from "@@/exports";
import InfiniteScroll from 'react-infinite-scroll-component';
import api from "@/services/api";
import Markdown from "@/components/Markdown";

const ArticleList: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const intl = useIntl();
  const [articlePageQueryInputDTO, setArticlePageQueryInputDTO] = useState<API.articlePageQueryParams>({
    pageIndex: 1,
    pageSize: 10,
    searchKeyword: searchParams.get("searchKeyword") || '',
  });
  const [page, setPage] = useState<API.PaginationArticleRepresentation>({
    pageIndex: 0,
    pageSize: 10,
    list: [],
    total: 0,
  });

  // 区分是不是热门文章查询
  const isHotQuery = location.pathname.includes("/headlines");

  const likeRun = async (item: any) => {
    const res = await api.articleApi.like({articleId: item.id});
    (page.list || [])
      .filter(it => {
        return it?.id === item.id
      })
      .map(it => {
        it.isLiked = !item.isLiked;
        it.likedNumber = res;
        return it;
      });

    const tempPage = {
      ...page,
      list: page.list,
    };
    setPage(tempPage);

  };

  const reportRun = async (item: any, form: any) => {
    const res = await api.articleApi.report({articleId: item.id, remark: form.remark});
    (page.list || [])
      .filter(it => {
        return it?.id === item.id
      })
      .map(it => {
        it.isReported = true;
        it.reportedNumber = res;
        return it;
      });

    const tempPage = {
      ...page,
      list: page.list,
    };
    setPage(tempPage);

  }

  const loadMore = async () => {
    setLoading(true);

    try {
      let res: API.PaginationArticleRepresentation = {};
      if (isHotQuery) {
        res = await api.articleApi.headlinesPageQuery(articlePageQueryInputDTO);
      } else {
        res = await api.articleApi.pageQuery(articlePageQueryInputDTO);
      }
      articlePageQueryInputDTO.pageIndex = (articlePageQueryInputDTO.pageIndex || 0) + 1;
      articlePageQueryInputDTO.lastValues = res.lastValues;
      setArticlePageQueryInputDTO(articlePageQueryInputDTO);

      const tempPage = {
        pageIndex: res.pageIndex,
        pageSize: res.pageSize,
        list: [...(page.list || []), ...(res.list || [])],
        total: res.total,
        lastValues: res.lastValues,
      };
      await setPage(tempPage);

    } finally {

      setLoading(false);
    }
  };

  const refresh = async () => {
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
    (<PageContainer
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
                  title={<a href={item?.account?.avatar}>{item?.account?.name}</a>}
                  description={item?.account?.introduction}
                />

                <Typography onClick={() => history.push(`/article/${item.id}`)}>
                  <Typography.Title ellipsis={{rows: 1, tooltip: item?.title}} level={1}>
                    <Markdown content={item?.title}/>
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
                        style={{paddingLeft:16}}
                        alt="cover"
                        src={item?.cover}
                        // 图片加载失败，就把图片设置为null，不展示图片。
                        onError={()=> setPage({
                        list: page.list.map(i => {
                            if(i.id === item.id){
                                i.cover = null;
                            }
                            return i;
                        }),
                        ...page})}
                      />
                    </Col>
                  </Row>
                </Typography>

              </List.Item>
            )
            }
          />
        </InfiniteScroll>
      </Card>
      <FloatButton.BackTop/>
    </PageContainer>)
  );
};

export default ArticleList;
