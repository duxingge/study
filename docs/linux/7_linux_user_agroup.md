# 用户和用户组

```shell

/etc/passwd
/etc/shadow
/etc/group
/etc/gshadow
/etc/skel

# 增加用户
useradd 用户名
# 设置用户密码
passwd 用户名 / passwd
# 锁定用户
passwd -l wjx
# 解锁用户
passwd -u wjx
# 重置用户附加组为xxx
usermode -G  xxx 用户名
# 将用户最后一次修改密码时间戳改为0(用户一登陆就必须更改密码)
chage -d 0 wjw

# 删除用户
userdel -r 用户名

# 切换为用户xx
su - xx

# 添加组
groupadd xxx

@ 删除组
groupdel xxx

# 将wjx加入到wjw组
gpasswd -a wjx wjw
# 将wjx移出到wjw组      
gpasswd -d wjx wjw      

# 查看当前登陆用户
w
```














---------
## 相关文件简介
对安全要求越高的服务器越需要严格的用户权限制度和严格的规范

### 相关文件
    用户信息文件 /etc/passwd
    影子文件 /etc/shadow
    组信息文件 /etc/group 和 组密码文件 /etc/gshadow

    注： 可以使用 man 5 /etc/xxx 来查看配置文件信息

#### 用户信息文件 /etc/passwd
    root:*:0:0:System Administrator:/var/root:/bin/sh

    格式  用户名:密码标志:UID:初始GID:用户说明:家目录:登陆后的shell

    解释
        间隔符 :
        * 为密码标志， 真实密码实际在/etc/shadow
        UID 用户唯一ID
            0 超级管理员
            1-499 系统用户
            500-65535 普通用户
        GID 用户组ID
            初始组: 一个用户必须有一个初始组。新创建的用户user1会新建一个user1组作为它的附加组
            附加组: 用户可以加入多个组来拥有这些组的权限。
        用户说明 对用户名解释
        家目录 即登陆后的～路径

#### 影子文件 /etc/shadow   --- 保存用户加密后的密码

    格式  用户名:加密密码:最后修改日期:允许的修改密码时间间隔:密码有效天数:密码到期警告天数:密码过期后的宽限天数:账号失效时间戳

    加密密码 
        SHA512加密
        如果密码位是!!或者* 表示没有密码，不能登陆
        密码前加!,可以使密码失效，从而使该用户不能登录
    最后修改时间：  1970后每过一天+1, 如 14323 即1970后14323天
    密码过期宽限天数：   设置为-1可以无限延期

    注1: date -d "1970-01-01 16666 days" 时间戳换算为日期
    注2: linux上的时间戳表示为 1970后每过一天+1, 如 14323 即1970后14323天

#### 组信息文件 /etc/group
    格式: 组名:组密码标志:GID:组中附加用户
    root:x:0:

#### 组影子文件 /etc/gshadow

#### 用户的家目录
    普通用户： /home/用户名 所有者和所属组都是此用户，权限是700
    超级用户: /root/    所有者和所属组都是root用户，权限是550

#### 用户的邮箱
    /var/spool/mail/用户名/

#### 用户的模版 /etc/skel
    创建新用户时，新用户家目录下的默认文件模版
```shell
[root@home152 skel]# ls -a
.  ..  .bash_logout  .bash_profile  .bashrc  .mozilla
```


## 用户相关操作

#### 添加用户 /etc/sbin/useradd

    useradd [选项] 用户名
            -u UID
            -d 家目录
            -c 用户说明
            -g 用户初始组名
            -G 用户的附加组
            -s shell 默认的shell 默认是/bin/bash
        用户添加的本质就是修改/etc/passwd,/etc/shadow,/etc/group等文件。
    例子： useradd wjx
#### 用户默认配置文件1 /etc/default/useradd
    
```shell
[root@home152 default]# cat useradd
# useradd defaults file
GROUP=100         #默认组
HOME=/home        #用户家目录
INACTIVE=-1       #密码过期宽限天数
EXPIRE=           #密码失效时间
SHELL=/bin/bash   #默认shell
SKEL=/etc/skel    #模版目录
CREATE_MAIL_SPOOL=yes # 是否建立邮箱

```
#### 用户默认配置文件2    /etc/login.defs

```shell
PASS_MAX_DAYS 99999 #密码有效期(5)
PASS_MIN_DAYS 0 #密码间隔时间(4)
PASS_MIN_LEN 5 #密码最小位数
PASS_WARN_AGE 7 #密码到期警告 (6)
UID_MIN       500 #最小和最大UID范围
GID_MAX       60000
```    
    
#### 密码设置

    passwd [选项] 用户名
        -S 查询密码状态       仅限root可用
        -l 暂时锁定用户       仅限root可用 本质是修改/etc/shadow文件(密码前+!!)
        -u 解锁用户          仅限root可用
    root 用户可以通过
        passwd + 用户名 来修改指定的用户密码
    普通用户只能
        passwd         来修改自己的密码

#### 修改用户信息

    usermode
        -u UID 修改用户UID号
        -c 用户说明
        -G 用户的附加组   注意会将-G之外的其他的附加组全部移除
        -L 临时锁定用户   本质是修改/etc/shadow文件(密码前+!)
        -U 解锁用户锁定

#### 修改用户密码状态
        
    chage [选项] 用户名
        -l      列出用户密码状态详情
        -d 日期     修改密码最后一次更改时间
        -m 天数     两次密码修改间隔(4)
        -M 天数     密码有效期(5)
        -W 天数     密码过期后的警告天数(6)
        -I 天数     密码过后的宽限时间(7)
        -E 日期     账号失效时间(8)

    chage -d 0 wjw #让用户一登陆必须修改密码 

####  删除用户

    userdel [-r] 用户名
        -r 删除用户的同时删除家目录

#### 切换用户身份 su
    
    su [选项] 用户名
        -       ：连带用户的环境变量一起切换  << 重点 >>
        -c 命令  : 仅执行一次命令，而不切换用户身份

    例子:
    su - root : 切换为root用户
    su - root -c "useradd user3"  : 不切换用户身份，但以root执行一次命令

#### 查看当前登陆用户
```shell
[root@localhost ~]# w
10:06:48 up  2:14,  3 users,  load average: 0.00, 0.00, 0.00
USER     TTY      FROM             LOGIN@   IDLE   JCPU   PCPU WHAT
root     tty1                      07:53    7:18   0.06s  0.06s -bash
wjx      pts/0    192.168.66.193   08:08    1:37m  0.04s  0.05s sshd: wjx [priv]
root     pts/2    192.168.66.193   09:59    0.00s  0.02s  0.01s w

```

#### 添加用户组
    
    groupadd [选项] 组名
        -g   指定初始组ID

#### 修改用户组
    
    groupmod [选项] 组名
        -g      修改组ID
        -n      修改组名
    例子:
        groupmod -n testgrp group1      # 将组名由group1改为testgrp


#### 删除用户组
    
    groupdel 组名 # 有初始组绑定的组名不能删除

#### 把用户添加入组/从组中删除
    
    gpasswd 选项 组名
        -a 用户名
        -d 用户名
    例子:
        gpasswd -a wjx wjw      # 将wjx加入到wjw组





###  Questions


Q: gpasswd -a wjx wjw 和 usermod -G wjw wjx 的区别?

A:

    都是用于将用户wjx添加到wjw用户组的命令，但是:
    gpasswd -a 是在当前附加组的基础上增加，不会影响wjx的其他附加组
    usermod -G 会将用户组-G之外的用户组全部删除








