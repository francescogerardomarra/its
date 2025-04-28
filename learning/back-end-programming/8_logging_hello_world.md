# Logging Hello World
We will enhance the existing Spring Boot REST JPA web service already protected with **JWT authentication**.

## `application.properties`
In the following configuration:

````properties
# Logging levels
logging.level.com.example=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG

# File logging
logging.file.name=target/logs/logs.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
````

we're setting up **basic file logging** for a Spring Boot 3.4 application using the default **Logback** logging system. The configuration is entirely based on `application.properties`, which is ideal for simple use cases where we want to persist logs to a file without needing advanced features like log rolling or asynchronous logging.

This setup does **not require a custom `logback-spring.xml`** file. Logback will automatically handle both console and file logging, and will apply the format we specify for file output.

> 💡 **Important:** This configuration only enables **basic file logging**. While you can customize the file name and log format, **rolling policies** (such as time- or size-based log rotation) are **not supported** through properties alone. For that, you must use a `logback-spring.xml` file.

### Logging Levels

These properties control the **verbosity** of log output for different parts of your application:

```properties
logging.level.com.example=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.springframework.web=DEBUG
```

- `com.example=DEBUG`: Enables detailed logs for your application code.
- `org.springframework.security=DEBUG`: Useful for tracking authentication and authorization flow.
- `org.springframework.web=DEBUG`: Helps trace how HTTP requests are handled by controllers and filters.

This kind of fine-grained level control helps you see the logs you care about during development or debugging without overwhelming the log with unrelated output.

### File Logging
This is the core of your file logging configuration:

```properties
logging.file.name=target/logs/logs.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
```

- `logging.file.name`: Specifies the file path and name (`target/logs/logs.log`). Spring Boot will create this file and directory if possible.
- `logging.pattern.file`: Controls the format of the file log entries — including timestamps, thread names, log level, logger, and message content — making them easy to read and parse.

> Note: Console logging is still active by default. This configuration adds file output **in addition to** console output.

While this setup covers basic logging well, it **does not implement any log rotation or retention**:

- The `logs.log` file will **grow indefinitely**
- There is **no limit** on file size or disk usage
- You won't get daily logs or historical backups unless you implement a rolling policy

## Other changes
Log lines of various log levels have been added across the code (especially in controllers and services). No new Maven dependencies need to be added.

## Test
You can re-run the testing steps from `security_hello_world` for the authenticated endpoints and from `rest_hello_world_refactore` and check how **file logging** behaves under `target/logs/logs.log`.

If you want to have a **clean start**, consider the following steps to empty tha database tables.

First off, connect to the database using postgres cli:

```bash
sudo -u postgres psql
```

```bash
\c my_app_db
```

then, if you want to use the `DELETE` command, since you have foreign key constraints in your schema (e.g. Order_Item depends on Order and Item), you must delete the data from the dependent tables first (otherwise, you will get a foreign key violation error):

````sql
-- Step 1: Delete from the join table first (Order_Item)
DELETE FROM shop_schema.Order_Item;

-- Step 2: Delete from the Order table
DELETE FROM shop_schema.Order;

-- Step 3: Delete from the Item table
DELETE FROM shop_schema.Item;

-- Step 4: Finally, delete from the User table
DELETE FROM shop_schema.User;
````
otherwise, if you want to **quickly remove all rows** and **bypass foreign key constraints**, you can use the `TRUNCATE` command:

```sql
TRUNCATE TABLE shop_schema.Order_Item CASCADE;
TRUNCATE TABLE shop_schema.Order CASCADE;
TRUNCATE TABLE shop_schema.Item CASCADE;
TRUNCATE TABLE shop_schema.User CASCADE;
```

**Note:** `TRUNCATE` is more efficient as it doesn't log individual row deletions, but it also **does not fire any triggers** and **does not respect foreign key constraints** by default unless you use **CASCADE**.

You are now ready to run your tests starting from a empty database.
