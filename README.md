# aw06


[Amazon Review Data (2018)](https://nijianmo.github.io/amazon/index.html) has a huge products metadata set of multiple categories.

| category                   | reviews                     | metadata                    |
|----------------------------|-----------------------------|-----------------------------|
| Amazon Fashion             | reviews (883,636 reviews)   | metadata (186,637 products) |
| All Beauty                 | reviews (371,345 reviews)   | metadata (32,992 products)  |
| Appliances                 | reviews (602,777 reviews)   | metadata (30,459 products)  |
| ...                        | ...                         | ...                         |
| Tools and Home Improvement | reviews (9,015,203 reviews) | metadata (571,982 products) |
| Toys and Games             | reviews (8,201,231 reviews) | metadata (634,414 products) |
| Video Games                | reviews (2,565,349 reviews) | metadata (84,893 products)  |

Please finish the following tasks:

- Download no less than two categories of these metadata.
- Referring the example code in this repo, convert each line in the downloaded files into a POJO of `Product` class and save the object in a database like MySQL. 
- Integrate the database containing Amazon products with your own AW04 project and build an Amazon WebPOS system.


And, of course, always try to make the system run as fast as possible.

## 阶段记录

* 此分支使用 Spring Batch 的 multi-threaded step 实现，使用 Digital_Music 和 Video_Games 两个数据集

* Sprint Batch 的 Job 对象是单例的，所以 ItemReader 是单例的；又因为 BufferedReader 读取文件是同步操作，所以这里多线程读文件没有线程问题

* 这个版本比单线程更快，主要原因是一个线程在写入数据库时，另一个线程可以开始读文件，处理下一个 batch

* 硬件环境：笔记本，CPU i7-9750H，总耗时 4389ms，用了 5 个线程（CPU 核数 - 1）

* 注意这里读文件是同步操作，增加线程数的收益会越来越小

后续再写一个加速版本，集中多个优化。

## 运行方法

此项目需要用到 MySQL 数据库，使用如下指令即可准备好运行环境：

```shell
# 创建运行环境
sudo docker run -d -p 23306:3306 -e MYSQL_ROOT_PASSWORD='$2a$10$1P5vApro6CGetwiQYGSxF.719D2qz/nbFM8FQpP59dJi85Q/p5n6m' --name mysql mysql

# 与 MySQL 容器进行交互
# mysql -u root -h 127.0.0.1 -P 23306 -p
# （输入上面的密码）
# ......
# 删除本次运行产生的数据
# drop database aw06_db;
# 不删除的话一个文件只能解析一次，否则会报错

# 删除 docker 容器和镜像
# sudo docker rm mysql; sudo docker rmi mysql
# 删除没有被任何容器使用的数据卷
# sudo docker volume prune
# 注意，如果存在目前没有被容器使用的数据卷，但以后可能被使用的，请不要用上述指令
# 查看 docker 数据卷
# sudo docker volume ls
# sudo docker volume inspect [数据卷名称]
# sudo ls [数据卷的磁盘路径]
# 看看里面放的什么内容，是不是 MySQL
```

目前有两种运行方法：

* 将要解析的 json 文件复制到 resources 目录下，并传入文件名作为命令行参数

* 使用要解析的 json 文件的绝对路径作为命令行参数

指定多个文件将依次处理。
