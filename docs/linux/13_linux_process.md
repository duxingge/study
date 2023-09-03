# 进程

## 一 进程管理

### 1.1 进程查看
显示所有进程信息

```shell
[root@localhost ~]# ps aux
# USER 进程运行的用户
# %CPU 进程占有CPU的百分比 
# %MEM 进程占用物理内存的百分比 
# VSZ 虚拟内存  单位KB 
# TTY 该进程是哪个终端运行的 tty1-tty6代表本地字符终端，tty7代表图形终端。pts/0-256代表虚拟远程终端
# STAT 进程状态: R 运行状态 S 睡眠 T 停止 s: 包含子进程 + 位于后台
# START 进程启动时间
# TIME 进程占用的CPU时间 
# COMMAND 产生该进程的命令


USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
root         1  0.0  0.5  92452  7760 ?        Ss   07:52   0:00 /usr/lib/systemd/systemd --switched-root --system --deserialize 22
root         2  0.0  0.0      0     0 ?        S    07:52   0:00 [kthreadd]
root         3  0.0  0.0      0     0 ?        I<   07:52   0:00 [rcu_gp]
```
### 1.2 查看进程状态
> top -d 3      # 每隔3秒刷新 

```shell
[root@localhost ~]# top
# 默认CPU排序, shift+M 内存排序 shift+P CPU排序 shift+N PID排序 ? 打开帮助
top - 09:28:03 up  1:35,  3 users,  load average: 0.00, 0.00, 0.00
Tasks: 197 total,   1 running, 114 sleeping,   0 stopped,   0 zombie
%Cpu(s):  0.2 us,  0.2 sy,  0.0 ni, 99.7 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
KiB Mem :  1494296 total,  1198224 free,   136940 used,   159132 buff/cache
KiB Swap:  2097148 total,  2097148 free,        0 used.  1272820 avail Mem
# 
  PID USER      PR  NI    VIRT    RES    SHR S  %CPU %MEM     TIME+ COMMAND
  893 root      20   0  425728  14892  10856 S   0.3  1.0   0:16.76 NetworkManager
 2119 root      20   0       0      0      0 I   0.3  0.0   0:00.62 kworker/0:0-eve
 2180 root      20   0  112356   4184   3388 R   0.3  0.3   0:00.02 top
    1 root      20   0   92452   7760   5252 S   0.0  0.5   0:00.67 systemd
    2 root      20   0       0      0      0 S   0.0  0.0   0:00.01 kthreadd
    3 root       0 -20       0      0      0 I   0.0  0.0   0:00.00 rcu_gp

# 第一行 任务队列信息
## top - 09:28:03(当前时间)  up  1:35(运行了1小时35分),  3 users (当前登陆了三个用户),  load average: 0.00, 0.00, 0.00(系统在1，5，15分钟的平均负载，一般小于1，大于1的话为超负荷)
# 第二行 进程信息
## Tasks: 197 total(总进程数),   1 running(正在运行的进程数), 114 sleeping(睡眠的进程),   0 stopped(停止的进程数),   0 zombie (僵尸进程数)
# 第三行 CPU信息
## %Cpu(s):  0.2 us,  0.2 sy,  0.0 ni, 99.7 id(空闲CPU的百分比，如果小于20,需要关注增加资源 ),  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
# 第四行 物理内存信息 单位KB
## KiB Mem :  1494296 total,  1198224 free,   136940 used,   159132 buff/cache
# 第五行 swap分区信息 单位KB

# 重要的关注点为 
#   平均负载不大于1(参考为内核数的一半)  如 load average: 0.00, 0.00, 0.00 
#   空闲CPU大于20                   如 99.7 id
#   内存空闲                        如 1198224 free              


```

### 1.3 杀死进程

#### kill
1. kill -l 查看kill相关进程信号

2. kill [信号] PID

            -1 重启PID对应进程
            -9 强制杀死PID对应进程
            -15 关闭PID对应进程，默认的
3. killall  [信号] 进程名
    按进程名杀死所有进程
4. pkill [选项] [信号] 进程名
    与killall相同
   
        -t 终端号， 常用于按终端号踢出用户
```shell
[root@localhost ~]# w
 10:15:40 up  2:22,  3 users,  load average: 0.00, 0.00, 0.00
USER     TTY      FROM             LOGIN@   IDLE   JCPU   PCPU WHAT
root     tty1                      07:53   16:10   0.06s  0.06s -bash
wjx      pts/0    192.168.66.193   08:08    1:45m  0.04s  0.05s sshd: wjx [priv]
root     pts/2    192.168.66.193   09:59    2.00s  0.03s  0.01s w
[root@localhost ~]# pkill  -t pts/0
[root@localhost ~]# w
 10:16:08 up  2:23,  2 users,  load average: 0.00, 0.00, 0.00
USER     TTY      FROM             LOGIN@   IDLE   JCPU   PCPU WHAT
root     tty1                      07:53   16:38   0.06s  0.06s -bash
root     pts/2    192.168.66.193   09:59    0.00s  0.03s  0.01s w
```



-------------


## 查看CPU信息
```shell
[root@localhost ~]# lscpu
Architecture:          aarch64
Byte Order:            Little Endian
CPU(s):                2
On-line CPU(s) list:   0,1
Thread(s) per core:    1
# CPU内核数
Core(s) per socket:    2
座：                 1
NUMA 节点：         1
型号：              0
BogoMIPS：            48.00
L1d 缓存：          128K
L1i 缓存：          192K
L2 缓存：           12288K
NUMA 节点0 CPU：    0,1
Flags:                 fp asimd evtstrm aes pmull sha1 sha2 crc32 atomics fphp asimdhp cpuid asimdrdm jscvt fcma lrcpc dcpop sha3 asimddp sha512 asimdfhm dit uscat ilrcpc flagm ssbs sb paca pacg dcpodp flagm2 frint
```

## 二 工作管理
注意： 前台与用户交互相关的进程放入后台会暂停，而不是后台执行
#### 把进程放入后台
1. &
> 例子： tar -zcf etc.tar /etc &
2. ctrl+z
> 例子: top # 执行的过程中按ctrl+z

```shell
# jobs # 查看后台进程 ## [1] jobID
wangjiaxing@MacBook-Pro ~ % jobs
[1]  - running    tar -zcf etc.tar /
[2]  + suspended  top
```


#### 把后台暂停的工作放到前台执行
> fg %jobID

```shell
wangjiaxing@MacBook-Pro ~ % jobs
[2]  + suspended  top
wangjiaxing@MacBook-Pro ~ % fg %2
```

#### 把后台暂停的工作放到后台执行
> bg %jobId

注意： 与前台用户交互的job不能放到后台执行,仍然保持暂停状态

## 三 系统资源查看

1. 监控系统资源
> vmstat [刷新时间] [刷新次数]
```shell
# 显示系统资源，间隔2秒，刷新3次
[root@localhost ~]# vmstat 2 3
# 显示的内容基本上top都有，主要关注 free memory, 和id cpu (空闲cpu) 
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 0  0      0 1203828   2716 149916    0    0   125     6   59  127  0  0 99  0  0
 0  0      0 1203860   2716 149984    0    0     0     0   80  100  0  0 100  0  0
 0  0      0 1203892   2716 149984    0    0     0     0   62   81  0  0 100  0  0
```

2. 开机检测信息
> dmesg
 
```shell
# 例子： 查看开机检测的CPU信息
[root@localhost ~]# dmesg | grep CPU
[    0.000000] Booting Linux on physical CPU 0x0000000000 [0x610f0000]
[    0.000000] Detected PIPT I-cache on CPU0
[    0.000000] CPU features: detected: GIC system register CPU interface
[    0.000000] CPU features: detected: Spectre-v4
[    0.000000] CPU features: detected: Address authentication (IMP DEF algorithm)
[    0.000000] SLUB: HWalign=64, Order=0-3, MinObjects=0, CPUs=2, Nodes=1
[    0.000000] rcu: 	RCU restricting CPUs from NR_CPUS=4096 to nr_cpu_ids=2.
[    0.000000] GICv3: CPU0: found redistributor 0 region 0:0x000000002c100000
[    0.000000] rcu: 	Offload RCU callbacks from CPUs: (none).
[    0.000517] smp: Bringing up secondary CPUs ...
[    0.000686] Detected PIPT I-cache on CPU1
```
3. 查看内存情况
   
        free
            -b 以byte为单位
            -m 以MB为单位
            -k 以KB为单位
            -g
```shell
[root@localhost ~]# free -m
              total        used        free      shared  buff/cache   available
Mem:           1459         134        1175           8         149        1244
Swap:          2047           0        2047
```
4. 查看CPU信息

```shell
[root@localhost ~]# cat /proc/cpuinfo
processor	: 0
BogoMIPS	: 48.00
Features	: fp asimd evtstrm aes pmull sha1 sha2 crc32 atomics fphp asimdhp cpuid asimdrdm jscvt fcma lrcpc dcpop sha3 asimddp sha512 asimdfhm dit uscat ilrcpc flagm ssbs sb paca pacg dcpodp flagm2 frint
CPU implementer	: 0x61
CPU architecture: 8
CPU variant	: 0x0
CPU part	: 0x000
CPU revision	: 0

processor	: 1
BogoMIPS	: 48.00
Features	: fp asimd evtstrm aes pmull sha1 sha2 crc32 atomics fphp asimdhp cpuid asimdrdm jscvt fcma lrcpc dcpop sha3 asimddp sha512 asimdfhm dit uscat ilrcpc flagm ssbs sb paca pacg dcpodp flagm2 frint
CPU implementer	: 0x61
CPU architecture: 8
CPU variant	: 0x0
CPU part	: 0x000
CPU revision	: 0

```

5. 查看启动时间和负载(其实就是top的第一行)
```shell
[root@localhost ~]# uptime
 13:11:09 up 21 min,  2 users,  load average: 0.00, 0.00, 0.00
```

6. 查看系统与内核相关信息 uname -a

7. 判断系统位数

没有专门的命令，但可以借助file命令来查看
```shell
[root@localhost ~]# file /bin/ls
/bin/ls: ELF 64-bit LSB executable, ARM aarch64, version 1 (SYSV)
```

8. 查看Linux发型版本
```shell
[root@localhost ~]# cat /etc/os-release
NAME="CentOS Linux"
VERSION="7 (AltArch)"
```
9. 列出进程打开/使用的文件信息

        lsof [选项]
            -s 字符串 :    列出以字符串开头的进程打开的文件
            -p pid :      列出pid进程打开的文件列表
            -u 用户名:     列出某个用户打开的文件列表


## 四 定时任务

#### 启动crond
> service crond resatrt
> chkconfig crond on

#### crontab设置

        crontab [选项]
            -e 进入编辑
            -l 查看
            -r 全部删除

```shell
# crontab支持cron表达式. 分 时 日 月 周几 执行对应命令
# * * * * * cammand

#例子： 每周2五点五分重启
5 5 * * 2 /sbin/shutdown -r now 
#例子2： 每天0点备份
0 0 */1 * * /root/sh/bak.sh

[root@localhost etc]# crontab -e
[root@localhost etc]# crontab -l
* * * * * echo 1111 >> /root/testcode/f1.txt
[root@localhost etc]# cat /root/testcode/f1.txt
1111
1111
```

注意： crontab 中 %有特殊含义，对应的shell脚本中的%前要加\进行转义