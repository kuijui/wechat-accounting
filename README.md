# 微信记账小程序 - 后端

## 项目简介
基于Spring Boot开发的微信记账小程序后端服务

## 技术栈
- Spring Boot 2.7.14
- MyBatis Plus 3.5.3.1
- MySQL 8.0
- Redis
- JWT
- Knife4j (API文档)

## 项目结构
```
bg/
├── src/main/java/com/accounting/
│   ├── controller/      # 控制器层
│   ├── service/         # 业务逻辑层
│   ├── mapper/          # 数据访问层
│   ├── entity/          # 实体类
│   ├── dto/             # 数据传输对象
│   ├── vo/              # 视图对象
│   ├── common/          # 公共类
│   ├── config/          # 配置类
│   └── utils/           # 工具类
├── src/main/resources/
│   ├── mapper/          # MyBatis XML
│   └── application.yml  # 配置文件
└── pom.xml
```

## 快速开始

### 1. 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis (可选)

### 2. 数据库配置
创建数据库：
```sql
CREATE DATABASE wechat_accounting DEFAULT CHARACTER SET utf8mb4;
```

### 3. 修改配置
修改 `application.yml` 中的数据库连接信息

### 4. 运行项目
```bash
mvn spring-boot:run
```

### 5. 访问API文档
http://localhost:8080/doc.html

## 开发进度
- [x] 项目初始化
- [ ] 用户登录模块
- [ ] 账单管理模块
- [ ] 统计分析模块
- [ ] 分类管理模块