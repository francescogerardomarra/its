# Dockerfile

- [Definition](definition/definition.md)
- [Image without `Dockerfile`](without-dockerfile/without_dockerfile.md)
- [Build context](build-context/build_context.md)
- `.dockerignore` file
  - [Introduction](dockerignore-file/intro/intro.md)
  - [Purpose](dockerignore-file/purpose/purpose.md)
  - [Format rules](dockerignore-file/format-rule/format_rule.md)
  - [Example](dockerignore-file/example/example.md)
  - [Parallelism with `.gitignore` file](dockerignore-file/gitignore-parallelism/gitignore_parallelism.md)
- [Where `Dockerfile` commands are executed](where-executed/where_executed.md)
- Caching
  - [Introduction to Docker layers ](caching/layer-intro/layer_intro.md)
  - [Key caching strategies](caching/key-strategy/key_strategy.md)
  - [Maven `go-offline` tip](caching/mvn-offline/mvn_offline.md)
  - Example
    - [Non-optimized `Dockerfile`](caching/example/non-optimized/non_optimized.md)
    - [Optimized `Dockerfile`](caching/example/optimized/optimized.md)
    - [Comparison](caching/example/comparison/comparison.md)
- Multi-stage builds
  - [Introduction](multi-stage/intro/intro.md)
  - How it works 
    - [Multiple stages](multi-stage/how-work/multiple-stage/multiple_stage.md)
    - [Interstage communication](multi-stage/how-work/interstage/interstgage.md)
    - [Final stage](multi-stage/how-work/final-stage/final_stage.md)
  - Build specific stage
    - [Introduction](multi-stage/specific-stage/intro/intro.md)
    - [Example](multi-stage/specific-stage/example/example.md)
  - Why use multi-stage builds?
    - [Reduce image size](multi-stage/why-use/reduce-size/reduce_size.md)
    - [Improve security](multi-stage/why-use/improve-security/improve_security.md)
    - [Better maintainability](multi-stage/why-use/maintainability/maintainability.md)
    - [Optimize build performance](multi-stage/why-use/optimize-build/optimize_build.md)
    - [Flexibility with multiple targets](multi-stage/why-use/flexibility/flexibility.md)
  - Key features
    - [Failing stages](multi-stage/key-feature/failing-stage/failing_stage.md)
  - Example
    - [Dockerfile](multi-stage/example/dockerfile/dockerfile.md)
    - Overview
      - [`build` stage](multi-stage/example/overview/build-stage/build_stage.md)
      - [`test` stage](multi-stage/example/overview/test-stage/test_stage.md)
      - [`runtime` stage](multi-stage/example/overview/runtime-stage/runtime_stage.md)
  - Pipeline vs multi-stage build
    - [Pipeline](multi-stage/pipeline-vs-multi/pipeline/pipeline.md)
    - [Common pipeline flow](multi-stage/pipeline-vs-multi/common-pipeline-flow/common_pipeline_flow.md)
    - [Pipeline flow using multi-stage `Dockerfile`](multi-stage/pipeline-vs-multi/pipeline-flow/pipeline_flow.md)
    - [Comparison](multi-stage/pipeline-vs-multi/comparison/comparison.md)
    - [Which one should you use?](multi-stage/pipeline-vs-multi/which-use/which_use.md)
- Environment variables and secrets management
  - Environment variables
    - [Definition](env-secret/env/definition/definition.md)
    - Different ways to pass the environment variable to a container
      - [`docker run` with `-e` flag](env-secret/env/way-of-pass/docker-run/docker_run.md)
      - [`.env` file](env-secret/env/way-of-pass/env-file/env_file.md)
      - [`docker-compose.yml` file](env-secret/env/way-of-pass/docker-compose/docker_compose.md)
      - [using `Dockerfile`](env-secret/env/way-of-pass/dockerfile/dockerfile.md)
  - Secrets
    - [Definition](env-secret/secret/definition/definition.md)
    - [Before proceeding](env-secret/secret/before-proceed/before_proceed.md)
    - [Why use secrets (comparison with env variables)](env-secret/secret/why-use/why_use.md)
    - How to use them
      - [Secrets don't exist in standard Docker](env-secret/secret/how-use/not-available/not_available.md)
      - Docker Swarm
        - [Definition](env-secret/secret/how-use/docker-swarm/definition/definition.md)
        - [Enable Docker Swarm](env-secret/secret/how-use/docker-swarm/enable/enable.md)
        - [Create a secret](env-secret/secret/how-use/docker-swarm/create-secret/create_secret.md)
        - [Use the secret in a service](env-secret/secret/how-use/docker-swarm/use-secret/use_secret.md)
        - [Access the secret in a container](env-secret/secret/how-use/docker-swarm/access-secret/access_secret.md)
        - [Storage and security](env-secret/secret/how-use/docker-swarm/storage/storage.md)
        - [Secret distribution](env-secret/secret/how-use/docker-swarm/distribution/distribution.md)
        - [Access control](env-secret/secret/how-use/docker-swarm/access-control/access_control.md)
        - [Secret lifecycle](env-secret/secret/how-use/docker-swarm/lifecycle/lifecycle.md)
      - Docker Compose
        - [Definition](env-secret/secret/how-use/docker-compose/definition/definition.md)
        - [Create a secret](env-secret/secret/how-use/docker-compose/create-secret/create_secret.md)
        - [Create `docker-compose.yml` file](env-secret/secret/how-use/docker-compose/create-compose/create_compose.md)
        - [Deploy the stack](env-secret/secret/how-use/docker-compose/deploy-stack/deploy_stack.md)
        - [Why don't use `docker service create` command?](env-secret/secret/how-use/docker-compose/why-not-service/why_not_service.md)
        - [Create the secret within the `docker-compose.yml` file](env-secret/secret/how-use/docker-compose/secret-with-compose/secret_with_compose.md)
      - Kubernetes
        - [Definition](env-secret/secret/how-use/kubernetes/definition/definition.md)
        - [Prerequisites](env-secret/secret/how-use/kubernetes/prerequisite/prerequisites.md)
        - Create secret
          - [Create from CLI](env-secret/secret/how-use/kubernetes/create/from-cli/from_cli.md)
          - [Create from `yaml` file](env-secret/secret/how-use/kubernetes/create/from-yaml/from_yaml.md)
        - Use secret
          - [Definition](env-secret/secret/how-use/kubernetes/use/definition/definition.md)
          - [Use secret as env variable](env-secret/secret/how-use/kubernetes/use/env-variable/env_variable.md)
          - [Use secret as volume](env-secret/secret/how-use/kubernetes/use/volume/volume.md)
        - Manage secret
          - [View secret](env-secret/secret/how-use/kubernetes/manage/view-secret/view_secret.md)
          - [Decode secret data](env-secret/secret/how-use/kubernetes/manage/decode-secret/decode_secret.md)
          - [Delete secret](env-secret/secret/how-use/kubernetes/manage/delete-secret/delete_secret.md)
      - [CLI](env-secret/secret/how-use/cli/cli.md)
      - [Alternatives](env-secret/secret/how-use/alternative/alternative.md)
    - Examples 
      - [AWS secrets manager without orchestrator](env-secret/secret/example/without-orchestrator/without_orchestrator.md)
      - Docker Swarm without Docker Compose
        - [Definition](env-secret/secret/example/swarm/definition/definition.md)
        - Steps
          1. [Initialize Docker Swarm](env-secret/secret/example/swarm/step/initialize-swarm/initialize_swarm.md)
          2. Create a secret
             - [Definition](env-secret/secret/example/swarm/step/create-secret/definition/definition.md)
             - [Who keeps the secret?](env-secret/secret/example/swarm/step/create-secret/who-keep/who_keep.md)
          3. [Maven project](env-secret/secret/example/swarm/step/maven-project/maven_project.md)
          4. [Build the code](env-secret/secret/example/swarm/step/build-code/build_code.md)
          5. [Run the code](env-secret/secret/example/swarm/step/run-code/run_code.md)
          6. [Dockerfile](env-secret/secret/example/swarm/step/dockerfile/dockerfile.md)
          7. [Build the image](env-secret/secret/example/swarm/step/build-image/build_image.md)
          8. [Deploy the Docker Swarm service](env-secret/secret/example/swarm/step/deploy-service/deploy_service.md)
          9. [Check if the service is running](env-secret/secret/example/swarm/step/check-service/check_service.md)
          10. View the service logs
              - [Use Docker Swarm](env-secret/secret/example/swarm/step/view-log/docker-swarm/docker_swarm.md)
              - [Use standalone Docker](env-secret/secret/example/swarm/step/view-log/standalone-docker/standalone_docker.md)
      - Docker Swarm integrated with Docker Compose
        - [Definition](env-secret/secret/example/swarm-compose/definition/definition.md)
        - [Comparison](env-secret/secret/example/swarm-compose/comparison/comparison.md)
        - Steps
          - [Follow the Docker Swarm without Docker Compose example](env-secret/secret/example/swarm-compose/step/follow-previous/follow_previous.md)
          - Differences
            - (c.) Maven project
              - [Download](env-secret/secret/example/swarm-compose/step/difference/maven-project/download/download.md)
              - [`docker-compose.yml` explanation](env-secret/secret/example/swarm-compose/step/difference/maven-project/docker-compose/docker_compose.md)
              - [Define secret within `docker-compose.yml` file](env-secret/secret/example/swarm-compose/step/difference/maven-project/define-secret/define_secret.md)
            - (h.) [Deploy the Docker Swarm service](env-secret/secret/example/swarm-compose/step/difference/deploy-service/deploy_service.md)
            - (j.) View the service logs
              - [Use Docker Swarm](env-secret/secret/example/swarm-compose/step/difference/view-log/docker-swarm/docker_swarm.md)
              - [Use standalone Docker](env-secret/secret/example/swarm-compose/step/difference/view-log/standalone-docker/standalone_docker.md)
      - Kubernetes
        - [Definition](env-secret/secret/example/kubernetes/definition/definition.md)
        - Prerequisites
          - [kubectl](env-secret/secret/example/kubernetes/prerequisite/kubectl/kubectl.md)
          - [Minikube](env-secret/secret/example/kubernetes/prerequisite/minikube/minikube.md)
          - [How they work together](env-secret/secret/example/kubernetes/prerequisite/work-together/work_together.md)
        - Steps
          1. [Initialize Minikube](env-secret/secret/example/kubernetes/step/minikube-init/minikube_init.md)
          2. [Check Kubernetes context](env-secret/secret/example/kubernetes/step/check-context/check_context.md)
          3. [Create a secret](env-secret/secret/example/kubernetes/step/create-secret/create_secret.md)
          4. Maven project
             - [Download](env-secret/secret/example/kubernetes/step/maven-project/download/download.md)
             - [`deployment.yaml` explanation](env-secret/secret/example/kubernetes/step/maven-project/deployment/deployment.md)
             - [Secret as env variable](env-secret/secret/example/kubernetes/step/maven-project/env-variable/env_variable.md)
             - [Define secret within `deployment.yaml` file](env-secret/secret/example/kubernetes/step/maven-project/define-secret/define_secret.md)
          5. [Build the code](env-secret/secret/example/kubernetes/step/build-code/build_code.md)
          6. [Run the code](env-secret/secret/example/kubernetes/step/run-code/run_code.md)
          7. [Dockerfile](env-secret/secret/example/kubernetes/step/dockerfile/dockerfile.md)
          8. [Build the image](env-secret/secret/example/kubernetes/step/build-image/build_image.md)
          9. [Copy the image within Minikube](env-secret/secret/example/kubernetes/step/copy-image/copy_image.md)
          10. [Apply the deployment](env-secret/secret/example/kubernetes/step/apply-deployment/apply_deployment.md)
          11. [Check if the pod is running](env-secret/secret/example/kubernetes/step/check-running/check_running.md)
          12. [View the logs of the pod](env-secret/secret/example/kubernetes/step/view-log/view_log.md)
          13. [Cleanup (optional)](env-secret/secret/example/kubernetes/step/cleanup/cleanup.md)
        - Useful operations
          - [Stop a pod without deleting the deployment](env-secret/secret/example/kubernetes/operation/stop/stop.md)
          - [Delete the deployment and its associated pods](env-secret/secret/example/kubernetes/operation/delete/delete.md)
    - [Comparison](env-secret/secret/comparison/comparison.md)
  - [Comparison](env-secret/comparison/comparison.md)
- Common commands
  - [Definition](common-command/definition/definition.md)
  - [ADD](common-command/add/add.md)
  - [ARG and ENV](common-command/arg-env/arg_env.md)
  - [CMD](common-command/cmd/cmd.md)
  - [COPY](common-command/copy/copy.md)
  - [ENTRYPOINT](common-command/entrypoint/entrypoint.md)
  - [ENV (environment variables)](common-command/env/env.md)
  - [EXPOSE](common-command/expose/expose.md)
  - [FROM](common-command/from/from.md)
  - HEALTHCHECK
    - [Definition](common-command/healthcheck/definition/definition.md)
    - [Example](common-command/healthcheck/example/example.md)
  - [LABEL (metadata)](common-command/label/label.md)
  - [ONBUILD](common-command/onbuild/onbuild.md)
  - [RUN](common-command/run/run.md)
  - [STOPSIGNAL](common-command/stopsignal/stopsignal.md)
  - [USER](common-command/user/user.md)
  - [VOLUME](common-command/volume/volume.md)
  - [WORKDIR](common-command/workdir/workdir.md)
  - [Multistage build](common-command/multistage-build/multistage_build.md)
