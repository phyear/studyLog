# Socket
java socket是java对网络编程的封装好的工具包，便于编程人员使用他进行开发

# 具体操作步骤
主要两个概念：服务器和客户端
## 服务器实现（ServerSocket）
实例化一个ServerSocket，并设置端口号
```
ServerSocket serverSocket=new ServerSocket(9999); //端口号9999
//实现服务端阻塞socket连接
Socket socket=serverSocket.accept();
```



