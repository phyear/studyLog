# AOP（面向切面编程）
## AOP理解图
![](/img/AOP1.png) 
![](/img/AOP2.png)

## 核心概念
1. Pointcut: 切点，用于定义哪个方法会被拦截，例如 1. execution(* cn.springcamp.springaop.service.*.*(..))

2. Advice: 拦截到方法后要执行的动作

3. Aspect: 切面，把Pointcut和Advice组合在一起形成一个切面

4. Join Point: 在执行时Pointcut的一个实例

5. Weaver: 实现AOP的框架，例如 AspectJ 或 Spring AOP

## AOP使用
依赖于aop的包
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### 普通的切面
创建切面类
```
@Aspect
@Component
public class AspctTestClass {
    private static final Logger log = LoggerFactory.getLogger(AspctTestClass.class);

    private static long  startTime;//开始时间

    private static long  endTime;//结束时间

//    @Around("@annotation(aspectTest)")
//    public Object around(AspectTest aspectTest, ProceedingJoinPoint joinPoint){
//      return  null;
//    }


    @Pointcut("execution(public * com.hand.test.springbootdemo.prop.*.*(..))")
    public void print() {

    }
    @Before("print()")
    public void before(JoinPoint joinPoint){
        log.info("前置切面before……");
        startTime=System.currentTimeMillis();
        ServletRequestAttributes servletRequestAttributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest=servletRequestAttributes.getRequest();
        String requestURI=httpServletRequest.getRequestURI();
        String ip=httpServletRequest.getRemoteAddr();
        String method=httpServletRequest.getMethod();
        String className=joinPoint.getSignature().getName();
        log.info("请求链接："+requestURI+" ip:"+ip+" 请求方法:"+method+" 类名:"+className);
    }
    @After("print()")
    public void after(JoinPoint joinPoint){
        endTime=System.currentTimeMillis();
        log.info("后置切面after……");
    }
    @AfterReturning(pointcut = "print()", returning = "object")
    public void getAfterReturn(Object object) {
        log.info("本次接口耗时={}ms", endTime-startTime);
        log.info("afterReturning={}", object.toString());
    }


}
```
> 在切点路径下创建一个控制类
![](/img/AOP3.png)

```
  @GetMapping("/test")
    public String test(){
        return  "aa";
    }
```
直接启动并访问`http://localhost:8080/test`

![](/img/AOP4.png)



## 自定义注解
1. 创建注解类
```
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AspectTest {
    String param() default "";
}
```
2. 创建切面类

```
@Aspect
@Component
public class AnontionAspect {
    private static  final Logger log=LoggerFactory.getLogger(AnontionAspect.class);
    @Around("@annotation(aspectTest)")
    public Object around(JoinPoint joinPoint,AspectTest aspectTest){
        log.info("注解启动");
     return  null;
    }
}
```

3. 使用注解
```
 @GetMapping("/test")
    @AspectTest
    public String test(){
        return  "aa";
    }
```
4. 运行效果
![](/img/AOP5.png)


