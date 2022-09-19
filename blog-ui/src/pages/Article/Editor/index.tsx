import {PageContainer} from '@ant-design/pro-components';
import {BackTop, Button, Card, Checkbox, Form, Input, Select,} from 'antd';
import React, {useEffect, useState} from 'react';
import api from '@/services/api';
import {history, useIntl, useParams} from "@@/exports";
import MarkdownEditor from '@/components/MarkdownEditor';

const CreateArticle: React.FC = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const params = useParams();
  const [article, setArticle] = useState<API.ArticleResult>({});
  let intl = useIntl();


  let updateArticle = async (form: any) => {
    setLoading(true);

    try {
      if (!article?.id) {
        // 新增
        let newArticleId = await api.articleApi.save(form);
        history.push(`/article/${newArticleId}`);
      } else {
        // 更新
        let newArticleId = await api.articleApi.update({articleId: article.id, ...form});
        history.push(`/article/${newArticleId}`);
      }
    } finally {
      setLoading(false);
    }
  };

  let loadArticle = async () => {
    try {
      let articleId = params.articleId;
      if (!articleId) {
        // 新增
        setArticle({});
      } else {
        // 更新
        let article = await api.articleApi.query({articleId: articleId});
        setArticle(article);
      }
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    setLoading(true);
    loadArticle();
  }, [params]);

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
          initialValues={{...article, isPublish: true}}
          onFinish={(values) => updateArticle(values)}
          onValuesChange={(changedValues, allValues) => {
            let tempArticle = {
              ...article,
              ...allValues,
            }
            setArticle(tempArticle);
          }}
          autoComplete="off"
        >
          <Form.Item
            label={intl.formatMessage({
              id: 'pages.ArticleEditor.title',
              defaultMessage: '标题',
            })}
            name="title"
            rules={[{required: true,}]}
          >
            <Input/>
          </Form.Item>
          <Form.Item
            label={intl.formatMessage({
              id: 'pages.ArticleEditor.tags',
              defaultMessage: '标签',
            })}
            name="tags"
            tooltip={intl.formatMessage({
              id: 'pages.ArticleEditor.tags.tooltip',
              defaultMessage: '自定义输入任意标签',
            })}
            rules={[{required: true,}]}
          >
            <Select mode="tags" tokenSeparators={[',']}>
            </Select>
          </Form.Item>
          <Form.Item
            label={intl.formatMessage({
              id: 'pages.ArticleEditor.content',
              defaultMessage: '内容',
            })}
            name="content"
            rules={[{required: true,}]}>
            <MarkdownEditor
              content={article.content || ''}
            />
          </Form.Item>

          <Form.Item name="isPublish" hidden={article.id !== undefined} valuePropName="checked"
                     wrapperCol={{offset: 0, span: 24}}>
            <Checkbox>
              {intl.formatMessage({
                id: 'pages.ArticleEditor.isPublish',
                defaultMessage: '是否发布',
              })}
            </Checkbox>
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

export default CreateArticle;
