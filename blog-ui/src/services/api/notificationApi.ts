// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 分页查询 GET /api/blog/notification */
export async function pageQuery(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageQueryParams,
  options?: { [key: string]: any },
) {
  return request<API.PaginationNotificationRepresentation>('/api/blog/notification', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 查询 GET /api/blog/notification/${param0} */
export async function query(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.queryParams,
  options?: { [key: string]: any },
) {
  const { notificationId: param0, ...queryParams } = params;
  return request<API.NotificationRepresentation>(`/api/blog/notification/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 清除 POST /api/blog/notification/${param0}/clear */
export async function clear(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.clearParams,
  options?: { [key: string]: any },
) {
  const { notificationId: param0, ...queryParams } = params;
  return request<any>(`/api/blog/notification/${param0}/clear`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 读 POST /api/blog/notification/${param0}/read */
export async function read(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.readParams,
  options?: { [key: string]: any },
) {
  const { notificationId: param0, ...queryParams } = params;
  return request<any>(`/api/blog/notification/${param0}/read`, {
    method: 'POST',
    params: { ...queryParams },
    ...(options || {}),
  });
}
