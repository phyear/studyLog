# sql考核：

	考试时长 2小时30分钟 （9：10 - 11：40）
	考试期间可以查阅个人的学习资料，不可上网查询、相互讨论、相互借阅资料

## 一、要求： 
	1. 阅读 - sql考核建表语句
	2. 根据建表语句的结果，完成 - sql考核题目，写出对应的sql语句
		
## 二、sql考核建表语句：

```
Create Table  HAND_STUDENT
(
 STUDENT_NO Varchar(10) Not Null,
 STUDENT_NAME Varchar(20),
 STUDENT_AGE BigInt,
 STUDENT_GENDER Varchar(5),
 OBJECT_VERSION_NUMBER BigInt Not Null Default 1,
 CREATION_DATE  datetime Not Null Default CURRENT_TIMESTAMP,
 CREATED_BY BigInt Not Null Default -1,
 LAST_UPDATED_BY BigInt Not Null Default -1,
 LAST_UPDATE_DATE datetime Not Null Default CURRENT_TIMESTAMP,
 LAST_UPDATE_LOGIN BigInt Default -1
);
ALTER TABLE  HAND_STUDENT COMMENT '学生信息表';
ALTER TABLE  HAND_STUDENT MODIFY `STUDENT_NO` Varchar(10) Not Null Comment '学号';
ALTER TABLE  HAND_STUDENT MODIFY `STUDENT_NAME` Varchar(20) Comment '姓名';
ALTER TABLE  HAND_STUDENT MODIFY `STUDENT_AGE` BigInt Comment '年龄';
ALTER TABLE  HAND_STUDENT MODIFY `STUDENT_GENDER` Varchar(5) Comment '性别';
ALTER TABLE  HAND_STUDENT MODIFY `OBJECT_VERSION_NUMBER` BigInt Not Null Default 1 Comment '行版本号，用来处理锁';

ALTER TABLE `HAND_STUDENT` ADD UNIQUE (`STUDENT_NO`);

Create Table  HAND_TEACHER
(
 TEACHER_NO Varchar(10) Not Null,
 TEACHER_NAME Varchar(20),
 MANAGER_NO Varchar(10),
 OBJECT_VERSION_NUMBER BigInt Not Null Default 1,
 CREATION_DATE  datetime Not Null Default CURRENT_TIMESTAMP,
 CREATED_BY BigInt Not Null Default -1,
 LAST_UPDATED_BY BigInt Not Null Default -1,
 LAST_UPDATE_DATE datetime Not Null Default CURRENT_TIMESTAMP,
 LAST_UPDATE_LOGIN BigInt Default -1
);
ALTER TABLE  HAND_TEACHER COMMENT '教师信息表';
ALTER TABLE  HAND_TEACHER MODIFY `TEACHER_NO` Varchar(10) Not Null Comment '教师编号';
ALTER TABLE  HAND_TEACHER MODIFY `TEACHER_NAME` Varchar(20) Comment '教师名称';
ALTER TABLE  HAND_TEACHER MODIFY `MANAGER_NO` Varchar(10) Comment '上级编号';
ALTER TABLE  HAND_TEACHER MODIFY `OBJECT_VERSION_NUMBER` BigInt Not Null Default 1 Comment '行版本号，用来处理锁';

ALTER TABLE `HAND_TEACHER` ADD UNIQUE (`TEACHER_NO`);


Create Table  HAND_COURSE
(
 COURSE_NO Varchar(10) Not Null,
 COURSE_NAME Varchar(20),
 TEACHER_NO Varchar(10) Not Null,
 OBJECT_VERSION_NUMBER BigInt Not Null Default 1,
 CREATION_DATE  datetime Not Null Default CURRENT_TIMESTAMP,
 CREATED_BY BigInt Not Null Default -1,
 LAST_UPDATED_BY BigInt Not Null Default -1,
 LAST_UPDATE_DATE datetime Not Null Default CURRENT_TIMESTAMP,
 LAST_UPDATE_LOGIN BigInt Default -1
);
ALTER TABLE  HAND_COURSE COMMENT '课程信息表';
ALTER TABLE  HAND_COURSE MODIFY `COURSE_NO` Varchar(10) Not Null Comment '课程号';
ALTER TABLE  HAND_COURSE MODIFY `COURSE_NAME` Varchar(20) Comment '课程名称';
ALTER TABLE  HAND_COURSE MODIFY `TEACHER_NO` Varchar(10) Not Null Comment '教师编号';
ALTER TABLE  HAND_COURSE MODIFY `OBJECT_VERSION_NUMBER` BigInt Not Null Default 1 Comment '行版本号，用来处理锁';

ALTER TABLE `HAND_COURSE` ADD UNIQUE (`COURSE_NO`);

Create Table  HAND_STUDENT_CORE
(
 STUDENT_NO Varchar(10) Not Null,
 COURSE_NO Varchar(10) Not Null,
 CORE Decimal(4,2),
 OBJECT_VERSION_NUMBER BigInt Not Null Default 1,
 CREATION_DATE  datetime Not Null Default CURRENT_TIMESTAMP,
 CREATED_BY BigInt Not Null Default -1,
 LAST_UPDATED_BY BigInt Not Null Default -1,
 LAST_UPDATE_DATE datetime Not Null Default CURRENT_TIMESTAMP,
 LAST_UPDATE_LOGIN BigInt Default -1
);
ALTER TABLE  HAND_STUDENT_CORE COMMENT '学生成绩表';
ALTER TABLE  HAND_STUDENT_CORE MODIFY `STUDENT_NO` Varchar(10) Not Null Comment '学号';
ALTER TABLE  HAND_STUDENT_CORE MODIFY `COURSE_NO` Varchar(10) Not Null Comment '课程号';
ALTER TABLE  HAND_STUDENT_CORE MODIFY `CORE` Decimal(4,2) Comment '分数';
ALTER TABLE  HAND_STUDENT_CORE MODIFY `OBJECT_VERSION_NUMBER` BigInt Not Null Default 1 Comment '行版本号，用来处理锁';

ALTER TABLE `HAND_STUDENT_CORE` ADD UNIQUE (`STUDENT_NO`,`COURSE_NO`);



/*******初始化学生表的数据******/
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s001','张三',23,'男');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s002','李四',23,'男');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s003','吴鹏',25,'男');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s004','琴沁',20,'女');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s005','王丽',20,'女');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s006','李波',21,'男');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s007','刘玉',21,'男');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s008','萧蓉',21,'女');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s009','陈萧晓',23,'女');
insert into HAND_STUDENT(STUDENT_NO,STUDENT_NAME,STUDENT_AGE,STUDENT_GENDER) values ('s010','陈美',22,'女');
commit;
/******************初始化教师表***********************/
insert into HAND_TEACHER(TEACHER_NO,TEACHER_NAME,MANAGER_NO) values ('t001', '刘阳','');
insert into HAND_TEACHER(TEACHER_NO,TEACHER_NAME,MANAGER_NO)  values ('t002', '谌燕','t001');
insert into HAND_TEACHER(TEACHER_NO,TEACHER_NAME,MANAGER_NO)  values ('t003', '胡明星','t002');
commit;
/***************初始化课程表****************************/
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c001','J2SE','t002');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c002','Java Web','t002');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c003','SSH','t001');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c004','Oracle','t001');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c005','SQL SERVER 2005','t003');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c006','C#','t003');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c007','JavaScript','t002');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c008','DIV+CSS','t001');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c009','PHP','t003');
insert into HAND_COURSE(COURSE_NO,COURSE_NAME,TEACHER_NO) values ('c010','EJB3.0','t002');
commit;
/***************初始化成绩表***********************/
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s001','c001',58.9);
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s002','c001',80.9);
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s003','c001',81.9);
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s004','c001',60.9);
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s001','c002',82.9);
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s002','c002',72.9);
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s003','c002',81.9);
insert into HAND_STUDENT_CORE(STUDENT_NO,COURSE_NO,CORE) values ('s001','c003','59');
commit;
	
	
```
三、sql考核题目：

1、查询没学过“谌燕”老师课的同学，显示（学号、姓名）
```
select STUDENT_NO,STUDENT_NAME from HAND_STUDENT
WHERE STUDENT_NO NOT IN (
SELECT STUDENT_NO FROM HAND_STUDENT_CORE 
left join HAND_COURSE  ON   HAND_COURSE.COURSE_NO=HAND_COURSE.COURSE_NO  
Left join HAND_TEACHER ON  HAND_TEACHER.TEACHER_NO=HAND_COURSE.TEACHER_NO AND HAND_TEACHER.TEACHER_NAME ='谌燕'  GROUP BY  STUDENT_NO 
)
```
2、查询没有学全所有课的同学，显示（学号、姓名）

```
select STUDENT_NO,STUDENT_NAME FROM HAND_STUDENT
WHERE STUDENT_NO IN(
select STUDENT_NO from HAND_STUDENT_CORE 
GROUP BY STUDENT_NO HAVING COUNT(STUDENT_NO) <(select COUNT(*) from HAND_COURSE ))
```             
3、查询“c001”课程比“c002”课程成绩高的所有学生，显示（学号、姓名）
```

SELECT STUDENT_NO,STUDENT_NAME from HAND_STUDENT where STUDENT_NO in(
select a.STUDENT_NO from HAND_STUDENT_CORE a
join HAND_STUDENT_CORE b
on a.STUDENT_NO=b.STUDENT_NO
where a.COURSE_NO='c001' and b.COURSE_NO='c002' and a.core>b.CORE
 )

````
                  
4、按各科平均成绩和及格率的百分数，按及格率高到低顺序，显示（课程号、平均分、及格率）
 
 ```

select COURSE_NO,avg(CORE) as 平均分,
(SUM(case when a.CORE>60 then 1 else 0 end)/SUM(case when a.CORE>0 then 1 else 0 end)) as 及格率 
FROM  HAND_STUDENT_CORE a
GROUP BY COURSE_NO

```
5、1992年之后出生的学生名单找出年龄最大和最小的同学，显示（学号、姓名、年龄）

```
select STUDENT_NO,STUDENT_NAME,STUDENT_AGE from HAND_STUDENT 
WHERE STUDENT_AGE IN (
 select MAX(STUDENT_AGE) FROM HAND_STUDENT )
 OR STUDENT_AGE IN (
 select MIN(STUDENT_AGE) FROM HAND_STUDENT ) 
```
6、统计列出矩阵类型各分数段人数，横轴为分数段[100-85]、[85-70]、[70-60]、[<60]，纵轴为课程号、课程名称

```
select COURSE_NO,
sum(case when CORE BETWEEN 85 and 100 then 1 else 0 end ) as '[85-100]'
,sum(case when CORE BETWEEN 70 and 85 then 1 else 0 end ) as '[70-85]'
,sum(case when CORE BETWEEN 60 and 70 then 1 else 0 end ) as '[60-70]'
,sum(case when CORE <60 then 1 else 0 end ) as '[60以下]'
FROM  HAND_STUDENT_CORE a
GROUP BY COURSE_NO
```


7、查询各科成绩前三名的记录:(不考虑成绩并列情况)，显示（学号、课程号、分数）

```
 select * from HAND_STUDENT_CORE a
where (
(select count(*) from HAND_STUDENT_CORE b where  a.COURSE_NO =b.COURSE_NO and a.CORE<b.CORE)<=2
 )  ORDER BY COURSE_NO,CORE DESC
 ```

8、查询选修“谌燕”老师所授课程的学生中每科成绩最高的学生，显示（学号、姓名、课程名称、成绩）

```
 
 select cor.STUDENT_NO,c.COURSE_NAME,CORE from HAND_STUDENT_CORE cor
 LEFT join HAND_COURSE c on c.COURSE_NO= cor.COURSE_NO
 Left join HAND_TEACHER t on t.TEACHER_NO=c.TEACHER_NO 
 where  not EXISTS(select  1 from HAND_STUDENT_CORE where COURSE_NO=cor.COURSE_NO and CORE>cor.core) and  t.TEACHER_NAME ='谌燕' 
```

9、查询两门及以上不及格课程的同学及平均成绩，显示（学号、姓名、平均成绩（保留两位小数））
 ```
 SELECT  HAND_STUDENT_CORE.STUDENT_NO,HAND_STUDENT.STUDENT_NAME,AVG(HAND_STUDENT_CORE.CORE)
FROM HAND_STUDENT,HAND_STUDENT_CORE
WHERE 
HAND_STUDENT.STUDENT_NO=HAND_STUDENT_CORE.STUDENT_NO
AND 
HAND_STUDENT_CORE.STUDENT_NO
IN
(SELECT STUDENT_NO
FROM 
(SELECT STUDENT_NO,COUNT(CORE) AS COT FROM HAND_STUDENT_CORE
WHERE CORE<60
GROUP BY STUDENT_NO) A
WHERE A.COT>=2)
```

10、查询姓氏数量最多的学生名单，显示（学号、姓名、人数）
   
11、查询课程名称为“J2SE”的学生成绩信息，90以上为“优秀”、80-90为“良好”、60-80为“及格”、60分以下为“不及格”，显示（学号、姓名、课程名称、成绩、等级）

```
select s.STUDENT_NO,s.STUDENT_NAME,c.COURSE_NAME,cor.CORE,
(case 
when cor.core>90 then '优秀'
when cor.core  BETWEEN 80 and 90  then '良好'
when cor.core  BETWEEN 60 and 80  then '及格'
else '不及格'
end 
) as level from HAND_STUDENT s
left join HAND_STUDENT_CORE cor on s.STUDENT_NO=cor.STUDENT_NO
left join HAND_COURSE  c on c.COURSE_NO=cor.COURSE_NO 
where  c.COURSE_NAME='J2SE'
```

12、查询教师“胡明星”的所有主管及姓名:（无主管的教师也需要显示），显示（教师编号、教师名称、主管编号、主管名称）

13、查询分数高于课程“J2SE”的所有学生课程信息，显示（学号，姓名，课程名称、分数）
   
14、分别根据教师、课程、教师和课程三个条件统计选课的学生数量：(使用rollup),显示（教师名称、课程名称、选课数量）
 
15、查询所有课程成绩前三名的按照升序排在最开头，其余数据排序保持默认（7分）,显示（学号、成绩）


	