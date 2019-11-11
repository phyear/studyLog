## Harbor 管理 chart 包

前置条件：
1.harbor版本在1.6.0 以上

## 安装harbor
在安装harbor时 配置管理使用helm-charts
```
./install.sh  --with-chartmuseum

```
## Helm 配置
1. helm 安装 push 插件
2. helm 添加添加源
```
helm plugin install https://github.com/chartmuseum/helm-push
helm  repo add  mylibrary  http://xxxx/chartrepo/library
helm repo list
```
## 上传
```
helm fetch stable/redis-ha   #下载charts
helm push redis-ha-2.0.1.tgz  mylibrary	  #上传到私有仓库
```

## Harbor管理chart 的API
接口|请求方式|参数|接口功能
:--|:--|:--|:--|
`api/chartrepo/health`|get||验证chartrepo库的运行状况
`api/chartrepo/{repo}/charts`|get|`repo`:项目的名称|获取指定项目下的chart
`api/chartrepo/{repo}/charts`|post|`repo`:项目的名称 `chart`: chart 文件  `prov`: prov 文件|将chart文件上传到指定的项目。
`api/chartrepo/{repo}/charts/{name}`|get|`repo`:项目的名称 `name` ：chart 名称|获取指定chart的所有版本
`api/chartrepo/{repo}/charts/{name}`|delete|`repo`:项目的名称 `name` ：chart 名称|删除指定chart的所有版本
`api/chartrepo/{repo}/charts/{name}/{version}`|get|`repo`:项目的名称 `name` ：chart 名称 `version`:版本 |获取chart指定版本
`api/chartrepo/{repo}/charts/{name}/{version}`|delete|`repo`:项目的名称 `name` ：chart 名称 `version`:版本 |删除指定的版本
`api/chartrepo/charts`| post|`chart`: chart 文件  `prov`: prov 文件|上传一个chart 到默认的library项目下
`api/chartrepo/{repo}/charts/{name}/{version}/labels`|get|`repo`:项目的名称 `name` ：chart 名称 `version`:版本|返回指定chart版本的附加标签

