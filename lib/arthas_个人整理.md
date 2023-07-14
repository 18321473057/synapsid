







# 									Arthas  阿里开源 中文工具



##### 中文文档

>  https://arthas.aliyun.com/doc



##### 下载安装

>-  官方模拟程序- （建议自己写模拟代码）
>  - curl -O https://arthas.aliyun.com/math-game.jar
>  - java -jar math-game.jar
>
>- 在线安装简版工具包
>  - curl -O https://arthas.aliyun.com/arthas-boot.jar
>  - java -jar arthas-boot.jar
>  - java -jar arthas-boot.jar --telnet-port 9998 --http-port -1    指定端口启动
>- 在线安装全量文件 （不推荐 卡）
>  - curl -O https://alibaba.github.io/arthas/arthas-boot.jar   
>  - jar -xvf arthas-boot.jar
>  - java -javaagent:/tmp/test/arthas-agent.jar -jar math-game.jar       java 探针方式启动
>- 离线文件包安装全量文件
>  - https://arthas.aliyun.com/download/latest_version?mirror=aliyun  下载地址
>  - unzip arthas-packaging.zip
>  - ./as.sh -h 或者 java -jar arthas-boot.jar -h



### 命令 

##### dashboard  图形界面看板

> - 线程区
>
> | ID     | NAME   | GROUP    | PRIORITY   | STATE    | %CPU          | DELTA_TIME            | INTERRUPTED          | daemow   |
> | ------ | ------ | -------- | ---------- | -------- | ------------- | --------------------- | -------------------- | -------- |
> | 线程ID | 线程名 | 线程组名 | 线程优先级 | 线程状态 | 线程CPU使用率 | 线程运行总时间，分:秒 | 当前线程的中断位状态 | 守护线程 |
>
> - 内存区-(Java各版本显示字段有出入)
>
>   - heap  堆内存
>
>     | par_eden_space | par_survivor_space | cms_old_gen    |
>     | -------------- | ------------------ | -------------- |
>     | eden区内存大小 | survivor区内存大小 | 老年代内存大小 |
>
>   - nonheap  非堆内存
>
>     | code_cache                     | metaspace             | compressed_class_space               | direct                                             | mapped                                                       |
>     | ------------------------------ | --------------------- | ------------------------------------ | -------------------------------------------------- | ------------------------------------------------------------ |
>     | 代码缓冲区(Java JIT半编译优化) | 元空间(永久代/方法区) | CSS 类压缩空间  64位机器使用32位指针 | NIO基于通道和缓冲区,Native函数库直接分配堆外内存。 | 将文件内容映射到内存，供应用程序直接使用，省去数据在 内核空间 和 用户空间 之间传输的损耗。 |
>
>     
>
> - 环境区
>
> | os .name     | os .version  | java. version | java.homes | systemload.average | processor | timestamp/uptime |
> | ------------ | ------------ | ------------- | ---------- | ------------------ | --------- | ---------------- |
> | 操作系统名称 | 操作系统版本 | Java版本      | java.homes | 系统负载平均值     | CPU核心数 | 系统时间         |



#####  堆转储

>- heapdump  /u01/app/dump.hprof             生成堆转储文件
>
>- heapdump --live /u01/app/dump.hprof     转储存活的对象



##### 线程

>- thread  -n  10  展示前十繁忙线程
>
>- thread  3        展示线程ID为3的详情
>
>- thread  -b   查找死锁



##### 编译、反编译与热加载

>- jad  com.tn.demo.simple.Demo   反编译并展示文件内容
>- jad --source-only com.tn.demo.simple.Demo > /u01/app/arthas/demo.java   反编译代码到 demo.java文件 
>
>- vim demo.java  修改文件
>- mc /u01/app/arthas/Demo.java            编译文件  文件将存储以arthas目录为根目录 以java包名做目录
>- mc -d /u01/app/arthas /u01/app/arthas/Demo.java        编译文件 指定根目录 
>- redefine /u01/app/arthas/com/tn/demo/simple/Demo.classs  热加载文件 
>  - 不能增加字段和方法
>  - 正在执行的方法无法生效。
>  - reset命令对redefine的类无效
>  - jad/watch/trace/monitor/tt 将重置 redefine 命令
>  - 重启后也会失效



##### 新版热加载  (推荐)

>- retransform /u01/app/arthas/com/tn/demo/simple/Demo.class
>  - jad/watch/trace 等命令 不会直接将热加载的文件还原
>  - 如要还原 请先retransform -d id
>- retransform -l  列出 retransform id
>- retransform -d 1                  删除retransform
>- retransform --deleteAll          删除所有retransform



##### 退出与停止

>- quit  退回arthas  只是退出当前 Arthas 客户端，Arthas 的服务器端并没有关闭，所做的修改也不会被重置
>- stop 停止arthas（建议）   关闭 Arthas 服务器之前，会重置掉所有做过的增强类。但是用 redefine 重加载的类内容不会被重置。
>  - 不停止 会损耗大约10%性能
>  - 不停止无法监控其他程序，会端口冲突



##### 方法、本地链路监控

>- monitor -c 5 com.tn.demo.simple.Demo dodo          							     监控方法的执行情况，每5秒统计一次。默认120s。
>
>- watch  com.tn.demo.simple.Demo dodo "{params,returnObj}" -x 2      查看方法的入参和返回   属性遍历深度为2
>- trace com.tn.demo.simple.Demo dodo '#cost > .5'                                   查看方法内部调用路径 以及每个耗时    查询耗时0.5ms以上方法
>- tt  com.tn.demo.simple.Demo dodo                                                             记录方法执行详情
>- tt -i  1001                                                                                                            查看 index为1001的方法执行详情
>- tt -i index -p                                                                                                       回溯执行index为1001的方法



​	





## 													非Arthas内容



##### 优化抉择

>- 访问量到达系统设计目标。 建议使用更强大的服务器、扩容、重构系统等。
>
>- 系统因内存不足或泄露、线程竞争或死锁、慢SQL、GC选择、等待依赖系统、网络带宽等造成的卡顿或假死 是我们需要调优的点。



##### 建议

>- 系统调优是建立在对业务和代码足够了解的情况下进行。
>- Xms 与 Xmx 大小设置一致。
>- -XX:+PrintGc  打印GC信息，检查GC频率以及回收内存大小是否符合预期。
>- 不建议直接线上dump堆转储文件 可设置 -XX:+HeapDumpOnOutOfMemoryError xxx.Problem01   在OOM时生成堆转储文件。
>- 多核大内存服务器建议尝试使用G1回收。



##### 常用命令

>- jsp  列出Java 服务
>
>- jinfo pId  打印服务Java信息
>
>- jstat  -gc  pId  500    Java状态 GC信息 500ms 刷新一次  监控内存分布情况
>
>| S0C               | S1C                   | S0U            | S1U                | EC           | EU               | OC         | OU               |                |
>| ----------------- | --------------------- | -------------- | ------------------ | ------------ | ---------------- | ---------- | ---------------- | -------------- |
>| S0区大小          | S1 区大小             | S0区已使用大小 | S1区已使用大小     | Eden区的大小 | Eden区已使用大小 | 老年代大小 | 老年代已使用大小 |                |
>| **MC**            | **MU**                | **CCSC**       | **CCSU**           | **YGC**      | **YGCT**         | **FGC**    | **FGCT**         | **GCT**        |
>| 元空间/方法区大小 | 元空间/方法区使用大小 | 压缩类空间大小 | 压缩类空间使用大小 | YGC 次数     | YGC 次数耗时     | FGC 次数   | FGC 耗时         | 所有GC的总耗时 |
>
>
>
>- jmap -histo 15040|head -50      查看前50 堆内存对象占比
>- jmap   -dump :format=b,file=20230708.dump.b     生成二进制堆转储文件
>
>
>
>- top   看进程 CPU 高              内存 cpu 情况
>- top -Hp Pid   找线程CPU高的
>- jstack  Pid     展示线程列表，与top -Hp 查找的线程对比查找卡顿的线程
>



##### 查看参数

>- java  -开头    标准参数
>
>- java -X开头  非标参数  非标参数因版本不同而不同
>
>- java -XX开头  非标调优参数 



##### Java 8 内存分区图

![img](E:/学习/图片/aHR0cHM6Ly9waWMxLnpoaW1nLmNvbS84MC92Mi0yODlmZTE5NDA0M2NiOTQ3MjE2OGE1YTczZmQ1ZmYwY18xNDQwdy5qcGc)



 





