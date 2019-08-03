# 基于微信小程序的GPS求救系统
#### 服务端

* 开发工具 IntelliJ IDEA *2019.1.1*
* 开发环境 JDK *1.8*
* 主要框架和依赖
    * Spring Boot *2.1.1.RELEASE*
    * Druid *1.1.10*
    * FastJson *1.2.15*
    * Jersey-Client *1.19.4*
* 数据库
    * MySQL *8.0.15*
    * Redis *3.2.100*
    
#### 其他说明
* 必须使用内网穿透才能让小程序真机调试连接到服务端
* 启动服务端前请先运行 frpc 文件夹中的 frpc.bat
    * 穿透地址： api4gpshelp.dragoncave.me
    * 本地端口： 8088
    * 远程端口： 8088