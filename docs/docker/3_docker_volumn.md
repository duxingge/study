## 容器卷
进行容器数据的持久化，防止容器数据丢失，达到数据备份的目的。

#### 1. 格式 -v 本地目录:容器目录
>docker run --privileged=true -v  /root/bak:/bak -d -it duxingge/myubuntu:1.3 /bin/bash

#### 2. 特点
容器卷之间，容器与本机之间可以共享数据

#### 3. 容器卷的rw(默认)和ro读写规则
rw容器可读可写，ro容器只能读取

> docker run --privileged=true -v  /root/bak:/bak:ro -d -it duxingge/myubuntu:1.3 /bin/bash

#### 4. 容器卷继承 -volumes-from 父类
继承"父类"所有的容器卷共享规则

> docker run -it  --volumes-from 2cb1320de8da --name ubuntu2 64fb5868a9c5







