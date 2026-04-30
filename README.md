# dbfound-spring-boot-starter

`dbfound-spring-boot-starter` 是 `dbfound` 在 Spring Boot 项目中的官方集成方式，用于在 Spring Boot 应用里快速启用：

- XML model 数据接口开发
- `ModelExecutor` Java 调用
- HTTP 方式访问 query / execute
- 多数据源与上下文能力

如果你正在使用 Spring Boot，希望以更少的样板代码开发后台数据接口，这个 starter 就是推荐入口。

## 它解决什么问题

在很多 Spring Boot 项目中，一个普通数据接口通常要写：

- Controller
- Service
- Repository / Mapper
- SQL 映射
- 参数对象 / 返回对象

而使用 `dbfound-spring-boot-starter` 后，你可以把大量数据访问逻辑直接放进 model XML 中，再通过统一的 `ModelExecutor` 或 HTTP 接口进行调用。

这特别适合：

- 管理后台
- 运营平台
- 数据报表
- 导入导出
- 内部业务系统

## 适用版本

请根据你的 Spring Boot 技术栈选择对应版本。

一般来说：

- Spring Boot 2.x 项目，通常使用基于 `javax.servlet` 的版本线
- Spring Boot 3.x 项目，通常使用基于 `jakarta.servlet` 的版本线

具体版本请以 Maven Central、仓库 release 或项目说明为准。

## 快速开始

## 1. 引入依赖

在 `pom.xml` 中加入：

```xml
<dependency>
    <groupId>com.github.nfwork</groupId>
    <artifactId>dbfound-spring-boot-starter</artifactId>
    <version>最新版本</version>
</dependency>
```

建议将版本替换为当前可用的正式版本。

## 2. 配置数据源

最简单的配置示例：

```yaml
dbfound:
  datasource:
    db0:
      url: jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
      username: root
      password: 123456
```

如果需要多个数据源，可以继续配置多个 `dbfound.datasource.db*` 节点，并通过 `provide-name` 或 `connectionProvide` 进行匹配。

## 3. 创建 model 文件

在 `classpath:model/` 目录下创建 XML 文件，例如：

`src/main/resources/model/sys/user.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<model xmlns="http://dbfound.googlecode.com/model"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://dbfound.googlecode.com/model https://raw.githubusercontent.com/nfwork/dbfound/master/tags/model.xsd">

    <query>
        <sql>
            select user_id, username, nick_name
            from sys_user
            #WHERE_CLAUSE#
            order by user_id desc
        </sql>
        <filter name="username" dataType="varchar" express="username like concat('%', ${@username}, '%')"/>
    </query>

</model>
```

这里的 modelName 就是：

```text
sys/user
```

## 4. 通过 Java 调用

在 Spring Bean 中注入 `ModelExecutor`：

```java
import com.github.nfwork.dbfound.starter.ModelExecutor;
import com.nfwork.dbfound.core.Context;

@Service
public class UserService {

    private final ModelExecutor modelExecutor;

    public UserService(ModelExecutor modelExecutor) {
        this.modelExecutor = modelExecutor;
    }

    public List<Map<String, Object>> listUsers(String username) {
        Context context = new Context().withParam("username", username);
        return modelExecutor.queryList(context, "sys/user", null);
    }
}
```

## 5. 通过 HTTP 调用

Starter 启动后，也可以直接通过 HTTP 调用 model：

```text
/sys/user.query
```

命名 query / execute 的地址规则分别为：

- `{modelName}.query!{queryName}`
- `{modelName}.execute!{executeName}`

## 核心能力

接入 starter 后，你可以直接使用 `dbfound` 的核心模型能力：

- `query`
- `execute`
- `filter`
- `verifier`
- `collisionSql`
- `sqlPart`
- `batchExecuteSql`
- `batchSql`
- `excelReader`
- `QueryAdapter / ExecuteAdapter`

同时，还可以通过 Spring Boot 的方式把它们整合进已有项目。

## 常见调用方式

`ModelExecutor` 中最常用的方法包括：

### 查询列表

```java
List<UserDto> list = modelExecutor.queryList(context, "sys/user", "list", UserDto.class);
```

### 查询单条

```java
UserDto user = modelExecutor.queryOne(context, "sys/user", "getById", UserDto.class);
```

### 查询带分页返回

```java
QueryResponseObject<UserDto> result = modelExecutor.query(context, "sys/user", "list", UserDto.class);
```

### 执行写操作

```java
modelExecutor.execute(context, "sys/user", "update");
```

### 批量执行

```java
modelExecutor.batchExecute(context, "sys/user", "update", "param.userList");
```
- 此方法内部仅为单纯的for循环处理，不具备sql批处理能力。如果大量数据需要批处理，请使用model api中的batchExecuteSql 

## model 文件推荐结构

在 Spring Boot 项目中，推荐采用下面的组织方式：

```text
src/main/resources/model/
  sys/
    user.xml
    role.xml
  report/
    daily.xml
  base/
    config.xml
```

推荐约定：

- 一个业务领域一个目录
- 一个资源对象一个 model 文件
- query / execute 命名尽量语义化

这样更适合多人维护。

## 适合放在 XML 的逻辑

推荐放在 model XML 中：

- SQL 查询
- 动态过滤条件
- 分页参数
- 关联查询
- 批量插入、更新
- 导入导出
- 通用校验

## 适合放在 Java / Service 的逻辑

推荐放在 Java 中：

- 认证与权限入口
- 多个 model 的业务编排
- 外部系统调用
- 复杂领域逻辑
- 对 HTTP 协议或页面协议强绑定的组装逻辑

## 进阶能力

如果简单 query / execute 已经不能满足需求，可以继续使用：

### 1. Adapter

通过 `QueryAdapter` / `ExecuteAdapter` 做：

- 查询前参数预处理
- 查询后结果增强
- execute 前后做权限校验、日志记录或补充逻辑

### 2. 多数据源

通过 `dbfound.datasource` 与 `connectionProvide` 组合使用不同数据源。

### 3. 批量处理

使用 `batchExecuteSql`、`batchSql`、`excelReader` 完成批量导入或批量写入。

## 常见问题

### 1. modelName 是什么

modelName 就是 `classpath:model/` 下的相对路径去掉 `.xml` 后的结果。

例如：

- `model/sys/user.xml` -> `sys/user`

### 2. Java 调用时参数放哪里

通常放在 `Context` 的 `param` 区，也就是：

```java
context.setParamData("id", 1);
```

### 3. 什么时候用 starter，什么时候直接看 dbfound 主项目

- 想在 Spring Boot 里落地：优先看 starter
- 想理解 model 语法本身：看 dbfound 主项目 wiki

## 推荐阅读顺序

建议按这个顺序阅读：

1. 本 README
2. [dbfound model api](https://github.com/nfwork/dbfound/wiki/dbfound-model-api)
3. [dbfound java api](https://github.com/nfwork/dbfound/wiki/dbfound-java-api)
4. [dbfound model adapter](https://github.com/nfwork/dbfound/wiki/dbfound-model-adapter)
5. [dbfound example](https://github.com/nfwork/dbfound/wiki/dbfound-example)

## 相关项目

- [dbfound](https://github.com/nfwork/dbfound)
- [dbfound-world](https://github.com/nfwork/dbfound-world)
- [dbfound-springboot-demo](https://github.com/nfwork/dbfound-springboot-demo)

## 一句话总结

如果 `dbfound` 是核心引擎，那么 `dbfound-spring-boot-starter` 就是它在现代 Spring Boot 项目中的推荐接入方式：更容易启动、更容易调用，也更适合现在常见的前后端分离应用。
