# Where `Dockerfile` Commands Are Executed

- all `Dockerfile` commands are executed inside intermediate temporary containers created during the build process;
- these containers are automatically removed after the build completes;
- therefore, commands such as `mvn clean package` will not affect your local machine or its environment;


- the build and its outputs are isolated within the container.