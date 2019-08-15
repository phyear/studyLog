## Hzero数据初始化
下载hzeo-resource    `https://code.choerodon.com.cn/hzero-hzero/hzero-resource.git`

使用data-init.sh 脚本初始化数据

## Hzero初始化数据问题
-  #### Specified key was too long; max key length is 767 bytes 
> InnoDB 引擎单一字段索引的长度最大为 767 字节，同样的，前缀索引也有同样的限制。
> 当使用 UTF-8 字符集，每一个字符使用 3 字节来存储，在 TEXT 或者 VARCHAR 类型的字段上建立一个超过 255 字符数的前缀索引时就会遇到问题。

>解决方案：这个坑来自于mysql的版本差异，mysql5.6出现此问题，换成5.7 以上的的完全不会停，一路无阻。
## hzero 基本服务的依赖顺序
服务|	简码|	默认端口	|描述
:--|:--|:--|:--
hzero-register|		HREG|		8000|		注册中心
hzero-config	|	HCFG|		8010	|	配置服务
hzero-gateway|		HGWY|		8080|		网关服务
hzero-oauth	|	HOTH	|	8020	|	认证服务
hzero-iam|		HIAM	|	8030|		IAM服务
hzero-swagger|		HSWG	|	8050|		Swagger测试服务（开发环境可装）
hzero-platform|		HPFM|		8100|		平台服务

## Hzero访问权限问题
在搭建完成基本服务后，尝试访问[swagger](http://localhost:8080/swagger/swagger-ui.html)
随便点击一个接口，跳转到登录界面输入用户名和密码： `admin/Admin@123` 报`组织错误`

>解决办法：在`iam_user`表中的`organization_id`字段值改为1
