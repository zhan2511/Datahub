# Design Document

## 0. Introduction

this is a design document for the project including:
+ [Entity](#1-entity)
+ [API](#2-api)
+ [Error Codes](#3-error-codes)

## 1. Entity

<!-- ### 1.1 User

| 属性名 | 类型 | 约束 | 描述 |
| :--- | :---: | --- | --- |
| user_id | 整数 | 主键 (PK) | 唯一标识一个用户 |
| username | 字符串 | 唯一 (UNIQUE) | 用于登录，不能重复 |
| password_hash | 字符串 | 必填 (NOT NULL) | 存储密码的加密值 |
| user_email | 字符串 | 唯一 (UNIQUE) | 用于找回密码或通知，不能重复 |
| role | 字符串 | 必填 (NOT NULL), CHECK IN（'管理员'（Administrator）, '科研秘书'（ResearchAssistant）, '教师'（Teacher）, '游客'（Guest）） | 身份/角色 |
| teacher_id | 整数 | 外键 (FK), 唯一 (UNIQUE), 可空 (NULLABLE) | 指向 $Teacher$ 表。UNIQUE 约束保证一个教师身份只能被一个用户账户绑定。 |
| assistant_id | 整数 | 外键 (FK), 唯一 (UNIQUE), 可空 (NULLABLE) | 指向 $ResearchAssistant$ 表。UNIQUE 约束保证一个科研秘书身份只能被一个用户账户绑定。 |
| last_login_time | 时间戳 | 可空 (NULLABLE) | 最后登录时间 | -->

### 1.1 User

| 属性名 | 类型 | 约束 | 描述 |
| :--- | :---: | --- | --- |
| user_id | 整数 | 主键 (PK) | 唯一标识一个用户 |
| username | 字符串 | 唯一 (UNIQUE) | 用于登录，不能重复 |
| password_hash | 字符串 | 必填 (NOT NULL) | 存储密码的加密值 |
| user_email | 字符串 | 唯一 (UNIQUE) | 用于找回密码或通知，不能重复 |
| role | 字符串 | 必填 (NOT NULL), CHECK IN（'管理员'（Administrator）, '科研秘书'（ResearchAssistant）, '游客'（Guest）） | 身份/角色 |
| assistant_id | 整数 | 外键 (FK), 唯一 (UNIQUE), 可空 (NULLABLE) | 指向 $ResearchAssistant$ 表。UNIQUE 约束保证一个科研秘书身份只能被一个用户账户绑定。 |

### 1.2 Teacher

| 属性名 | 类型 | 约束 | 描述 |
| :--- | :---: | --- | --- |
| teacher_id | 整数 | 主键 (PK) | 唯一标识一个教师 |
| employee_id | 字符串 | 唯一 (UNIQUE)，必填 (NOT NULL) | 教师工号 |
| teacher_name | 字符串 | 必填 (NOT NULL) | 教师姓名 |
| teacher_email | 字符串 | 唯一 (UNIQUE) | 教师邮箱地址 |
| dept_id | 整数 | 外键 (FK) | 指向 $Department$ 表 |

### 1.3 ResearchAssistant

| 属性名 | 类型 | 约束 | 描述 |
| :--- | :---: | --- | --- |
| assistant_id | 整数 | 主键 (PK) | 唯一标识一个科研秘书 |
| employee_id | 字符串 | 唯一 (UNIQUE)，必填 (NOT NULL) | 科研秘书工号 |
| assistant_name | 字符串 | 必填 (NOT NULL) | 科研秘书姓名 |
| assistant_email | 字符串 | 唯一 (UNIQUE) | 科研秘书邮箱地址 |


### 1.4 Department

| 属性名 | 类型 | 约束 | 描述 |
| :--- | :---: | --- | --- |
| dept_id | 整数 | 主键 (PK) | 唯一标识一个系 |
| dept_name | 字符串 | 必填 (NOT NULL) | 系名 |


### 1.5 CollectionTask

| 属性名 | 类型 | 约束 | 描述 |
| :--- | :---: | --- | --- |
| task_id | 整数 | 主键 (PK) | 唯一标识一个汇总任务 |
| task_name | 字符串 | 唯一 (UNIQUE) | 任务名称 |
| description | 字符串 | 可空 (NULLABLE) | 任务描述 |
| template_path | 字符串 | 必填 (NOT NULL) | Excel模板文件存储路径 |
| dept_id | 整数 | 外键 (FK) | 指向 $Department$ 表 |
| creator_id | 整数 | 外键 (FK) | 指向 $ResearchAssistant$ 表 |
| create_time | 时间戳 | 必填 (NOT NULL) | 任务创建时间 |
| deadline | 时间戳 | 必填 (NOT NULL) | 截止日期 |
| status | 字符串 | 必填 (NOT NULL), CHECK IN（'已完成-Finished', '正在进行-Ongoing'） | 任务状态 |

### 1.6 Submission

| 属性名 | 类型 | 约束 | 描述 |
| :--- | :---: | --- | --- |
| submission_id | 整数 | 主键 (PK) | 唯一标识一个提交 |
| task_id | 整数 | 外键 (FK) | 指向 $CollectionTask$ 表 |
| teacher_id | 整数 | 外键 (FK) | 指向 $Teacher$ 表 |
| submitted_at | 时间戳 | 可空 (NULLABLE) | 提交时间 |
| attachment_path | 字符串 | 可空 (NULLABLE) | 教师回复的附件存储路径 |
| attachment_description | 字符串 | 可空 (NULLABLE) | 教师回复的附件描述 |

### 1.7 E-R

```
                      1        N
+------------+   <------------------->   +---------------+
| Department |                           |    Teacher    |
+------------+                           +---------------+
| dept_id PK |                           | teacher_id PK |
| dept_name  |                           | employee_id   |
+------------+                           | teacher_name  |
           \ 1                           | teacher_email |
            \                            | dept_id (FK)  |
             \                           +---------------+
              \
               \ N
                +------------------------------+
                |       CollectionTask         |
                +------------------------------+
                | task_id (PK)                 |
                | task_name (UNIQUE)           |
                | description                  |
                | template_path                |
                | dept_id (FK)                 |
                | creator_id (FK)              |
                | create_time                  |
                | deadline                     |
                | status                       |
                +------------------------------+
                         | N
                         |
                         |
                         | 1
                +-----------------------+
                |  ResearchAssistant    |
                +-----------------------+
                | assistant_id (PK)     |
                | employee_id (UNIQUE)  |
                | assistant_name        |
                | assistant_email       |
                +-----------------------+
                         | 0..1
                         |
                         |
                         | 1
                +---------------------------------+
                |               User              |
                +---------------------------------+
                | user_id PK                      |
                | username UNIQUE                 |
                | password_hash                   |
                | user_email UNIQUE               |
                | role (CHECK IN ...)             |
                | assistant_id FK UNIQUE NULLABLE |
                +---------------------------------+


 Teacher N  ------------------------------------ 1 CollectionTask
            \                                  /
             \                                /
              \          Submission          /
               \                            /
                \                          /
               M \                        / M
                +---------------------------+
                |        Submission         |
                +---------------------------+
                | submission_id (PK)        |
                | task_id (FK)              |
                | teacher_id (FK)           |
                | submitted_at              |
                | attachment_path           |
                | attachment_description    |
                +---------------------------+
```



## 2. API

+ 采用 RESTful 风格设计 API
+ 采用 JSON 格式传输数据
+ 采用 JWT (JSON Web Token) 进行身份验证
+ 统一成功 Response 格式
```json
{
  "success": true,
  "data": {},
  "message": "Optional success message."
}
```
+ 统一错误 Response 格式
```json
{
  "success": false,
  "error": {
    "code": "ERROR_CODE",
    "message": "Error message."
  }
}
```


### 2.1 User

#### 2.1.1 Sign Up

+ POST /users/signup
+ Request Body
```json
{
  "username": "alice",
  "password": "123456",
  "user_email": "alice@univ.edu",
  "role": "Guest"
}
```
| 字段         | 类型     | 必填  | 描述              |
| ---------- | ------ | --- | --------------- |
| username   | string | Yes | 登录名，唯一          |
| password   | string | Yes | 明文密码（后端存 hash）  |
| user_email | string | Yes | 用户邮箱            |
| role       | string | Yes | 注册时为“游客” |

+ Response
```json
{
  "success": true,
  "data": {
    "user_id": 12
  },
  "message": "User created successfully."
}

```

<!--
#### 2.1.2 Sign In

+ POST /users/signin
+ Request Body
```json
{
  "username": "alice",
  "password": "123456"
}
```
| 字段       | 类型     | 必填  | 描述     |
| -------- | ------ | --- | ------ |
| username | string | Yes | 登录名     |
| password | string | Yes | 明文密码（后端验证） |
+ Response
```json
{
  "success": true,
  "data": {
    "token": "JWT_TOKEN_HERE",
    "user": {
      "user_id": 12,
      "username": "alice",
      "role": "Assistant"
    }
  },
  "message": "User signed in successfully."
}

```

#### 2.1.3 Sign Out

+ POST /users/signout
+ Response
```json
{
  "success": true,
  "message": "User signed out successfully."
}
```
-->
<!--
#### 2.1.4 Change Password

+ PUT /users/{user_id}/password
```json
{
  "old_password": "123456",
  "new_password": "654321"
}
```
| 字段           | 类型     | 必填  | 描述     |
| ------------ | ------ | --- | ------ |
| old_password | string | Yes | 旧密码     |
| new_password | string | Yes | 新密码     |
+ Response
```json
{
  "success": true,
  "message": "Password changed successfully."
}
```

#### 2.1.5 Reset Password

+ POST /users/reset-password
+ Request Body
```json
{
  "user_email": "alice@univ.edu"
}
```
| 字段   | 类型     | 必填  | 描述     |
| ---- | ------ | --- | ------ |
| email | string | Yes | 用户邮箱   |
+ Response
```json
{
  "success": true,
  "message": "Password reset email sent."
}
``` -->

#### 2.1.6 Verify Role
<!-- 验证 科研秘书 身份 -->
<!-- 修改 Role -->

+ POST /api/users/{user_id}/verify-role
+ Request Body
```json
{
  "role": "ResearchAssistant",
  "employee_id": "123456",
  "assistant_name": "Alice"
}
```
| 字段         | 类型     | 必填  | 描述              |
| ---------- | ------ | --- | --------------- |
| role       | string | Yes | 注册时为“游客”     |
| employee_id | string | Yes | 员工编号            |
| assistant_name | string | Yes | 科研秘书姓名         |
+ Response
```json
{
  "success": true,
  "data": {
    "role": "ResearchAssistant",
    "assistant_id": 5
  },
  "message": "Role verified successfully."
}
```

#### 2.1.7 Get User Detail

+ GET /users/{user_id}
+ Response
```json
{
  "success": true,
  "data": {
    "user": {
      "user_id": 12,
      "username": "alice",
      "user_email": "alice@univ.edu",
      "role": "ResearchAssistant",
      "assistant_id": 5
    }
  },
  "message": "User detail retrieved successfully."
}
```

### 2.2 CollectionTask

#### 2.2.1 Create Task

+ POST /tasks
+ Request Body
```json
{
  "task_name": "2023年第一学期课程教学情况汇总表",
  "description": "请填写本学期课程教学情况汇总表",
  "template_path": "/path/to/template.xlsx",
  "deadline": "2023-12-31T23:59:59",
  "dept_id": 1
}
```
| 字段            | 类型       | 必填  | 描述            |
| ------------- | -------- | --- | ------------- |
| task_name     | string   | Yes | 任务名称          |
| description   | string   | No  | 任务描述          |
| template_path | string   | Yes | 模板文件存储路径      |
| deadline      | datetime | Yes | 截止日期          |
| dept_id      | long    | Yes | 任务所属的部门 ID |
+ Response
```json
{
  "success": true,
  "data": {
    "task_id": 1
  },
  "message": "Task created successfully."
}

```
#### 2.2.2 Update Task

+ PUT /tasks/{task_id}
+ Request Body
```json
{
  "task_name": "2023年第一学期课程教学情况汇总表",
  "description": "请填写本学期课程教学情况汇总表",
  "template_path": "/path/to/template.xlsx",
  "deadline": "2023-12-31T23:59:59",
  "dept_id": 1
}
```
| 字段            | 类型       | 必填  | 描述            |
| ------------- | -------- | --- | ------------- |
| task_name     | string   | Yes | 任务名称          |
| description   | string   | No  | 任务描述          |
| template_path | string   | Yes | 模板文件存储路径      |
| deadline      | datetime | Yes | 截止日期          |
| dept_id      | long    | Yes | 任务所属的部门 ID |
+ Response
```json
{
  "success": true,
  "message": "Task updated successfully."
}
```

#### 2.2.3 Delete Task

+ DELETE /tasks/{task_id}
+ Response
```json
{
  "success": true,
  "message": "Task deleted successfully."
}
```

#### 2.2.4 Get Task List

+ GET /tasks
+ Parameters
```json
{
  "page_num": 1,
  "page_size": 10,
  "status": "pending",
  "dept_ids": [1, 2, 3],
  "sort_by": "deadline",
  "order": "asc"
}
```
| 字段       | 类型     | 必填  | 描述     |
| -------- | ------ | --- | ------ |
| page_num  | int    | No  | 页码     |
| page_size | int    | No  | 每页条数   |
| status   | string | No  | 任务状态   |
| dept_ids | array  | No  | 部门 ID 列表 |
| order_by  | string | No  | 排序字段(deadline, task_name, create_time)   |
| order_direction | string | No  | 排序顺序(asc, desc)   |
+ Response
```json
{
  "success": true,
  "data": {
    "tasks": [
      {
        "task_id": 1,
        "task_name": "2023年第一学期课程教学情况汇总表",
        "description": "请填写本学期课程教学情况汇总表",
        "template_path": "/path/to/template.xlsx",
        "deadline": "2023-12-31T23:59:59",
        "department": {
          "dept_id": 1,
          "dept_name": "计算机科学与技术学院"
        },
        "status": "Ongoing",
        "create_time": "2023-01-01T00:00:00"
      }
    ],
    "page": {
      "total_pages": 1,
      "page_num": 1,
      "page_size": 10
    }
  }
}
```

#### 2.2.5 Get Task Detail

+ GET /tasks/{task_id}
+ Response
```json
{
  "success": true,
  "task": {
    "task_id": 1,
    "task_name": "2023年第一学期课程教学情况汇总表",
    "description": "请填写本学期课程教学情况汇总表",
    "template_path": "/path/to/template.xlsx",
    "deadline": "2023-12-31T23:59:59",
    "department": {
      "dept_id": 1,
      "dept_name": "计算机科学与技术学院"
    },
    "status": "pending",
    "create_time": "2023-01-01T00:00:00"
  }
}
```

#### 2.2.6 Get Teacher List By task

+ GET /tasks/{task_id}/teachers
+ Parameters
```json
{
  "page_num": 1,
  "page_size": 10
}
```
| 字段       | 类型     | 必填  | 描述     |
| -------- | ------ | --- | ------ |
| page_num  | int    | No  | 页码     |
| page_size | int    | No  | 每页条数   |
| status   | string | No  | 提交状态(Pending, Submitted)   |

+ Response
```json
{
  "success": true,
  "task_id": 5,
  "teachers": [
    {
      "teacher_id": 3,
      "teacher_name": "王老师",
      "teacher_email": "wang@univ.edu",
      "submission": {
        "status": "Submitted",
        "submitted_at": "2024-12-30T12:00:00Z",
        "submission_id": 33
      }
    },
    {
      "teacher_id": 8,
      "teacher_name": "李老师",
      "teacher_email": "li@univ.edu",
      "submission": {
        "status": "Pending",
        "submitted_at": null,
        "submission_id": null
      }
    }
  ],
  "page": {
    "total_pages": 2,
    "page_num": 1,
    "page_size": 10
  }

}
```

#### 2.2.7 Remind Unsubmitted Teachers

+ POST /tasks/{task_id}/remind_teachers
+ Response
```json
{
  "success": true,
  "message": "Remind teachers successfully."
}
```

#### 2.2.8 Collect Submissions

+ POST /tasks/{task_id}/submissions/export
+ Response
```
Content-Type: application/xlsx
Content-Disposition: attachment; filename="submissions.xlsx"
```


### 2.3 Submission

<!-- #### 2.3.1 Submit

+ POST /submissions
```multipart/form-data
{
  "task_id": 1,
  "teacher_id": 1,
  "attachment": "file.xlsx",
  "attachment_description": "课程教学情况汇总表",
}
```
| 字段       | 类型     | 必填  | 描述     |
| -------- | ------ | --- | ------ |
| task_id  | long    | Yes | 任务 ID   |
| teacher_id | long    | Yes | 教师 ID   |
| attachment | file   | Yes | 附件     |
| attachment_description | string | Yes | 附件描述   |
+ Response
```json
{
  "success": true,
  "submission_id": 1,
  "message": "Submission created successfully."
}
```

#### 2.3.2 Update Submission

+ PUT /submissions/{submission_id}
+ Request Body
```multipart/form-data
{
  "attachment": "file.xlsx",
  "attachment_description": "课程教学情况汇总表",
}
```
| 字段       | 类型     | 必填  | 描述     |
| -------- | ------ | --- | ------ |
| attachment | file   | Yes | 附件     |
| attachment_description | string | Yes | 附件描述   |
+ Response
```json
{
  "success": true,
  "message": "Submission updated successfully."
}
```

#### 2.3.3 Delete Submission

+ DELETE /submissions/{submission_id}
+ Response
```json
{
  "success": true,
  "message": "Submission deleted successfully."
}
``` -->

<!--
#### 2.3.4 Get Submission List

+ GET /submissions
+ Parameters
```json
{
  "page": 1,
  "page_size": 10,
  "task_id": 1,
  "sort_by": "submitted_at",
  "order": "asc"
}
```
| 字段       | 类型     | 必填  | 描述     |
| -------- | ------ | --- | ------ |
| page     | int    | No  | 页码     |
| page_size | int    | No  | 每页条数   |
| task_id  | long    | No  | 任务 ID   |
| sort_by  | string | No  | 排序字段(submitted_at, teacher_name)   |
| order    | string | No  | 排序顺序(asc, desc)   |
+ Response
```json
{
  "success": true,
  "submissions": [
    {
      "submission_id": 1,
      "task_id": 1,
      "teacher_id": 1,
      "attachment_path": "/path/to/attachment.xlsx",
      "attachment_description": "课程教学情况汇总表",
      "submitted_at": "2023-01-01T00:00:00",
    }
  ],
  "total": 1,
  "page": 1,
  "page_size": 10
}
```

#### 2.3.5 Get Submission Detail

+ GET /submissions/{submission_id}
+ Response
```json
{
  "success": true,
  "submission": {
    "submission_id": 1,
    "task_id": 1,
    "teacher_id": 1,
    "attachment_path": "/path/to/attachment.xlsx",
    "attachment_description": "课程教学情况汇总表",
    "submitted_at": "2023-01-01T00:00:00",
  }
}
``` -->


### 2.4 Department

#### 2.4.1 Get Department List

+ GET /departments
+ Response
```json
{
  "success": true,
  "data": {
    "departments": [
      {
        "dept_id": 1,
        "dept_name": "计算机科学与技术学院"
      },
      {
        "dept_id": 2,
        "dept_name": "软件学院"
      }
    ]
  }
}

```

### 2.5 Auth

#### 2.5.1 Login

+ POST /auth/login
+ Request Body
```json
{
  "username": "alice",
  "password": "123456"
}
```
| 字段       | 类型     | 必填  | 描述     |
| -------- | ------ | --- | ------ |
| username | string | Yes | 登录名     |
| password | string | Yes | 明文密码（后端验证） |
+ Response
```json
{
  "success": true,
  "data": {
    "token": "JWT_TOKEN_HERE",
    "user": {
      "user_id": 12,
      "username": "alice",
      "role": "Assistant"
    }
  },
  "message": "User signed in successfully."
}

```
<!-- frontend should handle token expiration and refresh -->
<!--
#### 2.5.2 Logout

+ POST /auth/logout
+ Response
```json
{
  "success": true,
  "message": "User signed out successfully."
}
```
-->

## 3. Error Codes

| ERROR_CODE                 | HTTP_STATUS_CODE | Description |
|----------------------------| ---------------- | ------- |
| USER_NOT_FOUND             | 404 | User not found. |
| INVALID_CREDENTIALS        | 401 | Invalid username or password. |
| USER_EMAIL_ALREADY_EXISTS  | 400 | The provided email is already registered. |
| USER_NAME_ALREADY_EXISTS   | 400 | The provided username is already taken. |
| ASSISTANT_NOT_FOUND        | 404 | Assistant not found. |
| TASK_NOT_FOUND             | 404 | Task not found. |
| PERMISSION_DENIED          | 403 | Permission denied. |