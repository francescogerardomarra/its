# Rules
1. **Wrapper classes**:
   - in cases where a nullable SQL column needs to be
   mapped, it's common to use the wrapper classes 
   (`Integer` instead of `int`, `Long` instead of `long`, etc.)
   to allow for `null` values.
2. **Java 8 Date and Time API**:
   - with Java 8 and later, you can use the new
   date and time API (`java.time package`)
   for better handling of date and time types:
     - `DATE` can be mapped to `LocalDate`;
     - `TIME` can be mapped to `LocalTime`;
     - `DATETIME` and `TIMESTAMP` can be mapped to `LocalDateTime`;
     - `TIME WITH TIME ZONE` can be mapped to `OffsetTime`;
     - `TIMESTAMP WITH TIME ZONE` can be mapped to `OffsetDateTime`.
3. **LOB Types**:
   - for `BLOB` and `CLOB` types, Java provides interfaces 
   (`java.sql.Blob` and `java.sql.Clob`) that should be used
   for handling large binary and character data respectively.
