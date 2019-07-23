# springboot中集成Mapper插件
```
<dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
            <version>${mysql.version}</version>
        </dependency> 
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-spring-boot-starter</artifactId>
            <version>2.1.5</version>
        </dependency>
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper-generator</artifactId>
            <version>1.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.6</version>
        </dependency>ss
```

> 在classpath路径下新建`generatorConfig.xml`
```
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <property name="javaFileEncoding" value="UTF-8"/>
        <property name="useMapperCommentGenerator" value="false"/>

        <plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
            <property name="caseSensitive" value="true"/>
            <property name="forceAnnotation" value="true"/>
            <property name="beginningDelimiter" value="`"/>
            <property name="endingDelimiter" value="`"/>
        </plugin>

        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/sakila?useUnicode=true&amp;characterEncoding=UTF8"
                        userId="root"
                        password="12345">
        </jdbcConnection>

        <!--MyBatis 生成器只需要生成 Model-->

        <javaModelGenerator targetPackage="com.example.testmybatis.pojo"
                            targetProject="../testmybatis/src/main/java/">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="false"/>
        </javaModelGenerator>
         <!--MyBatis 生成器只需要生成 Mapper-->
        <sqlMapGenerator targetPackage="com.example.testmybatis.mapper"
                         targetProject="../testmybatis/src/main/java/">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>
        <!--MyBatis 生成器只需要生成 Xml-->
        <javaClientGenerator targetPackage="com.example.testmybatis.mapper"
                             targetProject="../testmybatis/src/main/java/"
                             type="XMLMAPPER" >
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!--
        MyBatis 对应的表格
         也可以使用$匹配所有的表格：
         <table tableName="$"  domainObjectName="Film" enableCountByExample="false"
               enableUpdateByExample="false"
               enableDeleteByExample="false"
               enableSelectByExample="false"
               selectByExampleQueryId="false">

        </table>
         -->
        <table tableName="film"  domainObjectName="Film" enableCountByExample="false"
               enableUpdateByExample="false"
               enableDeleteByExample="false"
               enableSelectByExample="false"
               selectByExampleQueryId="false">

        </table>
    </context>
</generatorConfiguration>
```
> 配置数据源

```
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/sakila?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC&useSSL=false
spring.datasource.username=root
spring.datasource.password=12345
```

>创建main方法去运行mapper插件
```
 public class Test {
    public static void main(String[] args) throws InvalidConfigurationException, InterruptedException, SQLException, IOException, XMLParserException {

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config =
                cp.parseConfiguration(getResourceAsStream("generatorConfig.xml"));
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}

```

> Mapper插件的自动化构建工具，比常用的自动化工具多了
```
<plugin type="tk.mybatis.mapper.generator.MapperPlugin">
            <property name="mappers" value="tk.mybatis.mapper.common.Mapper"/>
            <property name="caseSensitive" value="true"/>
            <property name="forceAnnotation" value="true"/>
            <property name="beginningDelimiter" value="`"/>
            <property name="endingDelimiter" value="`"/>
        </plugin>

```

> 他将常用的公有的数据库操作，进行了封装实现，你只需要专注于特别的方法。较普通的自动化工具，他新增了很多方法，还有使用的注解，@version、@keySet等。


# Mapper插件的一些注解

## @NameStyle 命名规则
直接添加在类上
`@NameStyle(Style.camelhumpAndUppercase)`

属性|描述
:--|:--
normal|//原值
camelhump|//驼峰转下划线
uppercase|//转换为大写
lowercase|//转换为小写
camelhumpAndUppercase|//驼峰转下划线大写形式
camelhumpAndLowercase|//驼峰转下划线小写形式

## @Table 注解（JPA）
设置表名映射，添加了就不按实体转换，schema的优先级最高，多个属性存在时，只会使用`schema`

- name
- catalog 
- schema

## @Column 注解（JPA）
设置实体属性对应的表字段

- name：表中对应的字段名
- insertable：对insert语句有用
- updateable：对update语句有用

## @ColumnType 注解（Mapper）
设置字段的类型

## @Transient 注解（JPA）
@Transient 注解来告诉通用 Mapper 这不是表中的字段。

## 主键策略
主键策略注解，用于配置如何生成主键。
>@KeySql 注解：用于替换GeneratedValue

>@GeneratedValue 注解

## @Version 注解（乐观锁）
@Version 是实现乐观锁

## @RegisterMapper 注解
了解决通用 Mapper 中最常见的一个错误而增加的标记注解，该注解仅用于开发的通用接口，不是实体类上使用的