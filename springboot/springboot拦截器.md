# Interceptor（拦截器）

## 首先实现HandlerInterception这个类

```
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static  final Logger logger=LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.info("拦截器拦截");
        String uname= (String) request.getSession().getAttribute("username");
        if(uname!=null){
            return true;
        }
         response.sendRedirect("/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
    
}
```


## 配置刚刚写好的拦截器类
实现WebMvcConfigurer中的addInterceptors方法

```
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器，拦截所有路径，但是移除登录导航、注册
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
    }

}
```

## 测试接口
编写一个Controller类进行调试
```
@RestController
public class TestInterceptorController {

    @GetMapping("/test")
    public String test(){
        return "aaa";
    }


    @GetMapping("/login")
    public String login(){
        return "登录来了";
    }
}
```
直接访问 /test，应该会显示`登录来了`,应为test不在排除请求中，而且也没有username的session,会直接跳转到login

## 测试
> 看下图使用拦截器已经启作用了‘

![](/img/interceptors1.png)

> 使用PostMan去访问test接口,返回值是`登录来了`

![](/img/interceptors2.png)

