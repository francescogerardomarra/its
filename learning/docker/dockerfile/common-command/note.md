Here's a comprehensive list of the most common `Dockerfile` commands that a developer will use throughout their career:

---

### **Base Image & FROM**
- `FROM <image>` – Defines the base image for the build.
- `FROM <image>:<tag>` – Uses a specific version of the base image.
- `FROM scratch` – Starts with an empty base (used for minimal images like Go binaries).

---

### **Metadata**
- `LABEL <key>=<value>` – Adds metadata to the image (e.g., author, version, description).

---

### **Environment Variables**
- `ENV <key>=<value>` – Defines environment variables available inside the container.

---

### **Copy & Add Files**
- `COPY <src> <dest>` – Copies files from the host machine into the image.
- `COPY ["<src1>", "<src2>", ..., "<dest>"]` – Copies multiple files.
- `ADD <src> <dest>` – Similar to COPY but can also handle remote URLs and tar archives.

---

### **Working Directory**
- `WORKDIR <path>` – Sets the working directory for subsequent commands.

---

### **Run Commands**
- `RUN <command>` – Executes commands inside the container during the build process.
- `RUN <command1> && <command2>` – Chains multiple commands.
- `RUN ["executable", "param1", "param2"]` – Alternative JSON array format.

---

### **User Management**
- `USER <username>` – Sets the user for executing commands inside the container.

---

### **Exposing Ports**
- `EXPOSE <port>` – Documents the port that the container will listen on.

---

### **Command Execution**
- `CMD ["executable", "param1", "param2"]` – Default command to run inside the container.
- `CMD <command>` – Shell form of CMD.
- `CMD ["sh", "-c", "echo Hello"]` – Executes a shell command.

---

### **Entry Point**
- `ENTRYPOINT ["executable", "param1", "param2"]` – Defines the main process for the container.
- `ENTRYPOINT ["sh", "-c", "exec myapp"]` – Executes a shell script.

---

### **Arguments & Build-time Variables**
- `ARG <variable>` – Defines a variable used only at build time.
- `ARG <variable>=<default>` – Defines a default value.
- `ENV VAR_NAME ${ARG_NAME}` – Uses an ARG in an ENV variable.

---

### **Health Check**
- `HEALTHCHECK --interval=<time> --timeout=<time> --retries=<num> CMD <command>` – Defines a health check.
- `HEALTHCHECK NONE` – Disables health checks.

---

### **Volume Management**
- `VOLUME ["/data"]` – Creates a mount point for persistent storage.

---

### **Stopping Signals**
- `STOPSIGNAL <signal>` – Defines the system signal for stopping the container (e.g., `STOPSIGNAL SIGTERM`).

---

### **Multistage Builds**
- `FROM <image> AS <name>` – Defines a new build stage.
- `COPY --from=<name> <src> <dest>` – Copies artifacts from one stage to another.

---

### **OnBuild Instructions**
- `ONBUILD <instruction>` – Defines instructions to be executed in derived Dockerfiles.

---

### **Maintainer (Deprecated)**
- `MAINTAINER <name>` – (Deprecated) Previously used for metadata, replaced by `LABEL`.

---

Would you like me to categorize them further or provide example Dockerfiles using these commands? 🚀


## Docker question:

explain me the dockerfile "Arguments & Build-time Variables" command in this way:

**Purpose:**
a list of bullet points containing the explanations, the sentences must be clear and not too much long, maximum 3 bullet points
**syntax:**
a generic code block showing the command syntax
**example:**
a list of bullet points containing the explanations, the sentences must be clear and not too much long, maximum 3 bullet points
+ the example code
