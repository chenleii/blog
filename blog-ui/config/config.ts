import { defineConfig } from '@umijs/max';
import { join } from 'path';
import defaultSettings from './defaultSettings';
import proxy from './proxy';
import routes from './routes';

const { REACT_APP_ENV } = process.env;

// https://umijs.org/docs/api/config
export default defineConfig({
  base: '/blog/',
  publicPath: '/blog/',
  // umi4还不支持
  // exportStatic: {},
  hash: true,
  antd: {},
  request: {
    // request响应取值
    dataField:'',
  },
  // https://umijs.org/docs/max/dva
  dva: {},
  access: {},
  initialState: {},
  model: {},
  layout: {
    // https://umijs.org/zh-CN/plugins/plugin-layout
    locale: true,
    disableMobile:false,
    ...defaultSettings,
  },
  // https://umijs.org/zh-CN/plugins/plugin-locale
  locale: {
    // default zh-CN
    default: 'zh-CN',
    antd: true,
    // default true, when it is true, will use `navigator.language` overwrite default
    baseNavigator: true,
  },
  targets: {
    ie: 11,
  },
  jsMinifier: 'terser',
  // umi routes: https://umijs.org/docs/routing
  routes,
  // Theme for antd: https://ant.design/docs/react/customize-theme-cn
  theme: {
    // 如果不想要 configProvide 动态设置主题需要把这个设置为 default
    // 只有设置为 variable， 才能使用 configProvide 动态设置主色调
    // https://ant.design/docs/react/customize-theme-variable-cn
    'root-entry-name': 'variable',
  },
  ignoreMomentLocale: true,
  proxy: proxy[REACT_APP_ENV || 'dev'],
  manifest: {
    basePath: '/',
  },
  // Fast Refresh 热更新
  fastRefresh: true,
  presets: ['umi-presets-pro'],
  openAPI: [
    {
      requestLibPath: "import { request } from '@umijs/max'",
      // 或者使用在线的版本
      // schemaPath: "http://localhost:7001/v3/api-docs",
      schemaPath: join(__dirname, 'oneapi.json'),
      mock: true,
      projectName: 'api',
      // namespace: 'blog',
      // hook: {
      //   customFunctionName: (v) => {
      //     console.log(v);
      //     return v
      //   },
      //   customClassName: (v) => {
      //     console.log(v);
      //     return v
      //   },
      // },
    },
  ],
});
