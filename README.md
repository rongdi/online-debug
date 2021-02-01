## 调试步骤
### 1、启动待调试程序/服务
添加如下启动参数启动online-debugger-test服务
``` -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5000```

如上表示开放5000端口，来用于远程调试连接
### 2、启动调试程序
正常配置application.properties端口启动online-debugger-core即可
### 3、开始调试
1）先调用online-debugger-core服务的/breakpoint接口给待调试服务打上可调试行的断点，如下

```
POST http://localhost:8888/breakpoint
tag:rongdi
className:com.debugger.test.controller.TestController
lineNumber:24
hostname:127.0.0.1
port:5000
```

2）再访问待调试程序打上断点的服务，这个时候这个接口应该已经被调试阻塞了，如下

```GET http://localhost:8080/test?name=zhangsan```

3）这时候断点调试breakpoint接口应该已经返回了

4）可以继续使用断点调试打上另外一个后面的行，请求如下

```
POST http://localhost:8888/breakpoint
tag:rongdi
className:com.rdpaas.debugger.test.controller.TestController
lineNumber:28
hostname:127.0.0.1
port:5000
```

5）如果发现不需要断点调试或者已经到了最后，可以使用关闭连接放行调试

```
POST http://localhost:8888/disconnect
tag:rongdi
```

6）这时候被调试服务应该已经解除阻塞正常返回了

### 4、调试接口说明

#### 4.1、断点调试接口 /breakpoint
```$xslt
tag:rongdi  // 调试连接唯一标识
className:com.rdpaas.debugger.test.controller // 待断点的类全限定名
lineNumber:33 // 断点行号
hostname:127.0.0.1 //待调试程序的ip
port:5000 //待调试程序暴露的调试端口
```
#### 4.2、结束调试接口 /disconnect
```$xslt
tag:rongdi  // 调试连接唯一标识，一定要与断点调试接口保持一致
```

### 5、调试注意事项
#### 5.1、先请求调试服务的断点接口再请求被调试接口
和在本地使用开发工具断点调试一样，一定要有断点才可以进入调试，先调用断点接口，再请求待调试接口
#### 5.2、被调试服务一定要以调试模式启动
启动被调试服务一定要添加调试模式相关参数
``` -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5000```
#### 5.3、断点调试接口传入的ip和端口一定要可以被访问
断点调试接口传入的ip和端口一定要保证在调试程序所在服务器上可以访问到被调试程序
#### 5.4、调试结束或者不再需要断点最好使用/discount接口释放连接
如果调试结束或者已经不需要断点，要记得使用discount接口释放调试连接，从而释放被调试阻塞的被调试服务。

