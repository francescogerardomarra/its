# `TEXT`
**`TEXT` has the following characteristics:**
- it is a reserved data type for databases such as Postgres, MySql and SQL Server;
- it is mapped to the `String` class;
- it is a **variable-length character string**;
- it only uses as much storage as
  needed for the actual text, plus a small overhead
  to store the length of the string;
- it is used to store large amounts of text data;
- it is typically used when the maximum length of `VARCHAR` is insufficient.