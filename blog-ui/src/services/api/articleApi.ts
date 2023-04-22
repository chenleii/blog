// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 分页查询 GET /api/blog/article */
export async function pageQuery(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageQuery1Params,
  options?: { [key: string]: any },
) {
  return request<API.PaginationArticleRepresentation>('/api/blog/article', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 更新 PUT /api/blog/article */
export async function update(
  body: API.ArticleUpdateAndPublishInputDTO,
  options?: { [key: string]: any },
) {
  return request<number>('/api/blog/article', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 保存 POST /api/blog/article */
export async function save(
  body: API.ArticleSaveAndPublishInputDTO,
  options?: { [key: string]: any },
) {
  return request<number>('/api/blog/article', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 查询 GET /api/blog/article/${param0} */
export async function query(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.query1Params,
  options?: { [key: string]: any },
) {
  const { articleId: param0, ...queryParams } = params;
  return request<API.ArticleRepresentation>(`/api/blog/article/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 账户的文章分页搜索 GET /api/blog/article/account */
export async function accountPageQuery(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.accountPageQueryParams,
  options?: { [key: string]: any },
) {
  return request<API.PaginationArticleRepresentation>('/api/blog/article/account', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 评论 POST /api/blog/article/comment */
export async function comment(body: API.ArticleCommentInputDTO, options?: { [key: string]: any }) {
  return request<number>('/api/blog/article/comment', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 头条分页查询 GET /api/blog/article/headlines */
export async function headlinesPageQuery(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.headlinesPageQueryParams,
  options?: { [key: string]: any },
) {
  return request<API.PaginationArticleRepresentation>('/api/blog/article/headlines', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 热搜分页查询 GET /api/blog/article/hot/search */
export async function hotSearchPageQuery(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.hotSearchPageQueryParams,
  options?: { [key: string]: any },
) {
  return request<API.PaginationString>('/api/blog/article/hot/search', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 喜欢 POST /api/blog/article/like */
export async function like(body: API.ArticleLikeInputDTO, options?: { [key: string]: any }) {
  return request<number>('/api/blog/article/like', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 回复子评论 POST /api/blog/article/replySubComment */
export async function replySubComment(
  body: API.ArticleReplySubCommentInputDTO,
  options?: { [key: string]: any },
) {
  return request<number>('/api/blog/article/replySubComment', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 举报 POST /api/blog/article/report */
export async function report(body: API.ArticleReportInputDTO, options?: { [key: string]: any }) {
  return request<number>('/api/blog/article/report', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
