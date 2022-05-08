# 分支说明

此分支使用多线程 Step 实现，虽然看起来只是加了一个 TaskExecutor，但其实它的并发度是很高的。

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
