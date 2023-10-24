##软件安装
基本步骤: 搜-拉-查-启-停-移
注： 镜像的启动命令等可以在docker官网的对应镜像查看https://hub.docker.com/


### 1.tomcat
```shell
[root@localhost ~]# docker run -d -p 8080:8080 billygoo/tomcat8-jdk8
# (如果用的虚拟机，那么执行端口转发，否则本地主机无法访问虚拟机的centOS的8080端口)
[root@localhost ~]# ssh -Nf -L 8080:localhost:8080 root@192.168.2.221
# 访问http://localhost:8080
```

### 2.mysql
```shell
#1. 拉镜像
[root@localhost ~]# docker pull mysql:8.0
#2. 启动 + 容器卷映射
## 必须使用数据卷，防止容器删除导致数据丢失，达到数据备份的目的
[root@localhost ~]# docker run -d -p 3306:3306 --privileged=true -v /zzyyuse/mysql/log:/var/log/mysql -v /zzyyuse/mysql/data:/var/lib/mysql -v /zzyyuse/mysql/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456 --name mysql mysql:8.0

# 注意：docker上的mysql默认字符集是latin1，不支持中文，会有乱码问题。
mysql> show variables like '%char%';
+--------------------------+--------------------------------+
| Variable_name            | Value                          |
+--------------------------+--------------------------------+
| character_set_client     | latin1                         |
| character_set_connection | latin1                         |
| character_set_database   | utf8mb4                        |
| character_set_filesystem | binary                         |
| character_set_results    | latin1                         |
| character_set_server     | utf8mb4                        |
| character_set_system     | utf8mb3                        |
| character_sets_dir       | /usr/share/mysql-8.0/charsets/ |
+--------------------------+--------------------------------+
8 rows in set (0.01 sec)

mysql> select * from t1;
+------+------+
| id   | name |
+------+------+
|    1 | z3   |
|    2 | lisi |
|    3 | ??   |
+------+------+
#3. 在/zzyyuse/mysql/conf下新增配置文件， 解决乱码问题
[root@localhost conf]# vim my.cnf

[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
[client]
default-character-set=utf8mb4

#4. 重启mysql容器
[root@localhost conf]# docker restart mysql

mysql> show variables like '%char%'
    -> ;
+--------------------------+--------------------------------+
| Variable_name            | Value                          |
+--------------------------+--------------------------------+
| character_set_client     | utf8mb4                        |
| character_set_connection | utf8mb4                        |
| character_set_database   | utf8mb4                        |
| character_set_filesystem | binary                         |
| character_set_results    | utf8mb4                        |
| character_set_server     | utf8mb4                        |
| character_set_system     | utf8mb3                        |
| character_sets_dir       | /usr/share/mysql-8.0/charsets/ |
+--------------------------+--------------------------------+
8 rows in set (0.01 sec)

mysql> select * from t1;
+------+--------+
| id   | name   |
+------+--------+
|    4 | 周六   |
+------+--------+

```

### 3. redis


```shell
# 1.拉
[root@localhost conf]# docker pull redis:6.0.8

# 2.创建配置文件(从官网下一个初始配置文件redis.conf)到/app/redis/redis.conf
## 2.1 注释掉 bind 127.0.0.1 使接受所有网络机器连接
## 2.2 daemonize no 注释掉(因为与-d参数冲突),使redis可以后台运行
## 2.3 (可选) 设置密码 requirepass 123456

# 3. 使用/etc/redis/redis.conf启动redis + 容器卷
[root@localhost redis]# docker run -d -p 6379:6379 --name myredis --privileged=true -v /app/redis/redis.conf:/etc/redis/redis.conf -v /app/redis/data:/data redis:6.0.8 redis-server /etc/redis/redis.conf



```





### mysql主从搭建

```shell
# 创建master节点
[root@localhost conf]# docker run -p 3307:3306 --name mysql-master -v /mydata/mysql-master/log:/var/log/mysql  -v /mydata/mysql-master/data:/var/lib/mysql -v /mydata/mysql-master/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456  -d mysql:8.0
# 配置master配置 /mydata/mysql-master/conf/my.cnf
[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
#设置serverId, 同一局域网中唯一
server-id = 101
#启用二进制文件，设置binlog日志前缀
log-bin = mall-mysql-bin
# binlog日志格式 ROW STATMENT mixed
binlog_format = mixed
expire_logs_days = 10
max_binlog_size = 100M
## 二进制日志使用的内存大小
binlog_cache_size=1M
## 不需要同步的数据库名称
binlog-ignore-db=mysql
## 跳过主从复制中所遇到的错误, 1062指的是一些主键重复错误
slave_skip_errors=1062
[client]
default-character-set=utf8mb4

# 重启容器 docker restart mysql-master 

# 进入mysql-master容器
[root@localhost conf]# docker exec -it mysql-master /bin/bash

# 数据库内创建数据同步用户
mysql> create user 'slave'@'%'IDENTIFIED BY '123456';
mysql> GRANT REPLICATION SLAVE,REPLICATION CLIENT ON *.* TO 'slave'@'%';
mysql> ALTER USER 'slave'@'%' IDENTIFIED WITH mysql_native_password BY '123456';

# 创建slave节点
[root@localhost ~]# docker run -p 3308:3306 --name mysql-slave -v /mydata/mysql-slave/log:/var/log/mysql  -v /mydata/mysql-slave/data:/var/lib/mysql -v /mydata/mysql-slave/conf:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=123456  -d mysql:8.0

# 配置slave配置

[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci
#设置serverId, 同一局域网中唯一
server-id = 102
#启用二进制文件，设置binlog日志前缀
log-bin = mall-mysq-slave1l-bin
# binlog日志格式 ROW STATMENT mixed
binlog_format = mixed
expire_logs_days = 10
max_binlog_size = 100M
## 二进制日志使用的内存大小
binlog_cache_size=1M
## 不需要同步的数据库名称
binlog-ignore-db=mysql
## 跳过主从复制中所遇到的错误, 1062指的是一些主键重复错误
slave_skip_errors=1062
[client]
default-character-set=utf8mb4

# 重启slave容器
[root@localhost conf]# docker restart mysql-slave

# 在主数据库中查看主从状态
mysql> show master status;
+-----------------------+----------+--------------+------------------+-------------------+
| File                  | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+-----------------------+----------+--------------+------------------+-------------------+
| mall-mysql-bin.000004 |      711 |              | mysql            |                   |
+-----------------------+----------+--------------+------------------+-------------------+

# 进入slave数据库，进行主从配置
[root@localhost conf]# docker exec -it mysql-slave /bin/bash
mysql> change master to master_host='192.168.31.221',master_user='slave', master_password='123456', master_port=3307, master_log_file='mall-mysql-bin.000004', master_log_pos=711, master_connect_retry=30;

# 在从数据库查看主从状态
mysql> show slave status \G
            Slave_IO_Running: No
            Slave_SQL_Running: No
# 在从数据中开启主从同步
mysql> start slave;

# 在从数据库再次查看主从状态
mysql> show slave status \G;
            Slave_IO_Running: Yes
            Slave_SQL_Running: Yes

```



### redis三主三从搭建
```shell
# 1. 启动6个reids服务 --net host 网络采用主机模式 --appendonly yes 开启持久化  --port 6381: 这个选项用于直接将容器内部的端口映射到主机上的一个端口，这里是将容器内部的Redis服务端口（默认为6379）映射到主机的6381端口。所以等同于 -p 6381:6379
[root@localhost conf]# docker run -d --name redis-node-1 --net host --privileged=true -v /data/redis/share/redis-node-1:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6381
docker run -d --name redis-node-2 --net host --privileged=true -v /data/redis/share/redis-node-2:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6382
docker run -d --name redis-node-3 --net host --privileged=true -v /data/redis/share/redis-node-3:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6383
docker run -d --name redis-node-4 --net host --privileged=true -v /data/redis/share/redis-node-4:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6384
docker run -d --name redis-node-5 --net host --privileged=true -v /data/redis/share/redis-node-5:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6385
docker run -d --name redis-node-6 --net host --privileged=true -v /data/redis/share/redis-node-6:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6386



[root@localhost conf]# docker ps
CONTAINER ID   IMAGE         COMMAND                  CREATED              STATUS              PORTS                                                  NAMES
9303182ff081   redis:6.0.8   "docker-entrypoint.s…"   31 seconds ago       Up 30 seconds                                                              redis-node-6
1965f77ca5b7   redis:6.0.8   "docker-entrypoint.s…"   31 seconds ago       Up 31 seconds                                                              redis-node-5
720149c09f9b   redis:6.0.8   "docker-entrypoint.s…"   31 seconds ago       Up 31 seconds                                                              redis-node-4
4c91b5fcf5ac   redis:6.0.8   "docker-entrypoint.s…"   31 seconds ago       Up 31 seconds                                                              redis-node-3
5d92d556e706   redis:6.0.8   "docker-entrypoint.s…"   31 seconds ago       Up 31 seconds                                                              redis-node-2
6e3f1c684f44   redis:6.0.8   "docker-entrypoint.s…"   About a minute ago   Up About a minute

# 2. 进入其中一台容器, 并创建集群

[root@localhost conf]# docker exec -it 9303182ff081  /bin/bash
root@localhost:/data# redis-cli --cluster create 192.168.31.221:6381 192.168.31.221:6382 192.168.31.221:6383 192.168.31.221:6384 192.168.31.221:6385 192.168.31.221:6386 --cluster-replicas 1

# 查看集群状态,节点 cluster info ; cluster nodes
root@localhost:/data# redis-cli -p 6381
127.0.0.1:6381> cluster info
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:3
cluster_current_epoch:6
cluster_my_epoch:1
cluster_stats_messages_ping_sent:618
cluster_stats_messages_pong_sent:647
cluster_stats_messages_sent:1265
cluster_stats_messages_ping_received:642
cluster_stats_messages_pong_received:618
cluster_stats_messages_meet_received:5
cluster_stats_messages_received:1265

127.0.0.1:6381> cluster nodes
6ff565306adaacc4b1ae13dfaf1568296a8663f3 192.168.31.221:6383@16383 master - 0 1697004976000 3 connected 10923-16383
4ef468b3e15591362f542eb91ba6b4f9c996e9d8 192.168.31.221:6384@16384 slave 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb 0 1697004979597 1 connected
9f2290999ac31f7a4382b85f4ce1b3f87f90396a 192.168.31.221:6385@16385 slave 49cbc6376991887078c420545bea0161472e0489 0 1697004979000 2 connected
ae4d5ff038dd426742dd8ae9ae822324ce940d33 192.168.31.221:6386@16386 slave 6ff565306adaacc4b1ae13dfaf1568296a8663f3 0 1697004980624 3 connected
49cbc6376991887078c420545bea0161472e0489 192.168.31.221:6382@16382 master - 0 1697004978000 2 connected 5461-10922
6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb 192.168.31.221:6381@16381 myself,master - 0 1697004977000 1 connected 0-5460

# 其他命令
## -c 以集群的方式进入redis-cli 
root@localhost:/data# redis-cli -p 6381 -c
## -- cluster check 集群内容检查. 注意 --cluster后面跟集群中的任意一个节点就行
root@localhost:/data# redis-cli --cluster check 192.168.31.221:6381
192.168.31.221:6381 (6d2d8975...) -> 0 keys | 5461 slots | 1 slaves.
192.168.31.221:6383 (6ff56530...) -> 1 keys | 5461 slots | 1 slaves.
192.168.31.221:6382 (49cbc637...) -> 0 keys | 5462 slots | 1 slaves.
[OK] 1 keys in 3 masters.
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 192.168.31.221:6381)
M: 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb 192.168.31.221:6381
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: 6ff565306adaacc4b1ae13dfaf1568296a8663f3 192.168.31.221:6383
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 4ef468b3e15591362f542eb91ba6b4f9c996e9d8 192.168.31.221:6384
   slots: (0 slots) slave
   replicates 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb
S: 9f2290999ac31f7a4382b85f4ce1b3f87f90396a 192.168.31.221:6385
   slots: (0 slots) slave
   replicates 49cbc6376991887078c420545bea0161472e0489
S: ae4d5ff038dd426742dd8ae9ae822324ce940d33 192.168.31.221:6386
   slots: (0 slots) slave
   replicates 6ff565306adaacc4b1ae13dfaf1568296a8663f3
M: 49cbc6376991887078c420545bea0161472e0489 192.168.31.221:6382
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.

```


### 主从扩容

增加6387，6388到三主三从上，使其变为四主四从

```shell
# 1. 启动 6387，6388
[root@localhost conf]# docker run -d --name redis-node-7 --net host --privileged=true -v /data/redis/share/redis-node-7:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6387
a4110be760d097ca94631b2950f7db33483a77cb69eb8c7f4b00317a3b925ec1
[root@localhost conf]# docker run -d --name redis-node-8 --net host --privileged=true -v /data/redis/share/redis-node-8:/data redis:6.0.8 --cluster-enabled yes --appendonly yes --port 6388
e0e53d0983eb79479b65126f60037afb72d0e91fc3c3924b3b9b34427bd0e691

# 2. 进入6387，然后将6387作为新的master节点加入集群(注意只是加入，暂时还没有slot) redis-cli --cluster add-node 新节点IP:新节点端口 集群节点IP:集群节点端口 
root@localhost:/data# redis-cli --cluster add-node 192.168.31.221:6387 192.168.31.221:6381
>>> Adding node 192.168.31.221:6387 to cluster 192.168.31.221:6381
>>> Performing Cluster Check (using node 192.168.31.221:6381)
M: 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb 192.168.31.221:6381
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: 6ff565306adaacc4b1ae13dfaf1568296a8663f3 192.168.31.221:6383
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 4ef468b3e15591362f542eb91ba6b4f9c996e9d8 192.168.31.221:6384
   slots: (0 slots) slave
   replicates 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb
S: 9f2290999ac31f7a4382b85f4ce1b3f87f90396a 192.168.31.221:6385
   slots: (0 slots) slave
   replicates 49cbc6376991887078c420545bea0161472e0489
S: ae4d5ff038dd426742dd8ae9ae822324ce940d33 192.168.31.221:6386
   slots: (0 slots) slave
   replicates 6ff565306adaacc4b1ae13dfaf1568296a8663f3
M: 49cbc6376991887078c420545bea0161472e0489 192.168.31.221:6382
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
>>> Send CLUSTER MEET to node 192.168.31.221:6387 to make it join the cluster.
[OK] New node added correctly.

# 节点检查，可以发现6387还没有slot分配
root@localhost:/data# redis-cli --cluster check 192.168.31.221:6381
192.168.31.221:6381 (6d2d8975...) -> 0 keys | 5461 slots | 1 slaves.
192.168.31.221:6383 (6ff56530...) -> 1 keys | 5461 slots | 1 slaves.
192.168.31.221:6387 (7353d091...) -> 0 keys | 0 slots | 0 slaves.
192.168.31.221:6382 (49cbc637...) -> 0 keys | 5462 slots | 1 slaves.

# 3. 进行分配
root@localhost:/data# redis-cli --cluster reshard 192.168.31.221:6387

# 4. 再次检查
root@localhost:/data# redis-cli --cluster check 192.168.31.221:6381
192.168.31.221:6381 (6d2d8975...) -> 0 keys | 4096 slots | 1 slaves.
192.168.31.221:6383 (6ff56530...) -> 1 keys | 4096 slots | 1 slaves.
192.168.31.221:6387 (7353d091...) -> 0 keys | 4096 slots | 0 slaves.
192.168.31.221:6382 (49cbc637...) -> 0 keys | 4096 slots | 1 slaves.
[OK] 1 keys in 4 masters.
0.00 keys per slot on average.
>>> Performing Cluster Check (using node 192.168.31.221:6381)
M: 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb 192.168.31.221:6381
   slots:[1365-5460] (4096 slots) master
   1 additional replica(s)
M: 6ff565306adaacc4b1ae13dfaf1568296a8663f3 192.168.31.221:6383
   slots:[12288-16383] (4096 slots) master
   1 additional replica(s)
S: 4ef468b3e15591362f542eb91ba6b4f9c996e9d8 192.168.31.221:6384
   slots: (0 slots) slave
   replicates 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb
S: 9f2290999ac31f7a4382b85f4ce1b3f87f90396a 192.168.31.221:6385
   slots: (0 slots) slave
   replicates 49cbc6376991887078c420545bea0161472e0489
S: ae4d5ff038dd426742dd8ae9ae822324ce940d33 192.168.31.221:6386
   slots: (0 slots) slave
   replicates 6ff565306adaacc4b1ae13dfaf1568296a8663f3
M: 7353d09144105646a8943898df8ed322a80829c9 192.168.31.221:6387
   slots:[0-1364],[5461-6826],[10923-12287] (4096 slots) master
M: 49cbc6376991887078c420545bea0161472e0489 192.168.31.221:6382
   slots:[6827-10922] (4096 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.

# 增加从节点6388到6387
root@localhost:/data# redis-cli --cluster add-node 192.168.31.221:6388 192.168.31.221:6387 --cluster-slave --cluster-master-id 7353d09144105646a8943898df8ed322a80829c9
>>> Adding node 192.168.31.221:6388 to cluster 192.168.31.221:6387
>>> Performing Cluster Check (using node 192.168.31.221:6387)
M: 7353d09144105646a8943898df8ed322a80829c9 192.168.31.221:6387
   slots:[0-1364],[5461-6826],[10923-12287] (4096 slots) master
S: ae4d5ff038dd426742dd8ae9ae822324ce940d33 192.168.31.221:6386
   slots: (0 slots) slave
   replicates 6ff565306adaacc4b1ae13dfaf1568296a8663f3
M: 49cbc6376991887078c420545bea0161472e0489 192.168.31.221:6382
   slots:[6827-10922] (4096 slots) master
   1 additional replica(s)
S: 4ef468b3e15591362f542eb91ba6b4f9c996e9d8 192.168.31.221:6384
   slots: (0 slots) slave
   replicates 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb
S: 9f2290999ac31f7a4382b85f4ce1b3f87f90396a 192.168.31.221:6385
   slots: (0 slots) slave
   replicates 49cbc6376991887078c420545bea0161472e0489
M: 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb 192.168.31.221:6381
   slots:[1365-5460] (4096 slots) master
   1 additional replica(s)
M: 6ff565306adaacc4b1ae13dfaf1568296a8663f3 192.168.31.221:6383
   slots:[12288-16383] (4096 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
>>> Send CLUSTER MEET to node 192.168.31.221:6388 to make it join the cluster.
Waiting for the cluster to join

>>> Configure node as replica of 192.168.31.221:6387.
[OK] New node added correctly.

```

### 主从缩容 
下掉节点6387,6388, 从四主四从缩容回三主三从

```shell
# 1. 下掉从节点6388

root@localhost:/data# redis-cli --cluster del-node 192.168.31.221:6381 5bcefb4d9e9fdac3d055e1cb328fef99c32d1530
>>> Removing node 5bcefb4d9e9fdac3d055e1cb328fef99c32d1530 from cluster 192.168.31.221:6381
>>> Sending CLUSTER FORGET messages to the cluster...
>>> Sending CLUSTER RESET SOFT to the deleted node.
# 查看集群节点状态
root@localhost:/data# redis-cli --cluster check 192.168.31.221:6381
192.168.31.221:6381 (6d2d8975...) -> 0 keys | 4096 slots | 1 slaves.
192.168.31.221:6383 (6ff56530...) -> 1 keys | 4096 slots | 1 slaves.
192.168.31.221:6387 (7353d091...) -> 0 keys | 4096 slots | 0 slaves.
192.168.31.221:6382 (49cbc637...) -> 0 keys | 4096 slots | 1 slaves.

# 2. 重新分配slots 

root@localhost:/data# redis-cli --cluster reshard 192.168.31.221:6381
>>> Performing Cluster Check (using node 192.168.31.221:6381)
M: 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb 192.168.31.221:6381
   slots:[1365-5460] (4096 slots) master
   1 additional replica(s)
M: 6ff565306adaacc4b1ae13dfaf1568296a8663f3 192.168.31.221:6383
   slots:[12288-16383] (4096 slots) master
   1 additional replica(s)
S: 4ef468b3e15591362f542eb91ba6b4f9c996e9d8 192.168.31.221:6384
   slots: (0 slots) slave
   replicates 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb
S: 9f2290999ac31f7a4382b85f4ce1b3f87f90396a 192.168.31.221:6385
   slots: (0 slots) slave
   replicates 49cbc6376991887078c420545bea0161472e0489
S: ae4d5ff038dd426742dd8ae9ae822324ce940d33 192.168.31.221:6386
   slots: (0 slots) slave
   replicates 6ff565306adaacc4b1ae13dfaf1568296a8663f3
M: 7353d09144105646a8943898df8ed322a80829c9 192.168.31.221:6387
   slots:[0-1364],[5461-6826],[10923-12287] (4096 slots) master
M: 49cbc6376991887078c420545bea0161472e0489 192.168.31.221:6382
   slots:[6827-10922] (4096 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
## 4096为要下线的6387 slots数目
How many slots do you want to move (from 1 to 16384)? 4096
## 设置转移的槽点数接收节点：由6381接收
What is the receiving node ID? 6d2d897543d6e1a50f0c8449c1317ed0c6c2b5cb
Please enter all the source node IDs.
  Type 'all' to use all the nodes as source nodes for the hash slots.
  Type 'done' once you entered all the source nodes IDs.
## 设置转移的槽点数来源节点：全由6387转移
Source node #1: 7353d09144105646a8943898df8ed322a80829c9
Source node #2: done

# 3.下线主节点6387节点

root@localhost:/data# redis-cli --cluster del-node 192.168.31.221:6381 7353d09144105646a8943898df8ed322a80829c9
>>> Removing node 7353d09144105646a8943898df8ed322a80829c9 from cluster 192.168.31.221:6381
>>> Sending CLUSTER FORGET messages to the cluster...
>>> Sending CLUSTER RESET SOFT to the deleted node.

root@localhost:/data# redis-cli --cluster check 192.168.31.221:6381
192.168.31.221:6381 (6d2d8975...) -> 0 keys | 8192 slots | 1 slaves.
192.168.31.221:6383 (6ff56530...) -> 1 keys | 4096 slots | 1 slaves.
192.168.31.221:6382 (49cbc637...) -> 0 keys | 4096 slots | 1 slaves.
```

















---------
#附录

## 远程端口转发
> ssh -Nf -L 8080:localhost:8080 root@192.168.2.221
> 
> Nf: 这些选项告诉SSH客户端不要执行远程命令（-N）并在后台运行（-f）。这样，SSH连接将仅用于端口转发，而不启动任何远程Shell会话。
> -L 是用来创建本地端口转发（Local Port Forwarding）的选项。它的作用是将本地端口与远程主机的某个端口建立一个安全的通信通道
> 8080:localhost:8080 指定了端口映射规则，将本地8080端口映射到远程主机的localhost（即远程主机本身）的8080端口。
> root@192.168.2.221 远程主机的用户名和ip




 