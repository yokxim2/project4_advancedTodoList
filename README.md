![image](https://github.com/user-attachments/assets/93f7887a-b13c-40ba-bf61-0423eb8efa40)

### **API 상세 내용**

### Todo 생성

> [POST] /api/todos
> 

request

```java
{
	"userId": 1,
	"title": "A",
	"content": "---"
}
```

response

```java
{
	"id": 1,
	"title": "A",
	"content": "---",
	"commentCount": 0,
	"createdBy": "unknown",
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
}
```

---

### Todo 수정 권한 부여

> [POST] /api/todos/{todoId}/add-manager
> 

request

- @PathVariable Long todoId

```java
{
	"password": 1234,
	"username": "Jackson"
}
```

response

- none

---

### Todo 조회

> [GET] /api/todos
> 

request

- none

response

```java
[
	{
		"id": 1,
		"title": "A",
		"content": "---",
		"commentCount": 0,
		"createdBy": "Simon",
		"createdAt": "XXXX-XX-XX XX:XX:XX",
		"modifiedAt": "XXXX-XX-XX XX:XX:XX"
	},
	{
		"id": 2,
		"title": "B",
		"content": "---",
		"commentCount": 1,
		"createdBy": "Dolly",
		"createdAt": "XXXX-XX-XX XX:XX:XX",
		"modifiedAt": "XXXX-XX-XX XX:XX:XX"
	},
	...
]
```

---

### Todo 수정

> [PUT] /api/todos/{todoId}
> 

request

- @PathVariable Long todoId

```java
{
	"password": "1234",
	"userId": 2,
	"title": "B",
	"content": "---"
}
```

response

```java
{
	"id": 2,
	"title": "B",
	"content": "---",
	"commentCount": 1,
	"createdBy": "Dolly",
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
}
```

---

### Todo 삭제

> [DELETE] /api/todos/{todoId}
> 

request

- @PathVariable Long todoId

```java
{
	"password": 1234
}
```

response

- none

*** 일정을 삭제 시 일정 id을 가진 Comment 모두 일괄 삭제

---

### Comment 생성

> [POST] /api/todos/{todoId}/comments
> 

request

- @PathVariable Long todoId

```java
{
	"userId": 1,
	"content": "XXX"
}
```

response

```java
{
	"id": 1,
	"username": "Jackson",
	"content": "XXX",
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
}
```

---

### Comment 조회

> [GET] /api/todos/{todoId}/comments
> 

request

- @PathVariable Long todoId

response

```java
[
	{
	"id": 1,
	"username": "Jackson",
	"content": "XXX",
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
	},
	{
	"id": 2,
	"username": "Jack",
	"content": "XXX",
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
	},
	...
]
```

---

### Comment 수정

> [PUT] /api/todos/{todoId}/comments/{commentId}
> 

request

- @PathVariable Long todoId
- @PathVariable Long commentId

```java
{
	"previousPassword": "1234",
	"userId": 4,
	"content": "---"
}
```

response

```java
{
	"id": 2,
	"username": "Tee",
	"content": "---",
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
}
```

---

### Comment 삭제

> [DELETE] /api/todos/{todoId}/comments/{commentId}
> 

request

- @PathVariable Long todoId
- @PathVariable Long commentId

```java
{
	"previousPassword": "1234",
	"userId": 4,
	"content": "---"
}
```

response

- none

---

### User 생성

> [POST] /api/users/signup
> 

request

```java
{
	"username": "A",
	"password": "xxx",
	"email": "abc@example.com",
	"isAdmin": false,
	"adminToken": ""
}
```

response

```java
{
	"id": 1,
	"username": "A",
	"email": "abc@example.com",
	"role": USER,
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
}
```

---

### User 조회

> [GET] /api/users
> 

request

- none

response

```java
[
	{
		"id": 1,
		"username": "A",
		"email": "abc@example.com",
		"role": USER,
		"createdAt": "XXXX-XX-XX XX:XX:XX",
		"modifiedAt": "XXXX-XX-XX XX:XX:XX"
	},
	{
		"id": 2,
		"username": "B",
		"email": "bcd@example.com",
		"role": USER,
		"createdAt": "XXXX-XX-XX XX:XX:XX",
		"modifiedAt": "XXXX-XX-XX XX:XX:XX"
	}
]
```

---

User 수정

> [PUT] /api/users/{userId}
> 

request

- @PathVariable Long userId

```java
{
	"previousPassword": "1234",
	"username": "BB",
	"password": "1313",
	"email": "yokxim@email.com"
}
```

response

```java
{
	"id": 1,
	"username": "BB",
	"email": "yokxim@email.com",
	"role": USER,
	"createdAt": "XXXX-XX-XX XX:XX:XX",
	"modifiedAt": "XXXX-XX-XX XX:XX:XX"
}
```

---

### User 삭제

> [DELETE] /api/users/{userId}
> 

request

- @PathVariable Long userId

```java
{
	"password": "1234"
}
```

response

- none

*** 유저 삭제 시 해당 유저가 쓴 일정과 댓글 일괄 삭제

---
---

ERD

![Project4 ERD](https://github.com/user-attachments/assets/f6be5221-88ad-4cff-9ad3-f1fda22f3266)
