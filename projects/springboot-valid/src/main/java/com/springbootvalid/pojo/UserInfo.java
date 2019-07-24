package com.springbootvalid.pojo;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserInfo {

   @NotBlank(message = "姓名不能为空")
   private String name;
   @NotBlank(message = "链接不能为空")
   @URL
   private String url;
   @Min(value = 12,message = "年龄必须大于十二周岁")
   @Max(value = 100,message = "你真的这么老吗")
   private int  age;


   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   @Override
   public String toString() {
      return "UserInfo{" +
              "name='" + name + '\'' +
              ", url='" + url + '\'' +
              ", age=" + age +
              '}';
   }
}
