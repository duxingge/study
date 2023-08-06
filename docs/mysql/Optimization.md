# Optimizing at the Database Level
1. 合适的数据结构
   - 字段是否有合适的类型
   - 表是否有合适的列？频繁更新的应用对应更多的表更少的列，大数据分析型应用对应更多的列更少的表
2. 合适的索引
3. appropriate storage engine
    - innodb is default storage engine, in practice, innodb table performance often outperform myisam table. 
    
## Optimizing query statement

> SELECT
> CREATE TABLE...AS SELECT
> INSERT INTO...SELECT
> DELETE ... WHERE









## Optimizing database structure
1. 使用更有效率(更小)的数据类型
2. 尽可能声明NOT NULL: 
   1). 对于包含索引的列，NOT NULL可以使存储引擎更好的利用索引查询。
   2) 消除查询时的NULL检测开销 
   3) 节省空间, 1bit/列
   4) NULL 会影响聚合函数的结果。例如，SUM、AVG、MIN、MAX 等聚合函数会忽略 NULL 值。 COUNT 的处理方式取决于参数的类型。如果参数是 *(COUNT(*))，则会统计所有的记录数，包括 NULL 值；如果参数是某个字段名(COUNT(列名))，则会忽略 NULL 值，只统计非空值的个数。


    
