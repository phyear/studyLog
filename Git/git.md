# 1.Git记录
 ## 1.1 git help
> git的帮助命令 可以很好的提示
## 1.2 git config --本地全局配置
```
git config --global user.name "John Doe"  //用户名
git config --global user.email johndoe@example.com //邮箱
```
> 作用于github提交后可以查看是谁提交的
## 1.3 git本地仓库 
- 创建本地仓库 -- git init
- 提交文件到暂存区 -- git add 文件名
- 提交到本地仓库   --  git commit -m "一些提交信息"

## 1.4 git远程提交和拉取
初次提交
```
//第一步：连接远程仓库
git remote add origin git@github.com:michaelliao/learngit.git
//第二步：提交到远程仓库：master为主要分支
git push -u origin master
```
除了初次提交，其他的直接push就可以了
## 1.5 git Branch（git 分支）
直接使用命令`git branch 分支名`创建分支

切换分支使用`git checkout -b 分支名`

删除分支使用`git checkout -D 分支名` 

>一般不建议直接在本地创建分支

## 1.6 git ssh
在本地生成秘钥，再到github设置公钥就可以直接 push 文件
```
ssh-keygen -t rsa -C "your_email@example.com"
```
在github->setting->ssh and GPG keys ->ssh Keys
直接粘贴公钥就可以了

## 1.5git flow