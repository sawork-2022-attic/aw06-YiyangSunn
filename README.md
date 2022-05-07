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

* 这个版本居然并不比 multi-threaded step 快多少，稍后分析原因

* 处理 meta_Sports_and_Outdoors.json 文件需要 27s 左右；一共有 5 个线程

* 过滤掉不完整的数据之后，最终写入 474973 条记录

* 稍后将提供一份详细的报告，解释具体实现

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
