# Commands that Create Layers

- not all commands in Dockerfile create layers, since some commands just set metadata for the image;
- just commands that modify the image filesystem create new layers:

  - `FROM`
  - `RUN`
  - `COPY`
  - `ADD`
  - `ENV`
  - `WORKDIR` (just in case it creates a new folder)
  - `VOLUME`
  - `USER`
  - `LABEL`

- commands that just add config or metadata to the image don't create new layers:

  - `CMD`: sets the default command to run for the instantiated container;
  - `EXPOSE`: documents the port the container listens on;
  - `ENTRYPOINT`: defines the container’s entry point;
  - `ARG`: build-time variables (not persisted after build);
  - `HEALTHCHECK`: defines health check behavior;
  - `STOPSIGNAL`: sets the system call signal for container stop;
  - `SHELL`: changes the default shell used in `RUN` commands.
