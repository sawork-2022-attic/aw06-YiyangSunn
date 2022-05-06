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

* 目前实现了一个单线程版本，使用 Digital_Music 和 Video_Games 两个数据集

* processor 里面过滤或者不过滤缺失数据都需要 20s 左右

* 不过滤的话写入大约 14 万条数据；过滤的话写入 7 万多条数据

* 硬件环境：笔记本，CPU i7-9750H

后续将实现多线程版本，并进一步提速。

## 运行方法

目前有两种运行方法：

* 将要解析的 json 文件复制到 resources 目录下，并传入文件名作为命令行参数

* 使用要解析的 json 文件的绝对路径作为命令行参数

指定多个文件将依次处理。
