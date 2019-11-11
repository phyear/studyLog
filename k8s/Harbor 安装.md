## Harbor仓库的安装

前置条件:
  主机上已经安装了docker以及docker-compose

## 在下载Harbor离线包
通过以下的地址去选择安装包：

[https://storage.googleapis.com/harbor-releases](https://storage.googleapis.com/harbor-releases)

##### 下载离线包
```
wget  --continue https://storage.googleapis.com/harbor-releases/release-1.8.0/harbor-online-installer-v1.8.1.tgz
```

##### 解压
```
tar zxvf harbor-offline-installer-v1.8.1.tgz
```
## 修改harbor配置
```
cd /harbor #到当前harbor目录下去
vim harbor.cfg
```
大多数情况下就需要修改下hostname和证书
```
#云主机很一般为外网IP
#也可以是域名地址
#不能写127.0.0.1 localhost
hostname = 178.10.123.22
```
```
./prepare
./install.sh
```

