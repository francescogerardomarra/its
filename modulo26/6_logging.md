# Logging

## Basics

### What is Logging?
Logging is the process of recording information about a program's execution. Logs provide visibility into the inner workings of software systems by capturing key events, errors, transactions, and system behaviors in textual format, typically outputted to files, consoles, or centralized systems.

### Why Logging is Important
- **Troubleshooting and Debugging**: Quickly identify and diagnose issues.
- **Auditing and Compliance**: Maintain records for security and legal audits.
- **Performance Monitoring**: Detect bottlenecks and optimize performance.
- **Operational Awareness**: Understand system behavior during normal operations.
- **Proactive Issue Detection**: Catch and resolve minor issues before they escalate.
- **Historical Data Analysis**: Analyze trends, predict future problems, and inform decision-making.

In short, robust logging is the backbone of system observability, aiding in both reactive problem-solving and proactive system improvements.

---

## Difference Between Logging, Monitoring, and Tracing

| Aspect          | Logging                              | Monitoring                                 | Tracing                                      |
|-----------------|--------------------------------------|--------------------------------------------|----------------------------------------------|
| **Purpose**     | Record discrete events               | Measure system health                      | Track a single request's flow across systems |
| **Granularity** | Event-level                          | Metrics-level                              | Request-level                                |
| **Examples**    | Errors, warnings, status updates     | CPU usage, memory consumption, error rates | API call durations, database query times     |
| **Tools**       | Logstash, Fluentd, Loki              | Prometheus, Datadog, CloudWatch            | Jaeger, Zipkin, OpenTelemetry                |
| **Format**      | Unstructured or semi-structured text | Aggregated metrics, time-series data       | Detailed spans and traces                    |

While logging captures **"what happened"**, monitoring answers **"how are things overall"**, and tracing addresses **"how did this specific request perform"**.

---

## Standard Logging Terminology

### Log Levels
Differentiate the severity or importance of the events being logged. Helps filter noise vs. actionable events.

### Log Messages
Human-readable (and sometimes machine-parsable) strings describing an event. Typically include a timestamp, log level, message text, and contextual data (e.g., user ID, session ID).

Example:
```
2025-04-11 12:00:00 INFO User 'jdoe' successfully logged in from IP 192.168.1.10
```

### Log Files
Storage medium for logs. Commonly stored in:
- Local disks
- Cloud storage buckets
- Centralized log management platforms

Best practices:
- Consistent naming conventions
- Include application name, environment, and date

### Log Rotation
Automated process of managing log file size and lifespan. Prevents disk space exhaustion.

Strategies:
- **Size-based rotation**: Rotate when file reaches a maximum size.
- **Time-based rotation**: Rotate daily, weekly, etc.
- **Retention policies**: Keep logs for a predefined period, archive, or delete older logs.

Tools:
- `logrotate` (Linux)
- Built-in features in logging libraries
- Cloud-native options (AWS S3 lifecycle policies)

---

## Log Levels and When to Use Them

Effective logging requires knowing *what* to log and at *what severity*.

### TRACE
- **Purpose**: Extremely detailed information; fine-grained internal states.
- **Audience**: Developers.
- **When to Use**: Deep-dive diagnostics during local development or troubleshooting specific production issues.
- **Example**:
```
TRACE Entered method 'calculateDiscount', parameters: userId=123, cartId=456
```

### DEBUG
- **Purpose**: Useful information during development and debugging.
- **Audience**: Developers and DevOps teams.
- **When to Use**: Standard in development environments; can be toggled on in production for issue investigations.
- **Example**:
```
DEBUG Loading user preferences for userId=123
```

### INFO
- **Purpose**: General operational messages about high-level system flow.
- **Audience**: Operators, support teams.
- **When to Use**: Document normal but significant events (e.g., startup, shutdown, user login).
- **Example**:
```
INFO Order 789 processed successfully.
```

### WARN
- **Purpose**: Something unexpected happened, but the system is still functional.
- **Audience**: Developers, SREs.
- **When to Use**: Highlight recoverable failures or degraded states.
- **Example**:
```
WARN Disk space low (10% remaining) on volume '/var/log'
```

### ERROR
- **Purpose**: A significant problem occurred that should be addressed.
- **Audience**: Developers, Operations, Alerting Systems.
- **When to Use**: Exceptions that were caught but affected operations.
- **Example**:
```
ERROR Failed to connect to database: timeout after 5 retries.
```

### FATAL / CRITICAL
- **Purpose**: Severe errors causing system crashes or immediate failures.
- **Audience**: Everyone (immediate attention required).
- **When to Use**: Irrecoverable states.
- **Example**:
```
FATAL Kernel panic - not syncing: Attempted to kill init!
```

---

## Best Practices

- **Consistency**: Follow a standard log format across services.
- **Contextual Information**: Always include context (user IDs, request IDs, environment tags).
- **Avoid Overlogging**: Don't flood logs with unnecessary detail.
- **Structured Logging**: Prefer JSON or key-value pairs for better machine parsing.
- **Security**: Never log sensitive information (passwords, credit card numbers).
- **Correlation IDs**: Attach unique IDs to trace requests across services.
- **Log Aggregation**: Centralize logs from multiple systems for easier querying.
- **Alerting on Errors**: Integrate with monitoring systems to alert based on ERROR or FATAL logs.

---

## Log Formatting & Structure

Logging is crucial for monitoring, debugging, and tracing applications. Good logging practices help reduce the time needed to troubleshoot issues and improve system observability. In this lesson, we'll cover:

- Plain Text vs Structured Logging
- Key components of a well-formatted log
- Using Correlation IDs and Request IDs for traceability

We will focus on **concepts** and **examples**, without assuming a specific language or framework.

### Plain Text Logging
Plain text logs are simple human-readable strings, often with a predefined format.

Example:
```
[2025-04-11 14:00:00] INFO User login successful: username=JohnDoe
```

**Advantages:**
- Easy for humans to read.
- No special tooling needed.

**Disadvantages:**
- Hard to parse by machines.
- Difficult to query specific fields in centralized logging systems.

### Structured Logging
Structured logging captures logs as key-value data objects (often in JSON), making them easy to parse and search.

Example:
```json
{
  "timestamp": "2025-04-11T14:00:00Z",
  "level": "INFO",
  "message": "User login successful",
  "username": "JohnDoe",
  "event": "user_login"
}
```

**Advantages:**
- Easier machine parsing and analysis.
- Facilitates centralized logging and monitoring.

**Disadvantages:**
- Slightly more complex to implement.

---

## Key Log Components

Regardless of format, every log entry should typically contain:

| Field     | Purpose                                     |
|-----------|---------------------------------------------|
| Timestamp | When the event occurred                     |
| Log Level | Severity (INFO, DEBUG, WARN, ERROR)         |
| Message   | Description of the event                    |
| Context   | Metadata like userId, sessionId, IP address |

### Plain Text Example
```
[2025-04-11 14:05:00] ERROR Database connection failed: error=TimeoutException retryCount=3
```

### Structured Example
```json
{
  "timestamp": "2025-04-11T14:05:00Z",
  "level": "ERROR",
  "message": "Database connection failed",
  "error": "TimeoutException",
  "retryCount": 3,
  "service": "UserService"
}
```

---

## Correlation IDs

In distributed or microservices architectures, tracing a request across multiple services is essential for debugging and monitoring. Correlation IDs make this possible.

### What is a Correlation ID?
- A unique identifier assigned to each request.
- Propagated across service boundaries.
- Enables full traceability of a request's journey through the system.

### Example Usage
Assign a Correlation ID at the start of a request:

```
Correlation-ID: 123e4567-e89b-12d3-a456-426614174000
```

Include the Correlation ID in all related log entries.

**Plain Text Example:**
```
[2025-04-11 14:10:00] INFO Fetching user profile: userId=42 correlationId=123e4567-e89b-12d3-a456-426614174000
```

**Structured Log Example:**
```json
{
  "timestamp": "2025-04-11T14:10:00Z",
  "level": "INFO",
  "message": "Fetching user profile",
  "userId": 42,
  "correlationId": "123e4567-e89b-12d3-a456-426614174000"
}
```

### How to Generate a Correlation ID
- Use a UUID generator.
- If a Correlation ID is already present (e.g., from an API Gateway), reuse it.
- Otherwise, generate a new one.

**Pseudo-code Example:**
```pseudo
if (incomingRequest.hasHeader("Correlation-ID")) {
    correlationId = incomingRequest.getHeader("Correlation-ID");
} else {
    correlationId = generateNewUUID();
}
addToLoggingContext("correlationId", correlationId);
```

---

## Request IDs

Request IDs help uniquely identify individual operations or transactions within services, providing an additional layer of traceability.

### What is a Request ID?
- A distinct identifier for each incoming request.
- Useful for tracking retries, partial failures, and load balancing decisions.

### Example of Request ID in Action

Consider a scenario where a user submits an API request to place an order. The request is handled by multiple services: the front-end service, the payment service, and the inventory service.

#### User submits order request:
- The API receives the order request and generates a Request ID `REQ-98765`.
- This Request ID is logged and passed along to the next service.

```json
{
    "request_id": "REQ-98765",
    "timestamp": "2025-04-11T10:00:00Z",
    "level": "INFO",
    "message": "Order received"
}
```

#### Payment Service Processes Payment:
- The payment service receives the request with the same Request ID `REQ-98765` and starts processing the payment. The Request ID is passed through to ensure continuity in tracking across different services.

```json
{
    "request_id": "REQ-98765",
    "timestamp": "2025-04-11T10:01:00Z",
    "level": "INFO",
    "message": "Payment processing started"
}
```

#### Inventory Service Checks Stock:
- After the payment is processed successfully, the inventory service checks the availability of the ordered products. The same Request ID `REQ-98765` is used to maintain traceability.

```json
{
    "request_id": "REQ-98765",
    "timestamp": "2025-04-11T10:02:00Z",
    "level": "INFO",
    "message": "Inventory check started"
}
```

#### Request Failure Tracking:
- If an error occurs, for example, during payment processing, the Request ID `REQ-98765` helps the team trace exactly where the failure occurred and what action needs to be taken.

```json
{
    "request_id": "REQ-98765",
    "timestamp": "2025-04-11T10:03:00Z",
    "level": "ERROR",
    "message": "Payment processing failed"
}
```

### Best Practices
- Use structured logs rather than plain text, especially in production environments.
- Always include both Correlation IDs and Request IDs.
- Format timestamps consistently (ISO 8601 format, UTC timezone).
- Apply appropriate log levels:
    - **DEBUG**: Development and troubleshooting.
    - **INFO**: Normal operational events.
    - **WARN**: Recoverable or unexpected conditions.
    - **ERROR**: Serious problems or failures.
- Consistently name log fields across all services.
- Never log sensitive information (e.g., passwords, personal data).

---

## Log rotation and retention
In any system, logs are crucial for auditing, debugging, and monitoring. However, without proper management, log files can grow uncontrollably, leading to disk space exhaustion, degraded performance, and difficulty in parsing relevant information. This guide provides a deep dive into **Log Rotation and Retention**, covering **size-based and time-based rotation**, **archiving**, and **deletion/compression strategies**. The audience for this material is technical, presupposing familiarity with filesystem operations, scripting, and basic system administration principles.

### Log Rotation Principles

**Log Rotation** refers to the practice of splitting logs into multiple files based on certain criteria (size or time) to manage their growth and ensure efficient storage and retrieval.

**Primary Objectives:**
- Prevent disk exhaustion
- Maintain system performance
- Facilitate easier log analysis
- Comply with data retention policies

### Rotation Strategies

Rotation can be triggered by:

#### a. Size-Based Rotation

Rotate logs once they reach a predefined maximum size.

**Example:**
- Rotate a file once it exceeds 100 MB.

**Pseudocode:**
```pseudocode
if get_file_size(log_file) > 100MB then
    rotate_log(log_file)
    create_new_log(log_file)
end if
```

**Implementation Details:**
- Monitor log size periodically.
- On threshold breach, rename current log to a new file (e.g., `logfile.1`, `logfile.2`, etc.).
- Create a new empty log file to continue logging.

#### b. Time-Based Rotation

Rotate logs at regular time intervals, irrespective of their size.

**Example:**
- Rotate logs daily at midnight.

**Pseudocode:**
```pseudocode
if current_time == scheduled_time then
    rotate_log(log_file)
    create_new_log(log_file)
end if
```

**Implementation Details:**
- Use system schedulers to trigger rotations.
- Timestamp log archives for clarity (e.g., `logfile-YYYYMMDD.log`).

### Archiving Old Logs

**Archiving** involves packaging rotated logs for storage, often with compression to save space.

#### Techniques:
- **Compression**: Reduce file size.
- **Packaging**: Bundle multiple logs together.

**Pseudocode:**
```pseudocode
logs_to_archive = list_old_logs(log_directory)
archive_name = "archive-" + current_date() + ".tar.gz"
create_archive(logs_to_archive, archive_name)
```

**Best Practices:**
- Archive frequently accessed logs separately.
- Maintain metadata for auditing.

**Security Considerations:**
- Encrypt archives if logs contain sensitive data.
- Generate checksums for integrity verification.

### Deleting or Compressing Logs

To conserve storage, old logs must be either deleted or compressed after a retention period.

#### a. Deletion Policies

- Delete logs older than a certain number of days (e.g., 90 days).

**Pseudocode:**
```pseudocode
for each log_file in log_directory do
    if get_file_age(log_file) > 90_days then
        delete(log_file)
    end if
end for
```

#### b. Compression Strategies

- Compress logs after a certain period (e.g., compress logs older than 7 days).

**Pseudocode:**
```pseudocode
for each log_file in log_directory do
    if get_file_age(log_file) > 7_days then
        compress(log_file)
    end if
end for
```

**Important Considerations:**
- Automate deletion/compression.
- Monitor disk space usage.
- Log the deletion/compression actions for auditing.

### Naming Conventions for Rotated Logs

Proper naming conventions simplify log management:
- **Incremental Numbers**: `logfile.1`, `logfile.2`, etc.
- **Timestamps**: `logfile-YYYYMMDD.log`
- **Combination**: `logfile-YYYYMMDD-001.log`

**Benefits:**
- Easy chronological sorting.
- Facilitates batch operations.

### Retention Policies

Retention policies govern how long logs should be kept.

Factors Influencing Retention Policies:
- **Compliance Requirements**
- **Business Needs**
- **Storage Constraints**

**Sample Retention Strategy:**

**Pseudocode:**
```pseudocode
for each log in log_directory do
    if get_file_age(log) <= 30_days then
        keep(log)
    else if get_file_age(log) > 30_days and get_file_age(log) <= 1_year then
        archive_and_compress(log)
    else
        delete(log)
    end if
end for
```

### Tools and Techniques

While implementation specifics vary, common patterns include:

- **Manual Scripting**
- **System Tools**
- **Centralized Logging Systems**

### Advanced Concepts

#### a. Dynamic Log Rotation Thresholds

Adjust size thresholds based on disk utilization dynamically.

**Pseudocode:**
```pseudocode
if get_disk_usage() > 80% then
    set_log_rotation_size(50MB)
else
    set_log_rotation_size(100MB)
end if
```

#### b. Cross-System Log Management

For distributed systems:
- Synchronize logs centrally.
- Ensure clock synchronization across systems.

#### c. Transactional Log Rotation

Ensure no log entries are lost during rotation:
- Lock file access during rotation.
- Flush and close file descriptors properly before renaming.

---

# Appenders

- **Definition**: An appender is a component responsible for delivering log messages to their final destination.
- **Key Role**: It separates the concerns of *what* is logged from *where* and *how* it is stored or displayed.

## Common Appender Destinations
- **Console**: Outputs logs to a command-line interface or system terminal.
- **File**: Writes logs into a file (optionally with rotation or rolling mechanisms).
- **Database**: Stores logs into a relational or NoSQL database.
- **Network**: Sends logs over the network to remote servers (e.g., via TCP/UDP protocols).
- **GUI Elements**: Displays logs in application user interfaces.

## Core Concepts Around Appenders

### Layouts/Formatters
- **Purpose**: Define the structure and style of the output log (e.g., JSON, plaintext, XML).
- **Example**:
```pseudocode
appender.layout = PatternLayout("[%timestamp%] %level%: %message%")
```

### Filters
- **Purpose**: Determine whether a log event should be handled by a particular appender.
- **Example**:
```pseudocode
appender.filter = LevelFilter(level = "ERROR", accept_on_match = true)
```

### Thresholds
- **Purpose**: Specify a minimum severity level for an appender.
- **Example**:
```pseudocode
appender.threshold = "WARN"
```

### Buffering
- **Purpose**: Batch multiple log events together to optimize performance.
- **Example**:
```pseudocode
appender.bufferSize = 100
```

## Appender Chaining
- **Concept**: Appenders can forward logs to other appenders.
- **Use Case**: A log could be simultaneously written to a file and sent to a remote server.

```pseudocode
mainAppender.attach(fileAppender)
mainAppender.attach(remoteServerAppender)
```

## Appender Configuration Lifecycle
- **Initialization**: Setting up destination, layout, filter, threshold.
- **Activation**: Appender starts processing incoming log events.
- **Shutdown**: Properly closing streams, sockets, or connections to avoid leaks.

```pseudocode
appender.initialize()
logger.addAppender(appender)
appender.shutdown()
```

## Advanced Topics

In this section, we dive deeper into sophisticated logging mechanisms that enhance performance and reliability in complex systems.

### Asynchronous Appenders

In many applications, logging operations can become a performance bottleneck, especially when writing to slow destinations like files, databases, or remote servers. **Asynchronous Appenders** solve this problem by **decoupling** the log-writing operations from the main application flow.

Instead of writing directly, log events are placed into a queue and processed by a separate thread.

#### Key Concepts
- **Non-blocking:** Logging does not pause the main application.
- **Queue:** A buffer that holds log events temporarily.
- **Worker Thread:** Processes events from the queue and sends them to the base appender.

#### Example Pseudocode
```pseudocode
# Configure an asynchronous appender
asyncAppender.baseAppender = fileAppender
asyncAppender.queueSize = 1000
asyncAppender.start()

# Logging flow
application.log("User logged in")
# Behind the scenes:
# -> Event is placed in asyncAppender queue
# -> Worker thread dequeues event and writes to fileAppender
```

#### Flow Diagram

```
[Application Log Event]
        |
        v
[Async Appender Queue] ---> [Worker Thread] ---> [Base Appender (e.g., File)]
```

#### Considerations
- **Queue Overflow:** If the queue fills up, you must decide whether to drop messages or block the application.
- **Shutdown Behavior:** Ensure all queued messages are flushed before shutdown.
- **Error Handling:** Handle failures in the background thread gracefully.

### Rolling Appenders

Applications often run continuously, leading to log files that can grow indefinitely. **Rolling Appenders** manage this by **rotating** log files based on size, time, or a combination of both.

This prevents excessive disk usage and keeps log files manageable.

#### Key Concepts
- **File Size Rolling:** Start a new file when the current file reaches a specified size.
- **Time-Based Rolling:** Create a new file at regular intervals (e.g., daily).
- **Backup Index:** Number of old log files to retain.

#### Example Pseudocode
```pseudocode
# Configure a rolling appender based on file size
rollingAppender.maxFileSize = "10MB"
rollingAppender.maxBackupIndex = 5
rollingAppender.fileName = "application.log"
rollingAppender.start()

# Logging flow
for i from 1 to 100000:
    application.log("Request processed: " + i)

# Behind the scenes:
# -> If application.log > 10MB:
#    -> Rename application.log to application.log.1
#    -> application.log.1 to application.log.2
#    -> Keep maximum 5 backups
```

#### Flow Diagram for Size-Based Rolling

```
[Write Log Entry]
        |
        v
[Check Current File Size]
        |
        |-- Size < 10MB --> [Append Entry to Current File]
        |
        |-- Size >= 10MB --> [Rotate Files]
                          |
                          |--> [Rename Files: application.log -> application.log.1, etc.]
                          |--> [Create New application.log]
```

#### Considerations
- **Disk Space Management:** Regular cleanup ensures you don't run out of disk space.
- **Naming Patterns:** Use timestamps for easier tracking in time-based rolling.
- **Concurrency:** Ensure thread-safe rolling if multiple threads log simultaneously.

### Summary Table

| Feature       | Asynchronous Appender            | Rolling Appender              |
|---------------|----------------------------------|-------------------------------|
| Purpose       | Decouple logging from app thread | Manage log file size/rotation |
| Key Mechanism | Queue + Worker Thread            | Size or Time Triggers         |
| Main Benefit  | Performance improvement          | Disk space control            |
| Key Challenge | Queue management                 | File naming and thread safety |

### Best Practices
- Use **asynchronous appenders** for high-performance systems.
- Combine **rolling appenders** with **asynchronous logging** for best results.
- Monitor and alert on queue saturation or disk space issues.
- Ensure graceful shutdown and error handling mechanisms.

---

## Wrap-up scenario

- **Scenario**: A web service processes incoming API requests.
- **Logging Actions**:
  - When a request is received (INFO level)
  - When a database lookup happens (DEBUG level)
  - When a warning condition occurs (WARN level)
  - When an error occurs (ERROR level)
- **Advanced Features**:
  - Use asynchronous logging so main thread is never blocked
  - Attach a Correlation ID to each request
  - Use structured logging (JSON format)
  - Log rotation (size-based)
  - Appenders:
    - ConsoleAppender (for development)
    - FileAppender (for production, with rotation)

````sql
INITIALIZE consoleAppender with JSONLayout
INITIALIZE fileAppender with JSONLayout and SizeBasedRotation (maxSize=10MB, maxBackups=5)
INITIALIZE asyncAppender with queueSize=1000
ATTACH consoleAppender and fileAppender to asyncAppender
ATTACH asyncAppender to mainLogger

LOOP for each incoming API request
IF request contains "Correlation-ID" header THEN
correlationId = request.getHeader("Correlation-ID")
ELSE
correlationId = generateUUID()
END IF

    SET loggingContext.correlationId = correlationId

    LOG INFO: "Received API request" with correlationId and request metadata

    TRY
        LOG DEBUG: "Querying database for user" with userId
        result = queryDatabase(request.userId)

        IF result is null THEN
            LOG WARN: "User not found in database" with userId
        ELSE
            LOG INFO: "Successfully processed request" with userId and result
        END IF

    CATCH exception
        LOG ERROR: "Exception while processing request" with error message and stacktrace
    END TRY

END LOOP

ON shutdown
FLUSH asyncAppender queue
SHUTDOWN all appenders
````

---

TODO

Security and Compliance
Redacting sensitive info (e.g., passwords, PII)

Logging for audit trails

Log tampering protection and immutability