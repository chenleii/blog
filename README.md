# 人人博客系统

## 功能介绍

人人博客主要为用户提供了文章的查看和搜索功能，并根据用户实时的文章访问和搜索行为，生成热搜和热门文章的数据供用户直接查看。
同时用户也可以登录本博客系统，编写发布自己的文章供其他用户查看。为了安全考虑针对关键场景也加入了风控功能。

**文章查看&搜索：** 
用户访问本博客系统即可查看和搜索文章信息，分为最新和热门两个板块用于查看文章列表。最新板块是按照文章发布顺序显示文章列表，热门板块是当前热门的文章列表。
也可以通过搜索框直接搜索感兴趣的文章内容，同时搜索框内也便捷的提供了实时热搜列表，供用户直接点击搜索。
本系统会基于用户的行为，实时的计算热搜和热门文章数据，并持久化后供用户查看。

**账户登录：**
用户如果想要在本博客系统中进行互动，如文章点赞、文章评论、发布文章等等，便需要进行账户登录完成互动操作。
同时用户也可以修改自己的个人账户信息，如头像、昵称、个人简介等。

**发布文章&风控：**
用户登录后，就可以编写发布自己的文章了。
用户在发布自己的文章时需要经过简单的风控审核（敏感词等），风控审核通过后，其他用户便可以查看到发布的文章了。
同时用户也需要为自己发布的文章负责，如果其他用户举报文章超过一定次数（系统动态计算），便会将该文章置为不可见，并用可能（系统动态计算）对发布文章账户进行处罚。


## 应用启动说明

* 安装相关依赖，项目启动： 执行 ```com.chen.blog.start.Application.java```
* 访问应用页面: http://127.0.0.1:7001
* 访问API文档: http://127.0.0.1:7001/doc.html

### 相关依赖安装步骤示例

```shell
# 安装docker省略......

# 拉取mongo镜像
docker pull mongo:5.0.11-focal
# 拉取elasticsearch镜像
docker pull elasticsearch:7.17.5

# 创建网卡
docker network create somenetwork

# 运行mongo
docker run -d --name mongo5 --net somenetwork -p 27017:27017 mongo:5.0.11-focal --replSet docker-rs
# 运行elasticsearch
docker run -d --name elasticsearch7 --net somenetwork -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.17.5

# mongo设置集群
docker exec -it mongo5 /bin/bash
mongo
rs.initiate(
{
_id: "docker-rs",
members: [
{ _id : 0, host : "127.0.0.1:27017"}
]
}
)
exit

# elasticsearch安装分词器
docker exec -it elasticsearch7 /bin/bash
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.17.5/elasticsearch-analysis-ik-7.17.5.zip
elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-pinyin/releases/download/v7.17.5/elasticsearch-analysis-pinyin-7.17.5.zip
exit

# 可以查看本项目中该目录下 ./app-metadata/init/ 的文件，执行创建索引和批量导入一些数据。
```

## 应用架构

DDD + CQRS + 事件总线

### 包结构说明

```
+-blog-core: 核心业务组件层。按照业务域划分模块。
|   +-blog-core-sharedkernel: 公共域（共享内核）
|       +-ddd: ddd领域驱动相关描述注解
|       +-cqrs: cqrs相关描述注解和公共接口
|       +-port: 外部端口相关
|       +-event: 领域事件
|       +-mq: 消息队列
|       +-trace: 链路追踪
|       +-logger: 通用日志相关如事件溯源日志
|       +-lock: 锁抽象
|       +-specification: 业务规则抽象
|       +-exception: 公共异常
|       +-idgenerator: id生成器抽象
|       +-cache: 缓存抽象
|       +-serializer: 序列化器抽象
|       +-converter: 对象转换器抽象
|       +-validator: 验证器抽象
|
|   +-blog-core-**: **业务域
|       +-application: 应用层
|           +-commandservice: 命令服务
|           +-queryservice: 查询服务
|           +-eventlistener: 事件监听
|       +-domain.model: 领域层
|           +-cqrs: cqrs相关
|           +-event: 领域事件
|           +-exception: 领域异常
|           +-repository: 仓储
|           +-factory: 领域工厂
|           +-service: 领域服务
|           +-**: 其他的领域相关
|           +-: 领域对象 
|       +-port:端口（当前域对外部能力的依赖. 例如订单域需要物流查询能力。）
|
+-blog-infrastructure: 基础设施层
|   +-event: 领域事件实现
|   +-persistence: 持久化实现
|   +-port: port的实现类，按不同port类型分包。
|
+-blog-api: 对外rpc接口层。按业务域分包，参数和返回值使用包装对象。
|   +-**: rpc接口
|
+-blog-interface: 接口适配层。包括http、rpc实现、mq、定时任务等。该层核心职责：参数校验，转换，面向展示的数据组装。
|   +-web: http接口相关
|   +-rpc: rpc接口的实现, Facade对象是对外Port，可以引用applicationService或repository对象
|   +-mq: 消息队列监听
|   +-timedtask: 定时任务
|
+-blog-configuration: 应用配置。
|
+-blog-start: 应用启动相关。
|   +-Application: 应用启动类。
|
+-blog-test: 测试
|   +-archunit: 架构约束
|   +-bdd: BDD测试
|
+-blog-ui: 前端ui

```

