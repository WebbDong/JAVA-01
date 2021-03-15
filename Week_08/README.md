# 目录介绍
- lesson15: 第八周第十五课
  - sharding-order-2-16: 2个订单分库，每个库16张订单分表
  - sharding-order-scaling-2-16: 将一个1000万条订单数据的单库单表，迁移到2个订单分库，每个库16张订单分表中
  - sharding-order-common: 订单分库分表所有公共类
  - sharding-order-4-64: 4个订单分库，每个库64张订单分表
  - sharding-order-scaling-4-64: 将一个2个订单分库，每个库16张订单分表，迁移到4个订单分库，每个库64张订单分表中
  - sharding-order-scaling-common: 订单数据迁移所有公共类

- lesson16: 第八周第十六课

# 第八周课程内容
## 第十五课
> 为什么要做数据库拆分、数据库垂直拆分、数据库水平拆分、相关的框架和中间件、如何做数据迁移
## 第十六课
> 分布式事务、XA 分布式事务、BASE 柔性事务、TCC/AT 以及相关框架、ShardingSphere 对分布式事务的支持

# 第七周作业
## 第十五课
> [1、设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。
并在新结构在演示常见的增删改查操作](https://github.com/WebbDong/JAVA-01/blob/main/Week_08/lesson15/sharding-order-2-16)    
>
> [2、模拟1000万的订单单表数据，迁移到上面作业2的分库分表中](https://github.com/WebbDong/JAVA-01/blob/main/Week_08/lesson15/sharding-order-scaling-2-16)
>> 使用 shardingsphere scaling 4.1.0 实现        
>>
>> 2.1 使用 docker 部署 zookeeper
```shell
docker run -d -p 12181:2181 --name zk1 zookeeper:latest
```
>> 2.2 配置 shardingsphere-sharding-proxy      
>>> 2.2.1 配置 shardingsphere-sharding-proxy 的 server.yaml
```yaml
# 权限配置
authentication:
  users:
    root:               # 用户名
      password: 123456	# 密码
    sharding:
      password: sharding
      authorizedSchemas: sharding_db
props:
  max.connections.size.per.query: 1
  acceptor.size: 16                    # 用于设置接收客户端请求的工作线程个数，默认为CPU核数 * 2
  executor.size: 16
  proxy.frontend.flush.threshold: 128
  proxy.transaction.type: LOCAL 	   # 默认为LOCAL事务
  proxy.opentracing.enabled: false     # 是否开启链路追踪功能，默认为不开启。
  query.with.cipher.column: true
  sql.show: true
  check.table.metadata.enabled: true   # 是否在启动时检查分表元数据一致性，默认值: false
orchestration:
  orchestration_ds:
    orchestrationType: registry_center,config_center,distributed_lock_manager
    instanceType: zookeeper
    serverLists: localhost:12181
    namespace: orchestration_order_4.1.0
    props:
      overwrite: true
      retryIntervalMilliseconds: 500
      timeToLiveSeconds: 60
      maxRetries: 3
      operationTimeoutMilliseconds: 500
```
>>> 2.2.2 配置 shardingsphere-sharding-proxy 的 config-sharding.yaml
```yaml
schemaName: order_sharding_db

dataSources:
  # 订单分库 1
  order_ds_0:
    url: jdbc:mysql://localhost:33008/order?rewriteBatchedStatements=true&max_allowed_packet=10M&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: root
    password: 123456
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
  # 订单分库 2
  order_ds_1:
    url: jdbc:mysql://localhost:33009/order?rewriteBatchedStatements=true&max_allowed_packet=10M&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: root
    password: 123456
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50

shardingRule:
  tables:
    t_order:
      # 库名与表名规则，库名 order_ds_0 - order_ds_1，表名 t_order_0 - t_order_15
      actualDataNodes: order_ds_${0..1}.t_order_${0..15}
      tableStrategy:
        inline:
          shardingColumn: id
          algorithmExpression: t_order_${id % 16}
      keyGenerator:
        type: SNOWFLAKE
        column: id
  bindingTables:
    - t_order
  defaultDatabaseStrategy:
    inline:
      shardingColumn: user_id
      algorithmExpression: order_ds_${user_id % 2}
  defaultTableStrategy:
    none:
```
>>> 2.2.3 在 shardingsphere-sharding-proxy 的根目录中新建 ext-lib 目录，并将 mysql-connector-java-8.0.23.jar 复制到该目录中      
>>
>>> 2.2.4 调用 start.bat 启动 shardingsphere-sharding-proxy
>>
>> 2.3 将 mysql-connector-java-8.0.23.jar 复制到 shardingsphere-sharding-scaling 的 lib 目录中并且启动     
>> 
>> 2.4 发送 scaling 任务
```shell
curl -X POST --url http://localhost:8888/shardingscaling/job/start \
--header 'content-type: application/json' \
--data '{
   "ruleConfiguration": {
      "sourceDatasource": "order_ds_0: !!org.apache.shardingsphere.orchestration.core.configuration.YamlDataSourceConfiguration\n  dataSourceClassName: com.zaxxer.hikari.HikariDataSource\n  properties:\n    jdbcUrl: jdbc:mysql://localhost:33010/order?rewriteBatchedStatements=true&max_allowed_packet=10M&allowPublicKeyRetrieval=true&serverTimezone=UTC\n    driverClassName: com.mysql.cj.jdbc.Driver\n    username: root\n    password: 123456\n    connectionTimeout: 30000\n    idleTimeout: 60000\n    maxLifetime: 1800000\n    maxPoolSize: 100\n    minPoolSize: 10\n    maintenanceIntervalMilliseconds: 30000\n    readOnly: false\n",
      "sourceRule": "tables:\n  t_order:\n    actualDataNodes: order_ds_0.t_order\n    keyGenerator:\n      column: id\n      type: SNOWFLAKE",
      "destinationDataSources": {
         "name": "order_sharding_db",
         "password": "123456",
         "url": "jdbc:mysql://localhost:3307/order_sharding_db?serverTimezone=UTC&useSSL=false",
         "username": "root"
      }
   },
   "jobConfiguration": {
      "concurrency": 16
   }
}'
```
>> 也可以使用 [ScalingStart.java](https://github.com/WebbDong/JAVA-01/blob/main/Week_08/lesson15/sharding-order-scaling-2-16/src/main/java/com/order/scaling/ScalingStart.java)
>> 使用 OkHttp 客户端进行任务发送
>
> [3、重新搭建一套4个库各64个表的分库分表，将作业2中的数据迁移到新分库](https://github.com/WebbDong/JAVA-01/blob/main/Week_08/lesson15/sharding-order-scaling-4-64)







