import Footer from '@/components/Footer';
import HeaderRightContent from '@/components/HeaderRightContent';
import {ExclamationCircleOutlined, LinkOutlined} from '@ant-design/icons';
import {SettingDrawer, Settings as LayoutSettings} from '@ant-design/pro-components';
import type {RunTimeLayoutConfig} from '@umijs/max';
import {history, Link} from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import {AxiosResponse, RequestConfig, RequestError, RequestOptions} from "@@/plugin-request/request";
import {isNil, negate, pickBy} from 'lodash';
import {Modal, notification, Space} from 'antd';
import {stringify} from 'querystring';
import api from './services/api';
import HeaderSearch from "@/components/HeaderSearch";

const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/account/login';

export type InitialState = {
  loading?: boolean;
  settings?: Partial<LayoutSettings>;
  loginPath?: string;
  isLoggedIn?: boolean;
  loggedInAccount?: API.LoggedInAccount;
  fetchLoggedInAccount?: () => Promise<API.LoggedInAccount | undefined>;
}

// umi3  https://umijs.org/zh-CN/plugins/plugin-initial-state
// umi4 https://umijs.org/docs/api/runtime-config#getinitialstate
export async function getInitialState(): Promise<InitialState> {
  const fetchLoggedInAccount = async () => {
    return await api.accountApi.getLoggedInAccount({skipErrorHandler: true})
  };

  try {
    const loggedInAccount = await fetchLoggedInAccount();
    return {
      fetchLoggedInAccount,
      isLoggedIn: true,
      loginPath: loginPath,
      loggedInAccount,
      settings: defaultSettings,
    };
  } catch (e) {
    return {
      fetchLoggedInAccount,
      isLoggedIn: false,
      loginPath: loginPath,
      settings: defaultSettings,
    };
  }

}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({initialState, setInitialState}) => {
  return {
    breadcrumbRender: false,
    rightContentRender: (props, defaultDom) => {
      if (document.body.clientWidth < 1400) {
        return <HeaderRightContent smallScreen/>;
      }
      if (props.isMobile) {
        return <HeaderRightContent smallScreen/>;
      }
      return <HeaderRightContent/>;
    },
    headerContentRender: (props, defaultDom) => {
      if (document.body.clientWidth < 1400) {
        return defaultDom;
      }
      if (props.isMobile) {
        return defaultDom;
      }
      return (
        <Space>
          <HeaderSearch/>
          {defaultDom}
        </Space>
      )
    },
    disableContentMargin: false,
    waterMarkProps: {
      content: initialState?.loggedInAccount?.name,
    },
    footerRender: () => <Footer/>,
    onPageChange: () => {
      // 如果没有登录，重定向到登录页。
      if (!initialState?.isLoggedIn
        && !initialState?.loggedInAccount
        && history.location.pathname !== loginPath) {
        // 不跳转，不登录也可以访问。
        // history.push(loginPath);
      }
    },
    links: isDev
      ? [
        <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
          <LinkOutlined/>
          <span>OpenAPI 文档</span>
        </Link>,
      ]
      : [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children, props) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          {isDev && (
            <SettingDrawer
              disableUrlParams
              enableDarkTheme
              settings={initialState?.settings}
              onSettingChange={(settings) => {
                setInitialState((preInitialState) => ({
                  ...preInitialState,
                  settings,
                }));
              }}
            />
          )}
        </>
      );
    },
    ...initialState?.settings,
  };
};

// request配置,https://umijs.org/docs/max/request
export const request: RequestConfig = {
  timeout: 3000,
  // params格式化规则
  paramsSerializer: (params) => {
    return new URLSearchParams(pickBy(params, negate(isNil))).toString()
    // return qs.stringify(params, {arrayFormat: 'repeat'});
  },
  headers: {'X-Requested-With': 'XMLHttpRequest'},
  withCredentials: true,
  errorConfig: {
    // 错误抛出
    errorThrower: (res: any) => {
      console.log("errorThrower", res);
      if (res?.errorCode) {
        const error: any = new Error(res?.errorMessage);

        // 抛出自制的错误
        error.name = 'BizError';
        error.info.errorCode = res?.errorCode;
        error.info.errorMessage = res?.errorMessage;
        error.info.showType = 1;
        error.info.data = res;
        throw error;
      }
    },
    errorHandler: (error: RequestError, opts: RequestOptions) => {
      console.log("errorHandler", error, opts);
      if (opts?.skipErrorHandler) throw error;

      let response: AxiosResponse = error['response'];
      if (!response) {
        // 请求没收到相应
        return;
      }
      // 拦截响应数据，进行个性化处理
      const status = response?.status;
      const data = response?.data;

      if (data?.errorCode === 'NotLogin') {
        // 如何国际化？？？
        Modal.confirm({
          title: '您还未登录哦',
          icon: <ExclamationCircleOutlined/>,
          content: '确认去登录吗？',
          okText: '确认',
          cancelText: '取消',
          onOk: () => {
            // const {search, pathname} = history.location;
            history.replace({
              pathname: loginPath,
              search: stringify({
                redirect: window.location.href,
              }),
            });
          },
        });
        return;
      }

      if (status >= 400) {
        notification.warning({
          message: data?.errorCode,
          description: data?.errorMessage,
        });
      } else if (status >= 500) {
        notification.error({
          message: data?.errorCode,
          description: data?.errorMessage,
        });
      }
    },
  },
  // 请求拦截器
  requestInterceptors: [
    (config: RequestOptions) => {
      // 拦截请求配置，进行个性化处理。
      return {...config};
    }
  ],
  // 响应拦截器
  responseInterceptors: [
    [(response: AxiosResponse<any>) => {
      // console.log("responseInterceptor", response);
      return response;
    }, (error: RequestError) => {
      // console.log("responseInterceptorError", error);
      return Promise.reject(error);
    },
    ]
  ]
};

