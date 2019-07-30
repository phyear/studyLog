## eureka(服务注册中心) Dalston版
> 个人选用的Dalston版，并且基于springboot的微服务架构应该导入springboot-starter-web 

## pom.xml公用的配置
```
<dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

## 依赖的包
```

<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
<!--核心的依赖-->
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>
```


## application配置文档
```
server.port=2001 //端口
eureka.client.fetch-registry=false
eureka.client.register-with-eureka=false
eureka.instance.hostname=localhost 

```

## 在启动入口添加注解 @EnableEurekaServer


```
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}

```

> ## 启动 EurekaServerApplication  访问 localhost:2001


## 创建客户端（eureka-client）
新建项目：除了eureka依赖，其他的都一样，改为：
```
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
```

## 配置文件写法

```
server.port=2002
spring.application.name=eureka-client

enreka.client.serverUrl.defaultZone=http://localhost:2001/eureka
```
## 在启动类添上 @EnableDiscoveryClient 注解
```
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

}
```

## 获取注册的服务

```
@RestController
public class DcController { 
    @Autowired
   DiscoveryClient discoveryClient;
   @RequestMapping("/dc")
   public String Dc(){
       String services = "Services: " + discoveryClient.getServices();
       System.out.println(services);
       return services;
   }
}
```


## 启动eureka-server 和eureka-client 

访问 eureka-server 

![](/img/eureka-server.png)


访问 eureka-client localhost:2002/dc

![](/img/eureka-client.png)

