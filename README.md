# SpringAi

这个应用程序展示了如何使用Spring AI阿里巴巴来构建一个AI驱动的系统，该系统：

- 能够访问条款和条件（检索增强生成，RAG）
- 可以访问工具（Java方法）来执行操作（函数调用）
- 使用LLM与用户交互

## 需求

- Java 17+
- 环境变量“AI_DASHSCOPE_API_KEY”中的Dashscope API KEY

## 运行

通过在IDE中运行‘ Application.java ’或在命令行中运行‘ mvn spring-boot: Run ’来运行应用程序。


在POM中添加Spring AI阿里巴巴启动器：

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-alibaba-starter</artifactId>
    <version>1.0.0-M3.3</version>
</dependency>
```

将DashScope的配置添加到“application.properties”中：

```
spring.ai.dashscope.api-key=${AI_DASHSCOPE_API_KEY}
spring.ai.dashscope.chat.options.model=qwen-max
```

## 项目构建

```shell
./mvnw clean package
```

```shell
java -jar ./target/playground-flight-bgd-example-0.0.1-SNAPSHOT.jar
```


## 前端构建（按需）

运行以下命令来构建前端，只有在对前端进行更改时才需要这样做。

```shell
mvn clean compile -P build-frontend
```
