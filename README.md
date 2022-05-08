# 分支简介

本次作业包含三个分支，分别使用三种不同的实现：

* single-thread：该分支使用单线程处理数据，2.9GB 文件需要 1 分半钟

* multithreaded-step：该分支使用多线程 Step，2.9GB 文件需要

# 如何运行

本次作业需要用到 MySQL 数据库，使用如下指令即可准备好运行环境：

```shell
# 创建运行环境
sudo docker run -d -p 23306:3306 -e MYSQL_ROOT_PASSWORD='$2a$10$1P5vApro6CGetwiQYGSxF.719D2qz/nbFM8FQpP59dJi85Q/p5n6m' --name mysql mysql

# 与 MySQL 容器进行交互（输入上面的密码，没有单引号）
# mysql -u root -h 127.0.0.1 -P 23306 -p

# （生成的商品数据在 aw06_db.product）
# use aw06_db;
# select count(*) from product;

# 删除本次运行产生的所有数据（不删除的话每个文件只能解析一次，否则会报错）
# drop database aw06_db;

# 删除 docker 容器和镜像
# sudo docker rm mysql
# sudo docker rmi mysql

# 如果你不关心磁盘空间，下面的指令可以忽略

# 删除当前没有被任何容器使用的数据卷（注意，如果你有用 docker 容器保存的重要数据，别执行这个指令）
# sudo docker volume prune

# 查看 docker 数据卷
# sudo docker volume ls
# sudo docker volume inspect [数据卷名称]
# sudo ls [数据卷的磁盘路径]
```

准备好运行环境之后，可以用两种方法运行本次作业的代码：

* 将要解析的 json 文件复制到 resources 目录下，并传入文件名作为命令行参数

* 使用要解析的 json 文件的绝对路径作为命令行参数

可以一次指定多个文件，多个文件将依次处理。
