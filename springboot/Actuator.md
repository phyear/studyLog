Spring Boot Actuator可以帮助你监控和管理Spring Boot应用，比如健康检查、审计、统计和HTTP追踪等。所有的这些特性可以通过JMX或者HTTP endpoints来获得。

## 开启spring-boot应用的Actuator
pom：
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
</dependencies>
```

## Actuator的endpoints
支持的endpoints如下：
| Endpoint ID | Description | 
|---|---|
auditevents|显示应用暴露的审计事件 (比如认证进入、订单失败)
info|显示应用的基本信息
health|显示应用的健康状态
metrics|显示应用多样的度量信息
loggers|显示和修改配置的loggers
logfile|返回log file中的内容(如果logging.file或者logging.path被设置)
httptrace|显示HTTP足迹，最近100个HTTP request/repsponse
env|显示当前的环境特性
flyway|显示数据库迁移路径的详细信息
liquidbase|显示Liquibase 数据库迁移的纤细信息
shutdown|让你逐步关闭应用
mappings|显示所有的@RequestMapping路径
scheduledtasks|显示应用中的调度任务
threaddump|执行一个线程dump
heapdump|返回一个GZip压缩的JVM堆dump

访问/actuator路径可以看到能够访问到的endpoints。默认，只有health和info通过HTTP暴露了出来。

## 暴露Actuator Endpoints
通过HTTP暴露Actuator endpoints。

```
# Use "*" to expose all endpoints, or a comma-separated list to expose selected ones
management.endpoints.web.exposure.include=health,info 
management.endpoints.web.exposure.exclude=
```


通过JMX暴露Actuator endpoints。
```
# Use "*" to expose all endpoints, or a comma-separated list to expose selected ones
management.endpoints.jmx.exposure.include=*
management.endpoints.jmx.exposure.exclude=
```
## 解析常用的actuator endpoint
### health
health endpoint通过合并几个健康指数检查应用的健康情况。
Spring Boot Actuator有几个预定义的健康指标比如[DataSourceHealthIndicator](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/actuate/jdbc/DataSourceHealthIndicator.html), [DiskSpaceHealthIndicator](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/actuate/system/DiskSpaceHealthIndicator.html), [MongoHealthIndicator](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/actuate/mongo/MongoHealthIndicator.html), [RedisHealthIndicator](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/actuate/redis/RedisHealthIndicator.html), [CassandraHealthIndicator](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/actuate/cassandra/CassandraHealthIndicator.html)等。它使用这些健康指标作为健康检查的一部分。
举个例子，如果你的应用使用Redis，RedisHealthindicator将被当作检查的一部分。如果使用MongoDB，那么MongoHealthIndicator将被当作检查的一部分。
你也可以关闭特定的健康检查指标，比如在prpperties中使用如下命令：
```
management.health.mongo.enabled=false
```
显示详细的健康信息
health endpoint只展示了简单的UP和DOWN状态。为了获得健康检查中所有指标的详细信息，你可以通过在application.yaml中增加如下内容：
```
management:
  endpoint:
    health:
      show-details: always
```
一旦你打开上述开关，你在/health中可以看到如下详细内容：
```
{"status":"UP","details":{"diskSpace":{"status":"UP","details":{"total":250790436864,"free":27172782080,"threshold":10485760}}}}
```
health endpoint现在包含了DiskSpaceHealthIndicator。
如果你的应用包含database(比如MySQL)，health endpoint将显示如下内容：
```
{
   "status":"UP",
   "details":{
      "db":{
         "status":"UP",
         "details":{
            "database":"MySQL",
            "hello":1
         }
      },
      "diskSpace":{
         "status":"UP",
         "details":{
            "total":250790436864,
            "free":100330897408,
            "threshold":10485760
         }
      }
   }
}
```
如果你的MySQL server没有启起来，状态将会变成DOWN：
```
{
   "status":"DOWN",
   "details":{
      "db":{
         "status":"DOWN",
         "details":{
            "error":"org.springframework.jdbc.CannotGetJdbcConnectionException: Failed to obtain JDBC Connection; nested exception is java.sql.SQLTransientConnectionException: HikariPool-1 - Connection is not available, request timed out after 30006ms."
         }
      },
      "diskSpace":{
         "status":"UP",
         "details":{
            "total":250790436864,
            "free":100324585472,
            "threshold":10485760
         }
      }
   }
}
```
### 创建一个自定义的健康指标
你可以通过实现HealthIndicator接口来自定义一个健康指标，或者继承AbstractHealthIndicator类。
```
package com.example.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // Use the builder to build the health status details that should be reported.
        // If you throw an exception, the status will be DOWN with the exception message.
        
        builder.up()
                .withDetail("app", "Alive and Kicking")
                .withDetail("error", "Nothing! I'm good.");
    }
}
```
一旦你增加上面的健康指标到你的应用中去后，health endpoint将展示如下细节:
```
{
   "status":"UP",
   "details":{
      "custom":{
         "status":"UP",
         "details":{
            "app":"Alive and Kicking",
            "error":"Nothing! I'm good."
         }
      },
      "diskSpace":{
         "status":"UP",
         "details":{
            "total":250790436864,
            "free":97949245440,
            "threshold":10485760
         }
      }
   }
}
```

## metrics

metrics endpoint展示了你可以追踪的所有的度量。
```
{
    "names": [
        "jvm.memory.max",
        "http.server.requests",
        "process.files.max",
        ...
        "tomcat.threads.busy",
        "process.start.time",
        "tomcat.servlet.error"
    ]
}
```
想要获得每个度量的详细信息，你需要传递度量的名称到URL中，像
```
http://localhost:8080/actuator/metrics/{MetricName}
```
举个例子，获得systems.cpu.usage的详细信息，使用以下URLhttp://localhost:8080/actuator/metrics/system.cpu.usage。它将显示如下内容:
```
{
    "name": "system.cpu.usage",
    "measurements": [
    {
        "statistic": "VALUE",
        "value": 0
    }
    ],
"availableTags": []
}
```
## loggers

loggers endpoint，可以通过访问 __http://localhost:8080/actuator/loggers__ 来进入。它展示了应用中可配置的loggers的列表和相关的日志等级。
你同样能够使用 __http://localhost:8080/actuator/loggers/{name}__ 来展示特定logger的细节。
举个例子，为了获得root logger的细节，你可以使用 __http://localhost:8080/actuator/loggers/root__ ：
```
{
   "configuredLevel":"INFO",
   "effectiveLevel":"INFO"
}
```
## 在运行时改变日志等级
loggers endpoint也允许你在运行时改变应用的日志等级。
举个例子，为了改变root logger的等级为DEBUG ，发送一个POST请求到 __http://localhost:8080/actuator/loggers/root__ ，加入如下参数
```
{
   "configuredLevel": "DEBUG"
}
```
这个功能对于线上问题的排查非常有用。
同时，你可以通过传递null值给configuredLevel来重置日志等级。

## info
info endpoint展示了应用的基本信息。它通过META-INF/build-info.properties来获得编译信息，通过git.properties来获得Git信息。它同时可以展示任何其他信息，只要这个环境property中含有infokey。
你可以增加properties到application.yaml中，比如：
```
# INFO ENDPOINT CONFIGURATION
info:
  app:
    name: @project.name@
    description: @project.description@
    version: @project.version@
    encoding: @project.build.sourceEncoding@
    java:
      version: @java.version@
```
注意，我使用了Spring Boot的Automatic property expansion 特征来扩展来自maven工程的properties。
一旦你增加上面的properties，info endpoint将展示如下信息：
```
{
    "app": {
    "name": "actuator",
    "description": "Demo project for Spring Boot",
    "version": "0.0.1-SNAPSHOT",
    "encoding": "UTF-8",
    "java": {
        "version": "1.8.0_161"
        }
    }
}
```
## 使用Spring Security来保证Actuator Endpoints安全
Actuator endpoints是敏感的，必须保障进入是被授权的。如果Spring Security是包含在你的应用中，那么endpoint是通过HTTP认证被保护起来的。
如果没有， 你可以增加以下以来到你的应用中去：

```
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

接下去让我们看一下如何覆写spring security配置，并且定义你自己的进入规则。
下面的例子展示了一个简单的spring securiy配置。它使用叫做EndPointRequest
的ReqeustMatcher工厂模式来配置Actuator endpoints进入规则。
```
package com.example.actuator.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

    /*
        This spring security configuration does the following

        1. Restrict access to the Shutdown endpoint to the ACTUATOR_ADMIN role.
        2. Allow access to all other actuator endpoints.
        3. Allow access to static resources.
        4. Allow access to the home page (/).
        5. All other requests need to be authenticated.
        5. Enable http basic authentication to make the configuration complete.
           You are free to use any other form of authentication.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class))
                        .hasRole("ACTUATOR_ADMIN")
                    .requestMatchers(EndpointRequest.toAnyEndpoint())
                        .permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                    .antMatchers("/")
                        .permitAll()
                    .antMatchers("/**")
                        .authenticated()
                .and()
                .httpBasic();
    }
}
```
为了能够测试以上的配置，你可以在application.yaml中增加spring security用户。
```
# Spring Security Default user name and password
spring:
  security:
    user:
      name: actuator
      password: actuator
      roles: ACTUATOR_ADMIN
```