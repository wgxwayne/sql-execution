# sql-execution

## 项目介绍

本项目主要是对SQL进行打分，来判断SQL索引的好坏。手段是得到SQL的explain执行计划和耗时。给执行计划的每个字段和耗时赋予相应的分值，计算出一条SQL的总得分。于此同时，统计一些其他信息：项目名称、项目版本、SQL模板、具体SQL、表名、数据库名等并保存到数据库中。



**本项目需要配合[explain-interception-starter](https://gitlab.gz.cvte.cn/i_weiguoxiong/explain-interception-starter)项目进行**。

- [explain-interception-starter](https://gitlab.gz.cvte.cn/i_weiguoxiong/explain-interception-starter)利用拦截器统计SQL执行计划信息和耗时

- 本项目主要是对执行计划进行分析，计算分值



## 主要功能

1、对SQL进行打分

2、获取一个项目中所有的SQL语句及其得分

3、从高到底排序一个项目中的SQL耗时

4、获取SQL得分排序



## 使用方法

1、如果一个业务项目第一次接入本项目，需要赋予该项目一个projectId；通过 http://localhost:8080/init//generateProjectId 获取，并配置到业务项目的配置文件中

2、在业务项目中的配置文件中配置以下信息

```
# 是否开启 explain-interception-starter 拦截器
explain.interception: true

# 获取项目名称和版本
name: '@project.name@'
version: '@project.version@'

# 配置projectId
projectId: 286512107663430

# SQL打分项目接收数据的接口
url-api: http://localhost:8080/init/allData

```

3、在业务项目中执行一条sql语句，执行计划信息和耗时以及得分会保存到本项目的数据库表中











