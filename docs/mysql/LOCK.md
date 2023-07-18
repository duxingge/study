## Mysql Internal Locking Methods

- Row-Level Locking

- Table-Level Locking

- Choosing the Type of Locking

#### Row-Level Locking
> mysql使用Row-Level Locking 为innodb的表来支持多进程高并发写入
> mysql具有死锁检测（回滚其中的一个事务），因此mysql中死锁只是影响性能而不是报错
 
>tip: 当一个事务有多个update时，为了避免死锁，开发者可以使用SELECT ... FOR UPDATE 语句为将来修改的记录提前获取锁。
