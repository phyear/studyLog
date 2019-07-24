# springboot 监听器
## 监听器的作用：
一般用于统计在线人数和在线用户，系统加载时进行信息初始化，统计网站的访问量等等

## 监听器的分类
- 按监听的对象划分，可以分为
  - ServletContext对象监听器
  - HttpSession对象监听器
  - ServletRequest对象监听器
- 按监听的事件划分
  - 对象自身的创建和销毁的监听器
  - 对象中属性的创建和消除的监听器

## 实现在线人数统计

编写监听内容
```
@WebListener
public class HttpSessionListenerTest implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {

        //获取session的上下文
        ServletContext servletContext = se.getSession().getServletContext();
        //判断count是否存在,不存在就创建
        if(StringUtils.isEmpty( servletContext.getAttribute("count"))){
            servletContext.setAttribute("count",0);
        }
        int count= (int) servletContext.getAttribute("count");
        //增加
        servletContext.setAttribute("count",count+1);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        if(StringUtils.isEmpty( servletContext.getAttribute("count"))){
            servletContext.setAttribute("count",0);
        }
        int count= (int) servletContext.getAttribute("count");
        if(count==0){ servletContext.setAttribute("count",0);}
        if(count>0){servletContext.setAttribute("count",count-1);}
    }
}

```
> 在主类添上注解@ServletComponentScan
```
@SpringBootApplication
@ServletComponentScan
public class SpringbootListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootListenerApplication.class, args);
    }

}

```

## controller 中编写
```
  @RequestMapping("/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("name","aaa");
        int count=0;
        if(session.getServletContext().getAttribute("count")!=null){
            count= (int) session.getServletContext().getAttribute("count");
        }
        return "当前在线人数："+count;
    }
```

> 不同浏览器访问`http://localhost:8080/test` 查看返回结果


