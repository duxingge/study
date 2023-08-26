# Shell

## 一. 基础

### 1. 什么是shell

shell是一个命令解释器，
将用户的命令解释为机器语言发送给内核，然后将内核的机器语言返回翻译为自然语言返回给用户。
shell 也是一个强大的编程语言，在shell中可以直接调用linux系统命令
### 2. shell分类

shell主要的语法类型有两种：

    Bourne家族: sh ksh Bash psh zsh
    C家族:      csh tcsh

### 3 语法

1.echo

    -e  支持符号转义
    例子: echo "hello word"
    例子: echo -e "hello\tword"
2.history
    
    -c: 清空历史命令 
    -w: 把缓存中的历史命令写入文件~/.bash_history
   
3.alias 别名='原命令'
    
    命令执行时检测顺序：
        1.使用绝对路径/相对路径执行的命令
        2.执行别名
        3.执行Bash内置命令
        4.按照$PATH环境变量定义的路径顺序查找到的第一个
    让别名永久生效：写入/root/.bashrc或者写入~/.bashrc中

4. 标准输入输出
    
    ![img.png](../pic/std_in_out.png)
    4.1 输出重定向
        
        命令 > 文件    以覆盖的方式，将正确的输出输出到指定的文件和设备中。
        命令 >> 文件   以覆盖的方式，将正确的输出输出到指定的文件和设备中。
   
        命令 2> 文件   以覆盖的方式，将错误的输出输出到指定的文件和设备中。
        命令 2>> 文件  以追加的方式，将错误的输出输出到指定的文件和设备中。
        
        命令 &> 文件   已覆盖的方式，将正确和错误的输出同时保存到文件。
        命令 &>> 文件   已追加的方式，将正确和错误的输出同时保存到文件。
        命令 >> 文件1 2>> 文件2 以追加的方式，分别将正确的输出保存到文件1，错误的输出保存到文件2
5. 多命令顺序执行
   
        命令1 ; 命令2       多个命令顺行执行, 命令之间没有任何的逻辑联系
        命令1 && 命令2      逻辑与，命令1执行成功后，命令2才会执行
        命令1 || 命令2      逻辑或，命令1执行不正确，命令2才会执行

6. 管道符
        
        命令1 ｜ 命令2   命令1的正确输出作为命令2的操作对象

7.通配符
? * [] '' "" `` $() \ #
![img.png](../pic/match_character.png)
![img.png](../pic/symbol.png)

## 二 变量

1. Bash中， 变量的默认类型是字符串型
2. 变量用等号连接值，等号左右不能有空格
3. 变量允许叠加，但需要使用"$var"xx或者${var}xx

### 变量分类

#### 1.用户自定义变量
```shell
# 变量赋值
name=sc
name2="$name"k1
name3=${name}k1
dd=${date}
# 变量查看
set # 查看所有变量

# 变量删除
unset name

```

#### 2. 环境变量(大写)
1. 用户自定义变量只对当前shell生效，而环境变量会对当前shell和子shell生效，
   如果写入配置文件，那么环境变量会对所有shell生效。
   
2. 用法
```shell
#设置环境变量
export 变量名=变量值

#查询环境变量
env

#删除变量
unset 变量名
```

注： pstree 可以查看进程详情以及进程嵌套关系

3. 常见环境变量
   PATH： 当前环境变量
   PS1： 当前提示符

#### 3.位置参数变量
```shell
 $0 命令本身 
 $n 第n个参数, 10及以上用${n}
 $* 命令行所有参数,所有参数当成一个整体
 $@ 命令行所有参数, 所有参数当初一个数组
 $# 参数的数目
```

#### 4.预定义变量
```shell
$? 上一个命令的执行结果. 0表示上一命令执行正确. 非0表示上一命令执行失败
$$ 当前进程ID
$! 后台运行的最后一个进程ID
```
接收键盘输入: 
   
      read [选项] [变量名]
         -t 秒数 等待用户的输入时间
         -p 提示信息
         -n 最大输入字符数
         -s 隐藏输入，用于敏感字段输入
```shell

read -t 30 -p "please input your name: " name
echo $name

read -t 30 -n 1 -p "please input your sex[M/F]: " sex
echo $sex
```
 
## 三 运算

### 数值运算与运算符

#### declare命令 了解即可

       declare [+-][选项] 变量名
         -： 给变量设置类型属性
         +： 取消变量类型属性
         -i: 将变量设置成整数类型
         -x: 将变量指定为环境变量
         -p: 显示变量类型

```shell
wangjiaxing@MacBook-Pro ~ % aa=111
wangjiaxing@MacBook-Pro ~ % bb=222
wangjiaxing@MacBook-Pro ~ % echo $aa+$bb
111+222
wangjiaxing@MacBook-Pro ~ % declare -i cc=$aa+$bb
wangjiaxing@MacBook-Pro ~ % echo $cc
333
```

#### $((运算式))     常用

```shell
wangjiaxing@MacBook-Pro ~ % aa=111
wangjiaxing@MacBook-Pro ~ % bb=222
wangjiaxing@MacBook-Pro ~ % echo $(($aa+$bb))
333
wangjiaxing@MacBook-Pro ~ % echo $(( (11+22)/3 ))
11
```
> 注意 $(命令)是执行系统命令 与 $(运算) 执行运算的区别

#### 常见运算符

![img.png](../pic/op_character.png)

## 四 环境变量配置文件

#### source
根据配置文件重新刷新

```shell
source 配置文件
#或者
. 配置文件
```

#### 常见的配置文件

      /etc/profile
      /etc/profile.d/*.sh
      ~/.bash_profile
      ~/.bashrc
      /etc/bashrc