# Linux 服务管理

## 基础
### 分类

        Linux服务
            RPM包安装服务
                独立的服务
                基于xinetd的服务
            源码包安装服务

## 一   RPM包安装的服务
查询已安装的RPM服务 chkconfig --list

```shell
[root@localhost ~]# chkconfig --list

Note: This output shows SysV services only and does not include native
      systemd services. SysV configuration data might be overridden by native
      systemd configuration.

      If you want to list systemd services use 'systemctl list-unit-files'.
      To see services enabled on particular target use
      'systemctl list-dependencies [target]'.

netconsole     	0:off	1:off	2:off	3:off	4:off	5:off	6:off
network        	0:off	1:off	2:on	3:on	4:on	5:on	6:off
```

注: 0,1,2,3,4,5,6指的是Linux运行级别, 0关机，3命令提示符界面,5图形界面,6重启,

### 1.1 RPM包独立服务的管理

1. 配置文件 /etc/, 运行脚本 /etc/rc.d/init.d/
2. 常见路径
        
         /etc/init.d : /etc/rc.d/init.d/的软链接, 独立服务的启动脚本位置
         /etc/sysconfig: 初始化环境配置文件位置
         /etc/: 配置文件位置
         /etc/xinetd.conf: xinetd 配置文件位置
         /etc/xinetd.d/: 基于xinetd.d服务的启动脚本
         /var/lib:       服务产生的数据, var包下面一般存储变化的数据
         /var/log:      日志
3. 独立服务的启动方式
   1) 推荐： 
   > /etc/init.d/独立服务名 start/stop/status/restart  
   2) 仅Redhat支持
   > service 独立服务名 start/stop/status/restart
4. 独立服务的开机自启动-3种方法
   1) chkconfig启动--level默认2345
   > chkconfig [--level 启动级别] [独立服务名] [on|off]
   
```shell
netconsole     	0:off	1:off	2:off	3:off	4:off	5:off	6:off
network        	0:off	1:off	2:off	3:off	4:off	5:off	6:off
[root@localhost rpm]# chkconfig --level 2345 network on
#等同 chkconfig network on
[root@localhost rpm]# chkconfig --list

netconsole     	0:off	1:off	2:off	3:off	4:off	5:off	6:off
network        	0:off	1:off	2:on	3:on	4:on	5:on	6:off
```
   2)修改/etc/rc.d/rc.local文件      --- 推荐
   增加启动命令，如
   > /etc/init.d/httpd start
   3) 使用ntsysv命令管理自启动

推荐修改/etc/rc.d/rc.local来管理自启动，有两个好处:
   1. 对于任意任意系统，自启动服务在rc.local一目了然
   2. chkconfig只支持rpm包安装的服务


### 1.1 基于xinetd服务的管理 --- 目前基于xinetd的服务越来越少

目前常用的xinetd的服务主要有两个: 
   1) telnet： 主要用来测试端口状态，远程连接目前用的更多的是安全的ssh
   2) sync: 备份命令

```shell
[wjx@localhost ~]$ yum -y install xinetd
[wjx@localhost ~]$ yum -y install telnet-server
[root@localhost ~]# service xinetd start
```

#### 启动xinetd服务
```shell
# 修改xinetd对应服务的配置文件的disable 由yes改为no
vi /etc/xinetd.d/telnet
# 重启xinetd服务
service xinetd restart
# 查看状态，telnet对应23端口
netstat -tlun 

```

#### xinetd服务自启动

> chkconfig telnet on

注意： xinetd对应服务的关闭和开启与自启动关闭与启动会被系统关联一起修改！！！


一些常用的命令
```shell
# 查看所有服务状态
service --status-all
```
## 二 源码包安装的服务

一般是在 /usr/local/下

#### 源码包的启动

源码包的启动说明会在源码包的INSTALL文件里面，注意查看
> [root@localhost xinetd.d]# /usr/local/apache2/bin/apachetcl start|stop|restart|status

#### 源码包的自启动
```shell
#修改/etc/rc.d/rc.local文件，加入
/usr/local/apache2/bin/apachetcl start

# 查看状态，apache对应80端口
netstat -tlun 
```
