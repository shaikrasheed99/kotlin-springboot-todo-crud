# Spring Boot TODO CRUD in Kotlin

## Gradle based spring boot application which provide APIs to create, read, update and delete the TODOs using test driven development.

## Features of the Application
    - Create todo
    - Read todo
    - Read todos by Priority
    - Read todos by Status
    - Update todo
    - Delete todo

## APIs

### Create a Todo

* Request
```
POST /todos
Host: localhost:8080
Content-Type: application/json
{
    "description": "Sleep",
    "status": "pending",
    "priority": "high"
}
```
* Response
```
{
    "status": "SUCCESS",
    "code": "OK",
    "message": "Todo has created successfully",
    "data": {
        "id": 1,
        "description": "Sleep",
        "status": "pending",
        "priority": "high"
    }
}
```

### Get all Todos 

* Request
```
GET /todos
Host: localhost:8080
```
* Response
```
{
    "status": "SUCCESS",
    "code": "OK",
    "message": "Successfully fetched all Todos details",
    "data": [
        {
            "id": 1,
            "description": "Sleep",
            "status": "pending",
            "priority": "high"
        }
    ]
}
```

### Get Todo details by Todo id

* Request
```
GET /todos/{1}
Host: localhost:8080
```
* Response
```
{
    "status": "SUCCESS",
    "code": "OK",
    "message": "Successfully fetched Todo details by id",
    "data": {
        "id": 1,
        "description": "Sleep",
        "status": "pending",
        "priority": "high"
    }
}
```

### Get Todos by Priority

* Request
```
GET /todos/priority/{"high"} 
Host: localhost:8080
```
* Response
```
{
    "status": "SUCCESS",
    "code": "OK",
    "message": "Successfully fetched all Todos details",
    "data": [
        {
            "id": 1,
            "description": "Sleep",
            "status": "pending",
            "priority": "high"
        }
    ]
}
```

### Get Todos by Status

* Request
```
GET /todos/status/{"pending"} 
Host: localhost:8080
```
* Response
```
{
    "status": "SUCCESS",
    "code": "OK",
    "message": "Successfully fetched all Todos details",
    "data": [
        {
            "id": 1,
            "description": "Sleep",
            "status": "pending",
            "priority": "high"
        }
    ]
}
```

### Update Todo details

* Request
```
PUT /todos/{1}
Host: localhost:8080
Content-Type: application/json
{
    "status": "started"
}
```
* Response
```
{
    "status": "SUCCESS",
    "code": "OK",
    "message": "Todo has updated successfully",
    "data": {
        "id": 1,
        "description": "Sleep",
        "status": "started",
        "priority": "high"
    }
}
```

### Delete a Todo by Todo id

* Request
```
DELETE /todos/{1} 
Host: localhost:8080
```
* Response
```
{
    "status": "SUCCESS",
    "code": "OK",
    "message": "Todo has deleted successfully",
    "data": {}
}
```
