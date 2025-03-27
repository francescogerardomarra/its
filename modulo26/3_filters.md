### Filters
A **filter** is an object that performs filtering tasks on either the **request** or the **response** within an application such as a Web Service. Filters are part of the servlet container and allow you to manipulate the request and/or response before it reaches the targeted servlet or after the servlet has processed it.

In Spring, filters are commonly used to process requests or responses in a way that is **independent of the business logic**. Filters sit in between the client and the servlet (or controller), intercepting and potentially modifying the request and/or response before the request reaches the servlet or after the servlet generates the response.

#### Definition, Chaining, Registration
From a theoretical point of view, the implementation lifecycle of a filter can be broken down into three main stages:

1. **Definition**: In this stage, the filter is created by implementing the `jakarta.servlet.Filter` interface or by extending an abstract class like `GenericFilter`. The filter class contains the logic that defines how incoming requests and outgoing responses should be processed, typically by overriding methods like `doFilter()`.
2. **Chaining**: As part of the definition, the **filter chain** comes into play. A filter chain allows multiple filters to be executed in a specific order. When the `doFilter()` method is called, the filter can either continue processing the request by calling `chain.doFilter(request, response)` or halt the chain. This chaining mechanism ensures that each filter in the chain can perform its task before passing the request to the next filter or the target resource (like a servlet or a JSP).
3. **Registration**: Once defined, the filter must be registered with the web application so that it can be invoked during the request-response cycle. This registration can be done either through the `web.xml` file (in older servlet versions) or via annotations like `@WebFilter` (in modern servlet-based applications). The filter is mapped to specific URL patterns or servlet paths to determine when it should be executed.

In summary, the filter lifecycle involves:
- **Definition** of the filter's behavior.
- **Chaining** of filters to ensure that multiple filters can work in sequence on the request and response.
- **Registration** of the filter within the web application's configuration.

#### Lifecycle
As soon as a request is made to the application, a **request-response pair** is created and associated throughout the entire request lifecycle.

Filters can intercept and modify both **requests** and **responses** at various stages of the HTTP request lifecycle:
- **Request Interception**: Filters can alter or inspect the request before it reaches the servlet or controller.
- **Response Interception**: Filters can manipulate the response that is sent back to the client after the servlet has processed the request.

Filters have the ability to:
- **Modify the request**: Add additional headers, parameters, or modify the request body.
- **Log request details**: Useful for auditing or debugging.
- **Authenticate requests**: A typical use case is JWT (JSON Web Token) authentication.
- **Modify the response**: Add headers, change the response body, or handle logging.
- **Redirect or terminate the request/response**: For example, if authentication fails, you might stop the request processing and return a 401 Unauthorized response.

Here's how this flow works in the context of filters:

1. **Request Handling**:
    - When the HTTP request reaches the application, a **request object** is created, and a **response object** is also prepared, even though it is initially empty (the response body hasn't been generated yet). This response object is created early by the servlet container to ensure that a response can be sent back to the client once the request is processed.
    - Both the request and response are passed through the filter chain together.

2. **Filters and Request-Response Interaction**:
    - Filters receive both the **request** and **response** objects and can interact with them.
    - **Request Interception**: Filters can inspect, log, or modify the incoming **request** before it reaches the servlet or controller.
    - **Response Interception**: Even before the controller processes the request, the filter has access to the **response** object. Although the response body is empty at this point, filters can set or modify **response headers**, like setting authentication or CORS headers.

   The key here is that the **response object** exists from the very beginning of the request lifecycle, even before the controller generates any content for it. Filters can modify headers or perform actions like logging, authentication checks, or other pre-processing tasks on the response, even though its body hasn't been populated yet.

3. **Controller Processing**:
    - After the request passes through all filters, it reaches the controller, where the request is processed. The controller generates content for the **response** object, such as setting the HTTP status, adding headers, and generating the response body.

4. **Post-Processing by Filters**:
    - Once the controller has generated the response, the response object travels back through the filter chain. Filters can now inspect or modify the **response** before it is sent to the client. This is typically where you can perform logging or modify the content of the response (e.g., adding custom headers or logging response times).

#### Use Cases
- **Authentication**: A filter can intercept incoming requests to verify the presence of a JWT token in the request headers, validate the token, and allow or deny access to the resources based on the token's validity.
- **Logging**: A filter can log details about incoming requests (e.g., request method, URL, headers, body) for auditing, debugging, or monitoring purposes.

#### Filter Chain
A **filter chain** is a sequence of filters that process a request and/or response in the order they are defined. Each filter in the chain can:

1. Perform its own task (e.g., authentication, logging, etc.).
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
    - This is the main method that intercepts the request and response. You can modify the request and response, perform logging, check authentication, manipulate headers, or any other tasks before passing control along the chain.
    - **Request-Response Pair**: As soon as a request is made to the application, a **request-response pair** is created and associated throughout the entire request lifecycle. This pair represents the HTTP request (from the client) and the response (that will be sent back to the client). In the context of filters:
        - The `request` object represents the HTTP request received from the client.
        - The `response` object represents the HTTP response that will be returned to the client.
    - You **must** call `chain.doFilter(request, response)` to pass the request and response to the next filter or servlet in the chain. If you don't call this method, the request/response will be stuck in the filter, and no further processing will occur. It’s crucial to allow the request to continue through the chain to complete the processing and return the response.
    - If you don't call `chain.doFilter(request, response)` in any filter—whether it's the last one or not:
        - The **request will not proceed** to the next filter or to the controller (or servlet).
        - The **response will not be generated** or sent back to the client.
        - The **request will be blocked** in the current filter.
        - Any **subsequent filters, including the controller or servlet, will not be reached**.
        - The **response will not be processed** either.
    - The `doFilter` method provides `ServletRequest` and `ServletResponse` objects, which are generic types. In many cases, you'll need more specific functionality, such as working with HTTP-specific headers, request parameters, or status codes. This is where **casting** becomes useful:
        - **`HttpServletRequest`**: By casting the generic `ServletRequest` to an `HttpServletRequest`, you can access HTTP-specific features like headers, cookies, parameters, and methods such as `getMethod()` or `getRequestURI()`.
        - **`HttpServletResponse`**: Similarly, casting the generic `ServletResponse` to an `HttpServletResponse` allows you to interact with HTTP-specific features like status codes, headers, and the response body.

3. **`destroy()`**:
    - This method is called when the filter is destroyed, typically when the servlet container shuts down or when the filter is no longer needed. You can use this method for cleanup tasks, such as releasing resources (e.g., closing database connections or cleaning up thread pools). The container automatically calls this method before destroying the filter instance.

As mentioned earlier, when a request is made to the application, a **request-response pair** is created and associated throughout the entire request lifecycle. Here’s how this flow works in the context of filters:

- When a request is made, the `ServletRequest` object is created to represent the HTTP request.
- The `ServletResponse` object is created to represent the HTTP response that will be sent back to the client.
- In the `doFilter()` method, the filter can inspect and modify both the request and the response objects before passing them to the next entity in the chain (whether it’s another filter or the target servlet).
- The filter must call `chain.doFilter(request, response)` to continue the request-response flow, allowing other filters or the servlet to process the request and generate a response.
- Without calling `chain.doFilter()`, the request/response processing will be halted in the filter, and no further action will be taken.

#### OncePerRequestFilter, GenericFilterBean
Apart from implementing this interface directly, a filter can be defined by extending specific abstract filter classes always of type `jakarta.servlet.Filter`.

These abstract classes simply offer default implementations of some of the methods from the `Filter` interface, allowing you to focus on overriding just the `doFilter()` method if no other customization is needed. You can define a filter either by directly implementing the `jakarta.servlet.Filter` interface or by extending an abstract class like `GenericFilter`, which is still part of the `jakarta.servlet.Filter` ecosystem.

Available Abstract Classes for Filter Implementation:

1. **`OncePerRequestFilter`**:
    - This class ensures that the filter is executed only once per request, even if the request undergoes multiple forwarding or dispatching during its lifecycle. In traditional servlet-based applications, requests can be forwarded from one resource to another (e.g., from one servlet to another or from a controller to a view). This means that a single HTTP request may pass through multiple resources, and without proper handling, a filter might execute multiple times during these forwards.
    - **Forwarding and Its Impact**: When forwarding occurs, the same HTTP request is internally routed to a different resource without the client being aware. However, this can cause filters to be executed repeatedly if the forwarding process isn't managed carefully. For example, if a filter is responsible for tasks like logging, authentication, or setting headers, it could execute multiple times across different forwards, resulting in duplicate log entries, unnecessary re-authentication, or redundant changes to the response.
    - **How `OncePerRequestFilter` Avoids Multiple Executions**: The `OncePerRequestFilter` class is specifically designed to prevent filters from running multiple times during the same request, even when forwarding or dispatching occurs. It achieves this by ensuring that the filter's actions are only performed once during the entire request lifecycle. The filter tracks whether it has already been executed for the current request and skips further executions if it has.
    - This is especially important when filters are involved in critical tasks like session validation, logging, or resource cleanup. Without this safeguard, a filter could cause unintended side effects if executed multiple times, potentially leading to errors or performance issues.
    - **Benefits**:
        - **Simplicity**: The `OncePerRequestFilter` reduces the complexity of filter management by handling forwarding and dispatching transparently, without requiring developers to manually track filter executions.
        - **Efficiency**: It ensures that the filter only performs necessary tasks once, preventing redundant actions and improving the overall efficiency of the web application.
        - **Consistency**: By guaranteeing that the filter runs only once, it avoids issues like double logging or reapplying filters that could cause inconsistencies in behavior.

    - **Example Use Case**: Imagine a filter that logs request details for monitoring purposes. Without `OncePerRequestFilter`, this logging could be duplicated if the request is forwarded to several resources (like different servlets or views). By using `OncePerRequestFilter`, the logging will happen only once for the entire request, regardless of how many forwards or dispatches occur.

2. **`GenericFilterBean`**:
    - This is a Spring-specific class that implements the `jakarta.servlet.Filter` interface and offers more Spring-friendly features, such as dependency injection.
    - It allows you to easily integrate Spring beans into your filter, which is helpful for service or repository injections.

#### Registration Methods
In Spring Boot, filters can be defined by either implementing the `jakarta.servlet.Filter` interface or by extending a superclass like `OncePerRequestFilter`. After defining the filter, you need to register it to make it part of the Spring Boot application context. Depending on how you define and register the filter, the filter may or may not be a Spring Bean. Below are the main methodologies for filter registration:

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
        // order (i.e., values greater than 1) are applied.
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
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Custom logic for request processing (e.g., JWT validation)
        System.out.println("Authenticating request...");

        // Pass the request/response along the filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
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
        // Pre-processing logic before passing the request along the filter chain
        System.out.println("Request is being filtered");

        // Pass request and response to the next filter in the chain
        chain.doFilter(request, response);

        // Post-processing logic after the request has been processed by the chain
        System.out.println("Response is being filtered");
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

3. **Using `@Component` with `FilterRegistrationBean`**:
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
        // Pre-processing logic
        System.out.println("Custom Filter Processing Request");

        // Pass the request along the filter chain
        chain.doFilter(request, response);

        // Post-processing logic
        System.out.println("Custom Filter Processing Response");
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
    - **Summary**: This method integrates the filter into the Spring Security filter chain, but the filter doesn't have to be a Spring Bean unless needed for dependency injection.

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
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

@Component  // Making the filter a Spring Bean
public class CustomSecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Custom filter logic here
        System.out.println("Custom Security Filter Applied");
        
        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
````

- **Method 1**: Register as a `@Bean` in a `@Configuration` class with `FilterRegistrationBean`. The filter is defined as a class and is **later registered as a Spring Bean** during the registration process.
- **Method 2**: Use `@WebFilter` annotation and `@ServletComponentScan`. The filter is **not a Spring Bean**, and Spring Boot automatically registers it with the servlet container.
- **Method 3**: Use `@Component` annotation with `FilterRegistrationBean`. The filter is defined as a Spring Bean and automatically registered by Spring.
- **Method 4**: Register the custom filter using `HttpSecurity` in Spring Security. The filter may or may not be a Spring Bean, but it is directly integrated into the security filter chain.
