# Failing Stages

- in a multistage Docker build, each stage depends on the successful completion of the previous one;
- if a stage fails, all subsequent stages are not executed, and the entire build process stops;
- by running tests in a dedicated stage, we ensure that if tests fail, the later stages, such as packaging and deployment, do not proceed;


- this prevents shipping faulty code, improving reliability and reducing the risk of deploying broken applications;
- as a result, only successfully tested code progresses to the final image, ensuring a stable and verified build.
