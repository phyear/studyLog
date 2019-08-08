
## 定义数据库表结构
```
databaseChangeLog(logicalFilePath:'test_user.groovy'){
   changeSet(id:'2019-7-31-test_user',author:'ztxemail@163.com'){
     createTable(tableName:'TEST_USER'){
         column(name:'ID',type:'BIGINT UNSIGNED',remarks: 'ID', autoIncrement: true){
             constraints(primaryKey:'true')
         }
         column(name:'NAME',type:'VARCHAR(32)',remarks: '员工名称')
     }
   }

}

```