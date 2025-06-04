# Troubleshooting

In case of errors, you can analyze the logs for each service:

1. open a terminal and run this command to discover the deployed service names:

    ```commandline
    docker service ls
    ```
    
    output:
    
    ```commandline
    ID             NAME                                     MODE         REPLICAS   IMAGE                  PORTS
    0gytfdbh8eg3   complete-compose-example-stack_backend   replicated   2/2        compose-backend:1.0    *:8091->8080/tcp
    jzdek3k1l1ch   complete-compose-example-stack_db        replicated   1/1        postgres:15.6          
    nehl01030c5h   complete-compose-example-stack_fronted   replicated   1/1        compose-frontend:1.0   *:8090->80/tcp
    ```
2. check the logs printed in the container console output of the service that’s causing issues (e.g., `backend`):

    ```commandline
    docker service logs complete-compose-example-stack_backend 
    ```
    
    output:
    
    ```commandline
    ...many other logs..
    complete-compose-example-stack_backend.1.jj0swb9mkcod@avolpe-pc    | Hibernate: select t1_0.id,t1_0.value from text t1_0
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:35.827Z  INFO 1 --- [backend] [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:35.865Z  INFO 1 --- [backend] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:36.298Z  WARN 1 --- [backend] [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:37.082Z  INFO 1 --- [backend] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:37.103Z  INFO 1 --- [backend] [           main] com.example.backend.BackendApplication   : Started BackendApplication in 6.255 seconds (process running for 6.741)
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:44.488Z  INFO 1 --- [backend] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:44.488Z  INFO 1 --- [backend] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | 2025-04-03T10:02:44.489Z  INFO 1 --- [backend] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | Hibernate: select t1_0.id,t1_0.value from text t1_0
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | Hibernate: insert into text (value) values (?)
    complete-compose-example-stack_backend.2.wkg80fri4kce@avolpe-pc    | Hibernate: select t1_0.id,t1_0.value from text t1_0
    ```
