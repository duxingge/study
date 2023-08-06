
#### 网络命令
1. write 给用户发信息, Ctrl + D保存结束
    1. /usr/bin/write
    2. 执行权限: 所有用户
    3. 语法: write <用户名>
    4. 范例: #write linzhiling
    
2. wall 给所有用户广播信息
    1. /usr/bin/wall
    2. 执行权限: 所有用户
    3. 语法: wall [message]
    4. 范例: #wall Have you had breakfast yet
    5. 英文原义： write all
    
3. ping 测试网络连通性
    1. /bin/ping
    2. 执行权限: 所有用户
    3. 语法: ping IP地址
    4. 范例: #ping 192.168.1.156

4. ifconfig 查看和设置网卡信息
    1. /sbin/ifconfig
    2. 执行权限: root
    3. 语法: ifconfig 网卡名称 IP地址
    4. 范例: #ifconfig eth0 192.168.8.250
    5. 英文原义： interface configure
    6. lo 回环网卡 127.0.0.1,每个linux都有的一个虚拟网卡

5. mail 查看和发送电子邮件
    1. /bin/mail
    2. 执行权限: 所有用户
    3. 语法: mail [用户名]
    4. 范例: #mail root
    
6. last 查看历史的用户登陆信息
   
7. lastlog 查看所有用户的最后一次登陆(包括未登录过的用户)
    1. /usr/bin/lastlog
    2. 执行权限: 所有用户
    3. 语法: lastlog
    4. 范例: #lastlog
    5. 范例: #lastlog -u 502
    
8. traceroute 显示数据包到主机间的路径,查看各个路由节点之间的网络状况
    1. /bin/traceroute
    2. 执行权限: 所有用户
    3. 语法: traceroute [域名/ip]
    4. 范例: #traceroute www.sina.com.cn
    
9. netstat 显示网络相关信息
    1. /bin/netstat
    2. 执行权限: 所有用户
    3. 语法: netstat [选项]
       > 1. -t TCP协议
       > 2. -u UDP协议
       > 3. -l 监听
       > 4. -r 路由
       > 5. -n 显示IP地址和端口
    4. 范例: 
       > 1. netstat -tlun 查看本机监听的端口(不包含ESTABLISHED的端口)
       > 2. netstat -an 查看本机所有网络连接
       > 3. netstat -rn     查看本机路由表
        
10. setup 图形化配置网络

11. mount 挂载， 将硬件设备挂载到某个目录
    1. /bin/mount
    2. 执行权限: 所有用户
    3. 语法: mount [-t 文件系统] 设备文件名 挂载点
    4. 范例: 
       > 1. mount -t iso9660 /dev/sr0 /mnt/cdrom 将设备/dev/sr0挂载到了/mnt/cdrom上面
       > 2. mount 查看所有挂载
       > 3. umount /dev/sr0 卸载挂载 （有的外载弹出前必须卸载）

> service network restart 修改完网络配置后记得重启网卡
    

    