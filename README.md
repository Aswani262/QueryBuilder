# 📦 `com.ash.query` — SQL Query Builder with JSON Filter DSL

A reusable Java library to:

✅ Build secure SQL queries from REST-style inputs  
✅ Support complex filters with nested `AND` / `OR` / `NOT` logic  
✅ Handle joins, sorting, pagination, and select fields  
✅ Evaluate filters on in-memory Java objects (DTOs)  
✅ Accept clean, expressive **JSON filters** via `POST /query`

---

## Features

| Feature                         | Supported |
|----------------------------------|-----------|
| SQL query builder with safe params | ✅ Yes |
| Joins (defined internally)      | ✅ Yes |
| Nested filter logic (AND, OR, NOT) | ✅ Yes |
| Search across multiple columns  | ✅ Yes |
| Pagination and sorting          | ✅ Yes |
| JSON filter DSL (POST /query)   | ✅ Yes |
| In-memory DTO filtering         | ✅ Yes |
| JSON Schema for OpenAPI/Validation | ✅ Yes |

---

##  Installation

```xml
<dependency>
  <groupId>com.ash</groupId>
  <artifactId>query-builder</artifactId>
  <version>1.0.0</version>
</dependency>
```

##  POST /query

POST /users/query
Content-Type: application/json

````json
{
  "search": "john",
  "searchColumns": ["first_name", "last_name"],
  "selectFields": ["u.id", "u.name"],
  "sort": "created_at,desc",
  "page": 0,
  "size": 10,
  "filter": {
    "type": "AND",
    "conditions": [
      {
        "type": "OR",
        "conditions": [
          { "field": "status", "operator": "=", "value": "active" },
          { "field": "role", "operator": "=", "value": "admin" }
        ]
      },
      {
        "field": "age",
        "operator": ">",
        "value": 30
      }
    ]
  }
}
````
### Output SQL

````sql
SELECT u.id, u.name FROM users u
WHERE ((u.status = ? OR u.role = ?) AND u.age > ?)
ORDER BY u.created_at DESC
LIMIT ? OFFSET ?
````



