# 1.Git记录
 ## 1.1 git help
> git的帮助命令 可以很好的提示
## 1.2 git config --本地全局配置
```
git config --global user.name "John Doe"  //用户名
git config --global user.email johndoe@example.com //邮箱
```
> 作用于github提交后可以查看是谁提交的
## 1.3 git本地仓库 
- 创建本地仓库 -- git init
- 提交文件到暂存区 -- git add 文件名
- 提交到本地仓库   --  git commit -m "一些提交信息"

## 1.4 git远程提交和拉取
初次提交
```
//第一步：连接远程仓库
git remote add origin git@github.com:michaelliao/learngit.git
//第二步：提交到远程仓库：master为主要分支
git push -u origin master
```
除了初次提交，其他的直接push就可以了
## 1.5 git Branch（git 分支）
直接使用命令`git branch 分支名`创建分支

切换分支使用`git checkout -b 分支名`

删除分支使用`git checkout -D 分支名` 

>一般不建议直接在本地创建分支

## 1.6 git ssh
在本地生成秘钥，再到github设置公钥就可以直接 push 文件
```
ssh-keygen -t rsa -C "your_email@example.com"
```
在github->setting->ssh and GPG keys ->ssh Keys
直接粘贴公钥就可以了

## 1.5git flow

# 2.MarkDown语法
Markdown是一种纯文本格式的标记语言。通过简单的标记语法，它可以使普通文本内容具有一定的格式。
优点：
1、因为是纯文本，所以只要支持Markdown的地方都能获得一样的编辑效果，可以让作者摆脱排版的困扰，专心写作。
2、操作简单。比如:WYSIWYG编辑时标记个标题，先选中内容，再点击导航栏的标题按钮，选择几级标题。要三个步骤。而Markdown只需要在标题内容前加#即可
缺点：
1、需要记一些语法（当然，是很简单。五分钟学会）。
2、有些平台不支持Markdown编辑模式。


## 2.1常用标记
***
标记|功能|描述|
:---:|:--:|:---:
`#`|标题|几个`#`就代表几级标题
`*文字*`|斜体|效果：*斜体*
`**文字**`|加粗|效果：**加粗**
`***文字***`|加粗+斜体|效果：***加粗+斜体***
`~文字~`|删除线|效果：~删除线~
`***`|分割线|三个或者三个以上的 - 或者 * 都可以
`[文字](连接)`|超链接|[简书](http://jianshu.com)
`* 文字`|列表|无序列表用 - + * 任何一种都可以
`![图片alt](图片地址 ''图片title'')`|图片|`![blockchain](https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1H "区块链")`
`单行代码`|单行代码|``dan``
`(```)`|多行代码|


# 3.Linux
虚拟机工具：
 * virtulbox
 * vagrant(便于环境布置)
 * Xshell、Xftp

# 4.Maven
使用maven的好处：
1、统一java工程的目录结构
2、统一管理jar包
3、减少因本地与线上环境的差异引起的问题
## 4.1 maven的目录结构
>src
>>main
>>>resource
>>>filters
>>>webapp

>>test
>>>java
>>>resource


配置文件配置`setting.xml`文件中：
本地仓库配置：
<localRepository>D:\maven\repo</localRepository>

阿里镜像配置：
```
<mirror>        
 	   <id>nexus-aliyun</id>      
  	   <name>nexus-aliyun</name>
       <url>http://maven.aliyun.com/nexus/content/groups/public</url> 
  	   <mirrorOf>central</mirrorOf>        
</mirror> 
```



## 4.2 maven的pom文件介绍
 *  modelVersion
 指定了当前Maven模型的版本号，对于Maven2和Maven3来说，它只能是4.0.0 
 *  groupId
 groupId一般由三个部分组成，每个部分之间以”.”分隔,第一部分是项目用途,比如用于商业的就是”com”,用于非营利性组织的就　　是”org”；第二部分是公司名，比如”hand”;第三部分是你的项目名 
* artifactId 
　　Maven构建的项目名，比如你的项目中有子项目，就可以使用”项目名-子项目名”的命名方式 
* version 
　　版本号，SNAPSHOT意为快照，说明该项目还在开发中，是不稳定的版本。在Maven中很重要的一点是，groupId、artifactId、version三个元素生成了一个Maven项目的基本坐标。
 * dependencies和dependency
 管理jar依赖
 * properties
 定义一些属性
 ```
<properties>   
 <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>   
 <maven.compiler.source>1.8</maven.compiler.source>   <maven.compiler.target>1.8</maven.compiler.target>
</properties>
 ```
 * build
  build表示与构建相关的配置，在这里面也可以引用一些插件
  如：maven-compiler-plugin



## 4.3 maven的生命周期
maven生命周期可以分成三个大的点clean(清理项目)、default(构建项目)、site(生成站点)。其中default周期是其中最大的、也是最多分类的周期。三个周期的互补干扰，只在本周期内执行。如：执行install 只会在default周期中执行，不会clean，也不会site。
### clean 清理项目
  clean分为：
|周期名| 描述 |
|--|--|
|  pre-clean  | 执行清理前的工作 |
|  clean  | 清理上一次构建生成的文件 |
| post-clean  | 执行清理后的工作 |

### default 构建项目
default周期较多，只列举常用的：
|周期名| 描述 |
|--|--|
|  complile (编译) | 编译项目的源代码 |  	
|  test(测试)| 使用合适的单元测试框架测试编译的源代码，这些测试不应该要求代码被打包或部署|  
|  packge (打包) | 采用编译的代码，并以其可分配格式（如JAR、war）进行打包。 |   
|  install(安装)|将软件包安装到本地存储库中，用作本地其他项目的依赖项 |   
|deploy(部署)| 在构建环境中完成，将最终的包复制到远程存储库以与其他开发人员和项目共享。|

### site 站点 
|周期名| 描述 |
|--|--|
|  pre-site | 在生成项目站点前要完成的工作 |
|  site   | 生成项目的站点文档 |
|  post-site | 在生成项目站点后要完成的工作|
|  site-deploy | 发布生成的站点到服务器上 |






# 5.docker
一般程序需要的资源：
* cpu
* 内存
* 网络
* 端口
* 中间件
docker可以来创建和管理

## 5.1 docker安装
>docker安装：
https://github.com/AliyunContainerService/k8s-for-docker-desktop
windows版最新：
https://hub.docker.com/editions/community/docker-ce-desktop-windows



## 5.2 docker指令

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

>docker top ip/name //容器内部信息

>docker start ip/name //重启容器

>docker stop  ip/name //关闭容器

## 5.3 dockerfile

   
docker bulid -t ngnix .
