# SpringBoot

## Bean定义
### 声明bean 
* @Scope 
>作用于类和方法上用，来配置 spring bean 的作用域，它标识 bean 的作用域
1. singleton单例模式
    > Spring 容器中有且只有一个Bean实例，只要Spring容器 不销毁或退出，该Bean实例就会一直存活
2. prototype原型模式
    > 每次获取Bean的时候会有一个新的实例，Spring容器不能对返回Bean实例的整个生命周期负责
3. request模式
    > request只适用于Web程序，每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效，当请求结束后，该对象的生命周期即告结束
4. session模式
    > session只适用于Web程序，session作用域表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效

5. application模式
    >application只适用于Web程序，全局作用域
* @Component
> 可以作用任何一层，没有明确角色的组件。
把普通pojo实例化到spring容器，尽量使用其拓展，service Repository,
作用域默认是`singleton`
* @Repositpry
 1. Repository注解作用在类上
 2. 作用域默认为singleton
 3. 用于标注数据访问组件，即DAO组件
 4. 能将所标注的类中抛出的数据访问异常封装为 Spring 的数据访问异常类型

* @Service 

  1. 用于标注业务层组件,表示定义一个bean
  2. 作用域默认为singleton
  3. 使用注解配置和类路径扫描时，被@Service注解标注的类会被Spring扫描并注册为Bean


* @Controller

  1. @Controller注解作用在类上
  2. 使用注解配置和类路径扫描时，被@Controller注解标注的类会被Spring扫描并注册为Bean
  3. 用于标注Web中控制层组件
  4. 被@Controller标注的类负责处理由DispatcherServlet分发的请求，它把用户请求的数据经过业务处理层处理之后封装成一个Model ，然后再把该Model返回给对应的View进行展示
  5. @Controller和@RequestMapping、@RequestParam等一些注解共同处理URL的映射
*  @ResponseBody注解
>返回json格式的数据
* @RestController
> 基于@contrller注解 实现了rest风格的返回请求数据 ,
@RestController = @Controller + @ResponseBody


* @RequestMapping注解
Spring Boot也提供了简化版后的@RequestMapping
1. @GetMapping
2. @PostMapping
3. @PutMapping
4. @DeleteMapping
5. @PatchMapping
> 方法上的@RequestMapping会继承在类上的@RequestMapping,返回字符串，默认是视图名,spring Boot视图默认路径：resources/templates
####    @RequestMapping的七个参数  
 - value：指定请求的实际地址
```
@RequestMapping("/test")
@RequestMapping(value="/test")
```
 - path（匹配符）：指定请求的实际地址
```
@RequestMapping(path="/test")
@RequestMapping(path="/test/*.do")
```
 - method：指定请求的method类型  
```
//实现常用的http请求方式
package org.springframework.web.bind.annotation

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE
}
```
 - params：指定request中必须包含的请求参数
```
// 仅处理request Content-Type为“text/plain”类型的请求
@RequestMapping(value="/test", consumes="text/plain")
@RequestMapping(value="/test", consumes={"text/plain", "application/*"})

// 仅处理请求中包含了名为“action”，值为“query”的请求
@RequestMapping(value = "/test", params="action=query")
```

 - headers：指定请求中必须包含的请求头，才能进入此方法
```
// 仅处理request的header中包含了指定content-type=text/*的请求；
@RequestMapping(value = "/pets", headers = "content-type=text/*")
```
 - consumes：指定处理允许的媒体类型
```
// 仅处理request Content-Type为“text/plain”类型的请求
@RequestMapping(value="/test", consumes="text/plain")
@RequestMapping(value="/test", consumes={"text/plain", "application/*"})
```
 - produces：指定返回的内容类型
 ```
 // 仅处理request请求中Accept头中包含了"text/plain"的请求，同时暗示了返回的内容类型为text/plain
 @RequestMapping(value="/test", products="text/plain")
@RequestMapping(value="/test", produces={"text/plain", "application/*"})
 ```
#### 请求数据
- @PathVariable

>从请求URL中获取参数并映射到方法的参数中
```
 @GetMapping("/{name}/index.do")
    public String get (@PathVariable Integer id, @PathVariable String name) {
        return id + "_" + name;
    }
```


- @RequestParam
>用于将指定的请求参数赋值给方法中的形参
```
public String requestParamTest(@RequestParam(value="username") String userName, @RequestParam(value="usernick") String userNick)
```
- @RequestHeader
>可以把Request请求header部分的值绑定到方法的参数上

- @CookieValue
>CookieValue 可以把Request header中关于cookie的值绑定到方法的参数上

## 依赖注入及配置

### @Autowired
1. @Autowired默认按类型装配（这个注解是属业spring的）
2. 默认情况下必须要求依赖对象必须存在
3. 如果要允许null值，可以设置它的required属性为false
```
@Autowired(required=false)
```
4. 如果我们想使用名称装配可以结合@Qualifier注解进行使用
```
@Autowired(required=false)@Qualifier("baseDao")
private BaseDao baseDao;
```


### @Resource

1. @Resource（这个注解属于J2EE的），默认按照名称进行装配
2. 名称可以通过name属性进行指定
```
@Resource(name="baseDao")
private BaseDao baseDao;
```
>推荐使用：@Resource注解在字段上，这样就不用写setter方法了，并且这个注解是属于J2EE的，减少了与spring的耦合。这样代码看起就比较优雅。

### @ComponentScan
1. ComponentScan做的事情就是告诉Spring从哪里找到bean
2. 怎么使用他

配置一个路径：
```
@ComponentScan("com.in28minutes.springboot")
@SpringBootApplication
public class SpringbootIn10StepsApplication {}
```
配置多个路径：
```
@ComponentScan("com.in28minutes.springboot")
@SpringBootApplication
public class SpringbootIn10StepsApplication {}
```
## @Component and @ComponentScan 的区别
@Component类似于举手，而@ComponentScan 就是看看那些举手了

## 环境变量

- 命令行参数。
- 通过 System.getProperties() 获取的 Java 系统参数。
- 操作系统环境变量。
- 从 java:comp/env 得到的 JNDI 属性。
- 通过 RandomValuePropertySource 生成的“random.*”属性。
- 应用 Jar 文件之外的属性文件。(通过spring.config.location参数)
- 应用 Jar 文件内部的属性文件。
- 在应用配置 Java 类（包含“@Configuration”注解的 Java 类）中通过“@PropertySource”注解声明的属性文件。
- 通过“SpringApplication.setDefaultProperties”声明的默认属性

获取环境变量
```
maven:
  path: ${M2_HOME:abc} //获取本地环境变量中为M2_HOME的值，没有就给abc
   
   //通过获取application.properties的值
  @Value("${maven.path}")
  private String myUrl;

maven.path=aaaa

在resource文件夹下新建目录code，code.properties里面填充内容
valiade.code=1321312
valiade.name=撒打算


//通过获取@ConfigurationProperties
package com.hand.test.springbootdemo.prop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "valiade")
@PropertySource(value ={"classpath:code/code.properties"},encoding ="utf-8")
public class ValiadeCode {
    private String name;

    private int code;
    
    @Value("${maven.path}")
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ValiadeCode{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", path='" + path + '\'' +
                '}';
    }
}

```



>依赖jar：
```
<!--spring-boot-configuration:spring boot 配置处理器; -->

       <dependency>

           <groupId>org.springframework.boot</groupId>

           <artifactId>spring-boot-configuration-processor</artifactId>

           <optional>true</optional>

       </dependency>
````



  
  ```
@Configuration

@ConditionalOnClass(Mongo.class)

@EnableConfigurationProperties(MongoProperties.class)

publicclassMongoAutoConfiguration {

 

    @Autowired

    private MongoProperties properties;

 

}
  ```
  * @ConditionOnClass：@Configuration仅仅在一定条件下才会被加载
  * @EnableConfigurationProperties :将Spring Boot的配置文件（application.properties）中的spring.data.mongodb.*属性映射为MongoProperties并注入到MongoAutoConfiguration中。
  *   @ConditionalOnMissingBean:说明Spring Boot仅仅在当前上下文中不存在Mongo对象时，才会实例化一个Bean。

## yaml和properties
yaml是比较推荐的一种配置文件方式，尤其在配置多环境方面
，properties方式会导致创建多个文件，而yaml只用一个文件就行
### properties配置
在application.properties中选择使用的环境
```
spring.profiles.active=dev
```
![](/img/yamlandprop1.png)

### yaml配置
通过`---`划分
```
spring:
    profiles:
          active: dev
---
spring:
    profiles:dev
```