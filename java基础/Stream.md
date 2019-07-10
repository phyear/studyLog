# Java流式编程（Stream）
Stream（流）是一个来自数据源的元素队列并支持聚合操作
* 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
* 数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
* 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
和以前的Collection操作不同， Stream操作还有两个基础的特征：

* Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
* 内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。

## 产生流
* stream() − 为集合创建串行流。

* parallelStream() − 为集合创建并行流。

```
List<String> strings = Arrays.asList("ss", "dd", "bfc", "sd", "ada","fghj", "yyy");
//串行流
strings.stream()
//并行流
strings.parallelStream() 
```
## 聚合操作
### map //对元素进行操作
```
List<String> strings = Arrays.asList("ss", "dd", "bfc", "sd", "ada","fghj", "yyy");
strings=strings.stream().map(n->n+"ss").collet(Collectors.toList());//拼接操作
```
### filter //对元素进行过滤操作
```
List<String> strings = Arrays.asList("ss", "dd", "bfc", "sd", "ada","fghj", "yyy");
strings=strings.stream().filter(n->n.isEmpty());//过滤操作
```

### limit //方法用于获取指定数量的流
```
Random random = new Random();
random.ints().limit(10).forEach(Sysout.out::println);
```

### collect //指定返回指定的结合或者字符串
```
        List<String> strings = Arrays.asList("ss", "dd", "bfc", "sd", "ada","fghj", "yyy");
        strings=strings.stream().map(n->n+"ss").collect(Collectors.toList());
        System.out.println(strings);
```
![](img/StreamCollect.png)
### sorted //排序
```
Random random=new Random();
random.ints().limit(20).sorted().forEach(System.out::println);
```
![运行图片](img/StreamSorted.png)

### 统计
```
        List<Integer> numbers = Arrays.asList(3, 2, 11, 9, 7, 9, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
```
![](img/StremCount.png)






