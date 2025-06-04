# `docker compose config`

- this command merges multiple Compose files (if you're using overrides like `docker-compose.override.yml`);
- it interpolates environment variables (like `${VAR_NAME}`);
- it shows the result in a fully expanded and resolved format, including all defaults and inferred settings that Docker Compose uses under the hood;


- it validates the syntax and structure of your Compose file;
- it's useful to debug your Compose setup and make sure everything is correct before running it;
- it's useful to see the final version of your services, volumes, networks, etc., with all the variables and overrides applied.

**Example:**

- `docker-compose.yml` file:

    ```yaml
    version: "3.8"
    services:
      web:
        image: "${WEB_IMAGE}"
        ports:
          - "8080:80"
    ```

- open a terminal within the above `docker-compose.yml` folder and run this command to create the `WEB_IMAGE` env variable:

    ```commandline
    export WEB_IMAGE=my_custom_image:1.0
    ```

- run this command:

    ```commandline
    docker compose config
    ```

- output:

    ```yaml
    name: desktop
    services:
      web:
        image: my_custom_image:1.0
        networks:
          default: null
        ports:
          - mode: ingress
            target: 80
            published: "8080"
            protocol: tcp
    networks:
      default:
        name: desktop_default
    ```

  - as you can see, the expanded version of `docker-compose.yml` file includes the actual value of `WEB_IMAGE` env variable;
  - other default and inferred settings are added by Docker Compose.
