![](https://img.gejiba.com/images/12ddcb2ad9325e3150b446595a9171b7.png)

<h1 style="text-align:center">一个简单的课程点播系统</h1>

<div align=center>
<img src="https://img.shields.io/badge/JAVA-11-brightgreen?style=for-the-badge&logo=java">
<img src="https://img.shields.io/badge/MYSQL-8-blue?style=for-the-badge">
<img src="https://img.shields.io/badge/version-1.0-orange?style=for-the-badge">
<img src="https://img.shields.io/badge/SpringBoot-2.5.2-success?style=for-the-badge&logo=springboot">
<img src="https://img.shields.io/badge/Redis-6-red?style=for-the-badge&logo=redis">
</div>

# 📖使用方法

#### 1. 部署MYSQL数据库环境

```
将文件夹内的 "MYSQL数据库初始结构.sql" 导入进数据库
```

#### 2. 部署RabbitMQ消息队列

#### 3. 部署Redis

#### 4. 安装JAVA和MAVEN环境

#### 5. 配置后端源码/src/main/resources/application.yml中的内容

```yaml
#据实际情况修改如下内容:

    #    MYSQL用户名
    username: root
    #    MYSQL密码
    password: root
    #    MYSQL链接URL
    url: jdbc:mysql://数据库IP:数据库端口/数据库名?useUnicode=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&characterEncoding=UTF-8
    
    # redis服务器IP
    host: 127.0.0.1
    # redis服务器端口
    port: 6379
    # redis服务器密码
    password: admin
    # redis 数据默认超时时间
    cacheTtl: 600s

    # rabbitmq虚拟主机
    virtual-host: /
    # rabbitmq服务器IP
    host: 127.0.0.1
    # rabbitmq服务器用户名
    username: guest
    # rabbitmq服务器密码
    password: guest
    # rabbitmq服务器端口
    port: 5672
    
    
    #设置订单过期时间 单位毫秒
    order:
      expire: 60000
```

#### 6. 修改好配置文件中的配置项后 , 打包源码成Jar包

```bash
mvn install
mvn package
```

#### 7. 运行程序

```bash
java -jar CheeseEdu-1.0.jar
```

