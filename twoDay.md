# docker安装实践
## 镜像加速器
```
{		
    "registry-mirrors":	[			
    "https:sr5arhkn.mirror.aliyuncs.com",
    "http://14d216f4.m.daocloud.io"],
    "insecure-registries":	[] 
    }

```

docker pull mysql：5.7

docker run -p 3306:3306 -d bash

(变量类型)转换变量
```
例如：
    int i=5;
    byte b=(byte)i;


```
可能导致精度的丢失
如：
```
float a=12.15f;
int b=(int)a;
System.out.println(b);
```
