# Logging in Springboot

## Default Setup
Spring Boot provides a powerful yet simple default logging configuration. Out of the box, it sets up everything you need to get started with logging using industry-standard tools.

- **Uses Apache Commons Logging (JCL) as a logging facade.**
  - JCL (Jakarta Commons Logging) is **not a logging framework itself**, but a *facade* or *abstraction layer* for various logging frameworks. It's designed to allow the actual logging implementation to be plugged in at runtime.
  - This pattern is known as the **Facade Design Pattern**, which provides a unified interface to a set of interfaces in a subsystem. In this context, JCL exposes a standard logging API (e.g. `Log log = LogFactory.getLog(Class<?> clazz)`) that includes method signatures like `log.info(String message)` or `log.error(String message, Throwable t)`, allowing developers to log messages without depending on the specific underlying logging implementation.
  - By using JCL, application code is decoupled from concrete logging libraries; developers code against a fixed interface, while the actual logging engine (like Log4j, Logback, etc.) can be chosen or swapped behind the scenes.

- **Internally, it routes log calls to SLF4J (Simple Logging Facade for Java).**
  - SLF4J is another logging facade (more modern and flexible than JCL) that provides a clean and consistent API to developers.
  - It defines interfaces like `org.slf4j.Logger` and a factory method `LoggerFactory.getLogger(Class<?> clazz)`, which returns a `Logger` instance. This instance exposes methods like `debug(String msg)`, `info(String msg)`, and `error(String msg, Throwable t)`, again focusing on *method signatures*, not implementations.
  - SLF4J serves as a contract: developers write code using its interfaces, while SLF4J itself binds (at compile time or runtime) to a real logging implementation such as Logback. In Spring Boot, bridges are included that redirect JCL-based log calls to SLF4J automatically, enabling consistent behavior.

- **The default implementation behind the scenes is Logback.**
  - Logback is a powerful and flexible logging framework authored by the same creator of Log4j.
  - It serves as the actual engine that performs the logging tasks, such as formatting log output, writing to files or consoles, and filtering messages based on severity levels.
  - SLF4J forwards calls like `logger.debug(String message)` or `logger.warn(String message, Throwable t)` to Logback’s implementation classes, where the message is processed and ultimately output according to configuration.
  - This backend implementation is transparent to the developer; they work with the facade (`Logger` interface), while Logback handles the real work.

This setup provides:

- A clean API (`org.slf4j.Logger`) for developers: you write `Logger logger = LoggerFactory.getLogger(MyClass.class)` and call methods like `logger.info(String message)`, abstracted from any implementation logic.
- Structured, performant, and extensible logging behavior via Logback.
- No configuration required to start using it; Spring Boot auto-configures everything to provide sensible defaults.

### Getting Started
When you create a Spring Boot project (e.g. using Spring Initializr or your favorite build tool), you don't need to add any logging dependencies manually as they come pre-configured!

You can start logging like this:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyService {

  private static final Logger logger = LoggerFactory.getLogger(MyService.class);

  public void performTask() {
    logger.info("Starting task execution...");

    try {
      // Simulate fetching data
      logger.debug("Fetching data from repository...");
      String data = fetchDataFromRepository();

      // Simulate processing logic
      logger.debug("Processing data: {}", data);
      String result = processData(data);

      // Simulate persisting result
      logger.debug("Saving processed result...");
      saveResult(result);

      logger.info("Task completed successfully.");
    } catch (IllegalArgumentException e) {
      logger.warn("Invalid argument encountered: {}", e.getMessage());
    } catch (Exception e) {
      logger.error("Unexpected error while performing task", e);
    }
  }

  private String fetchDataFromRepository() {
    // Stubbed method for fetching data
    return "sample-data";
  }

  private String processData(String data) {
    // Stubbed processing logic
    return data.toUpperCase();
  }

  private void saveResult(String result) {
    // Stubbed persistence logic
    // Pretend we're saving the result somewhere
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

### Where the Logs Go
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

### No Configuration Needed but You *Can* Customize It
Spring Boot auto-configures Logback using sensible defaults; as result, **you don’t need to set up any configuration** to start logging.

However, if you want to override the defaults, you have two main options:

- **Use `application.properties` or `application.yml`**  
  This is ideal for *simple configuration needs*, such as:
  - Setting log levels per package/class
    ```properties
    logging.level.root=INFO
    logging.level.com.example.myapp=DEBUG
    ```
  - Defining output destinations and patterns
    ```properties
    logging.file.name=app.log
    logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
    ```

- **Use `logback-spring.xml` (recommended) or `logback.xml`**  
  This gives you *full control* over Logback’s features:
  - Custom appenders (file, rolling, console, socket, etc.)
  - Encoders and filters
  - Conditional logic and profile-specific config (only with `logback-spring.xml`)
  - Fine-tuned layout formats

> `application.properties` and `logback-spring.xml` are **not fully interchangeable**.  
> For advanced use cases (e.g. multiple appenders, conditional logging, or structured output), prefer XML-based configuration.

Recap:

| Feature                                 | Value                        |
|-----------------------------------------|------------------------------|
| Logging Facade                          | Apache Commons Logging       |
| Developer API                           | SLF4J (`org.slf4j.Logger`)   |
| Underlying Logging Implementation       | Logback                      |
| Configuration Needed?                   | ❌ None (auto-configured)     |
| Customize via `application.properties`? | ✅ Yes (basic settings)       |
| Full Control via XML?                   | ✅ Yes (`logback-spring.xml`) |
| Logging Output                          | Console (default)            |

## Log levels
In this section, we'll cover how to configure logging levels in Spring Boot using `application.properties`.

Spring Boot (via SLF4J and Logback) supports the following standard log levels:

| Level | Description                                                               |
|-------|---------------------------------------------------------------------------|
| TRACE | Most detailed information; typically only useful for diagnosing problems. |
| DEBUG | Useful for debugging and internal system details.                         |
| INFO  | General application flow and important runtime events. *(Default)*        |
| WARN  | Something unexpected happened, but the application can still continue.    |
| ERROR | Serious issues where part of the application might not be working.        |

These levels are hierarchical:

> If a logger is set to `INFO`, then `INFO`, `WARN`, and `ERROR` messages will be shown but not `DEBUG` or `TRACE`.

### Setting Logging Levels in `application.properties`
You can change the log level globally or for specific packages/classes using the `logging.level` property.

To set a **global (root)** log level:

```properties
# application.properties
logging.level.root=INFO
```

This sets the default level for all loggers in the application.

To enable more detailed logging for a specific package (**per-package logging**):

```properties
# application.properties
logging.level.com.example.myapp=DEBUG
```

Or for a specific class (**per-class logging**):

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

This allows fine-grained control (e.g. verbose logs during development) but only for specific modules.

Summing-up:

| Property                                  | Meaning                           |
|-------------------------------------------|-----------------------------------|
| `logging.level.root=INFO`                 | Sets default logging level        |
| `logging.level.com.example=DEBUG`         | Sets log level for package        |
| `logging.level.com.example.MyClass=TRACE` | Sets log level for specific class |

### Example
Suppose you have this code in your `UserService` class:

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  public void createUser(String username) {
    logger.trace("Trace: Starting user creation for '{}'", username);

    if (username == null || username.trim().isEmpty()) {
      logger.warn("Warning: Provided username is null or empty");
      throw new IllegalArgumentException("Username must not be empty");
    }

    logger.debug("Debug: Validated username '{}'", username);

    try {
      // Simulate checking for existing user
      boolean userExists = checkIfUserExists(username);
      if (userExists) {
        logger.info("Info: User '{}' already exists", username);
        return;
      }

      // Simulate user creation logic
      logger.debug("Debug: Creating user entity for '{}'", username);
      String userId = saveUserToDatabase(username);

      logger.info("Info: Successfully created user '{}' with ID '{}'", username, userId);
    } catch (Exception e) {
      logger.error("Error: Failed to create user '{}'", username, e);
    }
  }

  private boolean checkIfUserExists(String username) {
    // Stub: pretend we checked a database
    return false;
  }

  private String saveUserToDatabase(String username) {
    // Stub: pretend we're saving the user and returning an ID
    return "usr-12345";
  }
}
```

And you configure the properties file like:

```properties
logging.level.com.example.myapp.service.UserService=DEBUG
```

In the output, you will see:
- `DEBUG`, `INFO`, `WARN`, and `ERROR`

You will not see:
- `TRACE` (because it's lower than the configured level)

If you change it to:

```properties
logging.level.com.example.myapp.service.UserService=TRACE
```

Then all logs, including `TRACE`, will be shown.

### Environment-wise logging
Spring Boot supports multiple configuration files, allowing you to separate settings for different environments (e.g. development, testing, production).

`application.properties` is your **default configuration file**, and it applies to **all environments** unless explicitly overridden. Use it for values that remain constant regardless of where your app runs.

An example of `application.properties` (shared settings for all environments):

```properties
# Common configuration that applies to all environments
logging.level.root=INFO
spring.datasource.driver-class-name=org.h2.Driver
app.feature-x.enabled=true
```

Then you can create **profile-specific configuration files** to tailor settings for different environments, such as:

- `application-dev.properties` for development
- `application-test.properties` for testing
- `application-prod.properties` for production

These files are **optional** but very useful when different environments require different behavior or settings.

Spring Boot automatically loads the main `application.properties` file first.  
Then, **if a profile is active**, it will load the corresponding file like `application-dev.properties` **on top of** the base configuration.

- **If a property exists in both**, the profile-specific value overrides the base value.
- **If a property exists only in the profile-specific file**, it is added to the environment configuration.

This allows you to maintain **shared defaults** in `application.properties`, and only override what’s different in each environment.

An example of `application-dev.properties` (development-specific overrides):

```properties
# Development environment-specific configuration
logging.level.root=DEBUG  # More detailed logging in development for troubleshooting
spring.datasource.url=jdbc:h2:mem:devdb  # In-memory database used in development for fast testing
spring.datasource.driver-class-name=org.h2.Driver  # Use the H2 driver in development
app.feature-x.enabled=true  # Enable Feature X for testing in the development environment
```

And an example of `application-prod.properties` (production-specific overrides):

```properties
# Production environment-specific configuration
logging.level.root=WARN  # Less verbose logging in production to reduce log size and improve performance
spring.datasource.url=jdbc:postgresql://prod-db:5432/app  # Connection to PostgreSQL production database
spring.datasource.driver-class-name=org.postgresql.Driver  # PostgreSQL driver for production environment
app.feature-x.enabled=false  # Disable Feature X in production to ensure stability and performance
spring.security.enabled=true  # Enable security features in production for enhanced protection
```

To activate the correct profile for the environment, you need to set the `spring.profiles.active` property. This property determines which profile-specific configuration files (e.g. `application-dev.properties`, `application-prod.properties`) will be used when the application starts.

You can activate a profile in various ways, allowing flexibility depending on how you are running the application.

1. **In `application.properties` (to set a default profile):**
   This method is ideal if you want to set a default active profile for your application that will be used unless overridden by other configurations.

   ```properties
   spring.profiles.active=prod  # Activates the 'prod' profile by default
   ```
2. As a Command-Line Argument when Running the App:

  ```bash
  java -jar myapp.jar --spring.profiles.active=dev  # Activates the 'dev' profile
  ```

  This command sets the active profile to `dev` at runtime, overriding any settings from `application.properties`. By specifying the `--spring.profiles.active=dev` argument, you ensure that the application will load the configuration from `application-dev.properties`, which contains settings specific to the development environment. This approach is particularly useful when you want to easily switch between environments without modifying your code or default configuration files.

3. As an Environment Variable:

  ```bash
  export SPRING_PROFILES_ACTIVE=prod  # Activates the 'prod' profile
  ```

## Default Output Format
Spring Boot’s default console log format looks like this:

```
2025-04-15T16:00:45.351  INFO 31708 --- [  restartedMain] com.example.demo.MyService : Performing task...
```

This log output consists of the following components:

- **Timestamp**: ISO-8601 style with milliseconds. It shows when the log was generated (e.g. `2025-04-15T16:00:45.351`).
- **Log Level**: The severity of the log message (e.g. `INFO`, `DEBUG`).
- **PID**: **Process ID**. This is the unique identifier of the current running process within the operating system. Each process running on your system has a distinct PID. In the example above, `31708` is the Process ID.
  - The **PID** remains the same for the entire duration of the application's run. It doesn't change unless the application is restarted or a new instance is launched.
- **Thread**: This indicates which thread generated the log message. It includes both the **thread name** and the **thread ID**.
  - The **Thread Name** (e.g. `restartedMain`) is a human-readable string that identifies the purpose or task of the thread.
  - The **Thread ID** (a numeric value) is a unique identifier for the thread within the process. It allows the system to distinguish between multiple threads running concurrently in the same process. This ID changes every time a new thread is created.
  - **Threads** are part of a process, and the ID is unique to the thread within the running process. Threads can change during the runtime of an application, especially when new threads are spawned to handle specific tasks (e.g. user requests or background jobs).
- **Logger**: The class name or the logger's identifier that is generating the log message. In this example, it is `com.example.demo.MyService`.
- **Message**: The actual log message generated by your code, such as `Performing task...`.

**Process ID vs. Thread ID**

- **Process ID (PID)**: A process is an instance of a running application. It is assigned a unique identifier (PID) by the operating system. Each process has its own memory and resources. The **PID** does not change while the application is running but will change if you restart the application or launch a new instance of it.
- **Thread ID**: A **thread** is a smaller unit of execution within a process. Every process has at least one thread (the main thread), but it can spawn multiple threads to execute tasks concurrently. Each thread is assigned a **thread ID** that uniquely identifies it within the process. The **thread ID** changes when new threads are created and can differ between invocations of the same process.
- **PID** refers to the unique identifier for the process and stays the same for the entire application runtime.
- **Thread ID** refers to the unique identifier for each thread within the process, and it can change when new threads are created.
- **Process ID**: Remains the same throughout the lifetime of the process. It only changes when the application restarts or when a new process is created.
- **Thread ID**: Changes dynamically as threads are created and destroyed. Each new thread spawned during the application's execution will have a different **Thread ID**.

## Custom Output Format
In this section, we'll explore how to customize the format of your log messages as they appear in the **console output**.

This allows you to make logs more readable, structured, or adapted to your organization's standards, all without touching any external configuration files like `logback.xml`.

Spring Boot allows you to customize the log pattern using the property:

```properties
logging.pattern.console
```

In Spring Boot (and underlying Logback):

- This property controls how log messages appear **in the console only**.
- The word **"console"** refers to the **standard output stream** of the application i.e. your terminal, command line interface, or the output panel in your IDE where the logs are printed during application runtime.
- The term "console" comes from the days of command-line interfaces and system terminals. It traditionally represents the text-based interface where a program outputs messages.
- `console` is **not a reserved keyword** in the sense that you create or name an appender called "console" manually.
- However, **Spring Boot preconfigures a `ConsoleAppender`** under the hood as part of its default logging setup.
- The property `logging.pattern.console` is a **convenient shortcut** provided by Spring Boot to customize the layout of that built-in `ConsoleAppender`.
- You **cannot rename** or alias this internal identifier (like calling it `terminal` or `stdoutAppender`). 

In order to customise the log output format, we can make use of the following placeholders:

| Pattern               | Description                                             |
|-----------------------|---------------------------------------------------------|
| `%d{pattern}`         | Timestamp in custom format (e.g. `yyyy-MM-dd HH:mm:ss`) |
| `%thread`             | Thread name                                             |
| `%level` / `%-5level` | Log level, optionally padded (e.g. `INFO`)              |
| `%logger`             | Fully qualified logger (class) name                     |
| `%logger{36}`         | Logger name, truncated to 36 characters                 |
| `%msg`                | The actual log message                                  |
| `%n`                  | Newline                                                 |
| `%M`                  | Method name (expensive; use with caution)               |
| `%line`               | Line number (also expensive)                            |

You can mix and match these to create the exact format you want.

For example, the following **simplified** output format configuration:

```properties
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

will result in:

```
2025-04-15 16:10:00 [main] INFO  com.example.demo.MyService - Performing task...
```
This format is clean and easier to scan visually.

If you want a developer-friendly **verbose** output format, use:

```properties
logging.pattern.console=%d{HH:mm:ss.SSS} [%thread] %-5level %logger{20}.%M(%line) - %msg%n
```

which will result in:

```
16:12:45.123 [main] INFO  MyService.createUser(27) - Creating user john_doe
```

This includes class, method, and line number; great for debugging, but adds overhead.

Notice that without padding the `%level`:

```properties
logging.pattern.console=%d{HH:mm:ss} [%thread] %level %logger - %msg%n
```

the output will look like this:

```makefile
10:00:00 [main] INFO com.example.MyService - Starting task
10:00:01 [main] ERROR com.example.MyService - Something failed
10:00:02 [main] DEBUG com.example.MyService - Debugging info
10:00:03 [main] WARN com.example.MyService - Warning issued
```

Notice how the log levels are not aligned; messages shift horizontally depending on the length of the level string.

Whereas, padding by 5 (which is the maximum length of a level message):

```properties
logging.pattern.console=%d{HH:mm:ss} [%thread] %-5level %logger - %msg%n
```

the output will look lik:

```makefile
10:00:00 [main] INFO  com.example.MyService - Starting task
10:00:01 [main] ERROR com.example.MyService - Something failed
10:00:02 [main] DEBUG com.example.MyService - Debugging info
10:00:03 [main] WARN  com.example.MyService - Warning issued
```

With `%-5level`, all log levels take up exactly 5 characters, aligned left, ensuring consistent column layout. This makes your logs cleaner and easier to scan.

To revert to the default Spring Boot pattern, just remove or comment out the property:
```properties
# logging.pattern.console=...
```

Or use the default Logback pattern (not officially documented, but roughly equivalent to):

```properties
logging.pattern.console=%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %5p [%t] --- %c{1}: %m%n
```

## File logging
As we said, by default, Spring Boot provides a ready-to-use **console logging** based on **Logback**, with no extra configuration needed. Here's what you get out of the box:

- Console logging is enabled by default; log messages are printed directly to the terminal.
- A default `Logger` is preconfigured and available for use; you can log at various levels: `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`.
- You can control the log level:
  - **Globally** (for the entire application)
  - **Per package**
  - **Per class**
- Log output is formatted with a default pattern that includes:
  - Timestamp
  - Log level
  - Process ID (PID)
  - Thread name
  - Logger name (usually the class)
  - Message
- You can **customize the output format** (without needing to use `logback.xml`) using properties like `logging.pattern.console`.

However, Spring Boot doesn’t just give you great console logging; it also makes it very easy to write logs to a file, and to control exactly how those logs are formatted.

Whether you're debugging locally or sending logs to a centralized logging system (like ELK, Splunk, or Fluentd), you’ll often want log files with clean, structured, and readable entries.

Thus, you can easily add **file logging** on top of **console logging**:
- File logging writes log messages to a specified file in addition to the console.
- Configuration options (log levels, patterns) work the same way as with console logging.

There are two ways to enable **file-logging**:

✅ **Option A: `logging.file.name`**

This writes logs to a specific file name, in the working directory by default:

```properties
logging.file.name=logs/myapp.log
```
This will create a file called `myapp.log` in the `./logs/` directory.

Spring Boot will **create the file and the directory if they don’t exist**.

Use this when you want full control over the filename.

✅ **Option B: `logging.file.path`**

This writes logs to the default file name (`spring.log`) in a custom directory:

```properties
logging.file.path=/var/logs/myapp
```
Output file: `/var/logs/myapp/spring.log`

Use this when you want control over the folder, but filename doesn’t matter.

> ⛑️ **Important**: Don’t use `logging.file.name` and `logging.file.path` at the same time; Spring Boot will **ignore** `logging.file.path` if both are defined.

It goes without saying that you can configure a custom output format for file-logging as well.

By default, file logs have the same format as console logs; however, you can customize them independently using:

```properties
logging.pattern.file
```
This property controls the format of the **file logs only**, and is separate from `logging.pattern.console`.

The word **file** here refers to the **FileAppender** that Spring Boot automatically configures when you enable file logging.

- You don't explicitly define or name the appender as "file"; Spring Boot handles it internally.
- The `logging.pattern.file` property is a **Spring Boot convention**, which targets the default FileAppender.
- You **must use** the exact key `logging.pattern.file`; it's not possible to rename it (e.g. to `logging.pattern.logs` or `logging.pattern.disk`).
- The `file` in `logging.pattern.file` refers to the internal FileAppender. The name is **not configurable**, and this property must use `file` as defined by Spring Boot.

For example, the following:

```properties
logging.file.name=logs/myapp.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
```

will result in (within `myapp.log`):

```
2025-04-15 16:42:03.125 [main] INFO  com.example.myapp.UserService - User created: john_doe
2025-04-15 16:42:03.130 [main] DEBUG com.example.myapp.UserService - User metadata: {email: john@example.com}
```
Readable, timestamped, and shows the source class.

A more minimal output format might be:

```properties
logging.file.name=logs/myapp.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} %-5level - %msg%n
```

which will result in:

```
2025-04-15 16:45:12 INFO  - Scheduled job started
2025-04-15 16:45:13 WARN  - Disk usage nearing limit
```

### Write Permissions
When **file logging** is introduced, ensure your Spring Boot application has **write permissions** to the directory where logs or other files will be stored.

In a **Linux environment**, the app runs under the **current Linux user** who starts the process, regardless of how it's launched:

- If run via `java -jar` from the terminal, it uses your current shell user (e.g. `ubuntu`)
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
# 1. Confirm your current Linux user; use this username when setting ownership with `chown`
whoami

# 2. Create the logs directory
sudo mkdir -p /opt/myapp/logs

# 3. Assign ownership to the Linux user
sudo chown -R ubuntu:ubuntu /opt/myapp/logs

# 4. Set appropriate permissions (chmod 755 sets permissions to owner: read/write/execute, group: read/execute, others: read/execute)
sudo chmod 755 /opt/myapp/logs
```

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

### 5. Profile-specific Configuration (e.g. `application-dev.properties`)
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

Structured logging (e.g. JSON logs for use in ELK)
Switching to Log4j2 (optional)