# Registry

TODO: aggiungere esempio in cui è dockerhub ad accorgersi delle modifiche e buildare il codice

- [Introduction](intro/intro.md)
- [Registry vs repository](registry-vs-repo/registry_vs_repo.md)
- What is stored in a registry?
  - [Docker images](what-stored/image/image.md)
  - [Images metadata](what-stored/metadata/metadata.md)
  - [Repositories data](what-stored/repository/repository.md)
  - [Access control and security information](what-stored/access-control/access_control.md)
  - [Event and usage logs](what-stored/log/log.md)
- Types of registry
  - Public registry
    - [Definition](type/public/definition/definition.md)
    - [Access control](type/public/access-control/access_control.md)
    - [Use case](type/public/use-case/use_case.md)
    - [Cost](type/public/cost/cost.md)
    - [Security](type/public/security/security.md)
    - [Scalability](type/public/scalability/scalability.md)
    - [Example](type/public/example/example.md)
  - Private registry
    - [Definition](type/private/definition/definition.md)
    - [Access control](type/private/access-control/access_control.md)
    - [Use case](type/private/use-case/use_case.md)
    - [Cost](type/private/cost/cost.md)
    - [Security](type/private/security/security.md)
    - [Scalability](type/private/scalability/scalability.md)
    - [Example](type/private/example/example.md)
  - [Comparison](type/comparison/comparison.md)
- Self-hosting
  - [Definition](self-hosting/definition/definition.md)
  - [Why is it used?](self-hosting/why-used/why_used.md)
  - Example
    - [Definition](self-hosting/example/definition/definition.md)
    - [Run the registry container](self-hosting/example/run/run.md)
    - [Push an image](self-hosting/example/push/push.md)
    - [Pull an image](self-hosting/example/pull/pull.md)
    - Advanced configuration
      - Authentication and security
        - TLS encryption
          - [What it is](self-hosting/example/advanced-config/authentication/tls-encryption/what-is/what_is.md)
          - [Why it's important](self-hosting/example/advanced-config/authentication/tls-encryption/why-important/why_important.md)
          - How to enable it
            - [Obtain a SSL/TLS certificate](self-hosting/example/advanced-config/authentication/tls-encryption/how-enable/obtain-certificate/obtain_certificate.md)
            - [Configure the Docker registry](self-hosting/example/advanced-config/authentication/tls-encryption/how-enable/configure-registry/configure_registry.md)
            - [`config.yml` within a volume](self-hosting/example/advanced-config/authentication/tls-encryption/how-enable/within-volume/within_volume.md)
            - [What is the `config.yml`](self-hosting/example/advanced-config/authentication/tls-encryption/how-enable/what-is/what_is.md)
            - [Restart the Docker registry](self-hosting/example/advanced-config/authentication/tls-encryption/how-enable/restart-registry/restart_registry.md)
        - Basic authentication
          - [What it is](self-hosting/example/advanced-config/authentication/basic-auth/what-is/what_is.md)
          - [Why it's important](self-hosting/example/advanced-config/authentication/basic-auth/why-important/why_important.md)
          - [How to enable it](self-hosting/example/advanced-config/authentication/basic-auth/how-enable/how_enable.md)
          - [Login to the Docker CLI](self-hosting/example/advanced-config/authentication/basic-auth/docker-login/docker_login.md)
        - [Can TLS encryption and Basic auth be used together?](self-hosting/example/advanced-config/authentication/used-together/used_together.md)
        - [TLS encryption and Basic auth comparison](self-hosting/example/advanced-config/authentication/comparison/comparison.md)
      - Data storage
        - [Definition](self-hosting/example/advanced-config/data-storage/definition/definition.md)
        - Use AWS S3
          - [Definition](self-hosting/example/advanced-config/data-storage/aws/definition/definition.md)
          - [Advantages](self-hosting/example/advanced-config/data-storage/aws/advantage/advantage.md)
          - [Security](self-hosting/example/advanced-config/data-storage/aws/security/security.md)
      - [Third-party tools](self-hosting/example/advanced-config/third-party/third-party.md)
  - Where are they located? // initial there is just a storage not a registry
    - [Introduction](self-hosting/where-located/intro/intro.md)
    - [Dedicated private server](self-hosting/where-located/dedicated-server/dedicated_server.md)
    - [VM in cloud](self-hosting/where-located/vm-in-cloud/vm_in_cloud.md)
    - [Kubernetes cluster](self-hosting/where-located/kubernetes/kubernetes.md)
    - [Developer localhost](self-hosting/where-located/dev-localhost/dev_localhost.md)
    - [Private network (behind a VPN or firewall)](self-hosting/where-located/private-network/private_network.md)
  - Tools to enhance self-hosted registries
    - [Definition](self-hosting/tool/definition/tool.md)
    - [Harbor](self-hosting/tool/harbor/harbor.md)
    - [Nexus repository](self-hosting/tool/nexus/nexus.md)
    - [Comparison](self-hosting/tool/comparison/comparison.md)
- Docker Hub
  - [Definition](docker-hub/definition/definition.md)
  - Key features
    - [Public and private repositories](docker-hub/key-feature/public-private/public_private.md)
    - [Official images](docker-hub/key-feature/official-image/official_image.md)
    - [Automated Builds](docker-hub/key-feature/automated-build/automated_build.md)
    - [Image tagging](docker-hub/key-feature/image-tagging/image_tagging.md)
    - [Search and discovery](docker-hub/key-feature/search-discovery/search_discovery.md)
    - [Webhooks](docker-hub/key-feature/webhook/webhook.md)
  - [Parallelism with GitHub](docker-hub/github-parallelism/github_parallelism.md)
  - Integration with GitHub
    - [Definition](docker-hub/github-integration/definition/definition.md)
    - Key features
      - [Automated builds](docker-hub/github-integration/key-feature/automated-build/automated_build.md)
      - [Webhooks and notifications](docker-hub/github-integration/key-feature/webhook/webhook.md)
      - [Versioning with tags](docker-hub/github-integration/key-feature/versioning/versioning.md)
      - [Build context configuration](docker-hub/github-integration/key-feature/build-context/build_context.md)
      - [Collaborative development](docker-hub/github-integration/key-feature/collaborative/collaborative.md)
      - [Security and access control](docker-hub/github-integration/key-feature/security/security.md)
      - [Public and private repositories](docker-hub/github-integration/key-feature/public-private/public_private.md)
    - [Use cases](docker-hub/github-integration/use-case/use_case.md)
    - Example
      - [Definition](docker-hub/github-integration/example/definition/definition.md)
      - [Prerequisites](docker-hub/github-integration/example/prerequisite/prerequisite.md)
      - Steps
        1. [GitLab repository](docker-hub/github-integration/example/step/gitlab-repo/gitlab_repo.md)
        2. [Docker Hub repository](docker-hub/github-integration/example/step/dockerhub-repo/dockerhub_repo.md)
        3. [Docker Hub token](docker-hub/github-integration/example/step/dockerhub-token/dockerhub_token.md)
        4. [Docker Hub credentials on GitLab](docker-hub/github-integration/example/step/dockerhub-credential/dockerhub_credential.md)
        5. [Maven project](docker-hub/github-integration/example/step/maven-project/maven_project.md)
        6. [Connect the Maven project with Gitlab](docker-hub/github-integration/example/step/connect-gitlab/connect_gitlab.md)
        7. [Dockerfile](docker-hub/github-integration/example/step/dockerfile/dockerfile.md)
        8. GitLab pipeline
           - [Definition](docker-hub/github-integration/example/step/gitlab-pipeline/definition/definition.md)
           - [What is a pipeline](docker-hub/github-integration/example/step/gitlab-pipeline/pipeline/pipeline.md)
           - [Base image](docker-hub/github-integration/example/step/gitlab-pipeline/base-image/base_image.md)
           - [Docker service](docker-hub/github-integration/example/step/gitlab-pipeline/docker-service/docker_service.md)
           - [Stages](docker-hub/github-integration/example/step/gitlab-pipeline/stage/stage.md)
           - [Environment variables](docker-hub/github-integration/example/step/gitlab-pipeline/environment/environment.md)
           - [Maven build job](docker-hub/github-integration/example/step/gitlab-pipeline/maven-build/maven_build.md)
           - [Docker image build and push job](docker-hub/github-integration/example/step/gitlab-pipeline/docker-image/docker_image.md)
        9. [Push the code to GitLab](docker-hub/github-integration/example/step/gitlab-push/gitlab_push.md)
        10. [Verify the GitLab pipeline](docker-hub/github-integration/example/step/gitlab-verify/gitlab_verify.md)
        11. [Verify the automatic push on Docker Hub](docker-hub/github-integration/example/step/dockerhub-verify/dockerhub_verify.md)
- Common commands
  - [Definition](common-command/definition/definition.md)
  - [Docker Hub login](common-command/dockerhub-login/dockerhub_login.md)
  - [Docker Hub logout](common-command/dockerhub-logout/dockerhub_logout.md)
  - [Tag image for registry](common-command/tag-image/tag_image.md)
  - [Pull image from registry](common-command/pull-image/pull_image.md)
  - [Push image to registry](common-command/push-image/push_image.md)
  - [Set up local registry](common-command/setup-registry/setup_registry.md)
  - [Debug registry](common-command/debug-registry/debug_registry.md)
