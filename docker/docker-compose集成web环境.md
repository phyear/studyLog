# docker-compose集成java+mysql环境

## 构建mysql

首先编写mysql的Dockerfile文件
文件在`docker/mysql/`目录下
```
#docker容器中有的话就不用拉取
FROM MYSQL:5.7 
#定义工作路径变量
ENV WORK_PATH /usr/local/
#定义自启动的目录变量
ENV AUTO_RUN_DIR /docker-entrypoint-initdb.d

ENV FILE1 sakila-schema.sql
ENV FILE2 sakila-data.sql

ENV AUTO_INSTALL docker-entrypoint.sh

#将要导入的数据文件放入工作路径内
COPY $FILE1 $WORK_PATH
COPY $FILE2 $WORK_PATH

#将执行的脚本放入自启动目录
COPY $AUTO_INSTALL $AUTO_RUN_DIR

#为脚本授权
RUN chmod a+x /$AUTO_RUN_DIR/$AUTO_INSTALL

```

>编写docker-entrypoint.sh 脚本
```
#!bash/bin

echo '开始执行sql'
mysql -uroot -p12345 <<EOF

source WORK_PATH/FILE1;

source WORK_PATH/FILE2;

EOF

echo '结束执行sql'
```

## 构建java

在`docker/java` 创建Dockerfile

```
FROM  registry.saas.hand-china.com/tools/javabase:0.5.0

#工作区
WORKDIR /

#定义环境变量
ENV LANG C.UTF-8
ENV LANGUAGE C.UTF-8
ENV LC_ALL C.UTF-8

#将jar包放入容器的工作目录`/`下,并改名为test.jar
COPY javatest.jar /test.jar

#当容器启动时,执行
CMD ["java","-jar","test.jar"]

```


>## 构建镜像，只需执行build.sh 
 
```
#!bash/bin

bulid -t mysql:test ./docker/mysql

bulid -t java:test ./docker/java
```

# 使用docker-compose

>编写docker-compose.yml


```
version: '3'
service:
    mysqls:
      restart: always
      images: mysql:test
      container_name:mysqltest
      ports:
        - "3306:3306"
      commmand:
          [
                 '--character-set-server=utf8mb4',
                 '--collation-server=utf8mb4_unicode_ci'
          ]

       environment:
            MYSQL_ROOT_PASSWORD: 12345
    
    javas:
       images: java:test
       container_name: javatest 
       depends_on:
          - mysqls
       environment:
            IP: mysqls
            PORT: 3306
            USERNAME: root
            PASSWORD: 12345

```
>主要发布的代码连接字符串中引用通配符获取 `javas` 下的`environment`的属性获取值

```
spring:
   datasource:
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://${IP}:${PORT}/sakila?serverTimezone=UTC&&useSSL=false
        username: ${USERNAME}
        password: ${PASSWORD}
```


> 编写start.sh脚本
```
docker-compose up -d
```
>注意：start.sh 应该和docker-compose.yml文件在同一个目录

>目录结构

```
----start.sh
----docker-compose.yml
----build.sh
----docker
-----mysql
------Dockerfile
--------entrypoint.sh
--------sakila-schema.sql
--------sakila-data.sql
-----java
--------Dockefile
--------javatest.jar
```

