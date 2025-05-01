A reusable Java library to dynamically build SQL queries and evaluate conditions in-memory, using REST-like parameters.

Supports:

✅ SELECT field control
✅ JOINs
✅ Complex WHERE logic (AND, OR, NOT)
✅ Search across multiple columns
✅ Sort and pagination
✅ Safe SQL parameterization
✅ In-memory filtering of Java objects (DTOs) using the same conditions

🚀 Getting Started
📦 Maven Dependency

<dependency>
  <groupId>com.ash</groupId>
  <artifactId>query-builder</artifactId>
  <version>1.0.0</version>
</dependency>

REST API Call:

GET /users?search=john&sort=created_at,desc&page=0&size=10

QueryRequest request = new QueryRequest();
request.setBaseTable("users");
request.setBaseAlias("u");
request.setSearch("john");
request.setSearchColumns(List.of("first_name", "last_name", "email"));
request.setSort("created_at,desc");
request.setPage(0);
request.setSize(10);

QueryBuilderResult result = QueryBuilder.build(request);
System.out.println("SQL: " + result.getSql());
System.out.println("Params: " + result.getParameters());

📤 Output
 
SELECT u.* FROM users u
WHERE (u.first_name LIKE ? OR u.last_name LIKE ? OR u.email LIKE ?)
ORDER BY u.created_at DESC
LIMIT ? OFFSET ?

🔀 With Filters + Joins

GET /users?status=active&join=orders&sort=u.created_at,desc


request.setSelectFields(List.of("u.id", "u.name", "o.total"));
request.setJoins(List.of(
new JoinClause("LEFT", "orders", "o", "u.id = o.user_id")
));
request.setWhereCondition(new SimpleCondition("status", "=", "active", "u"));


Complex WHERE Clause with AND/OR/NOT

(status = 'active' AND age > 30) OR NOT (role = 'banned')

request.setWhereCondition(
new CompositeCondition(CompositeCondition.Type.OR, List.of(
new CompositeCondition(CompositeCondition.Type.AND, List.of(
new SimpleCondition("status", "=", "active", "u"),
new SimpleCondition("age", ">", 30, "u")
)),
new NotCondition(
new SimpleCondition("role", "=", "banned", "u")
)
))
);

