# 前端ui

使用 [Ant Design Pro](https://pro.ant.design) 进行搭建。更多内容请自行查看完整文档。

## 环境准备

```bash
# 安装node省略......

# 安装依赖
npm i

# 启动项目，连接本地后端api。
npm run start:dev

# 打包
npm run build
```

## 相关文档

* https://pro.ant.design/zh-CN/docs/overview/
* https://ant.design/docs/spec/introduce-cn
* https://procomponents.ant.design/docs/
* https://umijs.org/docs/tutorials/getting-started
* https://v3.umijs.org/zh-CN/docs

## 目录结构说明

```
+-config                   # umi配置，包含布局，菜单，打包等等
+-mock                     # 本地mock数据
+-public
|   +-favicon.png          # 浏览器标题图标
+- src
|   +-assets               # 本地静态资源
|   +-components           # 业务公共组件
|   +-e2e                  # 集成测试用例
|   +-layouts              # 通用布局
|   +-models               # 全局 dva model
|   +-pages                # 业务页面入口和常用模板
|   +-services             # 后台接口服务
|   +-utils                # 工具库
|   +-locales              # 国际化资源
|   +-global.less          # 全局样式
|   +-global.ts            # 全局 JS
+-tests                    # 测试工具
+-package.json
+-README.md
```
