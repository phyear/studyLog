# sql基础语句

## select语句
`SELECT * | {[DISTINCT] column|expression [alias],...}
FROM	table;`
* SELECT 表示要取哪些列
* FROM 表示要从哪些表中取


```
SELECT *FROM employees; ---获取全部列
SELECT last_name, salary, salary + 300	FROM employees; ---选取一部分
```
> ### 给列取别名(AS|"")

![](../img/SqlAs.png)


>### NULL

NULL表示 不可用、未赋值、不知道、不适用 ， 它既不是0 也不是空格。

**记住：一个数值与NULL进行四则运算，其结果是？ NULL**

>### 符串连接操作符： “||”

```
select first_name || last_name || '来自成都' from employees ---拼接字符串

select first-name || last_name from employees; ---拼接列
```



>### distinct ---去重

SELECT  name FROM mytable;

![](../img/SqlContact1.png)

SELECT DISTINCT name FROM mytable;

![](../img/SqlContact2.png)

## 一般不在循环中执行sql语句，最好在sql中写

## 查询条件与排序

> ### where 

```
select * from mytable where id=2 
```
>### 比较操作

|比较操作符|意义|
|:--|:--|
=|等于
`>`|大于
`>=`|	大于等于
`<`	|小于
`<=`|	小于等于
`<>`|	不等于
`BETWEEN ...AND...`	|在两个值之间
`IN(set)`	|在一个集合范围内
`LIKE`	|匹配一个字符串样子，可以使用%通配符
`IS NULL`	|是一个空值，注意不能使用 =NULL

---
![](../img/Sqlmain.png)

----
>> =

`SELECT *  FROM mytable WHERE id=1`

![](../img/Sqleq.png)

>> `>`

`SELECT *  FROM mytable WHERE id>1`

>> `>=`

`SELECT *  FROM mytable WHERE id>=1`
![](../img/Sqlmain.png)
>> `<=`

`SELECT *  FROM mytable WHERE id<=3`

![](../img/Sqllt.png)

>> `<>`

`SELECT *  FROM mytable WHERE id<>3`

![](../img/Sqlnot.png)

>> BETWEEN  and

`SELECT *  FROM mytable WHERE id BETWEEN 2 and 4;`

![](../img/SqlBet.png)

>> like 

`SELECT *  FROM mytable WHERE name like '_3%'`

![](../img/Sqllike1.png)

>ORDER BY --默认升序排序

`SELECT *  FROM mytable ORDER BY id desc;`

![](../img/Sqlorder.png)



# DML语句（数据操作语句） 

## insert --插入数据
---
### insert四种方式：
* 方式一：表名+列名
```
INSERT INTO table [(column [, column...])]
VALUES	(value [, value...]);

insert into mytable(id,name)
values(6,'adas');
```
* 方式二：只写表名
```
INSERT INTO table
VALUES	(value [, value...]);

insert into mytable
values(6,'adas');
```

* 方式三：从另一个表中 Copy 一行

```
INSERT INTO table [(column [, column...])]
subquery;

insert into mytb(id,name) SELECT id,name from mytable where id>3
```
![](../img/Sqlinsert1.png)

```
SELECT * from mytb
```

![](../img/Sqlinsert2.png)

* 方式四：子查询插入 ---mysql中语句运行未成功 可以试试oracle--

```
insert into(
SELECT id,name from mytb where id=3
)values(1,'aa');
```
## update --修改数据
---
### 更新一列数据
```
update mytb set name='sda' where id=1
```

### 使用子查询的结果作为更新后的值
```
update mytbs set name=(select name from mytable where id=4) where id=5

```
>**mysql不允许子查询的表和更新的表是同一张表**
>**注意约束**

## delete --删除数据
---
### 删除一条数据
```
delete from mytbs where id=1
```
### 删除全部数据
```
delete from mytbs;



truncate table mytbs;
```

>**注意约束**

# 多表查询

> 内连接：只选取两张表中等值或者说连接列值对应的数据

一张表为：
id|name
:--|:--
1|23
2|45
3|33

另一张表为：
id|home
:--|:--
1|aa
4|bb
7|34e

查询出来的数据：

id|name|home
:--|:--|:--
1|23|aa



## 外连接
>左外连接:左边的全部显示，没有右边表的用null填充,如果右表右多个列对应，就生成多条。
一张表为：
id|name
:--|:--
1|23
2|45
3|33

另一张表为：
id|home
:--|:--
1|aa
1|cc
4|bb
7|34e

查询出来的数据：

id|name|home
:--|:--|:--
1|23|aa
1|23|cc
2|45|null
3|33|null


>右外连接：同理，和左连接相反，以右表为主

id|name
:--|:--
1|23
2|45
3|33

另一张表为：
id|home
:--|:--
1|aa
1|cc
4|bb
7|34e

查询出来的数据：

id|name|home
:--|:--|:--
1|23|aa
1|23|cc
null|4|bb
null|7|34e

> 全连接:左连接和右连接整合在一起

id|name|home
:--|:--|:--
1|23|aa
1|23|cc
2|45|null
3|33|null
1|23|aa
1|23|cc
null|4|bb
null|7|34e

## UNION
>每个 SELECT 语句必须有相同数量的选中列、相同数量的列表达式、相同的数据类型，并且它们出现的次序要一致，不过长度不一定要相同。

```
SELECT column1 [, column2 ]
    FROM table1 [, table2 ]
    [WHERE condition]

    UNION

    SELECT column1 [, column2 ]
    FROM table1 [, table2 ]
    [WHERE condition]
```
## UNION All
>UNION ALL 运算符用于将两个 SELECT 语句的结果组合在一起，重复行也包含在内。
```
SELECT column1 [, column2 ]
    FROM table1 [, table2 ]
    [WHERE condition]

    UNION ALL

    SELECT column1 [, column2 ]
    FROM table1 [, table2 ]
    [WHERE condition]
```


# NULL和NOT NULL
>NULL 用于表示缺失的值。数据表中的 NULL 值表示该值所处的字段为空

>NOT NULL表示对于给定列，必须按照其数据类型明确赋值。

在选取数据时可以使用 IS NOT NULL 或者 IS NULL 来过滤。

# SQL 索引
索引是一种特殊的查询表，可以被数据库搜索引擎用来加速数据的检索。类似于字典的目录，可以快速选出想要的信息

## 对表加索引
```
CREATE INDEX index_name ON table_name;
```
## 对表中一列加索引
```
CREATE INDEX index_name ON table_name(id);
```

## 唯一索引:不仅提高查询速度，还保证数据的完整性
```
create unique index index_name on table_name(id);
```

## 复合索引(聚簇索引)
>用于经常一起被查询的列上

```
CREATE INDEX index_name
on table_name (column1, column2);
```



# ALTER TABLE 命令
>新增一列

`alter table table_name add 列名 datatype`

>删除一列

`alter table table_name drop 列名 datatype`

>修改一列

`alter table table_name modify 列名 datatype not null`

>给表中添加主键约束

```
ALTER TABLE table_name 
ADD CONSTRAINT MyPrimaryKey PRIMARY KEY (column1, column2...);
```

>给表中添加UNIQUE约束

```
ALTER TABLE table_name 
ADD CONSTRAINT MyUNIQUE UNIQUE (column1, column2...);
```
>删除索引

```
ALTER TABLE table_name 
DROP constraint MyUniqueConstraint;
````
>mysql写法
```
ALTER TABLE table_name 
DROP INDEX MyUniqueConstraint;
````

```
ALTER TABLE table_name 
DROP constraint MyPrimaryKey;


ALTER TABLE table_name 
DROP primary key;
````

>mysql写法
```
ALTER TABLE table_name 
DROP INDEX primary key;
````

# TRUNCATE TABLE 删除表中数据
` TRUNCATE TABLE table_name;`

# 删除表
```
DROP table tb_name;
```

# 事务和锁

## 事务
事务的属性：

* 原子性：保证任务中的所有操作都执行完毕；否则，事务会在出现错误时终止，并回滚之前所有操作到原始状态。
* 一致性：如果事务成功执行，则数据库的状态得到了进行了正确的转变。
* 隔离性：保证不同的事务相互独立、透明地执行。
* 持久性：即使出现系统故障，之前成功执行的事务的结果也会持久存在。

事务控制：

* commit:提交更改
* rollback:回滚更改
* SAVEPOINT:设置还原点

通过回滚到还原点当时的状态和情况。

>`ROLLBACK TO SAVEPOINT`;

* SET TRANSACTION:命名事务

## 乐观锁
初始数据|操作 | version
:--|:--|:--
最初|500  |   1
操作1|-100 |
1.1|500-100=400 | 1
1.2|400| 2
操作2|+200|
获取的值|500|1
2.1|500+200=700|1
2.2|700|2
--|提交|
--|版本号冲突| 
--|重新获取值|
--|计算再次提交|3

## 悲观锁

数据库默认每条数据都可能发生冲突，每查询一条数据就加锁，别人不能再对其进行操作。