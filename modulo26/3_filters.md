### Spring Filters
A **filter** is an object that performs filtering tasks on either the **request** or the **response** within an application such as a Web Service. 

Recall that a **servlet** is a Java class that handles HTTP requests and responses in a web application. It runs on a web server, typically an application server like Apache Tomcat. Servlets are used to extend the capabilities of a server by providing dynamic content.

Filters are part of the servlet container and allow you to manipulate the request and/or response before it reaches the targeted servlet or after the servlet has processed it.

In Spring, filters are commonly used to process requests or responses in a way that is **independent of the business logic**. Filters sit in between the client and the servlet (or controller), intercepting and potentially modifying the request and/or response before the request reaches the servlet or after the servlet generates the response.

#### Definition, Chaining, Registration
The implementation of a filter is a **three-step** process:

1. **Definition**: In this stage, the filter is created by implementing the `jakarta.servlet.Filter` interface or by extending an abstract class like `GenericFilter`. The filter class contains the logic that defines how incoming requests and outgoing responses should be processed, typically by overriding methods like `doFilter()`.
2. **Chaining**: As part of the definition, the **filter chain** comes into play. A filter chain allows multiple filters to be executed in a specific order. When the `doFilter()` method is called, the filter can either continue processing the request by calling `chain.doFilter(request, response)` or halt the chain. This chaining mechanism ensures that each filter in the chain can perform its task before passing the request to the next filter or the target resource (like a servlet or controller).

   The `doFilter()` method is executed for both **requests** and **responses**, depending on where the code is placed inside the method:

   - **Before** calling `chain.doFilter(request, response)`, the filter executes for **incoming requests** (pre-processing). This is where you can inspect or modify the request before it reaches the target resource.

   - **After** calling `chain.doFilter(request, response)`, the filter executes for **outgoing responses** (post-processing). This is where you can modify or inspect the response after it has been processed by the target resource or subsequent filters.

   This dual processing mechanism allows for powerful and flexible request and response handling at different stages of the request lifecycle.
3. **Registration**: Once defined, the filter must be registered with the web application so that it can be invoked during the request-response cycle. This registration can be done in different ways.

#### Lifecycle
As soon as a request is made to the application, a **request-response pair** is created and associated throughout the entire lifecycle of that request.

Filters can **intercept** and **modify** both **requests** and **responses** at various stages of the HTTP request lifecycle:

- **Request Interception**: Filters can alter, validate, or inspect the incoming request **before** it reaches the servlet or controller.
- **Response Interception**: Filters can manipulate or enrich the outgoing response **after** the controller has processed the request.

The **filter flow** is **bidirectional**.

First it moves **forward** (request), reaching the controller, then **unwinds back** (response), returning through the filters.

- **Request Handling Phase**
  - When an HTTP request arrives, the **servlet container** immediately creates:
     - a **request object** (populated with incoming request data),
     - a **response object** (empty initially, but ready to be filled).
  - The **request and response** objects are passed through the **filter chain**.
  - Each filter gets a chance to **intercept, validate, or modify** the incoming request **before** it hits the controller.

- Execution Inside Filters
  - Filters operate through the `doFilter(request, response, chain)` method.

- Thus:
    - **Before** calling `chain.doFilter(...)` → filters handle the **request phase**.
    - **After** calling `chain.doFilter(...)` → filters handle the **response phase**.

- **Forward flow**: request is pushed through the filters toward the target controller.
- **Backward flow**: after the controller processes the request, the response is sent back through the filters in reverse order.

| **Phase**                                      | **Inside doFilter**                                                |
|:-----------------------------------------------|:-------------------------------------------------------------------|
| **Before** `chain.doFilter(request, response)` | Code runs for incoming requests (**pre-processing**)               |
| **After** `chain.doFilter(request, response)`  | Code runs for outgoing responses (**post-processing**)             |
| **Before** `chain.doFilter(request, response)` | During **request flow** (before reaching controller)               |
| **After** `chain.doFilter(request, response)`  | During **response flow** (after controller has generated response) |

So the flow timeline looks like this:

```plaintext
Incoming HTTP Request
    ↓
[Filter A] (before chain.doFilter)
    ↓
[Filter B] (before chain.doFilter)
    ↓
[Controller] (business logic, populates response)
    ↑
[Filter B] (after chain.doFilter)
    ↑
[Filter A] (after chain.doFilter)
    ↑
Outgoing HTTP Response
```

Summing up:

- The request **travels down** the filter chain.
- After reaching the controller, **the response travels back up** through the chain.
- **Each filter regains control** *after* `chain.doFilter()` returns, allowing post-processing on the response.
- The **response object exists from the beginning** of the request, even before it has any content. Filters can set headers or status codes early if needed.
- **The body** (payload) of the response is typically **populated by the controller**.
- **Post-processing** filters (code after `chain.doFilter`) can:
   - Inspect response headers and status codes.
   - Log the outcome (e.g., response time, success/failure).
   - Modify the response if necessary (add CORS headers, security tokens, etc.).
- **Control Flow**: Once `chain.doFilter` is called, the flow **"dives" into deeper filters or the controller**. Once the controller has finished, the flow **"unwinds" back** through the filters.

| **Stage**                                        | **Action**                                         |
|:-------------------------------------------------|:---------------------------------------------------|
| Request enters                                   | Request object and empty Response object created.  |
| Pre-Processing (Filters before `chain.doFilter`) | Request inspection, validation, modification.      |
| Controller processing                            | Request is handled, response body generated.       |
| Post-Processing (Filters after `chain.doFilter`) | Response inspection, logging, header modification. |
| Response exits                                   | Response sent back to client.                      |

A quick code example of a filter definition and chaining:

```java
@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

    // Request phase: pre-processing
    System.out.println("🔸 Before chain.doFilter - Request incoming");

    // Passing control to the next filter or controller
    chain.doFilter(request, response);

    // Response phase: post-processing
    System.out.println("After chain.doFilter - Response outgoing");
}
````

Output Order:

```
🔸 Before chain.doFilter - Request incoming
(Controller runs)
🔸 After chain.doFilter - Response outgoing
```

#### Use Cases

Filters are often used to perform specific tasks at different stages of the request-response lifecycle. These tasks can be categorized into actions taken **before** and **after** the call to `chain.doFilter(request, response)`.

- **Before `chain.doFilter(request, response)`**:
   - **Authentication / Authorization Checks**: A filter can intercept incoming requests to verify the presence of an authentication token (e.g., JWT in the headers). It can then validate the token, check the user's roles or permissions, and allow or deny access to the requested resource based on the token's validity.
   - **Logging Request Details**: Filters can log essential details about the incoming request such as the HTTP method, requested URL, headers, query parameters, and even the body. This is useful for debugging, monitoring, or auditing requests.
   - **Modifying Headers or Body of Incoming Requests**: Filters can modify or enrich incoming requests by adding headers (e.g., security headers), or even transforming the request body if necessary (e.g., sanitizing input or enriching the payload).

- **After `chain.doFilter(request, response)`**:
   - **Logging Response Details**: After the request has been processed by the target resource and subsequent filters, a filter can log details about the outgoing response, such as the response status code, headers, and body content. This is essential for debugging or performance monitoring.
   - **Adding Security Headers to the Outgoing Response**: Filters can add important security-related headers to the response, such as CORS (Cross-Origin Resource Sharing) or CSP (Content Security Policy), which enhance the security posture of the application.
   - **Auditing Outgoing Data**: Filters can be used to audit outgoing data for compliance or security reasons, such as checking for sensitive information before the response is sent to the client or logging the data for analysis.

These use cases demonstrate how filters can play a crucial role in various stages of request and response handling, helping manage authentication, logging, security, and data auditing in a flexible and modular way.

#### Filter Chain
A **filter chain** is a sequence of filters that process a request and/or response in the order they are defined. Each filter in the chain can:

1. Perform its own task (e.g. authentication, logging, etc.).
2. Decide whether to pass control to the next filter in the chain.
3. Optionally modify the request or response.

The **Filter Chain** is an essential part of Spring’s filter mechanism. The filter chain determines the order in which filters are executed and ensures that the request and response are passed from one filter to another, ultimately reaching the servlet or controller.

#### jakarta.servlet.Filter
In Spring Boot, a filter is an object that implements the `jakarta.servlet.Filter` interface.

The most generic approach is to directly implement the `jakarta.servlet.Filter` interface, overriding its methods:

````java
package jakarta.servlet;

public interface Filter {
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
        throws IOException, ServletException;

    void init(FilterConfig filterConfig) throws ServletException;
    void destroy();
}
````

The `jakarta.servlet.Filter` interface has the three key methods above.

If a filter is correctly **defined, registered and chained**, its three methods (`init()`, `doFilter()`, and `destroy()`) will be called automatically by the servlet container at the appropriate points in the lifecycle. Here's how each method fits into the lifecycle:

1. **`init(FilterConfig filterConfig)`**:
    - This method is called once when the filter is created. It’s typically used for initialization tasks, like reading configuration parameters, setting up resources, or preparing anything that the filter needs to operate. The `FilterConfig` provides access to the filter's initialization parameters and the `ServletContext` for the filter.

2. **`doFilter(ServletRequest request, ServletResponse response, FilterChain chain)`**:
   - This is the main method that intercepts the request and response. You can modify the request and response, perform logging, check authentication, manipulate headers, or perform any other tasks before passing control along the chain to the next filter or servlet.
   - **Request-Response Pair**: As soon as a request is made to the application, a **request-response pair** is created and associated throughout the entire request lifecycle. This pair represents the HTTP request (from the client) and the response (that will be sent back to the client). In the context of filters:
      - The `request` object represents the HTTP request received from the client.
      - The `response` object represents the HTTP response that will be returned to the client.
   - You **must** call `chain.doFilter(request, response)` to pass the request and response to the next filter or servlet in the chain. If you don't call this method, the request/response will be stuck in the filter, and no further processing will occur. It’s crucial to allow the request to continue through the chain to complete the processing and return the response.
   - If you don't call `chain.doFilter(request, response)` in any filter, whether it's the last one or not:
      - The **request will not proceed** to the next filter or to the controller (or servlet).
      - The **response will not be generated** or sent back to the client.
      - The **request will be blocked** in the current filter.
      - Any **subsequent filters, including the controller or servlet, will not be reached**.
      - The **response will not be processed** either, leaving the client without a response.
   - The `doFilter` method provides `ServletRequest` and `ServletResponse` objects, which are generic types. In many cases, you'll need more specific functionality, such as working with HTTP-specific headers, request parameters, or status codes. This is where **casting** becomes useful:
      - **`HttpServletRequest`**: By casting the generic `ServletRequest` to an `HttpServletRequest`, you can access HTTP-specific features like headers, cookies, parameters, and methods such as `getMethod()` or `getRequestURI()`.
      - **`HttpServletResponse`**: Similarly, casting the generic `ServletResponse` to an `HttpServletResponse` allows you to interact with HTTP-specific features like status codes, headers, and the response body.
   - **Unwinding During Response**: After the request has been passed down the filter chain and the target resource (like a servlet or controller) has generated a response, the filters will **unwind** in reverse order. This means that the filters that originally processed the request will now handle the response. This is the **post-processing phase**, where you can modify the response, log details, or add additional headers before sending it back to the client. For example:
      - **Logging Response Details**: Filters can log the response status, headers, or even the content of the response.
      - **Adding Headers**: Security-related headers, such as CORS or Content-Security-Policy (CSP), can be added during the response phase.
      - **Modifying the Response**: Filters can make changes to the response body, such as compression or encryption, before it is sent back to the client.
   - **Control Flow Returns After `chain.doFilter(request, response)`**: It's important to note that once `chain.doFilter(request, response)` is called, the control flow passes to the next filter or the target resource. After that, the filter method returns to each filter in reverse order. This means that after the request has been processed and the response is being sent back, control returns to each filter in the reverse order, allowing them to perform tasks such as logging response details, adding headers, or auditing the data. This gives filters an opportunity to modify the response before it reaches the client.

   This **unwinding process** ensures that each filter gets a chance to handle the response after the request has been processed, providing a powerful mechanism for both pre- and post-processing tasks.

3. **`destroy()`**:
    - This method is called when the filter is destroyed, typically when the servlet container shuts down or when the filter is no longer needed. You can use this method for cleanup tasks, such as releasing resources (e.g. closing database connections or cleaning up thread pools). The container automatically calls this method before destroying the filter instance.

#### OncePerRequestFilter, GenericFilterBean
You can define a filter either by directly implementing the `jakarta.servlet.Filter` interface or by extending an abstract class like `GenericFilter`, which is still part of the `jakarta.servlet.Filter` ecosystem.

These abstract classes simply offer default implementations of some of the methods from the `Filter` interface, allowing you to focus on overriding just the `doFilter()` method if no other customization is needed.

Available Abstract Classes for Filter Implementation:

1. **`OncePerRequestFilter`**:
    - This class ensures that the filter is executed only once per request, even if the request undergoes multiple forwarding or dispatching during its lifecycle.
    - In traditional servlet-based applications, requests can be forwarded from one resource to another (e.g. from one servlet to another or from a controller to a view). This means that a single HTTP request may pass through multiple resources, and without proper handling, a filter might execute multiple times during these forwards.
    - **Forwarding**: When forwarding occurs, the same HTTP request is internally routed to a different resource without the client being aware. However, this can cause filters to be executed repeatedly if the forwarding process isn't managed carefully. For example, if a filter is responsible for tasks like logging, authentication, or setting headers, it could execute multiple times across different forwards, resulting in duplicate log entries, unnecessary re-authentication, or redundant changes to the response.
    - **How `OncePerRequestFilter` Avoids Multiple Executions**: The `OncePerRequestFilter` class is specifically designed to prevent filters from running multiple times during the same request, even when forwarding or dispatching occurs. It achieves this by ensuring that the filter's actions are only performed once during the entire request lifecycle. The filter tracks whether it has already been executed for the current request and skips further executions if it has.
    - This is especially important when filters are involved in critical tasks like session validation, logging, or resource cleanup. Without this safeguard, a filter could cause unintended side effects if executed multiple times, potentially leading to errors or performance issues.
    - **Benefits**:
        - **Simplicity**: The `OncePerRequestFilter` reduces the complexity of filter management by handling forwarding and dispatching transparently, without requiring developers to manually track filter executions.
        - **Efficiency**: It ensures that the filter only performs necessary tasks once, preventing redundant actions and improving the overall efficiency of the web application.
        - **Consistency**: By guaranteeing that the filter runs only once, it avoids issues like double logging or reapplying filters that could cause inconsistencies in behavior.

    - **Example Use Case**: Imagine a filter that logs request details for monitoring purposes. Without `OncePerRequestFilter`, this logging could be duplicated if the request is forwarded to several resources (like different servlets or views). By using `OncePerRequestFilter`, the logging will happen only once for the entire request, regardless of how many forwards or dispatches occur.

2. **`GenericFilterBean`**:
    - This is a Spring-specific class that implements the `jakarta.servlet.Filter` interface and offers more Spring-friendly features.

#### Registration Methods
After defining and chaining a filter, you need to register it to make it part of the Spring Boot application context. Depending on how you define and register the filter, the filter may or may not be a Spring Bean. Below are the main methodologies for filter registration:

1. **Registering a Filter as a Bean in a `@Configuration` Class**:
    - **Filter Definition**: The filter is defined as a standard class, often implementing the `jakarta.servlet.Filter` interface or extending `OncePerRequestFilter`. It is not necessarily a Spring Bean at this point.
    - **Registration Flow**: The filter is **registered as a Spring Bean** within a `@Configuration` class, typically using the `@Bean` annotation. The registration is done through a `FilterRegistrationBean`, which allows you to configure details such as URL patterns, filter order, and other properties.
    - **Bean Creation**: The filter itself is **not a Spring Bean initially**, but it is instantiated and registered as a Spring Bean during the configuration process. The `FilterRegistrationBean` acts as a container that allows customization of the filter's behavior.
    - **Summary**: This approach gives you full control over the filter's lifecycle, and the filter is explicitly registered as a Spring Bean during the registration process.

````java
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FilterConfig {

    // Registering the filter as a Spring Bean within a @Configuration class
    @Bean
    public FilterRegistrationBean<CustomFilter> registrationBean() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        
        // Create an instance of CustomFilter and register it
        registrationBean.setFilter(new CustomFilter());
        
        // Specify URL patterns where the filter should apply
        registrationBean.addUrlPatterns("/api/*");

        // Set the filter order to 1, which means this filter has the highest priority 
        // and will be executed first in the filter chain. Filters with lower order values 
        // are given higher priority and are executed before those with higher order values.
        // This setting ensures that this filter runs early in the processing sequence, 
        // allowing it to handle requests or responses before any other filters with a higher 
        // order (i.e. values greater than 1) are applied.
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
````

````java
// Custom filter class implementing jakarta.servlet.Filter
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization logic (if needed)
        // This method runs once when the filter is initialized, and it's often used for setup tasks.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Phase 1: **Before chain.doFilter(request, response)** (Request Processing / Pre-processing)
        // This is where we can perform actions on the incoming request before it reaches the target resource (e.g., servlet).

        System.out.println("Processing incoming request...");  // Before passing the request along the chain

        // Custom logic for request processing (e.g., JWT validation, logging, security checks)
        // In this phase, you might check for an authentication token, validate input, or log details for auditing.

        // Pass the request and response along the filter chain
        // The control is passed to the next filter in the chain, or the target servlet/controller, if there are no more filters.
        chain.doFilter(request, response);

        // Phase 2: **After chain.doFilter(request, response)** (Response Processing / Post-processing)
        // This is where we can modify the response or perform additional actions after the target resource has processed the request.

        // Control flow returns here once the request has passed through the rest of the filter chain and the target resource.

        System.out.println("Processing outgoing response...");  // After the target resource has processed the request

        // Custom logic for response processing (e.g., adding headers, logging response status)
        // In this phase, we could add security headers to the response, log response details, or modify the response content.

    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
        // This method is called once when the filter is destroyed. It’s often used for resource cleanup (e.g., closing database connections).
    }
}
````

2. **Using `@WebFilter` Annotation with `@ServletComponentScan`**:
    - **Filter Definition**: The filter is defined by implementing the `jakarta.servlet.Filter` interface or extending `OncePerRequestFilter`, and it is annotated with the `@WebFilter` annotation.
    - **Registration Flow**: The filter **is not a Spring Bean**. Instead, the `@ServletComponentScan` annotation in the Spring Boot application automatically registers the filter with the servlet container.
    - **Bean Creation**: The filter definition itself is **not a Spring Bean**, but Spring Boot automatically detects and registers it via the servlet container without requiring a Spring Bean definition.
    - **Summary**: This is a more automated registration process where the filter is integrated into the servlet context without explicitly being a Spring Bean.

````java
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/api/*") // Define the URL pattern this filter applies to
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization logic (if needed)
        System.out.println("Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Phase 1: **Before chain.doFilter(request, response)** (Request Processing / Pre-processing)
        // This is where we perform actions on the incoming request before it reaches the target resource (e.g., servlet).

        System.out.println("Processing incoming request..."); // Before passing the request along the chain

        // Custom logic for request processing (e.g., JWT validation, logging, security checks)
        // In this phase, you might check for an authentication token, validate input, or log details for auditing.

        // Pass the request and response along the filter chain
        // The control is passed to the next filter in the chain, or the target servlet/controller, if there are no more filters.
        chain.doFilter(request, response);

        // Phase 2: **After chain.doFilter(request, response)** (Response Processing / Post-processing)
        // This is where we can modify the response or perform additional actions after the target resource has processed the request.

        // Control flow returns here once the request has passed through the rest of the filter chain and the target resource.

        System.out.println("Processing outgoing response..."); // After the target resource has processed the request

        // Custom logic for response processing (e.g., adding headers, logging response status)
        // In this phase, we could add security headers to the response, log response details, or modify the response content.
    }

    @Override
    public void destroy() {
        // Cleanup resources (if needed)
        System.out.println("Filter destroyed");
    }
}
````

````java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.servlet.annotation.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan  // This enables automatic scanning and registration of filters and servlets
public class MySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }
}
````

3. **Using `@Component`**:
    - **Filter Definition**: The filter is defined as a Spring Bean by annotating it with `@Component`, either implementing the `jakarta.servlet.Filter` interface or extending `OncePerRequestFilter`.
    - **Registration Flow**: The filter is **already a Spring Bean** due to the `@Component` annotation. The filter is automatically registered by Spring as part of the application context. You can customize the filter's registration by using a `FilterRegistrationBean`, where you can specify URL patterns, order, and other configurations.
    - **Bean Creation**: The filter definition is a Spring Bean right from the start because of the `@Component` annotation. It’s registered directly with Spring Boot during application startup.
    - **Summary**: This approach simplifies filter registration by letting Spring manage the filter as a bean while still allowing customization through `FilterRegistrationBean`.

````java
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  // This makes the filter a Spring Bean
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic (if needed)
        System.out.println("Custom Filter Initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Phase 1: **Before chain.doFilter(request, response)** (Request Processing / Pre-processing)
        // This is where we can perform actions on the incoming request before it reaches the target resource (e.g., servlet).

        System.out.println("Processing incoming request...");  // Before passing the request along the chain

        // Custom logic for request processing (e.g., JWT validation, logging, security checks)
        // In this phase, you might check for an authentication token, validate input, or log details for auditing.

        // Pass the request and response along the filter chain
        // The control is passed to the next filter in the chain, or the target servlet/controller, if there are no more filters.
        chain.doFilter(request, response);

        // Phase 2: **After chain.doFilter(request, response)** (Response Processing / Post-processing)
        // This is where we can modify the response or perform additional actions after the target resource has processed the request.

        // Control flow returns here once the request has passed through the rest of the filter chain and the target resource.

        System.out.println("Processing outgoing response...");  // After the target resource has processed the request

        // Custom logic for response processing (e.g., adding headers, logging response status)
        // In this phase, we could add security headers to the response, log response details, or modify the response content.
    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
        System.out.println("Custom Filter Destroyed");
    }
}
````

4. **Registering a Custom Filter with Spring Security Using `HttpSecurity`**:
    - **Filter Definition**: The filter is defined by implementing `jakarta.servlet.Filter` or extending `OncePerRequestFilter`, as a custom filter for Spring Security.
    - **Registration Flow**: The filter may or may not be a Spring Bean. If it is a Spring Bean, it can be injected directly into the Spring Security configuration. If it's not a Spring Bean, it can still be registered programmatically using `http.addFilterBefore()` or `http.addFilterAfter()` within the `HttpSecurity` configuration to integrate it into the security filter chain; in Spring Security, the **security filter chain** is a sequence of filters that intercept HTTP requests and apply security-related logic, such as authentication and authorization. Each filter in the chain performs a specific task, such as checking credentials or verifying permissions. The filters are applied in a specific order to process incoming requests and outgoing responses.
    - **Bean Creation**: The filter is **not automatically registered as a Spring Bean** unless explicitly defined as one (via `@Bean` or `@Component`). It is manually registered during Spring Security configuration.
    - **Summary**: This method integrates the filter into the Spring Security filter chain.

Here it follows an example where the filter is a bean at the definition level:

````java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomSecurityFilter customSecurityFilter;

    @Autowired
    // Injecting the CustomSecurityFilter because it is a Spring Bean
    public SecurityConfig(CustomSecurityFilter customSecurityFilter) {
        this.customSecurityFilter = customSecurityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/**").authenticated()  // Example: Secure endpoints
                .anyRequest().permitAll()               // Allow other requests
            .and()
            .addFilterBefore(customSecurityFilter, UsernamePasswordAuthenticationFilter.class);  // Register filter before Spring Security's default filter

        return http.build();
    }
}
````

````java
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

@Component  // Making the filter a Spring Bean
public class CustomSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Phase 1: **Before filterChain.doFilter(request, response)** (Request Processing / Pre-processing)
        // This is where we can perform actions on the incoming request before it reaches the target resource (e.g., servlet).

        System.out.println("Processing incoming request in Custom Security Filter...");  // Pre-processing the request

        // Custom logic for request processing (e.g., security checks, logging, validation)
        // In this phase, you might check for authentication tokens, validate user roles, or log incoming request details.

        // Continue with the filter chain, passing the request and response to the next filter or resource
        filterChain.doFilter(request, response);

        // Phase 2: **After filterChain.doFilter(request, response)** (Response Processing / Post-processing)
        // This is where we can perform actions after the request has been processed by the target resource (e.g., servlet).

        // Control flow returns here once the request has passed through the rest of the filter chain and the target resource.

        System.out.println("Processing outgoing response in Custom Security Filter...");  // Post-processing the response

        // Custom logic for response processing (e.g., adding security headers, logging response status)
        // In this phase, you could add security headers to the response or log details about the response before sending it to the client.
    }
}
````

Here it follows an example where the filter is **not** a bean at the definition level:

````java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.Filter;

@Configuration
public class SecurityConfig {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .authorizeRequests()
              .antMatchers("/api/**").authenticated()  // Example: Secure endpoints
              .anyRequest().permitAll()               // Allow other requests
              .and()
              .addFilterBefore(customSecurityFilter(), UsernamePasswordAuthenticationFilter.class);  // Register filter before Spring Security's default filter

      return http.build();
   }

   @Bean
   public Filter customSecurityFilter() {
      return new CustomSecurityFilter();  // Return the custom filter bean
   }
}
````

````java
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

public class CustomSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Phase 1: **Before filterChain.doFilter(request, response)** (Request Processing / Pre-processing)
        // This is where we can perform actions on the incoming request before it reaches the target resource (e.g., servlet).

        System.out.println("Processing incoming request in Custom Security Filter...");  // Pre-processing the request

        // Custom logic for request processing (e.g., security checks, logging, validation)
        // You could validate authentication tokens, check user roles, log the incoming request, or perform other security-related checks.

        // Continue with the filter chain, passing the request and response to the next filter or resource
        filterChain.doFilter(request, response);

        // Phase 2: **After filterChain.doFilter(request, response)** (Response Processing / Post-processing)
        // This is where we can perform actions after the request has been processed by the target resource (e.g., servlet).

        // Control flow returns here once the request has passed through the rest of the filter chain and the target resource.

        System.out.println("Processing outgoing response in Custom Security Filter...");  // Post-processing the response

        // Custom logic for response processing (e.g., adding security headers, logging response status)
        // You could add security headers, modify the response, log details about the response, or perform other post-processing actions.
    }
}
````

#### Summary
- **Method 1**: Register as a `@Bean` in a `@Configuration` class with `FilterRegistrationBean`. The filter is defined as a class and is **later registered as a Spring Bean** during the registration process.
- **Method 2**: Use `@WebFilter` annotation and `@ServletComponentScan`. The filter is **not a Spring Bean**, and Spring Boot automatically registers it with the servlet container.
- **Method 3**: Use `@Component` annotation. The filter is defined as a Spring Bean and automatically registered by Spring; you can customize the filter's registration by using a `FilterRegistrationBean`
- **Method 4**: Register the custom filter using `HttpSecurity` in Spring Security. The filter may or may not be a Spring Bean, but it is directly integrated into the security filter chain.

# Chain of Responsibility
In Java web applications, the `doFilter` method in the servlet filter API is a concrete implementation of the Chain of Responsibility pattern. Each filter in the chain can decide whether to process the request and response, or pass them along to the next filter. This allows for the dynamic composition of filters, where each filter is responsible for a specific task in the processing of the request and response.

The Chain of Responsibility is a behavioral design pattern that allows multiple objects to handle a request sequentially. Instead of sending a request directly to a handler, it is passed along a chain of potential handlers until one of them processes it.

## Analogy
Imagine a technical support system where a customer contacts support with an issue. The request first goes to a Level 1 technician. If they can resolve the issue, they do. Otherwise, they escalate it to Level 2. If Level 2 cannot handle it, they pass it to Level 3, and so on. This structured escalation mechanism ensures that the request is handled by the most suitable handler in the chain.

## When to Use Chain of Responsibility
- When multiple handlers could process a request, and the handler isn't determined until runtime.
- When decoupling the sender and receiver of a request is required.
- When implementing request processing pipelines (e.g. middleware in web frameworks).

## Pseudocode
```plaintext
class Handler {
    nextHandler: Handler
    
    method handleRequest(request):
        if canHandle(request):
            process(request)
        else if nextHandler != null:
            nextHandler.handleRequest(request)
}

// Usage
handler1 = new ConcreteHandler1()
handler2 = new ConcreteHandler2()
handler3 = new ConcreteHandler3()

handler1.setNext(handler2)
handler2.setNext(handler3)

handler1.handleRequest(request)
```

## Java Implementation
```java
abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handleRequest(String request) {
        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }
}

class Level1Support extends Handler {
    public void handleRequest(String request) {
        if (request.equals("basic issue")) {
            System.out.println("Level 1 resolved the issue.");
        } else {
            super.handleRequest(request);
        }
    }
}

class Level2Support extends Handler {
    public void handleRequest(String request) {
        if (request.equals("intermediate issue")) {
            System.out.println("Level 2 resolved the issue.");
        } else {
            super.handleRequest(request);
        }
    }
}

class Level3Support extends Handler {
    public void handleRequest(String request) {
        System.out.println("Level 3 handling the request.");
    }
}

public class ChainOfResponsibilityExample {
    public static void main(String[] args) {
        Handler level1 = new Level1Support();
        Handler level2 = new Level2Support();
        Handler level3 = new Level3Support();
        
        level1.setNextHandler(level2);
        level2.setNextHandler(level3);
        
        level1.handleRequest("basic issue");
        level1.handleRequest("intermediate issue");
        level1.handleRequest("complex issue");
    }
}
```

1. **Handler (Abstract Class)**: Defines a method for handling requests and maintains a reference to the next handler.
2. **Concrete Handlers**: Each subclass checks if it can handle the request. If not, it forwards the request to the next handler.
3. **Client Code**: The request starts at the first handler and propagates through the chain until it is processed.

## Advantages
- Reduces coupling between sender and receivers.
- Supports dynamic composition of handlers.
- Provides flexibility to add new handlers without modifying existing ones.
