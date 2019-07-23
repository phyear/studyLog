# spring主要的核心包
一般来说，想要搭建一个简单的spring小项目，只需要导入core、bean、context就可以使用一些基本功能了

## 创建一个maven项目，导入这三个包。

```
 <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.1.8.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.1.8.RELEASE</version>
        </dependency>
```


## 引入添加spring.xml 配置一个bean

```
public class Entity {

    public void hello(){
        System.out.println("adas");
    }
}

```

```
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd"
>

 <bean id="hello" class="Entity"></bean>


</beans>
```

## 调用xml的bean完成实例
```
ApplicationContext tx=new ClassPathXmlApplicationContext("spring.xml");
       Entity entity= (Entity) tx.getBean("hello");
       entity.hello();
```


# 复杂bean，多文件拆分，和引入
在spring配置多很多bean时，为了容易理解和清晰关系，可以使用将多个bean 从application.xml 中提取出来，分成多个xml文件。通过在主配置文件引入文件，完成整合
```
 //可能存在的文件，按层次划分。。也可以按功能划分等等
 dao.xml
 entity.xml
 service.xml
 ....
```

application.xml中使用`<import resource=""/>`引入
```
<beans>
    <import resource="services.xml"/>
    <import resource="dao.xml"/>
    <import resource="entity.xml"/> 
</beans>
```


# Bean的属性
属性|描述
:--|:--
Class|托管类的路径
Name|Bean的名字
ID|bean的唯一标识，只允许出现一个
Scope|作用域:singleton单例模式,prototype,request,session模式,application



## bean 依赖注入
>前提条件：参数完成get和set，必须有构造函数，写入的参数必须对应
```
<bean id="hello" class="Entity">
   //根据参数名赋值
   <!--<constructor-arg name="va" value="1"/>-->
   <!--<constructor-arg name="msg" value="aaa"/>-->
   //根据参数类型赋值
  <constructor-arg type="int" value="1"/>
  <constructor-arg type="java.lang.String" value="aaa"/>
   //根据参数位置赋值
  <!--<constructor-arg index="0" value="1"/>-->
  <!--<constructor-arg index="1" value="aaa"/>-->

 </bean>
```

两种注入模式：
- 基于构造函数注入
根据构造函数去注入参数或者值
- 基于setterd方法注入
通过类中setter方法去注入
> setter注入方式可能会导致不是完全注入，有的属性并不会实现setter方式，这是基于构造函数的方式是唯一的选择




 ## 指定构建时使用的工厂方法`factory-method`
使用实例工厂方法实例化
第一种：

 ```
    public  static  Entity entity=new Entity();

    public static Entity createInstance(){
        return  entity;
    }
 ```

```
<bean id="hello" class="Entity" factory-method="createInstance">
```

第二种：

 ```
    public  static  Entity entity=new Entity();

    public  Entity createInstance(){
        return  entity;
    }
 ```

```
<bean id="hello" class="Entity">

<bean id="hellos" factory-bean="hello" factory-method="createInstance">
```


## 集合类型的注入方式

- Properties(java.util.properties)
```
<!--properties-->
  <property name="properties">
   <props>
    <prop key="name">312</prop>
    <prop key="age">12</prop>
    <prop key="driver">2313</prop>
   </props>
  </property>

```

- List类型(java.util.List)
```

  <!--List集合-->
  <property name="list">
     <list>
       <value>ada</value>
      <value>ada</value>
     </list>
  </property>
  
  
  ```
- Map(java.util.Map)
```
<!--Map-->
  <property name="map">
      <map>
        <entry key="name" value="aas"/>
         <entry key="age" value="ada"/>
      </map>
  </property>
```
- set类型(java.util.Set)
```
 <!--Set-->
   <property name="set">

     <set>
        <value>asdasd</value>
        <value>sdadfg</value>
     </set>
   </property>
  ```

# 方法注入：<lookup-method>
```
<bean id="a" class="A"></bean>
<bean id="b" Class="B">
  <lookup-method name="createName" bean="a">
</bean>  
```

# 任意方法替换
主要适用于父类和子类的方法替换

```
 <bean id="parent" class="Parent">
   <replaced-method name="getName" bean="children">
   <arg-type>String</arg-type>
   </replaced-method>
 </bean>

 <bean id="children" class="children" ></bean>
    
```

# bean 的作用域
Scope|Description
:--|:--
singleton|(Default) Scopes a single bean definition to a single object instance for each Spring IoC container.
prototype|Scopes a single bean definition to any number of object instances.
request|Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring ApplicationContext.
session|Scopes a single bean definition to the lifecycle of an HTTP Session. Only valid in the context of a web-aware Spring ApplicationContext.
application|Scopes a single bean definition to the lifecycle of a ServletContext. Only valid in the context of a web-aware Spring ApplicationContext.
websocket|Scopes a single bean definition to the lifecycle of a WebSocket. Only valid in the context of a web-aware Spring ApplicationContext.


> 单例模式Bean在注入原型模式的Bean的依赖在spring容器解析时，原型模式只能注入一次，当然可以通过方法注入的方式解决


# 生命周期回调


## Initialization callbacks（初始化回调）

可以使用`init-method`在bean上指定初始化时可能要执行的方法
```
//类中写init方法
public void init(){
      this.va=11;
      this.msg="adasd";
    }
```

```
//在bean中使用初始化方法，为类属性付初始化值
<bean id="hello" class="Entity"  init-method="init"></bean>
```
> 打印结果： Entity{va=11, msg='adasd'}

## Destruction callbacks（销毁回调）

可以指定bean在被销毁时执行的方法：
```
<bean id="hello" class="Entity" destroy-method="destory"/>
```


