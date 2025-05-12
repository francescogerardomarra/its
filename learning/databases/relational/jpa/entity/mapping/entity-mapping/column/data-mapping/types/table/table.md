# Table
The following table showcases how various SQL data types are defined, used, and mapped to corresponding Java types across different database technologies.

| DB Data Type | DB Technology                   | DDL Definition          | Example DB Value        | Java Data Type                         |
|-------------:|---------------------------------|-------------------------|-------------------------|----------------------------------------|
|         CHAR | MySQL / PostgreSQL / SQL Server | `name CHAR(10)`         | `'A'`                   | `String`                               |
|      VARCHAR | MySQL / PostgreSQL / SQL Server | `username VARCHAR(100)` | `'alice'`               | `String`                               |
|         TEXT | MySQL / PostgreSQL / SQL Server | `description TEXT`      | `'Free‑form text…'`     | `String`                               |
|        NCHAR | SQL Server                      | `code NCHAR(10)`        | `N'A'`                  | `String`                               |
|     NVARCHAR | SQL Server                      | `comment NVARCHAR(200)` | `N'Hello'`              | `String`                               |
|        NTEXT | SQL Server                      | `notes NTEXT`           | `N'Long text…'`         | `String`                               |
|          INT | MySQL / PostgreSQL / SQL Server | `age INT`               | `34`                    | `int` / `Integer`                      |
|       BIGINT | MySQL / PostgreSQL / SQL Server | `views BIGINT`          | `1234567890123`         | `long` / `Long`                        |
|     SMALLINT | MySQL / PostgreSQL / SQL Server | `level SMALLINT`        | `5`                     | `short` / `Short`                      |
|      TINYINT | MySQL / SQL Server              | `score TINYINT`         | `255`                   | `byte` / `Byte`                        |
|      NUMERIC | MySQL / PostgreSQL / SQL Server | `rate NUMERIC(5,2)`     | `123.45`                | `BigDecimal`                           |
|      DECIMAL | MySQL / PostgreSQL / SQL Server | `price DECIMAL(10,2)`   | `99.99`                 | `BigDecimal`                           |
|        FLOAT | MySQL / PostgreSQL / SQL Server | `ratio FLOAT`           | `3.14`                  | `double` / `Double`                    |
|         REAL | MySQL / PostgreSQL / SQL Server | `score REAL`            | `2.71`                  | `float` / `Float`                      |
|        MONEY | SQL Server                      | `cost MONEY`            | `100.00`                | `BigDecimal`                           |
|   SMALLMONEY | SQL Server                      | `fee SMALLMONEY`        | `10.00`                 | `BigDecimal`                           |
|          BIT | MySQL / PostgreSQL / SQL Server | `is_active BIT`         | `1` (or `TRUE`)         | `boolean` / `Boolean`                  |
|         BLOB | MySQL / PostgreSQL              | `data BLOB`             | `<binary data>`         | `byte[]` / `Blob`                      |
|         CLOB | Oracle / PostgreSQL             | `text CLOB`             | `<large text>`          | `String` / `Clob`                      |
|       BINARY | MySQL / SQL Server              | `hash BINARY(16)`       | `<raw bytes>`           | `byte[]`                               |
|    VARBINARY | MySQL / SQL Server              | `file VARBINARY(255)`   | `<raw bytes>`           | `byte[]`                               |
|         DATE | MySQL / PostgreSQL / SQL Server | `birth_date DATE`       | `'1990-07-15'`          | `java.sql.Date` / `LocalDate`          |
|         TIME | MySQL / PostgreSQL / SQL Server | `login_time TIME`       | `'14:23:55'`            | `java.sql.Time` / `LocalTime`          |
|    TIMESTAMP | MySQL / PostgreSQL / SQL Server | `created_at TIMESTAMP`  | `'2025-04-30 10:45:00'` | `java.sql.Timestamp` / `LocalDateTime` |
