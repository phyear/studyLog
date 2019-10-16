# Prometheus
Prometheus是什么？Prometheus是由SoundCloud开发的开源监控报警系统和时序列数据库(TSDB)。Prometheus使用Go语言开发，是Google BorgMon监控系统的开源版本。

prometheus结构图如下：
![image](https://minio.choerodon.com.cn/knowledgebase-service/file_e8c47fffb78e43999829f276f47463fc_blob.png)

# Prometheus Operator
prometheus Operator 可以在 Kubernetes 中创建、配置和管理 Prometheus 集群。这个 Chart 包括了多种组件以用于不同的用途。
![image](https://minio.choerodon.com.cn/knowledgebase-service/file_408931eef2fe4d5da54f1321617ccae0_blob.png)

默认安装会监控安装这个 chart 的 kubernetes 集群。它与 kube-prometheus 项目紧密相关。

- [prometheus-operator](https://github.com/coreos/prometheus-operator)
- [prometheus](https://prometheus.io/)
- [alertmanager](https://prometheus.io/)
- [node-exporter](https://github.com/helm/charts/tree/master/stable/prometheus-node-exporter)
- [kube-state-metrics](https://github.com/helm/charts/tree/master/stable/kube-state-metrics)
- [grafana](https://github.com/helm/charts/tree/master/stable/grafana)
- 收集 Kubernetes 内部组件指标的监控服务
  - kube-apiserver
  - kube-scheduler
  - kube-controller-manager
  - etcd
  - kube-dns/coredns
  - kube-proxy

## 基础条件

- Kubernetes 1.10+ with Beta APIs
- Helm 2.10+
- 对于版本高于 0.18.0 的 Prometheus Operator 要求 Kubernetes 集群版本高于 1.8.0。如果你才开始使用 Prometheus Operator，推荐你使用最新版。
    
## 组件

### 1. 必须的组件:
组件|功能描述
:-|:-
prometheus-operator|可以非常简单的在kubernetes集群中部署Prometheus服务，并且提供对kubernetes集群的监控，并且可以配置和管理prometheus
prometheus|监控告警工具
alertmanager|用于接收 Prometheus 发送的告警信息，它支持丰富的告警通知渠道，而且很容易做到告警信息进行去重，降噪，分组等，是一款前卫的告警通知系统。
node-exporter|用于采集服务器层面的运行指标，包括机器的loadavg、filesystem、meminfo等基础监控，类似于传统主机监控维度的zabbix-agent
kube-state-metrics|轮询Kubernetes API，并将Kubernetes的结构化信息转换为metrics
grafana|用于大规模指标数据的可视化展现，是网络架构和应用分析中最流行的时序数据展示工具

### 2.可选的组件:
组件|功能介绍
:-|:-
mysqld_exporter|监控数据库的工具
redis_exporter|监控redis的工具
cadvisor|监控docker容器运行状态
## 安装 Chart
通过安装一个版本名为 my-release 的 chart：

```bash
helm install --name my-release c7n/prometheus-operator
```

这会在集群中安装一个默认配置的 prometheus-operator。这份[配置文件](https://github.com/helm/charts/blob/master/stable/prometheus-operator/README.md#configuration)列出了安装过程中可以配置的选项。

默认会安装 Prometheus Operator, Alertmanager, Grafana。并且会抓取集群的基本信息
安装部署后，通过value.yaml中配置的ingress的域名即可直接访问Grafana界面。查看指定集群的监控信息

### 配置信息
下面列出 Prometheus Operator 常用可配置的参数，其他配置参考[官方文档](https://github.com/helm/charts/tree/master/stable/prometheus-operator#configuration)



### 3.Prometheus

| 参数 | 描述 | 必填 |
|------|------|-------|
| `prometheus.ingress.hosts` | Prometheus  的域名 | `true`|
| `prometheus.ingress.paths` | Prometheus 的 路径 | `true` |
| `prometheus.prometheusSpec.storageSpec.volumeClaimTemplate.spec.storageClassName` | 指定集群中已创建的存储类的name| `true` |
| `prometheus.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 | `true` |

### 4.Grafana

| 参数 | 描述 | 必填 |
|------|------|-------|
| `grafana.adminPassword` | 登录grafana UI的管理员密码 |  `true` |
| `grafana.ingress.hosts` | Grafana  的 域名 |  `true` |
| `grafana.grafana.ini.server.root_url` |root的地址 |  `true` |
| `grafana.grafana.ini.auth.generic_oauth.auth_url` | 权限服务器授权地址 |  `true` |
| `grafana.grafana.ini.auth.generic_oauth.token_url` | 权限服务器获取token（ |  `true` |
| `grafana.grafana.ini.auth.generic_oauth.api_url` | 权限服务器api地址 |  `true` |
| `grafana.persistence.storageClassName` | grafana 存储定义,指定集群中已创建的存储类的name | `true`|
| `grafana.serviceMonitor.relabelings` | grafana 实例收集的 `relabel_configs` 配置，需要修改 `cluster` 标签的replacement 为目标集群名 | `true`|


### 5.Alertmanager

| 参数 | 描述 | 必填 |
|------|------|-------|
| `alertmanager.ingress.hosts` | 设置Alertmanager 的域名 |  `true` |
| `alertmanager.ingress.paths` | 设置 Alertmanager 的 路径|  `true` |
| `alertmanager.alertmanagerSpec.storage.volumeClaimTemplate.spec.storageClassName` |指定集群中已创建的存储类的name |`true`  |
| `alertmanager.serviceMonitor.relabelings` |  alertmanager 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名  | `true` |

### 6. Exports

| 参数 | 描述 | 必填 |
|------|------|-------|
| `kubeApiServer.serviceMonitor.relabelings.replacement` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 | `true`  |
| `kubelet.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 |`true`   |
| `kubelet.serviceMonitor.cAdvisorRelabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 |`true`   |
| `kubeControllermanager.serviceMonitor.relabelings` |实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 | `true`  |
| `coreDns.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 |`true`   |
| `kubeDns.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 | `true`  |
| `kubeScheduler.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 | `true`  |
| `kubeProxy.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 |`true`   |
| `kubeStateMetrics.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 |`true`   |
| `nodeExporter.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 | `true`  |
| `prometheusOperator.serviceMonitor.relabelings` | 实例收集的 `relabel_configs`，需要修改 `cluster` 标签的replacement 为目标集群名 | `true`  |






