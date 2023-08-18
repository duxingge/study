#1. ACL权限
## 一. 简介
###1.作用
    主要解决UGO(user+group+other)权限不够用的情况
举例：目录project的权限如下图，现在想要新加一个rx权限的试听用户，原来的UGO权限就不够用了。
![xx](./../pic/acl.png)
###2. 查看分区是否支持ACL权限（大多数分区默认支持ACL）
    dumpe2fs -h /dev/sda3
    dumpe2fs是查看指定分区详细文件系统信息的命令
    -h 进显示超级块中的信息
###2.1 临时开启ACL
mount -o remount,acl /

###2.2 永久开启分区acl
1. 配置文件 /etc/fstab. 将defaults改为defaults,acl
2. 重新挂载，使修改生效 mount -o remount /

## 二，ACL权限操作
###1. 查看ACL权限
####1.查看ACL权限
getfacle 文件名
    
####2. 设定ACL权限命令

    setfacl [选项] 文件名
        -m 设定ACL权限
        -x 删除指定的ACL权限
        -b 删除所有的ACL权限
        -d 设定默认的ACL权限
        -k 删除默认的ACL权限
        -R 递归设定ACL权限
    例子：
        setfacl -m u:st:rx /project/

#2. 文件特殊权限

#3. 文件系统属性chattr权限

#4. 系统命令sudo权限