# Logging in Springboot

## Default Setup
Spring Boot provides a powerful yet simple default logging configuration. Out of the box, it sets up everything you need to get started with logging using industry-standard tools.

🔧 1. Overview of the Default Logging Setup
--------------------------------------------------

Spring Boot:

- **Uses Apache Commons Logging (JCL) as a logging facade.**
    - JCL (Jakarta Commons Logging) is **not a logging framework itself**, but a *facade* or *abstraction layer* for various logging frameworks. It's designed to allow the actual logging implementation to be plugged in at runtime.
    - This pattern is known as the **Facade Design Pattern**, which provides a unified interface to a set of interfaces in a subsystem. This way, JCL decouples application code from specific logging implementations.

- **Internally, it routes log calls to SLF4J (Simple Logging Facade for Java).**
    - SLF4J is another logging facade (more modern and flexible than JCL) that provides a clean and consistent API to developers.
    - SLF4J bridges the logging calls to the actual implementation (such as Logback).
    - When Spring Boot starts, it includes the necessary SLF4J bridges to route JCL calls to SLF4J automatically.

- **The default implementation behind the scenes is Logback.**
    - Logback is a powerful and flexible logging framework authored by the same creator of Log4j.
    - It is the default logging backend used by SLF4J when running a Spring Boot application.

This setup provides:

- A clean API (`org.slf4j.Logger`) for developers.
- Structured, performant, and extensible logging behavior via Logback.
- No configuration required to start using it.

🚀 2. Getting Started
-------------------------------------------------------

When you create a Spring Boot project (e.g., using Spring Initializr or your favorite build tool), you don't need to add any logging dependencies manually—they come pre-configured!

You can start logging like this:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyService {

    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    public void performTask() {
        logger.info("Performing task...");
        logger.debug("Detailed debug info");
        logger.warn("A warning message");
        logger.error("Something went wrong!");
    }
}
```

🔍 **Explanation:**

- `LoggerFactory.getLogger(MyService.class)`:
    - This is a **factory method** provided by SLF4J to create a logger instance.
    - It takes a `Class` as an argument and returns a logger associated with that class.
    - This association helps the logging framework identify where the log message originated.
    - Under the hood, SLF4J delegates the actual logging to the bound implementation (Logback by default).

- You can use different logging levels:
    - `trace`, `debug`, `info`, `warn`, `error`

📦 3. Where the Logs Go
-------------------------------

By default, Spring Boot logs:

- To the **console**
- Using a **color-coded pattern** (when supported by the terminal)
- With **log level set to INFO**

Default output looks like:

```
2025-04-15T14:20:32.487  INFO 12345 --- [  restartedMain] c.e.demo.MyService       : Performing task...
```

Output format:

```
timestamp log-level process-id --- [thread-name] logger-name : message
```

📁 4. No Configuration Needed — But You Can Add One
---------------------------------------------------------

Spring Boot auto-configures Logback using a default `logback.xml` under the hood. But if you want to override it:

- Create your own `logback-spring.xml` in `src/main/resources`
- Or customize via `application.properties`

We’ll explore that later — for now, just know you don’t need to configure anything to start logging.

✅ Quick Recap
----------------

| Feature                           | Value                      |
|-----------------------------------|----------------------------|
| Logging Facade                    | Apache Commons Logging     |
| Underlying Logging Implementation | Logback                    |
| Developer API                     | SLF4J (`org.slf4j.Logger`) |
| Configuration Needed?             | ❌ None                     |
| Logging Output                    | Console (default)          |

## Log levels
In this section, we'll cover how to configure logging levels in Spring Boot using `application.properties`.

Spring Boot (via SLF4J and Logback) supports the following standard log levels:

| Level | Description                                                                |
|-------|----------------------------------------------------------------------------|
| TRACE | Most detailed information — typically only useful for diagnosing problems. |
| DEBUG | Useful for debugging and internal system details.                          |
| INFO  | General application flow and important runtime events. *(Default)*         |
| WARN  | Something unexpected happened, but the application can still continue.     |
| ERROR | Serious issues where part of the application might not be working.         |

These levels are hierarchical:

> If a logger is set to `INFO`, then `INFO`, `WARN`, and `ERROR` messages will be shown — but not `DEBUG` or `TRACE`.

### ⚙️ Setting Logging Levels in `application.properties`
You can change the log level globally or for specific packages/classes using the `logging.level` property.

#### ✅ Global Log Level
To set a global (root) log level:

```properties
# application.properties
logging.level.root=INFO
```

This sets the default level for all loggers in the application.

#### 🎯 Per-Package Logging
To enable more detailed logging for a specific package:

```properties
# application.properties
logging.level.com.example.myapp=DEBUG
```

Or for a specific class:

```properties
logging.level.com.example.myapp.service.UserService=TRACE
```

You can configure multiple loggers like this:

```properties
logging.level.root=WARN
logging.level.com.example=INFO
logging.level.com.example.myapp=DEBUG
logging.level.com.example.myapp.repository=TRACE
```

This allows fine-grained control — e.g., verbose logs during development but only for specific modules.

### 🧪 Example in Action
Suppose you have this code in your `UserService` class:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void createUser(String username) {
        logger.trace("Trace: Creating user {}", username);
        logger.debug("Debug: Creating user {}", username);
        logger.info("Info: Creating user {}", username);
        logger.warn("Warning: Creating user {}", username);
        logger.error("Error: Failed to create user {}", username);
    }
}
```

And you configure the properties file like:

```properties
logging.level.com.example.myapp.service.UserService=DEBUG
```

**Output:**
You will see:
- `DEBUG`, `INFO`, `WARN`, and `ERROR`

You will not see:
- `TRACE` (because it's lower than the configured level)

If you change it to:

```properties
logging.level.com.example.myapp.service.UserService=TRACE
```

Then all logs, including `TRACE`, will be shown.

---

### 📁 Using `application.yml` Instead?
Here’s the same config in `application.yml`:

```yaml
logging:
  level:
    root: WARN
    com.example.myapp: DEBUG
    com.example.myapp.service.UserService: TRACE
```

### 🧠 Pro Tip

You can also use environment-specific config files:

- `application-dev.properties`
- `application-prod.properties`

And then activate the profile via:

```properties
spring.profiles.active=dev
```

So in development, you can log everything, while production remains clean.

### ✅ Summary

| Property                                  | Meaning                           |
|-------------------------------------------|-----------------------------------|
| `logging.level.root=INFO`                 | Sets default logging level        |
| `logging.level.com.example=DEBUG`         | Sets log level for package        |
| `logging.level.com.example.MyClass=TRACE` | Sets log level for specific class |

This flexibility gives you control over your log output, which is essential for debugging and performance.

## Customizing Log Output Format

In this section, we'll explore how to customize the format of your log messages as they appear in the **console output**.

This allows you to make logs more readable, structured, or adapted to your organization's standards — all without touching any external configuration files like `logback.xml`.

### 🖨️ What Does the Default Console Log Look Like?
Spring Boot’s default console log format looks like this:

```
2025-04-15T16:00:45.351  INFO 31708 --- [  restartedMain] com.example.demo.MyService : Performing task...
```

### 🔍 Components:
- **Timestamp**: ISO-8601 style with milliseconds
- **Log Level**: e.g., INFO, DEBUG
- **PID**: Process ID
- **Thread**: Which thread generated the log
- **Logger**: Class name
- **Message**: Your custom log message

This is useful — but you might want a simpler or differently structured format.

### 🛠️ Customizing the Console Log Format
Spring Boot allows you to customize the log pattern using the property:

```properties
logging.pattern.console
```

This property controls how log messages appear **in the console only**.

### 🤔 What Does "Console" Mean Here?
The word **"console"** refers to the **standard output stream** of the application — typically your terminal, command line interface, or the output panel in your IDE where the logs are printed during application runtime.

The term "console" comes from the days of command-line interfaces and system terminals. It traditionally represents the text-based interface where a program outputs messages.

In Spring Boot (and underlying Logback):
- `console` is **not a reserved keyword** in the sense that you create or name an appender called "console" manually.
- However, **Spring Boot preconfigures a `ConsoleAppender`** under the hood as part of its default logging setup.
- The property `logging.pattern.console` is a **convenient shortcut** provided by Spring Boot to customize the layout of that built-in `ConsoleAppender`.

You **cannot rename** or alias this internal identifier (like calling it `terminal` or `stdoutAppender`). The configuration key **must be**:

```properties
logging.pattern.console
```

This is a **Spring Boot convention** — and while the appender itself is not explicitly named in your code, this property will always point to the log pattern used for console output.

> 📌 TL;DR: You can't change the name `console` in `logging.pattern.console` — it's a Spring Boot convention tied to the default ConsoleAppender. It's not a general-purpose label you can rename.

### 🧩 Common Pattern Placeholders
| Pattern               | Description                                              |
|-----------------------|----------------------------------------------------------|
| `%d{pattern}`         | Timestamp in custom format (e.g., `yyyy-MM-dd HH:mm:ss`) |
| `%thread`             | Thread name                                              |
| `%level` / `%-5level` | Log level, optionally padded (e.g. `INFO`)               |
| `%logger`             | Fully qualified logger (class) name                      |
| `%logger{36}`         | Logger name, truncated to 36 characters                  |
| `%msg`                | The actual log message                                   |
| `%n`                  | Newline                                                  |
| `%M`                  | Method name (expensive; use with caution)                |
| `%line`               | Line number (also expensive)                             |

You can mix and match these to create the exact format you want.

### 🧪 Example 1: Simplified Log Format
```properties
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

Sample Output:
```
2025-04-15 16:10:00 [main] INFO  com.example.demo.MyService - Performing task...
```
✅ This format is clean and easier to scan visually.

### 🧪 Example 2: Developer-Friendly Verbose Format
```properties
logging.pattern.console=%d{HH:mm:ss.SSS} [%thread] %-5level %logger{20}.%M(%line) - %msg%n
```

Sample Output:
```
16:12:45.123 [main] INFO  MyService.createUser(27) - Creating user john_doe
```
🧠 Includes class, method, and line number — great for debugging, but adds overhead.

### 🧪 Example 3: Minimalist Format for Production
```properties
logging.pattern.console=%d{HH:mm:ss} %-5level - %msg%n
```

Sample Output:
```
16:13:01 INFO  - User created successfully
```
✅ This format strips out class and thread info — ideal for lightweight, readable logs in production.

### 🔄 Can You Change the Pattern at Runtime?
No — changes to `logging.pattern.console` require an application restart. It's part of the logging system initialization process.

### 🔁 Switching Back to Default
To revert to the default Spring Boot pattern, just remove or comment out the property:
```properties
# logging.pattern.console=...
```

Or use the default Logback pattern (not officially documented, but roughly equivalent to):
```properties
logging.pattern.console=%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %5p [%t] --- %c{1}: %m%n
```

### ✅ Summary
| Property                                | Description                                       |
|-----------------------------------------|---------------------------------------------------|
| `logging.pattern.console`               | Customizes how logs appear in the console         |
| `%d`, `%level`, `%thread`, `%msg`, etc. | Common placeholders in log patterns               |
| **Scope**                               | Works with **console output only**, not file logs |
| **"console" identifier**                | Spring Boot convention (cannot be renamed)        |

## Custom Log File Output
Spring Boot doesn’t just give you great console logging — it also makes it very easy to write logs to a file, and to control exactly how those logs are formatted.

Whether you're debugging locally or sending logs to a centralized logging system (like ELK, Splunk, or Fluentd), you’ll often want log files with clean, structured, and readable entries.

### 📁 1. Enable File Logging (Custom File Destination)

#### ✅ Option A: `logging.file.name`
This writes logs to a specific file name, in the working directory by default:

```properties
logging.file.name=logs/myapp.log
```
This will create a file called `myapp.log` in the `./logs/` directory.

Spring Boot will **create the file and the directory if they don’t exist**.

Use this when you want full control over the filename.

#### ✅ Option B: `logging.file.path`
This writes logs to the default file name (`spring.log`) in a custom directory:

```properties
logging.file.path=/var/logs/myapp
```
Output file: `/var/logs/myapp/spring.log`

Use this when you want control over the folder, but filename doesn’t matter.

> ⛑️ **Important**: Don’t use `logging.file.name` and `logging.file.path` at the same time—Spring Boot will **ignore** `logging.file.path` if both are defined.

### 🎨 2. Customize the File Log Output Format

By default, file logs have the same format as console logs — but you can customize them independently using:

```properties
logging.pattern.file
```
This property controls the format of the **file logs only**, and is separate from `logging.pattern.console`.

#### 🧪 What Does "file" Mean in `logging.pattern.file`?

The word **"file"** here refers to the **FileAppender** that Spring Boot automatically configures when you enable file logging.

- You don't explicitly define or name the appender as "file" — Spring Boot handles it internally.
- The `logging.pattern.file` property is a **Spring Boot convention**, which targets the default FileAppender.
- You **must use** the exact key `logging.pattern.file`; it's not possible to rename it (e.g., to `logging.pattern.logs` or `logging.pattern.disk`).

> 📌 TL;DR: The `file` in `logging.pattern.file` refers to the internal FileAppender. The name is **not configurable**, and this property must use `file` as defined by Spring Boot.

### 🧪 Example 1: Clean and Structured Log File
```properties
logging.file.name=logs/myapp.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
```

Sample Output (in `myapp.log`):
```
2025-04-15 16:42:03.125 [main] INFO  com.example.myapp.UserService - User created: john_doe
2025-04-15 16:42:03.130 [main] DEBUG com.example.myapp.UserService - User metadata: {email: john@example.com}
```
Readable, timestamped, and shows the source class.

### 🧪 Example 2: Production-Friendly Minimal Format
```properties
logging.file.name=logs/myapp.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} %-5level - %msg%n
```

Output:
```
2025-04-15 16:45:12 INFO  - Scheduled job started
2025-04-15 16:45:13 WARN  - Disk usage nearing limit
```
Simple, short, easy to parse with log monitoring tools.

### 🧪 Example 3: Verbose Developer Format (with method + line number)
```properties
logging.file.name=logs/myapp.log
logging.pattern.file=%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M(%line) - %msg%n
```

Output:
```
16:46:01.142 [main] INFO  UserService.createUser(27) - User created successfully
```
🔥 Super helpful during development. Method names and line numbers can slow performance slightly — avoid in production.

### 🗋 Common File Pattern Placeholders (Quick Ref)
| Pattern       | Meaning                           |
|---------------|-----------------------------------|
| `%d{...}`     | Timestamp (with custom format)    |
| `%thread`     | Thread name                       |
| `%level`      | Log level (INFO, DEBUG, etc.)     |
| `%logger{36}` | Logger (class) name, max 36 chars |
| `%M`          | Method name                       |
| `%line`       | Source code line number           |
| `%msg`        | Log message                       |
| `%n`          | Newline                           |

### 🔐 File Permissions and Deployment Notes
Ensure your Spring Boot application has **write permissions** to the directory where logs or other files will be stored.

In a **Linux environment**, the app runs under the **current Linux user** who starts the process, regardless of how it's launched:

- If run via `java -jar` from the terminal, it uses your current shell user (e.g., `ubuntu`)
- If launched from IntelliJ, it uses the logged-in user's account
- If started as a systemd service, it may use a specific service account like `springapp`

That user needs permission to:
- **Create** the log file (if it doesn't already exist)
- **Write** to the log file
- **Write into** the containing directory

Otherwise, logging (and potentially other file operations) will fail, possibly triggering an error like:

```bash
java.io.FileNotFoundException: /opt/myapp/logs/app.log (Permission denied)
```

To enable logging to `/opt/myapp/logs/app.log` for a user like `ubuntu` you need to set proper permissions like this:

```bash
# 1. Create the logs directory
sudo mkdir -p /opt/myapp/logs

# 2. Assign ownership to the Linux user
sudo chown -R ubuntu:ubuntu /opt/myapp/logs

# 3. Set appropriate permissions
sudo chmod 755 /opt/myapp/logs
```

To confirm your current Linux user, run:
```bash
whoami
```
Use this username when setting ownership with `chown`.

Always use **absolute paths** for logging in production. Relative paths (e.g., `logs/app.log`) can break depending on how the app is launched.

Example for `application.properties`:
```properties
logging.file.name=/opt/myapp/logs/api.log
```

| Task                      | Command / Action                                     |
|---------------------------|------------------------------------------------------|
| Identify your Linux user  | `whoami`                                             |
| Create log directory      | `mkdir -p /opt/myapp/logs`                           |
| Set directory ownership   | `chown -R <your-user>:<your-user> /opt/myapp/logs`   |
| Set write permissions     | `chmod 755 /opt/myapp/logs`                          |
| Define user for systemd   | `User=springapp` (or user that owns the directory)   |
| Use absolute logging path | `logging.file.name=/opt/myapp/logs/api.log`          |
| For Docker apps           | Mount volumes and verify container user write access |

### 🌍 Bonus: Different Logs for Different Profiles (Dev/Prod)
Use Spring profiles to define different logging setups.

**application-dev.properties**:
```properties
logging.file.name=logs/dev.log
logging.level.root=DEBUG
logging.pattern.file=%d{HH:mm:ss} %-5level %logger - %msg%n
```

**application-prod.properties**:
```properties
logging.file.name=/var/log/myapp/prod.log
logging.level.root=INFO
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} %-5level %logger - %msg%n
```

### ✅ Summary
| Property                     | Purpose                                                 |
|------------------------------|---------------------------------------------------------|
| `logging.file.name`          | Sets full path and name of log file                     |
| `logging.file.path`          | Sets the directory (uses `spring.log`)                  |
| `logging.pattern.file`       | Customizes log output format (file only)                |
| `%logger`, `%thread`, `%msg` | Useful placeholders for formatting patterns             |
| **"file" identifier**        | Spring Boot convention (not configurable or renameable) |

## Coding example
Spring Boot comes with built-in logging support via SLF4J and Logback. In most cases, **you do not need to add any extra Maven dependencies** because `spring-boot-starter` includes `spring-boot-starter-logging` by default. This makes it easy to get started with logging without worrying about configuration setup.

### 1. No Extra Dependencies Required
By default, Spring Boot includes SLF4J and Logback:
```xml
<!-- No need to include manually -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```

### 2. Logging Configuration in `application.properties`
```properties
# Global logging level
logging.level.root=INFO

# Logging level for your specific package
logging.level.com.example.loggingdemo=DEBUG

# Format for console output
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n

# File logging
logging.file.name=logs/app.log
logging.file.max-size=10MB
logging.file.total-size-cap=100MB
logging.logback.rollingpolicy.max-history=10
```

### 3. Controller with Logging
```java
package com.example.loggingdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("/greet")
    public String greet() {
        logger.info("Handling /greet request");
        logger.debug("Returning greeting message");
        return "Hello from the controller!";
    }
}
```

### 4. Service with Logging
```java
package com.example.loggingdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    public String getServiceMessage() {
        logger.info("Executing service logic");
        logger.debug("Returning service message");
        return "Hello from the service!";
    }
}
```

### 5. Profile-specific Configuration (e.g., `application-dev.properties`)
```properties
# Enable debug-level logging in development
logging.level.root=DEBUG
logging.level.com.example.loggingdemo=TRACE
```

This setup demonstrates typical use of logging in a Spring Boot application using built-in dependencies and property-based configuration only. It includes logging in both a controller and a service, package-specific and profile-specific level tuning, and console/file output control.




NEXT

Appenders
Log file rotation (max file size, daily rollovers)

Advanced Logback configuration using logback-spring.xml

Structured logging (e.g., JSON logs for use in ELK)
Switching to Log4j2 (optional)