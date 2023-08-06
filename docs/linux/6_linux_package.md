### Linux软件包

####分类
    1. 源码包： 
        1. 缺点：
            1. 安装慢(编译慢)，
            2. 安装步骤多，容易出错
        2. 优点: 
            1. 开源(可以看到源代码),能力足够，可以根据自己的需求更改。
            2. 开源安装的软件一般效率更高(5%左右)
    2. 二进制包(RPM包)： 已经编译好的二进制包,但不能看到源代码了
            1.缺点:
                1. 不能看到源代码
                2. 依赖性
            2.优点:
                1. 包管理简单，只需要几个命令就可以实现包的安装从，查询，升级和卸载
                2. 安装速度比源码包快
#### 命名规则
![xx](./..//pic/rpm_package_name_rule.png "sda")

包全名: httpd-2.2.15-15.el6.centos.1.i686.rpm
包名: httpd   （包名其实是搜索 /var/lib/rpm下的数据库）

#### 依赖
1. 树形依赖: a->b->c
2. 循环依赖: a->b->c->a 需要一起装

注: 依赖查询网站: www.rpmfind.net
注： xxxx.so.xxxx是某个包中的某一个模块，可以在www.rpmfind.net里面查具体的包
```shell
[root@home152 Packages]# rpm -ivh mysql-connector-odbc-5.2.5-8.el7.x86_64.rpm
错误：依赖检测失败：
libodbcinst.so.2()(64bit) 被 mysql-connector-odbc-5.2.5-8.el7.x86_64 需要
```
#### rpm安装
1. rpm -ivh 包全名
    -i install 安装
    -v verbose 显示详细信息
    -h hash 显示安装进度
   




