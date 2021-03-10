# 目录介绍
- lesson13: 第七周第十三课
  - InsertOneMillionOrderData.java: 批量插入100万条订单数据
  - InsertTenMillionOrderData.java: 批量插入1000万条订单数据

- lesson14: 第七周第十四课
  - abstract-routing-dynamic-data-source: 基于 AbstractRoutingDataSource 实现动态数据源
  - abstract-routing-read-write-split: 基于 AbstractRoutingDataSource 实现读写分离
  - mybatis-plus-dynamic-datasource-spring-boot: 基于 MyBatis Plus 的 dynamic-datasource-spring-boot-starter 实现动态数据源
  - shardingsphere-dynamic-datasource: 基于 ShardingSphere 实现动态数据源
  - shardingsphere-proxy-read-write-split: 基于 ShardingSphere Proxy 实现读写分离
  - shardingsphere-read-write-split: 基于 ShardingSphere 实现读写分离

# 第七周课程内容
## 第十三课
> MySQL 事务与锁、DB 与 SQL 优化、常见场景分析
## 第十四课
> MySQL 集群、MySQL 主从复制、MySQL 读写分离、MySQL 高可用

# 第七周作业
## 第十三课
> 测试环境:
>   CPU: i9-9900KS 8核16线程     
>   内存: 32G    
>   操作系统: Windows10   
> 插入的订单表字段数量: 32个字段
>
> [1、插入100万订单模拟数据，测试不同方式的插入效率](https://github.com/WebbDong/JAVA-01/blob/main/Week_07/lesson13/src/main/java/lesson13/InsertOneMillionOrderData.java)    
> 1.1 使用多个 values 进行批量插入方式（insert....values....）    
>> 插入策略1，每 10000 条一个批次，条数到 10000 时，提交并插入一批    
>> rewriteBatchedStatements 关闭情况: 耗时 30561 ms    
>> rewriteBatchedStatements 开启情况: 耗时 34773 ms
>> 
>> 插入策略2，每 20000 条一个批次，条数到 20000 时，提交并插入一批      
>> rewriteBatchedStatements 关闭情况: 耗时 30844 ms     
>> rewriteBatchedStatements 开启情况: 耗时 35334 ms     
> 
> 1.2 使用批量插入方式      
>> 插入策略1，每 10000 条一个批次，条数到 10000 时，提交并插入一批     
>> rewriteBatchedStatements 关闭情况: 速度过慢，5-8分钟只插入 10 万条左右数据     
>> rewriteBatchedStatements 开启情况: 耗时 34420 ms   
>> 
>> 插入策略2，每 20000 条一个批次，条数到 20000 时，提交并插入一批       
>> rewriteBatchedStatements 开启情况: 耗时 36422 ms     
> 
> 1.3 使用批量插入方式 + 手动控制事务 + rewriteBatchedStatements=true        
>> 插入策略1，每 10000 条一个批次，条数到 10000 时，提交并插入一批，耗时 29202 ms       
>> 插入策略2，每 20000 条一个批次，条数到 20000 时，提交并插入一批，耗时 29798 ms        
> 
> 1.4 使用多个 values 进行批量插入方式 + 手动控制事务 + rewriteBatchedStatements=true&max_allowed_packet=10M       
>> 插入策略1，每 50000 条一个批次，条数到 50000 时，提交并插入一批，耗时: 28741 ms         
>> 插入策略2，每 100000 条一个批次，条数到 100000 时，提交并插入一批，耗时: 31102 ms           
> 
> 1.5 使用批量插入方式 + 手动控制事务 + rewriteBatchedStatements=true&max_allowed_packet=10M           
>> 插入策略: 每 50000 条一个批次，条数到 50000 时，提交并插入一批，耗时: 29961 ms          
> 
> 1.6 使用批量插入方式 + 手动控制事务并且分批提交事务 + rewriteBatchedStatements=true&max_allowed_packet=10M        
>> 插入策略1，每 50000 条一个批次，条数到 50000 时，插入并提交一批事务，耗时: 29090 ms         
>> 插入策略2，每 100000 条一个批次，条数到 100000 时，插入并提交一批事务，耗时: 30546 ms    
> 
> 1.7 使用批量插入方式 + 手动控制事务并且分批提交事务 + 多线程       
>> 插入策略: 100万数据分成八份，每个线程处理 125000 条数据，每个线程单独一个数据库连接，耗时: 15262 ms      
> 
> [2、插入1000万订单模拟数据，测试不同方式的插入效率](https://github.com/WebbDong/JAVA-01/blob/main/Week_07/lesson13/src/main/java/lesson13/InsertTenMillionOrderData.java)    
>> 2.1 使用批量插入方式 + 手动控制事务并且分批提交事务 + 多线程      
>> 插入策略1，每 50000 条一个批次，耗时: 177080 ms          
>> 插入策略2，每 100000 条一个批次，耗时: 179366 ms      

## 第十四课