# `docker compose top`

- this command is used to display the running processes inside the containers managed by your Docker Compose project;
- it shows the processes running inside the containers, similar to what docker `top <container>` does for individual containers.

**Example program explanation:**

- to show this command, we created an `hello-world` program;
- it consists of a Java program that simply prints `Hello, World from Java!` each 10 seconds;
- a Dockerfile to containerize and run the program is present:

    ```dockerfile
    FROM openjdk:21
    COPY Hello.java .
    RUN javac Hello.java
    CMD ["java", "Hello"]
    ```


- the program image is built by a `docker-compose.yml` file:

    ```yaml
    version: "3.9"
    
    services:
      my-service:
        build: .
        image: java-program:1.0
    ```

**Run the example:**

1. download the [hello-world.zip](resources/hello-world.zip) project;
2. extract it on your machine;
3. enter the project root folder and run this commands:

    ```commandline
    docker compose up -d
    docker compose top
    ```

- output:

    ```commandline
    SERVICE     #   UID   PID     PPID    C   STIME  TTY  TIME      CMD
    my-service  1   root  130065  130041  0   16:21  ?    00:00:00  java Hello
    ```

    it shows the only active java process.
