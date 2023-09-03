# 备份与恢复


## 备份
#### 需要备份的目录

    /root/目录
    /home/目录
    /var/spool/mail/目录
    /etc/目录
    /其他目录
#### 备份策略

1. 完全备份: 将所有的数据完全备份，备份整个分区或者目录。

2. 增量备份: 先完全备份一次，然后每次备份备份增量。

### 一 备份命令 dump

        dump [选项] 备份后的文件名 原文件/目录
            -[0-9] 备份级别，一般选0，1-9为第1-9次增量备份, 备份目录不支持1-9增量备份
            -f 备份之后的文件名
            -j 调用bzlib进行压缩备份，即压缩成.bz2格式
            -u 压缩成功后，记录备份时间到/etc/dumpdates中, 备份目录时不能使用此项
            -v 显示压缩过程
            -W 查看分区的备份时间和备份级别
        例子：
            全量备份分区: dump -0uj -f /root/disk1.bak.bz2 /mnt/disk1
            增量备份分区: dump -1uj /root/disk1.bak1.bz2 /mnt/disk1
            全量备份目录: dump -0j -f /root/study.bak.bz2 /mnt/disk5/study/
            

例子:
```shell
[root@localhost disk1]# dump -0uj -f /root/disk1.bak.bz2 /mnt/disk1
  DUMP: mount: bad UUID
  DUMP: Date of this level 0 dump: Sun Sep  3 10:10:50 2023
  DUMP: Dumping /dev/nvme0n2p1 (/mnt/disk1) to /root/disk1.bak.bz2
  DUMP: Label: none
  DUMP: Writing 10 Kilobyte records
  DUMP: Compressing output at compression level 2 (bzlib)
  DUMP: mapping (Pass I) [regular files]
  DUMP: mapping (Pass II) [directories]
  DUMP: estimated 64 blocks.
  DUMP: Volume 1 started with block 1 at: Sun Sep  3 10:10:50 2023
  DUMP: dumping (Pass III) [directories]
  DUMP: dumping (Pass IV) [regular files]
  DUMP: Closing /root/disk1.bak.bz2
  DUMP: Volume 1 completed at: Sun Sep  3 10:10:50 2023
  DUMP: 50 blocks (0.05MB) on 1 volume(s)
  DUMP: finished in less than a second
  DUMP: Date of this level 0 dump: Sun Sep  3 10:10:50 2023
  DUMP: Date this dump completed:  Sun Sep  3 10:10:50 2023
  DUMP: Average transfer rate: 0 kB/s
  DUMP: Wrote 50kB uncompressed, 11kB compressed, 4.546:1
  DUMP: DUMP IS DONE
[root@localhost disk1]# cd /root/
[root@localhost ~]# ls
anaconda-ks.cfg  disk1.bak.bz2  testcode
[root@localhost disk1]# cat /etc/dumpdates
/dev/nvme0n2p1 0 Sun Sep  3 10:10:50 2023 -0400
[root@localhost disk1]# dump -1uj /root/disk1.bak1.bz2 /mnt/disk1
  DUMP: mount: bad UUID
  DUMP: Only level 0 dumps are allowed on a subdirectory
  DUMP: The ENTIRE dump is aborted.
[root@localhost disk1]# dump -1uj -f /root/disk1.bak1.bz2 /mnt/disk1
  DUMP: mount: bad UUID
  DUMP: Date of this level 1 dump: Sun Sep  3 10:16:36 2023
  DUMP: Date of last level 0 dump: Sun Sep  3 10:10:50 2023
  DUMP: Dumping /dev/nvme0n2p1 (/mnt/disk1) to /root/disk1.bak1.bz2
  DUMP: Label: none
  DUMP: Writing 10 Kilobyte records
  DUMP: Compressing output at compression level 2 (bzlib)
  DUMP: mapping (Pass I) [regular files]
  DUMP: mapping (Pass II) [directories]
  DUMP: estimated 55 blocks.
  DUMP: Volume 1 started with block 1 at: Sun Sep  3 10:16:37 2023
  DUMP: dumping (Pass III) [directories]
  DUMP: dumping (Pass IV) [regular files]
  DUMP: Closing /root/disk1.bak1.bz2
  DUMP: Volume 1 completed at: Sun Sep  3 10:16:37 2023
  DUMP: 50 blocks (0.05MB) on 1 volume(s)
  DUMP: finished in less than a second
  DUMP: Date of this level 1 dump: Sun Sep  3 10:16:36 2023
  DUMP: Date this dump completed:  Sun Sep  3 10:16:37 2023
  DUMP: Average transfer rate: 0 kB/s
  DUMP: Wrote 50kB uncompressed, 11kB compressed, 4.546:1
  DUMP: DUMP IS DONE
[root@localhost disk1]# dump -W
  DUMP: mount: bad UUID
Last dump(s) done (Dump '>' file systems):
  /dev/nvme0n2p1	(/mnt/disk1) Last dump: Level 1, Date Sun Sep  3 10:16:36 2023
  /dev/nvme0n2p5	(/mnt/disk5) Last dump: never
[root@localhost disk1]# ls /root/
anaconda-ks.cfg  disk1.bak1.bz2  disk1.bak.bz2  testcode

[root@localhost disk1]# cd /mnt/disk5/
[root@localhost disk5]# ls
lost+found
[root@localhost disk5]# mkdir study
[root@localhost disk5]# dump -0j -f /root/study.bak.bz2 /mnt/disk5/study/
  DUMP: mount: bad UUID
  DUMP: Date of this level 0 dump: Sun Sep  3 10:23:57 2023
  DUMP: Dumping /dev/nvme0n2p5 (/mnt/disk5 (dir /study)) to /root/study.bak.bz2
  DUMP: Label: none
  DUMP: Writing 10 Kilobyte records
  DUMP: Compressing output at compression level 2 (bzlib)
  DUMP: mapping (Pass I) [regular files]
  DUMP: mapping (Pass II) [directories]
  DUMP: estimated 85 blocks.
  DUMP: Volume 1 started with block 1 at: Sun Sep  3 10:23:57 2023
  DUMP: dumping (Pass III) [directories]
  DUMP: dumping (Pass IV) [regular files]
  DUMP: Closing /root/study.bak.bz2
  DUMP: Volume 1 completed at: Sun Sep  3 10:23:57 2023
  DUMP: 80 blocks (0.08MB) on 1 volume(s)
  DUMP: finished in less than a second
  DUMP: Date of this level 0 dump: Sun Sep  3 10:23:57 2023
  DUMP: Date this dump completed:  Sun Sep  3 10:23:57 2023
  DUMP: Average transfer rate: 0 kB/s
  DUMP: Wrote 80kB uncompressed, 11kB compressed, 7.273:1
  DUMP: DUMP IS DONE
[root@localhost disk5]# ls /root/
anaconda-ks.cfg  disk1.bak1.bz2  disk1.bak.bz2  study.bak.bz2  testcode
```


### 二 恢复命令 restore

    restore [模式选项] [选项]
        支持四种模式,不能同时使用:
            -C 比较备份数据与实际数据的变化
            -i 进入交互模式,手工选择需要恢复的文件
            -t 查看模式，查看有哪些数据
            -r 还原模式, 用于数据还原
        -f 指定文件
    例子:
    查看目前数据与备份文件不同   restore -C -f disk1.bak.bz2
    查看备份文件内容            restore -t -f /root/disk1.bak1.bz2
    在当前目录下恢复全量文件      restore -r -f /root/disk1.bak.bz2
    在当前目录下恢复增量文件      restore -r -f /root/disk1.bak1.bz2
    
```shell
[root@localhost ~]# restore -t -f /root/disk1.bak.bz2
Dump tape is compressed.
Dump   date: Sun Sep  3 10:10:50 2023
Dumped from: the epoch
Level 0 dump of /mnt/disk1 on localhost.localdomain:/dev/nvme0n2p1
Label: none
         2	.
        11	./lost+found
        13	./f1.txt
[root@localhost ~]# restore -t -f /root/disk1.bak1.bz2
Dump tape is compressed.
Dump   date: Sun Sep  3 10:16:36 2023
Dumped from: Sun Sep  3 10:10:50 2023
Level 1 dump of /mnt/disk1 on localhost.localdomain:/dev/nvme0n2p1
Label: none
         2	.
        14	./f2.txt
[root@localhost bak]# restore -r -f /root/disk1.bak.bz2
Dump tape is compressed.
restore: ./f1.txt: EA set security.selinux:unconfined_u:object_r:unlabeled_t:s0 failed: Function not implemented
[root@localhost bak]# ls
f1.txt  lost+found  restoresymtable
[root@localhost bak]# restore -r -f /root/disk1.bak1.bz2
Dump tape is compressed.
restore: ./f2.txt: EA set security.selinux:unconfined_u:object_r:unlabeled_t:s0 failed: Function not implemented
[root@localhost bak]# ls
f1.txt  f2.txt  lost+found  restoresymtable
```