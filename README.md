# 微信记账小程序 - 后端

## 项目简介
基于Spring Boot开发的微信记账小程序后端服务

## 技术栈
- Spring Boot 2.7.14
- MyBatis Plus 3.5.3.1
- MySQL 8.0
- Redis（可选）
- JWT

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

### 5. 接口测试
推荐使用 Apifox 导入以下接口进行联调：

**用户模块**
- `/user/login` 小程序登录（code 可使用 `test-xxxx` 进入测试模式）
- `/user/info` 获取用户信息
- `/user/update` 更新用户信息

**账单模块**
- `/bill/create` 创建账单
- `/bill/update/{id}` 更新账单
- `/bill/delete/{id}` 删除账单
- `/bill/detail/{id}` 获取账单详情
- `/bill/list` 查询账单列表

**分类模块**
- `/category/create` 创建自定义分类
- `/category/update/{id}` 更新分类
- `/category/delete/{id}` 删除分类
- `/category/list` 获取分类列表（含系统预设）

**统计模块**
- `/statistic/overview` 收支概览统计
- `/statistic/category` 分类占比统计
- `/statistic/trend` 收支趋势统计

**预算模块**
- `/budget/create` 创建预算
- `/budget/update/{id}` 更新预算
- `/budget/delete/{id}` 删除预算
- `/budget/detail/{id}` 获取预算详情
- `/budget/list` 获取预算列表

> 说明：接口默认运行在 `http://localhost:8081/api`，除登录接口外均需携带 `Authorization: Bearer <token>`。

## 开发进度
- [x] 项目初始化
- [x] 用户登录模块
- [x] 账单管理模块
- [x] 分类管理模块
- [x] 统计分析模块
- [x] 预算管理模块
- [x] MyBatis XML 映射完善
- [ ] 后端接口测试
- [ ] 前端 uni-app 开发
- [ ] 前后端联调测试