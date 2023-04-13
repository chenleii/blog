// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 是否已登录 GET /api/blog/account/isLoggedIn */
export async function isLoggedIn(options?: { [key: string]: any }) {
  return request<boolean>('/api/blog/account/isLoggedIn', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取登录账户 GET /api/blog/account/login */
export async function getLoggedInAccount(options?: { [key: string]: any }) {
  return request<API.LoggedInAccount>('/api/blog/account/login', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 登录 POST /api/blog/account/login */
export async function login(body: API.AccountLoginInputDTO, options?: { [key: string]: any }) {
  return request<API.LoggedInAccount>('/api/blog/account/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 登出 POST /api/blog/account/logout */
export async function logout(options?: { [key: string]: any }) {
  return request<any>('/api/blog/account/logout', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 刷新登录 POST /api/blog/account/refreshLogin */
export async function refreshLogin(options?: { [key: string]: any }) {
  return request<API.LoggedInAccount>('/api/blog/account/refreshLogin', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 发送登录验证码 POST /api/blog/account/sendLoginVerificationCode */
export async function sendLoginVerificationCode(
  body: API.AccountSendLoginVerificationCodeInputDTO,
  options?: { [key: string]: any },
) {
  return request<any>('/api/blog/account/sendLoginVerificationCode', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新 POST /api/blog/account/update */
export async function update1(body: API.AccountUpdateInputDTO, options?: { [key: string]: any }) {
  return request<any>('/api/blog/account/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
