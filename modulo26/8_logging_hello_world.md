# Logging Hello World
We will enhance the existing Spring Boot REST JPA web service already protected with **JWT authentication**.

## application.properties
In the following configuration, we are setting up **file logging** for a Spring Boot 3.4 application using the default Logback logging framework, with **console logging** left untouched. We use the **properties-based configuration** approach because we're only utilizing basic logging features. As a result, the **appender will be synchronous** and we apply a **basic rolling policy**. This setup doesn't require a custom `logback-spring.xml` file, making it simple and effective for straightforward logging needs.

By defining these properties, we ensure that log entries are captured and stored in a structured and manageable way, preventing logs from growing indefinitely and making it easy to trace events over time. The configuration includes features like log rolling, file size limits, and log file history retention, ensuring efficient management of log files without consuming excessive disk space.

### Logging Levels
The logging level configuration allows you to specify how verbose the logging output should be for different packages or components in the application.

- `logging.level.com.example=DEBUG`: Enables DEBUG-level logging for the application's own code within the `com.example` package. This is useful during development or debugging, as it reveals detailed internal flow such as method entries/exits, variable values, or custom log statements.

- `logging.level.org.springframework.security=DEBUG`: Enables DEBUG logging for Spring Security, which is especially helpful when working with authentication and authorization. It exposes security filters, request interception, and decisions like access granted/denied or role evaluation.

- `logging.level.org.springframework.web=DEBUG`: Enables DEBUG logging for Spring Web, including controllers, handler mappings, and request-response processing. This helps trace how HTTP requests are handled and routed through your application.

These levels allow fine-grained control over the verbosity of logs, helping you debug effectively without flooding the output with unnecessary details from unrelated packages.

### File Logging
The next set of properties configures how logs are written to disk. Note that **console logging remains the default behavior**, and we only add configuration to handle file-based logging.

- `logging.file.name=target/logs/app.log`: Defines the full path and filename for the primary log file. In this case, logs will be written to a file named `app.log` inside the `target/logs/` directory. The file will be created once the application starts logging. If the directory does not exist, Spring Boot (via Logback) will attempt to create it, provided it has the necessary permissions.

- `logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n`: Sets the format for log entries written to the file. It includes a timestamp, thread name, log level, the logger's name (abbreviated to 36 characters), and the actual message. This makes the logs structured and easy to read or parse.

### Rolling Policy
To prevent the log file from growing indefinitely, a rolling policy is defined to archive old logs and maintain a manageable log history.

- `logging.logback.rollingpolicy.file-name-pattern=logs/app-%d{yyyy-MM-dd}.log`: Configures daily log rotation. Each day, a new log file will be created with the current date appended to the filename. For example, `app-2025-04-17.log` will contain all logs for April 17, 2025.

- `logging.logback.rollingpolicy.max-history=7`: Tells Logback to retain only the last 7 days' worth of log files. Older files beyond this retention window will be deleted automatically.

- `logging.logback.rollingpolicy.total-size-cap=1GB`: Sets a total size cap for all log files combined. If the cumulative size of all log files exceeds 1GB, Logback will start deleting the oldest files to stay within the limit.

### How It All Works
When the application starts, Spring Boot initializes Logback using these configuration properties. Log messages from the application, Spring framework components, and any third-party libraries will be processed according to the defined log levels. These messages will appear both in the **console** (as per the default configuration) and in the log file (`target/logs/app.log`) if the relevant logging events are triggered.

As the application runs, new log entries are continuously appended to the current log file. At midnight (or upon reaching certain file sizes, if such policies are configured), Logback rolls the current log file over to a new one with the current date in its name. Older files are deleted if they exceed the defined retention count or total size cap.

This configuration provides a production-ready logging setup with **console logging untouched**, while allowing for **file persistence, file rotation, and automatic cleanup** to avoid disk overuse.

## Other changes
Log lines have been added across the code (especially in controllers and services). No new Maven dependencies need to be added.

## Test
You can re-run the testing steps from `security_hello_world` for the authenticated endpoints and from `rest_hello_world_refactore` and check how **file logging** behaves under `target/logs/app.log`.

If you want to have a **clean start**, consider the following steps to empty tha database tables.

First off, connect to the database using postgres cli:

```bash
sudo -u postgres psql
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
