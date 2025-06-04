# Introduction

- in a **multi-stage `Dockerfile`**, you can build specific stages using the `--target` flag when running `docker build`;
- this allows you to stop the build process at a particular stage and use only what's necessary.
