# Maven
使用maven的好处：
1、统一java工程的目录结构
2、统一管理jar包
3、减少因本地与线上环境的差异引起的问题
## maven的目录结构
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



## maven的pom文件介绍
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



## maven的生命周期
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

