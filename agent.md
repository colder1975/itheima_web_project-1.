# agent.md — Tlias Web Management

## 项目概述

Tlias（天籁）Web 管理系统，基于 Spring Boot 3 + MyBatis 构建的员工与部门管理后台 REST API 服务。

## 技术栈

| 类别 | 技术 | 版本 |
|------|------|------|
| 语言 | Java | 17 |
| 框架 | Spring Boot | 3.5.15 |
| ORM | MyBatis + PageHelper | 3.0.5 / 2.1.0 |
| 数据库 | MySQL | 8.x (MySQL Connector/J) |
| 工具 | Lombok | latest |
| 构建 | Maven | — |

## 项目结构

```
src/main/java/com/itheima/
├── TliasWebManagementApplication.java   # 启动类
├── controller/
│   ├── EmpController.java               # 员工接口 /emps
│   ├── DeptController.java              # 部门接口 /depts
│   └── UploadController.java            # 文件上传接口 /upload
├── service/
│   ├── EmpService.java                  # 员工服务接口
│   ├── DeptService.java                 # 部门服务接口
│   └── impl/
│       ├── EmpServiceImpl.java
│       └── DeptServiceImpl.java
├── mapper/
│   ├── EmpMapper.java                   # 员工 Mapper（接口 + 注解）
│   └── DeptMapper.java                  # 部门 Mapper（接口 + 注解）
└── pojo/
    ├── Emp.java                         # 员工实体
    ├── Dept.java                        # 部门实体
    ├── Result.java                      # 统一响应结果 {code, msg, data}
    └── PageBean.java                    # 分页结果 {total, rows}

src/main/java/com/itheima/utils/
└── AliOSSUtils.java                     # 阿里云OSS文件上传工具

src/main/resources/
├── application.yml                      # 主配置（数据源、MyBatis）
└── mapper/
    ├── EmpMapper.xml                     # 员工 SQL 映射
    └── DeptMapper.xml                    # 部门 SQL 映射
```

## 分层架构约定

严格遵守三层架构，**不可跨层调用**：

- **Controller** — 接收 HTTP 请求，参数校验，调用 Service，返回 Result
- **Service** — 业务逻辑，接口 + 实现类（`interface XxxService` + `impl/XxxServiceImpl`）
- **Mapper** — 数据访问，接口标注 `@Mapper`，复杂 SQL 写在 `resources/mapper/*.xml`

```
Controller → Service(接口) → ServiceImpl → Mapper → DB
```

## API 规范

### 统一响应格式

所有接口返回 `Result` 对象：

```json
{
  "code": 1,       
  "msg": "success",
  "data": null
}
```

使用静态工厂方法：
- 增删改成功：`Result.success()`
- 查询成功：`Result.success(data)`
- 失败：`Result.error("错误信息")`

### RESTful 路由命名

| 操作 | 方法 | 路径示例 |
|------|------|---------|
| 分页/列表 | GET | `/emps` |
| 单个查询 | GET | `/emps/{id}` |
| 新增 | POST | `/emps` |
| 更新 | PUT | `/emps` |
| 单个删除 | DELETE | `/emps/{id}` |
| 批量删除 | DELETE | `/emps/batch` |

## 编码规范

- **包名**：`com.itheima.*`
- **实体类**：使用 Lombok `@Data`、`@NoArgsConstructor`、`@AllArgsConstructor`
- **依赖注入**：使用 `@Autowired` 字段注入
- **参数绑定**：简单参数用 `@RequestParam`，路径参数用 `@PathVariable`，JSON 用 `@RequestBody`
- **MyBatis 参数**：多参数时使用 `@Param` 注解
- **日期格式**：前后端交互使用 `yyyy-MM-dd` 字符串
- **中文注释**：类和关键方法写中文 JavaDoc

## 数据库

- 数据库名：`tlias`
- 连接：`jdbc:mysql://localhost:3306/tlias`
- 字符集：UTF-8，时区 Asia/Shanghai
- 驼峰映射：`map-underscore-to-camel-case: true`（`user_name` → `userName`）
- 不自动执行 SQL 初始化脚本（`sql.init.mode: never`）

## 开发命令

```bash
# 启动应用
mvn spring-boot:run

# 运行测试（测试环境使用随机端口 server.port=0）
mvn test

# 打包
mvn clean package -DskipTests
```

## 分支与 Git 规范

- 主分支：`main`
- 功能分支：`feature/<功能名>`
- 修复分支：`fix/<问题描述>`
- 提交信息使用中文，格式：`类型: 描述`（如 `feat: 新增员工批量导入`, `fix: 修复分页越界`）

## 阿里云 OSS 文件上传

- Bucket 名称：`talis-web10`
- 配置项：`aliyun.oss.endpoint`、`access-key-id`、`access-key-secret`、`bucket-name`
- 上传接口：`POST /upload`，参数名 `file`，返回 OSS 公网 URL
- 文件命名：`images/{UUID}{扩展名}`，避免文件名冲突
- 文件大小限制：单文件 10MB，单次请求 100MB
- SDK：`com.aliyun.oss:aliyun-sdk-oss:3.17.4`

## AI 工具协作原则

- **优先理解现有模式** — 新增功能时先参考同层已有代码的风格
- **保持分层** — 新增代码严格遵守 Controller → Service → Mapper 三层
- **不引入新依赖** — 除非明确讨论并同意，不擅自添加 Maven 依赖
- **中文注释** — 生成的代码注释使用中文
- **最小改动** — 只修改与需求直接相关的文件，不做无关重构
- **先问后改** — 涉及数据库结构变更、依赖升级、架构调整时，先讨论方案
