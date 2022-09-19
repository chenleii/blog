import {PageContainer} from '@ant-design/pro-components';
import {BackTop, Button, Card, Input, List, Space, Typography,} from 'antd';
import Avatar from 'antd/lib/avatar/avatar';
import React, {useEffect, useState} from 'react';
import {history, useIntl, useModel, useParams} from "@@/exports";
import api from '@/services/api';
import Markdown from "@/components/Markdown";
import {
  LikeFilled,
  LikeOutlined,
  MessageOutlined,
  SearchOutlined,
  WarningFilled,
  WarningOutlined
} from '@ant-design/icons';
import {InitialState} from "@/app";


const AccountCenter: React.FC = () => {
  const {initialState} = useModel('@@initialState');
  const {loggedInAccount} = initialState as InitialState || {};
  const [loading, setLoading] = useState<boolean>(false);
  const [articleListLoading, setArticleListLoading] = useState<boolean>(false);
  const params = useParams();
  const [account, setAccount] = useState<API.LoggedInAccount>({});
  const [accountArticlePage, setAccountArticlePage] = useState<API.PaginationArticleResult>({});
  let [accountArticlePageQueryInputDTO, setAccountArticlePageQueryInputDTO] = useState<API.accountPageQueryParams>({
    pageIndex: 1,
    pageSize: 10,
  });
  let intl = useIntl();

  let loadMoreAccountArticlePage = async () => {
    setArticleListLoading(true);
    try {
      let accountArticlePageRes = await api.articleApi.accountPageQuery(accountArticlePageQueryInputDTO);

      accountArticlePageQueryInputDTO.pageIndex = (accountArticlePageQueryInputDTO?.pageIndex || 0) + 1;
      accountArticlePageQueryInputDTO.lastValues = accountArticlePageRes?.lastValues;
      setAccountArticlePageQueryInputDTO(accountArticlePageQueryInputDTO)

      let tempPage = {
        pageIndex: accountArticlePageRes.pageIndex,
        pageSize: accountArticlePageRes.pageSize,
        list: [...accountArticlePage.list || [], ...accountArticlePageRes.list || []],
        total: accountArticlePageRes.total,
        lastValues: accountArticlePageRes.lastValues,
      };
      setAccountArticlePage(tempPage);
    } finally {
      setLoading(false);
      setArticleListLoading(false);
    }
  }

  let restLoadMoreAccountArticlePage = async (searchValue: any) => {
    accountArticlePageQueryInputDTO.pageIndex = 1;
    accountArticlePageQueryInputDTO.lastValues = [];
    accountArticlePageQueryInputDTO.searchKeyword = searchValue;
    setAccountArticlePageQueryInputDTO(accountArticlePageQueryInputDTO)

    accountArticlePage.pageIndex = 1;
    accountArticlePage.list = [];
    accountArticlePage.lastValues = [];
    setAccountArticlePage(accountArticlePage);

    await loadMoreAccountArticlePage();
  }


  useEffect(() => {
    setLoading(true);
    setArticleListLoading(true);

    setAccount(loggedInAccount || {});
    loadMoreAccountArticlePage();
  }, [params]);

  return (
    <PageContainer
      header={{
        title: '',
      }}
      loading={loading}
    >
      <Card bordered={false} style={{marginBottom: 24}} loading={loading} title={''}>
        <Space direction={"vertical"} align={'center'} style={{width: '100%'}}>
          <Avatar src={account?.avatar} alt="" size={128}/>
          <Typography.Title level={3}>
            {account?.name}
          </Typography.Title>
          <Typography.Paragraph>
            {account?.introduction}
          </Typography.Paragraph>
        </Space>
      </Card>

      <Card
        title={
          intl.formatMessage({
            id: 'pages.account.center.articleList.title',
            defaultMessage: '文章列表',
          })}
        extra={
          <Input
            size="middle"
            allowClear={true}
            suffix={<SearchOutlined/>}
            onKeyDown={async (e) => {
              if (e.key === 'Enter') {
                await restLoadMoreAccountArticlePage(e?.target['value']);
              }
            }}/>
        }
      >
        <List
          itemLayout="vertical"
          size="default"
          loading={loading || articleListLoading}
          dataSource={accountArticlePage?.list}
          loadMore={
            <div style={{textAlign: 'center', marginTop: 12,}}>
              <Button onClick={loadMoreAccountArticlePage}>
                {intl.formatMessage({
                  id: 'pages.account.center.articleList.loadMore',
                  defaultMessage: '加载更多',
                })}
              </Button>
            </div>
          }
          renderItem={item => (
            <List.Item
              key={item?.id}
              actions={
                [
                  <Space>
                    {item?.isLiked ? <LikeFilled/> : <LikeOutlined/>}
                    {item?.likedNumber}
                  </Space>,
                  <Space>
                    {item?.isReported ? <WarningFilled/> : <WarningOutlined/>}
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
                avatar={<Avatar src={item?.account?.avatar}/>}
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
      </Card>
      <BackTop/>
    </PageContainer>
  );
};

export default AccountCenter;
