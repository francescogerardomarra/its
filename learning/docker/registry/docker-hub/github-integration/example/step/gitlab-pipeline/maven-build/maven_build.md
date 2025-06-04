# Maven build job

- uses `maven:3.8.4-openjdk-8` as the image, overriding the default [`docker:20.10.24` image](../base-image/base_image.md) declared on top of the file;
- runs in the `maven-build` stage;
- executes `mvn clean package` to build the Java application;


- stores the built `.jar` file (`target/docker-course-test.jar`) as an artifact for subsequent stages.

**`gitlab-ci.yml` Section:**

```yaml
maven-build-step-one:
  image: maven:3.8.4-openjdk-8
  stage: maven-build
  script: mvn clean package
  artifacts:
    paths:
      - target/docker-course-test.jar
```
