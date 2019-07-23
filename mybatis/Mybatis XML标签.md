# choose(when,otherwise) 类似于java中 switch

```
<select>
 select * from user 
 <where>
    <choose>
      <when test="name!=null">and name=${name}</when>
      <when test="age!=null">and age=${age}</when>
      <when test="address!=null">and address=${address}</when>
      <otherwise></otherwise> 
    </choose>
 </where>

<select>
```

# foreach标签--遍历数组和集合
>## 注意：只能是当个参数，不能是object对象
## 传入array
```
<select id="dynamicForeach3Test" parameterType="java.util.HashMap" resultType="Blog">
         select * from t_blog where title like "%"#{title}"%" and id in
         <foreach collection="array" index="index" item="item" open="(" separator="," close=")">
               #{item}
         </foreach>
 </select>
```
## 传入list
```
<select id="dynamicForeach3Test" parameterType="java.util.HashMap" resultType="Blog">
         select * from t_blog where title like "%"#{title}"%" and id in
         <foreach collection="list" index="index" item="item" open="(" separator="," close=")">
               #{item}
         </foreach>
 </select>
```

## 传入Map，collection的值写map的Key值
```
<select id="dynamicForeach3Test" parameterType="java.util.HashMap" resultType="Blog">
         select * from t_blog where title like "%"#{title}"%" and id in
         <foreach collection="ids" index="index" item="item" open="(" separator="," close=")">
               #{item}
         </foreach>
 </select>
```