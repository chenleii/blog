import {PageContainer} from '@ant-design/pro-components';
import {Card, FloatButton,} from 'antd';
import React, {useEffect, useState} from 'react';
import {useLocation, useSearchParams} from "@@/exports";
import api from "@/services/api";
import ArticleList from "@/components/ArticleList";

const ArticleListPage: React.FC = () => {
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const [articlePageQueryInputDTO, setArticlePageQueryInputDTO] = useState<API.articlePageQueryParams>({
    pageIndex: 1,
    pageSize: 2,
    searchKeyword: searchParams.get("searchKeyword") || '',
  });

  // 区分最新|热门文章查询
  const isHotQuery = location.pathname.includes("/headlines");

  const pageQuery = async (pageIndex: number) => {
    let res: API.PaginationArticleRepresentation = {};
    if (isHotQuery) {
      res = await api.articleApi.headlinesPageQuery({...articlePageQueryInputDTO, pageIndex: pageIndex});
    } else {
      res = await api.articleApi.pageQuery({...articlePageQueryInputDTO, pageIndex: pageIndex});
    }
    articlePageQueryInputDTO.lastValues = res.lastValues;
    setArticlePageQueryInputDTO(articlePageQueryInputDTO);
    return res;
  };

  useEffect(() => {

  }, []);

  return (
    (<PageContainer
      header={{
        title: '',
      }}
    >
      <Card bordered={false} style={{marginBottom: 24}}>
        <ArticleList pageQuery={pageQuery} />
      </Card>
      <FloatButton.BackTop/>
    </PageContainer>)
  );
};

export default ArticleListPage;
