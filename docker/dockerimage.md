# docker实践
一般程序需要的资源：
* cpu
* 内存
* 网络
* 端口
* 中间件
docker可以来创建和管理


>docker安装：
https://github.com/AliyunContainerService/k8s-for-docker-desktop
windows版最新：
https://hub.docker.com/editions/community/docker-ce-desktop-windows
## docker指令

> docker version


> docker images(查看本地镜像) [-a 显示所有镜像]/[-f 过滤]

 docker	images	-f	dangling=true //虚悬镜像
。由于新旧镜像同名，旧镜像名称被取 消，从而出现仓库名、标签均为  `<none>`的镜像
可以删除虚悬镜像：
docker	rmi	$(docker	images	-q	-f	dangling=true)


> docker tag 镜像名 


> docker pull 拉镜像
 
 docker pull [docker registry][仓库名]:[标签]
例如：docker pull ubuntu:14.04 
没有配置docker registry 的话，直接去docker Hub上拉取
配置了的，直接到国内仓库中拉取


> docker push 推镜像

> docker bulid

> docker ps(显示运行的镜像) or ps -a （显示所有的）

> docker  run
docker run -d -p 3301:3306 -e mysql

docker run -di --name pinyougou_mysql -p 33306:3306 -e MYSQL_ROOT_PASSWORD=123456 mysql

>docker exec -it 容器 路径 //进入容器内部

>docker top id/name //容器内部信息

>docker start id/name //重启容器

>docker stop  id/name //关闭容器
## 镜像加速器
```
{		
    "registry-mirrors":	[			
    "https:sr5arhkn.mirror.aliyuncs.com",
    "http://14d216f4.m.daocloud.io"],
    "insecure-registries":	[] 
    }

```


# docker images

`-f`  --filter  过滤作用

`-f since=库名：标签`  选出之前安装的镜像

`-q` 删除一些特定的镜像会用到

`--format` 可以做出自己想要的镜像列表

## docker images --显示所有顶层的完好镜像

## dangling	image --虚悬镜像
    docker image -f dangling=true --显示虚悬镜像

    docker rmi $(docker images -q -f dangling=true) --删除虚悬镜像

## docker	images	-a --所有的镜像

## docker images mysql  --只显示mysql的镜像

## docker images -f since=mysql:5.7 --显示mysql5.7镜像之前安装的镜像

## docker images --format "{{.ID}}:{{.Repository}}" 

# docker run --运行镜像
>`-p`  映射的端口号

>`-d`  后台运行

>`docker run -d -p 80:80 ngnix`


>`docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=12345 mysql`

# docker exec  ---进入容器的内部

>`docker exec -it 容器ID`


# docker commit --将容器构建成镜像（不推荐）

>docker commit --author "" --message "" 容器ID 构建后镜像名：标签`

# Dockerfile ---将容器构建成镜像（推荐）
```
From ngnix:v2
run  echo '<h1>Hello,	Docker!</h1>'	>	/usr/share/nginx/html/index.html
```
在文件根路径

输入：
>`docker bulid -t 构建后的镜像名：标签  .` 

## Dockerfile命令

### From  镜像名/(镜像名：标签)---指定基础镜像

### run

### run







