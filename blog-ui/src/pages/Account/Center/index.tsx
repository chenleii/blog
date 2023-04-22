import {PageContainer} from '@ant-design/pro-components';
import {Card, FloatButton, Input, Space, Typography,} from 'antd';
import Avatar from 'antd/lib/avatar/avatar';
import React, {useEffect, useState} from 'react';
import {useIntl, useLocation, useModel, useParams} from "@@/exports";
import api from '@/services/api';
import {InitialState} from "@/app";
import ArticleList from "@/components/ArticleList";


const AccountCenter: React.FC = () => {
  const {initialState} = useModel('@@initialState');
  const {loggedInAccount} = initialState as InitialState || {};
  const [accountLoading, setAccountLoading] = useState<boolean>(false);
  const [searchValue, setSearchValue] = useState<string>('');
  const params = useParams();
  const [account, setAccount] = useState<API.AccountRepresentation>({});
  const [accountArticlePageQueryInputDTO, setAccountArticlePageQueryInputDTO] = useState<API.accountPageQueryParams>({
    pageIndex: 1,
    pageSize: 10,
    accountId: params?.accountId as string || loggedInAccount?.id + '',
    searchKeyword: searchValue,
  });
  const intl = useIntl();
  const location = useLocation();

  // 区分是不是个人中心页
  const isAccountCenter = location.pathname.includes("/center");

  let loadAccount = async () => {
    setAccountLoading(true);
    try {
      if (isAccountCenter) {
        setAccount({...loggedInAccount});
        return;
      }

      let account = await api.accountApi.query({accountId: params.accountId as string});
      setAccount(account);
    } finally {
      setAccountLoading(false);
    }
  }

  const pageQuery = async (pageIndex: number) => {

    let accountArticlePageRes = await api.articleApi.pageQuery({
      ...accountArticlePageQueryInputDTO,
      searchKeyword: searchValue,
      pageIndex: pageIndex,
    });

    accountArticlePageQueryInputDTO.pageIndex = (accountArticlePageQueryInputDTO?.pageIndex || 0) + 1;
    accountArticlePageQueryInputDTO.lastValues = accountArticlePageRes?.lastValues;
    setAccountArticlePageQueryInputDTO(accountArticlePageQueryInputDTO)
    return accountArticlePageRes;
  };

  useEffect(() => {
    loadAccount();
  }, [params]);

  return (
    (<PageContainer
      header={{
        title: '',
      }}
    >
      <Card bordered={false} style={{marginBottom: 24}} loading={accountLoading} title={''}>
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
          <Input.Search
            size="middle"
            allowClear={true}
            onSearch={async (value, event) => setSearchValue(value)}
          />
        }
      >
        <ArticleList pageQuery={pageQuery} forceUpdate={searchValue}/>
      </Card>
      <FloatButton.BackTop/>
    </PageContainer>)
  );
};

export default AccountCenter;
