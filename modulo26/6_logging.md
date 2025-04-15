# Logging

Logging is the process of recording information about a program's execution. Logs provide visibility into the inner workings of software systems by capturing key events, errors, transactions, and system behaviors in textual format, typically outputted to files, consoles, or centralized systems.

- **Troubleshooting and Debugging**: Quickly identify and diagnose issues.
- **Auditing and Compliance**: Maintain records for security and legal audits.
- **Performance Monitoring**: Detect bottlenecks and optimize performance.
- **Operational Awareness**: Understand system behavior during normal operations.
- **Proactive Issue Detection**: Catch and resolve minor issues before they escalate.
- **Historical Data Analysis**: Analyze trends, predict future problems, and inform decision-making.

In short, robust logging is the backbone of system observability, aiding in both reactive problem-solving and proactive system improvements.

## Logging, Monitoring, Tracing
| Aspect          | Logging                              | Monitoring                                 | Tracing                                      |
|-----------------|--------------------------------------|--------------------------------------------|----------------------------------------------|
| **Purpose**     | Record discrete events               | Measure system health                      | Track a single request's flow across systems |
| **Granularity** | Event-level                          | Metrics-level                              | Request-level                                |
| **Examples**    | Errors, warnings, status updates     | CPU usage, memory consumption, error rates | API call durations, database query times     |
| **Tools**       | Logstash, Fluentd, Loki              | Prometheus, Datadog, CloudWatch            | Jaeger, Zipkin, OpenTelemetry                |
| **Format**      | Unstructured or semi-structured text | Aggregated metrics, time-series data       | Detailed spans and traces                    |

While logging captures **"what happened"**, monitoring answers **"how are things overall"**, and tracing addresses **"how did this specific request perform"**.

## Log Messages
Human-readable (and sometimes machine-parsable) strings describing an event. Typically include a timestamp, log level, message text, and contextual data (e.g. user ID, session ID).

Example:
```
2025-04-11 12:00:00 INFO User 'jdoe' successfully logged in from IP 192.168.1.10
```

## Log Storage
Commonly stored in:

- Local disks
- Cloud storage buckets
- Centralized log management platforms

Best practices:
- Consistent naming conventions
- Include application name, environment, and date

## Log Levels
Differentiate the severity or importance of the events being logged. Helps filter noise vs. actionable events.

Effective logging requires knowing *what* to log and at *what severity*.

**TRACE:**

- **Purpose**: Extremely detailed information; fine-grained internal states.
- **Audience**: Developers.
- **When to Use**: Deep-dive diagnostics during local development or troubleshooting specific production issues.
- **Example**:
```
TRACE Entered method 'calculateDiscount', parameters: userId=123, cartId=456
```

**DEBUG:**

- **Purpose**: Useful information during development and debugging.
- **Audience**: Developers and DevOps teams.
- **When to Use**: Standard in development environments; can be toggled on in production for issue investigations.
- **Example**:
```
DEBUG Loading user preferences for userId=123
```

**INFO:**

- **Purpose**: General operational messages about high-level system flow.
- **Audience**: Operators, support teams.
- **When to Use**: Document normal but significant events (e.g. startup, shutdown, user login).
- **Example**:
```
INFO Order 789 processed successfully.
```

**WARN:**

- **Purpose**: Something unexpected happened, but the system is still functional.
- **Audience**: Developers, SREs.
- **When to Use**: Highlight recoverable failures or degraded states.
- **Example**:
```
WARN Disk space low (10% remaining) on volume '/var/log'
```

**ERROR:**

- **Purpose**: A significant problem occurred that should be addressed.
- **Audience**: Developers, Operations, Alerting Systems.
- **When to Use**: Exceptions that were caught but affected operations.
- **Example**:
```
ERROR Failed to connect to database: timeout after 5 retries.
```

**FATAL/CRITICAL:**

- **Purpose**: Severe errors causing system crashes or immediate failures.
- **Audience**: Everyone (immediate attention required).
- **When to Use**: Irrecoverable states.
- **Example**:
```
FATAL Kernel panic - not syncing: Attempted to kill init!
```

## Log Formatting
Logging is crucial for monitoring, debugging, and tracing applications. Good logging practices help reduce the time needed to troubleshoot issues and improve system observability. In this lesson, we'll cover:

- Plain Text vs Structured Logging
- Key components of a well-formatted log
- Using Correlation IDs and Request IDs for traceability

We will focus on **concepts** and **examples**, without assuming a specific language or framework.

**Plain Text Logging**

Plain text logs are simple human-readable strings, often with a predefined format.

Example:
```
[2025-04-11 14:00:00] INFO User login successful: username=JohnDoe
```

Advantages:
- Easy for humans to read.
- No special tooling needed.

Disadvantages:
- Hard to parse by machines.
- Difficult to query specific fields in centralized logging systems.

**Structured Logging**

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

Advantages:
- Easier machine parsing and analysis.
- Facilitates centralized logging and monitoring.

Disadvantages:
- Slightly more complex to implement.

## Log Entry
Regardless of format, every log entry should contain key information to ensure it's actionable, searchable, and useful for debugging. In modern distributed systems, this goes beyond just time and message—capturing execution context is equally important.

| Field       | Purpose                                                                 |
|-------------|-------------------------------------------------------------------------|
| Timestamp   | When the event occurred                                                 |
| Log Level   | Severity of the event (e.g., INFO, DEBUG, WARN, ERROR)                  |
| Message     | Description of what happened                                            |
| Context     | Metadata such as `userId`, `sessionId`, `ipAddress`, `correlationId`    |
| Class       | The class or module where the log originated                            |
| LOC         | Line of code where the log was emitted                                  |
| Thread ID   | Identifier for the thread handling the operation                        |

These additional fields (`class`, `LOC`, `threadId`) greatly improve traceability, especially in concurrent environments or during complex workflows.

**Plain Text Example**

```
[2025-04-11 14:05:00] [ERROR] [Thread-12] [UserService.java:87] Database connection failed: error=TimeoutException retryCount=3 userId=84213 correlationId=CORR-12345 requestId=REQ-44822
```

In this example:

- **Timestamp**: Indicates when the log was generated.
- **Log Level**: Shows this is an `ERROR` log.
- **Thread-12**: Identifies the thread handling the operation.
- **UserService.java:87**: Pinpoints the exact class and line where the error was logged.
- **Message**: Describes the error and includes helpful context such as `error type`, `retry count`, and user/session identifiers.
- **Correlation ID**: Links this event to a broader transaction across services.
- **Request ID**: Tracks the specific request within this service.

**Structured Log Example**

Structured logs allow for easier querying and filtering in log aggregation tools.

```json
{
  "timestamp": "2025-04-11T14:05:00Z",
  "level": "ERROR",
  "message": "Database connection failed",
  "error": "TimeoutException",
  "retryCount": 3,
  "userId": "84213",
  "correlation_id": "CORR-12345",
  "request_id": "REQ-44822",
  "service": "UserService",
  "class": "com.example.user.UserService",
  "line": 87,
  "thread_id": "Thread-12"
}
```

This structured format significantly enhances how logs are consumed and leveraged by automated systems. Because each field is clearly defined and machine-readable, it becomes much easier to:

- **Filter** logs by severity, service, user, or request identifiers.
- **Search** for specific errors or behaviors tied to a `correlation_id`.
- **Visualize** request paths, error rates, and system health in dashboards.
- **Trigger alerts** when specific conditions are met (e.g., repeated timeouts, high error rates, slow response times).

Structured logging is foundational for modern observability stacks, enabling seamless integration with platforms like:

- **Elasticsearch + Kibana (ELK Stack)** for search and visualization
- **Grafana Loki** for log aggregation and dashboards
- **Datadog**, **New Relic**, **Splunk**, or **CloudWatch** for unified monitoring and alerting

## Correlation IDs
In distributed or microservices architectures, tracing a request across multiple services is essential for debugging and monitoring. Correlation IDs make this possible.

- A unique identifier assigned to each request.
- Propagated across service boundaries.
- Enables full traceability of a request's journey through the system.

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

To generate a Correlation ID:
- Use a UUID generator.
- If a Correlation ID is already present (e.g. from an API Gateway), reuse it.
- Otherwise, generate a new one.

## Request IDs
Request IDs help uniquely identify individual operations or transactions within services, providing an additional layer of traceability.

- A distinct identifier for each incoming request.
- Useful for tracking retries, partial failures, and load balancing decisions.

In distributed or microservices architectures, tracing a request across multiple services is essential for debugging and monitoring.

Both **Request IDs** and **Correlation IDs** help enable traceability, but they serve different purposes.

- **Correlation ID**: A unique identifier assigned to a *logical transaction* that spans multiple services or requests. It ties together all activity related to a single user interaction or workflow.
- **Request ID**: A unique identifier for a *single request* to a service. Multiple Request IDs may exist under a single Correlation ID if the workflow spans multiple services.


| ID Type        | Scope                       | Purpose                                             |
|----------------|-----------------------------|-----------------------------------------------------|
| Correlation ID | Entire transaction/workflow | Tracks the full journey across multiple services    |
| Request ID     | Individual service request  | Tracks a single call within the broader transaction |


Consider a scenario where a user submits an API request to place an order. The request is handled by multiple services:

1. Frontend Service
2. Payment Service
3. Inventory Service

A **Correlation ID** is generated at the entry point and propagated to all downstream services. Each service generates its own **Request ID** to trace local processing.

The API Gateway receives the request and generates:

- `correlation_id`: `CORR-12345`
- `request_id`: `REQ-98765` (specific to the API layer)

```json
{
    "correlation_id": "CORR-12345",
    "request_id": "REQ-98765",
    "timestamp": "2025-04-11T10:00:00Z",
    "level": "INFO",
    "message": "Order received"
}
```

The request is forwarded to the Payment Service. A **new Request ID** is generated by the service, but the **same Correlation ID** is retained from the original request to ensure continuity across the workflow.

- `correlation_id`: `CORR-12345`
- `request_id`: `REQ-24680` (specific to the Payment Service)

```json
{
    "correlation_id": "CORR-12345",
    "request_id": "REQ-24680",
    "timestamp": "2025-04-11T10:01:00Z",
    "level": "INFO",
    "message": "Payment processing started"
}
```

After the payment is processed successfully, the workflow continues to the Inventory Service. As with other services, it generates a **new Request ID** while continuing to propagate the **same Correlation ID**.

- `correlation_id`: `CORR-12345`
- `request_id`: `REQ-13579` (specific to the Inventory Service)

```json
{
    "correlation_id": "CORR-12345",
    "request_id": "REQ-13579",
    "timestamp": "2025-04-11T10:02:00Z",
    "level": "INFO",
    "message": "Inventory check started"
}
```

Errors can occur at any stage of the request lifecycle. When they do, logging both the **Correlation ID** and the **Request ID** is critical for efficient debugging and root cause analysis.

For instance, if the Payment Service encounters an error:

```json
{
    "correlation_id": "CORR-12345",
    "request_id": "REQ-24680",
    "timestamp": "2025-04-11T10:03:00Z",
    "level": "ERROR",
    "message": "Payment processing failed due to insufficient funds"
}
```

This log entry allows teams to:

- **Use the `correlation_id`** to trace the entire lifecycle of the user's transaction across all services involved, starting from the frontend request, through payment processing, and into inventory checks.
- **Use the `request_id`** to focus on the specific operation that failed; in this case, the request handled by the Payment Service.

By combining both IDs in log entries, observability platforms or centralized log management tools (like ELK, Datadog, or Splunk) can reconstruct the full execution flow. This enables engineers to:

- Understand the context in which the failure occurred.
- Quickly identify

## Log Rotation
In any system, logs are crucial for auditing, debugging, and monitoring. However, without proper management, log files can grow uncontrollably, leading to disk space exhaustion, degraded performance, and difficulty in parsing relevant information.

**Log Rotation** refers to the practice of splitting logs into multiple files based on certain criteria (size or time) to manage their growth and ensure efficient storage and retrieval.

- Prevent disk exhaustion
- Maintain system performance
- Facilitate easier log analysis
- Comply with data retention policies

Strategies:
- **Size-based rotation**: Rotate when file reaches a maximum size.
- **Time-based rotation**: Rotate daily, weekly, etc.

As result, rotation can be triggered by size or time.

**Size-Based Rotation**

Rotate logs once they reach a predefined maximum size.

- Rotate a file once it exceeds 100 MB.
- Monitor log size periodically.
- On threshold breach, rename current log to a new file (e.g. `logfile.1`, `logfile.2`, etc.).
- Create a new empty log file to continue logging.

**Time-Based Rotation**

Rotate logs at regular time intervals, irrespective of their size.

- Rotate logs daily at midnight.
- Use system schedulers to trigger rotations.
- Timestamp log archives for clarity (e.g. `logfile-YYYYMMDD.log`).

## Log retention
Having a log retention policy means to keep logs for a predefined period, archive, or delete older logs.

To conserve storage, old logs must be either deleted or compressed after a retention period.

**Archiving** involves packaging rotated logs for storage, often with compression to save space.

- Use compression to reduce file size.
- Delete logs older than a certain number of days (e.g. 90 days).
- Compress logs after a certain period (e.g. compress logs older than 7 days).
- Archive frequently accessed logs separately.
- Maintain metadata for auditing.
- Encrypt archives if logs contain sensitive data.
- Generate checksums for integrity verification.
- Automate deletion/compression.
- Monitor disk space usage.
- Log the deletion/compression actions for auditing.
- Use proper naming conventions when archiving:
  - **Incremental Numbers**: `logfile.1`, `logfile.2`, etc.
  - **Timestamps**: `logfile-YYYYMMDD.log`
  - **Combination**: `logfile-YYYYMMDD-001.log`
- Proper naming conventions:
  - simplify log management
  - easy chronological sorting.
  - facilitates batch operations.

## Appenders
An appender is a component responsible for delivering log messages to their final destination.

It separates the concerns of *what* is logged from *where* and *how* it is stored or displayed.

Common Appender Destinations:

- **Console**: Outputs logs to a command-line interface or system terminal.
- **File**: Writes logs into a file (optionally with rotation or rolling mechanisms).
- **Database**: Stores logs into a relational or NoSQL database.
- **Network**: Sends logs over the network to remote servers (e.g. via TCP/UDP protocols).
- **GUI Elements**: Displays logs in application user interfaces.

**Rolling Appenders**

Applications often run continuously, leading to log files that can grow indefinitely.

Rolling Appenders manage this by **rotating** log files based on size, time, or a combination of both.

This prevents excessive disk usage and keeps log files manageable.

**Asynchronous Appenders**

In many applications, logging operations can become a performance bottleneck, especially when writing to slow destinations like files, databases, or remote servers.

Asynchronous Appenders solve this problem by **decoupling** the log-writing operations from the main application flow.

Instead of writing directly, **log events are placed into a queue and processed by a separate thread**.

- **Non-blocking:** Logging does not pause the main application.
- **Queue:** A buffer that holds log events temporarily.
- **Worker Thread:** Processes events from the queue and sends them to the base appender.

**Summary**

| Feature       | Asynchronous Appender            | Rolling Appender              |
|---------------|----------------------------------|-------------------------------|
| Purpose       | Decouple logging from app thread | Manage log file size/rotation |
| Key Mechanism | Queue + Worker Thread            | Size or Time Triggers         |
| Main Benefit  | Performance improvement          | Disk space control            |
| Key Challenge | Queue management                 | File naming and thread safety |

## Log Security
Logging is not only a tool for observability and debugging, but also a potential vector for information leakage, compliance violations, and malicious tampering. To maintain operational integrity and trust, logs must be treated as security-critical assets. This chapter outlines key security and compliance concerns related to logs, along with strategies to mitigate risks.

Logs often contain data that may fall under regulatory frameworks such as GDPR, HIPAA, or PCI-DSS. Ensuring compliance requires strict control over what is logged, how long logs are retained, who can access them, and how they are stored and transmitted. Security policies should treat logs as sensitive data, enforce access controls, and audit all interactions with log data.

Key concerns include access control, retention policies, auditability, and data locality and sovereignty. Access control involves restricting who can view, write, or modify logs. Retention policies define how long logs should be kept to comply with legal and operational requirements. Auditability ensures all access and changes to logs are themselves logged and auditable. Data locality and sovereignty require storing logs in jurisdictions aligned with legal obligations.

**Log sanitization** is an essential practice in maintaining security and compliance. Log sanitization refers to the process of removing or obfuscating sensitive data in logs to prevent it from being exposed. This includes ensuring that personally identifiable information (PII), passwords, authentication tokens, and any other sensitive data are not stored in plaintext in the logs.

Log sanitization is critical for several reasons. First, it helps organizations comply with regulations such as GDPR, HIPAA, and PCI-DSS, which mandate the protection of sensitive data. Second, it helps mitigate the security risks posed by malicious actors who may gain unauthorized access to logs. Lastly, it ensures user privacy is protected by preventing unnecessary exposure of sensitive personal or financial data.

There are several techniques for sanitizing logs.

One common method is **pattern-based** redaction, where sensitive data, such as email addresses, credit card numbers, and session tokens, are automatically identified and redacted using regular expressions or similar techniques. For example:

```plaintext
[2025-04-11T14:30:00] INFO User login attempt: email=[REDACTED], password=[REDACTED]
```

Another technique involves **allowlisting**, which ensures that only predefined, safe fields are logged. Any data that is not on the allowlist is either excluded or sanitized before it is logged. This approach helps limit the risk of inadvertently exposing sensitive information. For example, in a login event, you might choose to log the user ID and timestamp, but exclude the user's email or IP address:

```json
{
  "timestamp": "2025-04-11T14:30:00Z",
  "level": "INFO",
  "userId": "user_12345"
}
```

This ensures that only non-sensitive information is stored in the logs, minimizing the exposure of PII or other confidential data. The allowlist approach works best when the specific data needed for analysis is well-defined and limited to non-sensitive identifiers.

**Anonymization** is another important technique. This method replaces personally identifiable information with non-reversible identifiers, such as hashed values or random tokens, when that information is not needed for further processing. For example, instead of logging a user's actual name or email address, you could log a hashed version of the user ID, ensuring that the data can still be analyzed without exposing any sensitive information. Here's an example:

```json
{
  "timestamp": "2025-04-13T14:20:44Z",
  "event": "user_login",
  "userId": "b8eaa9f1b84f8d2c9f2a16bce639b33f"
}
```

In this example, the user’s actual name or email address is replaced with a hashed identifier, ensuring that even if the logs are accessed, no personally identifiable information (PII) is exposed.

**Tokenization** is another method used to protect sensitive data in logs. It replaces sensitive information with a non-sensitive token, which can only be mapped back to the original data in a secure, isolated environment. For instance, instead of logging a full credit card number, you could store a token that represents it, like this:

```json
{
  "timestamp": "2025-04-13T14:22:01Z",
  "event": "payment_processed",
  "userId": "user_48927",
  "paymentToken": "txn_4f8c8b84767b4a8b9d19ac8bcba7d774"
}
```

In this example, the token `txn_4f8c8b84767b4a8b9d19ac8bcba7d774` is stored in place of sensitive payment data, ensuring that the real payment details are not exposed in the logs. Only authorized systems with access to the tokenization mapping can retrieve the actual payment data.

Another technique for protecting sensitive information in logs is **masking**, where only part of the sensitive data is exposed, and the rest is replaced with characters such as `x` or `*`. This method is especially useful when only a small portion of the data is necessary for reference, such as the last few digits of a credit card number or a social security number. For example:

```json
{
  "timestamp": "2025-04-13T14:26:00Z",
  "event": "payment_failed",
  "userId": "user_48927",
  "cardNumber": "xxxx-xxxx-xxxx-1234"
}
```

In this example, only the last four digits of the credit card number are exposed in the logs, while the rest is masked. This ensures that sensitive data like full card numbers are not visible, minimizing the risk of data breaches.

One additional method for securing sensitive information is **log redaction**. This involves removing or completely omitting sensitive data from logs before it is written or transmitted. For instance, if a log contains a user’s address or phone number, it can be redacted to prevent exposure.

```json
{
  "timestamp": "2025-04-13T14:30:00Z",
  "event": "user_profile_update",
  "userId": "user_48291",
  "address": "[REDACTED]",
  "phone": "[REDACTED]"
}
```

In this example, both the user's address and phone number are **redacted**, ensuring that sensitive data is excluded from the logs. **Redaction** is particularly useful when sensitive information should never be logged in any form, regardless of the context.

It’s important to note that **redaction**, like other sanitization techniques, should be applied **before** logs are stored or transmitted. This means that the sensitive data is **removed or masked early** in the logging process, ensuring that even if logs are intercepted or accessed by unauthorized users, no sensitive information is exposed.

Finally, **secure storage and transmission** of logs are critical aspects of log security. Even when logs are properly sanitized, storing them in an insecure manner or transmitting them over unsecured channels can still lead to data breaches. Logs should be **encrypted both in transit and at rest**, ensuring that sensitive information remains protected from unauthorized access during storage and transfer.

In summary, by applying techniques such as **tokenization**, **masking**, **allowlisting**, **anonymization**, **redaction**, and **encryption**, organizations can significantly reduce the risk of exposing sensitive information through logs. It's essential to align these practices with **regulatory compliance requirements** and **industry best practices** to maintain both security and privacy.

## Log tampering
Logs are only as trustworthy as their resistance to tampering. Whether for internal investigation or legal evidence, it must be possible to prove that logs have not been altered.

**Immutability** ensures that once a log entry is created, it cannot be changed or deleted without detection.

- **Write-once, read-many (WORM)**: Use systems that physically or logically prevent modification after write.
- **Cryptographic integrity**: Use hashing and chain-linking techniques to detect tampering.
- **Centralized aggregation**: Collect logs in secure, centralized systems to reduce the attack surface.
- **Append-only semantics**: Ensure logging systems support only additive operations.

> ✅ Example (Tamper-evident Log Format):
> ```
> 2025-04-13T15:00:21Z INFO event=transaction_success, txn_id=3381, amount=120.00, hash=ae1f...
> ```

> 🛑 A missing or inconsistent hash here would raise red flags in integrity checks.

Logs that can be rewritten or silently deleted lose their value for audits, investigations, and post-mortem analysis. Immutability ensures logs can be trusted.

## Logging for Audit Trails
Audit trails are a specialized use of logging focused on traceability, accountability, and compliance.

Unlike diagnostic logs, audit logs are not about understanding how the system works. They are about recording *who did what, when, and why*. Effective audit logging ensures that all significant actions are traceable, verifiable, and tamper-evident.

Audit logs support operational integrity, security investigations, and compliance enforcement. They are essential for answering critical questions during incidents, such as:

- Who accessed or modified sensitive data?
- What actions were performed on protected resources?
- When did key changes occur in the system?
- Were access policies or roles altered?

Audit trails must be accurate, consistent, and written with the assumption they may be used in legal, regulatory, or disciplinary contexts.

> ✅ Example (Clear Audit Event):
> ```
> 2025-04-13T16:03:12Z AUDIT user_id=20417 action=role_update target_user_id=817 role=admin performed_by=system_admin_003
> ```

This log clearly records a sensitive action (privilege escalation), who performed it, on whom, and when.

Not all logs qualify as audit logs. Audit-quality logs must meet strict criteria:

- **Completeness**: Capture all critical events relevant to security, compliance, and governance.
- **Consistency**: Use a structured and predictable format for easy parsing and long-term analysis.
- **Traceability**: Include immutable identifiers like user IDs, session tokens, or request IDs, not just names or emails.
- **Timestamp accuracy**: Use synchronized, high-resolution timestamps (preferably in UTC) to correlate events across systems.

> ❌ Example (Weak Audit Entry):
> ```
> 2025-04-13T16:10:01Z INFO User changed something in the system
> ```

> ✅ Example (Detailed and Auditable):
> ```
> 2025-04-13T16:10:01Z AUDIT user_id=728 event=updated_settings section=billing threshold=5000 ip=203.0.113.99
> ```

Clarity, context, and specificity are what elevate a log to an audit record.

Not everything needs to be audit-logged. Focus on recording **security-relevant** and **policy-relevant** activities, such as:

- User authentication attempts and outcomes
- Permission or role changes
- Access to sensitive resources
- Configuration changes
- System-level commands or escalations
- Data exports or deletions

> ✅ Example (Data Access):
> ```
> 2025-04-13T16:15:45Z AUDIT user_id=3824 accessed document_id=9917 classification=confidential action=view
> ```

> ✅ Example (Authentication Event):
> ```
> 2025-04-13T16:16:33Z AUDIT login_attempt user_id=3824 result=success method=2fa ip=198.51.100.25
> ```

Audit logs must be stored securely for a defined period based on legal and operational needs. Retention is not just about storage; it’s about **preserving trust** in the log’s validity over time.

Audit logging is not about verbosity. Rather, it’s about **precision**. Well-crafted audit logs are your first and last line of accountability. They prove what happened, how, and by whom. If you can't trust your audit trail, you can't trust your system.

## Wrap-up scenario
A web service processes incoming API requests and logs various internal and external events. Logging is not only for observability and debugging; it also supports security auditing, compliance, and post-incident investigations.

- **Actions:**
  - Log when a request is received at the **INFO** level, including a correlation ID and sanitized request metadata.
  - Log internal operations like database lookups at the **DEBUG** level (only in safe environments), making sure sensitive data is never exposed.
  - Log unexpected but recoverable conditions at the **WARN** level, with redacted contextual information.
  - Log application or system-level failures at the **ERROR** level, preserving full stack trace (if appropriate) and correlation ID.
  - Log critical security and access-related events (e.g. login attempts, permission changes, data access) at a special **AUDIT** level with immutable identifiers.

- **Appenders:**
  - `ConsoleAppender`: Used only in development and testing environments, with redacted output.
  - `RollingFileAppender`: Used in production, writing to immutable or append-only files.
  - (Optional) `AuditAppender`: Writes audit-level events to a hardened storage system (e.g. external WORM volume or signed blob store).

- **Features:**
  - **Asynchronous Logging**: All logs are written using non-blocking appenders to prevent performance impact on request processing.
  - **Structured Logging**: All logs are in JSON format for compatibility with log aggregation and SIEM systems.
  - **Correlation IDs**: Every request gets a unique correlation ID to enable traceability across systems and log levels.
  - **Log Rotation**: Implemented using a rolling file strategy, with rotation based on file size and retention policy defined per log type (e.g. audit logs retained longer than debug logs).

- **Security:**
  - Redact or anonymize any sensitive information before it is logged (e.g. passwords, PII, tokens).
  - Implement access control to restrict who can read or modify logs.
  - Ensure audit logs are written in a tamper-evident format (e.g. with hash chaining or write-once storage).
  - Only store logs in jurisdictions and storage systems compliant with applicable legal regulations (e.g. GDPR, HIPAA).
  - Ensure all log data is timestamped in UTC using a reliable clock source.

- **Separation Strategy**:
  - **Operational Logs** (INFO, DEBUG, WARN, ERROR): Used for monitoring and diagnostics. Rotation and retention tuned for performance and volume.
  - **Audit Logs** (AUDIT): Stored separately, in tamper-resistant form, with longer retention and tighter access controls.

## Best Practices
- **Consistency**: Follow a standard log format across services to maintain structure and readability.

- **Structured Logging**: Use JSON or key-value pairs for better machine parsing and ease of integration with log aggregation and SIEM systems.

- **Contextual Information**: Always include essential context, such as user IDs, correlation IDs, environment tags, and session identifiers, to trace logs back to specific requests or users.

- **Correlation IDs**: Attach unique request and trace identifiers to logs to link events across different services, facilitating better debugging and forensic analysis.

- **Log Level Application**:
  - **DEBUG**: For development and troubleshooting purposes.
  - **INFO**: For normal operational events and significant system interactions.
  - **WARN**: For recoverable or unexpected conditions that should be monitored.
  - **ERROR**: For critical failures that require immediate attention.

- **Avoid Overlogging**: Don't log unnecessary details that could clutter log files or degrade performance in production environments. Log only what is essential for traceability.

- **Sensitive Data Hygiene**: Never log sensitive information such as passwords, authentication tokens, personally identifiable information (PII), or credit card numbers. Use sanitization and allowlisting techniques.

- **Audit-Specific Logging**: Separate audit logs from operational logs, ensuring all actions related to security (e.g., user access, role changes, data modifications) are logged with clear identifiers, timestamps, and relevant context (who, what, when, where).

- **Integrity Verification**: Apply cryptographic techniques like hashing, signing, or chain-linking logs to detect tampering and maintain the integrity of audit logs. This ensures logs are tamper-evident and trustworthy.

- **Retention Policy Enforcement**: Implement retention policies that apply different durations for different log types. For example, audit logs should be retained longer for compliance, while debug logs can be rotated or discarded more frequently.

- **Immutable Storage Enforcement**: Ensure critical logs (especially audit logs) are stored in immutable or append-only mediums to prevent unauthorized modifications or deletions.

- **Log Classification**: Classify logs by their purpose, such as `AUDIT`, `SECURITY`, and `OPERATIONS`. This helps to apply different storage, processing, and access control policies to each category of logs.

- **Audit Log Access Logging**: Record and monitor all access to audit logs, creating a secondary audit trail to ensure that any interaction with sensitive logs is also accountable.

- **Timezone Alignment**: Ensure all logs use synchronized time, preferably in **UTC**, and are timestamped using ISO 8601 format to maintain consistency and facilitate correlation across distributed systems.

- **Legal and Jurisdictional Awareness**: Store logs in compliance with local and international data residency and privacy laws. For example, avoid storing EU-based PII in non-EU regions to comply with GDPR.

- **Log Format Stability**: Ensure that the format of audit logs remains backward compatible to maintain readability and consistency over time, even as systems evolve.

- **Asynchronous Logging**: Use non-blocking appenders for high-throughput systems to ensure that log writing does not impact application performance or response times.

- **Rolling File Appenders**: Combine rolling file appenders with asynchronous logging to efficiently manage disk space and prevent logs from growing uncontrollably.

- **Access Control**: Enforce strict role-based access control (RBAC) to limit who can read or modify logs. All interactions with sensitive logs should be logged and auditable.

- **Alerting and Monitoring**: Integrate logs with monitoring systems to trigger alerts based on log events such as errors, high severity warnings, or unauthorized access attempts.

- **Redundancy and Backup**: Replicate logs across systems or regions to avoid data loss. Use redundant storage solutions to ensure logs remain available even in the event of failures.

- **Log Aggregation**: Centralize logs from multiple systems for easier querying, analysis, and correlation. This enables faster identification of issues across distributed architectures.

- **Compliance Tagging**: Include metadata tags in logs to indicate sensitive or regulated data (e.g., `confidential`, `regulated`) to support compliance requirements and avoid inadvertent exposure.

- **Graceful Shutdown and Error Handling**: Ensure logs are not lost during application shutdowns, crashes, or unexpected restarts by flushing logs safely and handling errors appropriately.

- **Long-Term Readability**: Choose log formats and storage solutions that ensure long-term readability and compatibility, even as your system evolves or is audited years later.
