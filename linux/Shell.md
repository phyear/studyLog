# shell脚本基础

## 输出打印（echo）
`echo "啊啊啊啊" `

## 变量
变量名=值

```
name="12334"
//输出打印
echo $name
```
### 设置变量只读 readonly


```
name="12334"

readonly name
#会报错
name="aaa"

#输出打印
echo $name
```

### 删除变量


```
name="12334"

unset name

#输出打印，没有任何值
echo $name
```

## bash(进入子进程)

## Shell 中有两种方式可以完成命令替换，一种是反引号` `，一种是$()
```
begin_time=`date`    #开始时间，使用``替换
sleep 20s            #休眠20秒
finish_time=$(date)  #结束时间，使用$()替换
echo "Begin time: $begin_time"
echo "Finish time: $finish_time"
```
![](/img/shelldate1.png)

### 使用时间戳
```
begin_time=`date +%s`    #开始时间，使用``替换
sleep 20s            #休眠20秒
finish_time=$(date +%s)  #结束时间，使用$()替换
echo "Begin time: $begin_time"
echo "Finish time: $finish_time"
```
![](/img/shelldate2.png)

## 位置



变量|含义
:--|:--
$0|	当前脚本的文件名。
$n（n≥1）|	传递给脚本或函数的参数。n 是一个数字，表示第几个参数。例如，第一个参数是 $1，第二个参数是 $2。
$#|	传递给脚本或函数的参数个数。
$*|	传递给脚本或函数的所有参数。
$@|	传递给脚本或函数的所有参数。当被双引号" "包含时，$@ 与 $* 稍有不同
$?|	上个命令的退出状态，或函数的返回值
$$|	当前 Shell 进程 ID。对于 Shell 脚本，就是这些脚本所在的进程 ID。

```
function  fun(){
     echo "你真是$1,不得不$2"
     echo $* #获取所有参数
     echo $$ #当前进程的ID
     exit 1
}

fun 丑 躲

echo "File Name: $0"

echo $?
```
![](/img/shell$.png)


