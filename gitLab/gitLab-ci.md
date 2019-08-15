# gitLab-ci 参数明细

## stages
stages用来定义可以被job调用的stages。

stages的规范允许有灵活的多级pipelines。stages中元素的顺序决定了对应job的执行顺序

- 相同stage的job是并行执行的；
- 下一个stage的job在前一个stage的job成功完成后才开始执行；
- 如果.gitlab-ci.yml中没有定义stages，那么stages默认定义为build、test和deploy；
- 如果一个job没有指定stage，那么这个任务会分配到test stage。

## variables

variables用来定义变量，全局变量作用于所有job，也可以在指定的job中定义变量（优先级高于全局变量）
如果在job中想禁用全局定义的变量，可通过variables: {}定义一个空的哈希值。

## Jobs
定义一组作业

## job.stage（默认是test）
这里的stage指向前面定义的stages里面的参数

## job.tags
设置job注册Runner指定的tag

## job.allow_failure
设置指定的job是否允许执行失败，当前job执行失败也不会影响下一个job的执行。

## job.script

script是job必须执行的语句，指定runner时执行的脚本命令

## job.before_script、job.after_script

指定在job的script执行之前或者之后执行的语句，也可以设置器全局，不管之前还是之后都会执行。


## job.artifacts
用于指定执行成功后，发送到gitLab的文件，默认会按照优先级下载之前多有的文件

- artifacts.paths：必选
- artifacts.name：指定artifact的名称，同时Gitlab上下载的文件名即为artifact_name.zip
- artifacts.when：指定artifact上传到Gitlab的条件（on_success [默认],on_failure,always）
- artifacts.expire_in：指定artifact的过期时间（默认为30天），使用keep可永久保存


## job.dependencies
用于在不同的job下传递artifacts ,可以指定job不下载artifacts

## job.only、job.except

only和except是两个参数用分支策略来限制jobs构建

only和except可同时使用。如果在一个job配置中同时存在，则同时有效；

only和except可以使用正则表达式；

only和except允许使用特殊的关键字：branches，tags和triggers；
