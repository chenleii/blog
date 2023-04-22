import {PageContainer} from '@ant-design/pro-components';
import {Avatar, BackTop, Button, Card, Form, Input, Space,} from 'antd';
import React, {useEffect, useState} from 'react';
import {useIntl, useModel} from "@@/exports";
import api from '@/services/api';


const AccountUpdate: React.FC = () => {
  const {initialState, setInitialState} = useModel('@@initialState');
  const [loading, setLoading] = useState<boolean>(false);
  const [account, setAccount] = useState<API.LoggedInAccount>({});
  let intl = useIntl();


  let updateAccount = async (form: any) => {
    setLoading(true);

    try {
      await api.accountApi.update1({...form});

      let loggedInAccount: API.LoggedInAccount = await api.accountApi.getLoggedInAccount();
      setInitialState({
        ...initialState,
        loggedInAccount: loggedInAccount,
      })
    } finally {
      setLoading(false);
    }
  };

  let loadAccount = async () => {
    try {
      let account = await api.accountApi.getLoggedInAccount();
      setAccount(account);

    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    setLoading(true);
    loadAccount();
  }, []);

  return (
    <PageContainer
      header={{
        title: '',
      }}
    >
      <Card bordered={false} style={{marginBottom: 24}}
            loading={loading}>
        <Form
          name="basic"
          layout={'vertical'}
          labelCol={{span: 24}}
          wrapperCol={{span: 24}}
          initialValues={{...account,}}
          onFinish={(values) => updateAccount(values)}
          onValuesChange={(changedValues, allValues) => {
            let tempAccount = {
              ...account,
              ...allValues,
            }
            setAccount(tempAccount);
          }}
          autoComplete="off"
        >
          <Form.Item
            label={intl.formatMessage({
              id: 'pages.account.update.avatar',
              defaultMessage: '头像',
            })}
            name="avatar"
            rules={[{required: true,}]}
          >
            <Space direction={"vertical"} align={'center'} style={{width: '100%'}}>
              <Avatar src={account?.avatar} alt="" size={128}/>
              <Input value={account?.avatar}/>
            </Space>
          </Form.Item>
          <Form.Item
            label={intl.formatMessage({
              id: 'pages.account.update.name',
              defaultMessage: '昵称',
            })}
            name="name"
            rules={[{required: true,}]}
          >
            <Input/>
          </Form.Item>
          <Form.Item
            label={intl.formatMessage({
              id: 'pages.account.update.introduction',
              defaultMessage: '个人简介',
            })}
            name="introduction"
            rules={[{required: true,}]}
          >
            <Input/>
          </Form.Item>

          <Form.Item wrapperCol={{offset: 0, span: 24}}>
            <Button type="primary" htmlType="submit" loading={loading}>
              {intl.formatMessage({
                id: 'pages.ArticleEditor.submitButton',
                defaultMessage: '保存',
              })}
            </Button>
          </Form.Item>
        </Form>
      </Card>

      <BackTop/>
    </PageContainer>
  );
};

export default AccountUpdate;
