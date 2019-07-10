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







