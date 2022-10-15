// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 爬虫定时任务 POST /api/timedtask/reptile */
export async function reptile(options?: { [key: string]: any }) {
  return request<any>('/api/timedtask/reptile', {
    method: 'POST',
    ...(options || {}),
  });
}
