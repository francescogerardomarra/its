# Base Image

Specifies `docker:20.10.24` as the default base Docker image that all jobs in the pipeline will use, unless a specific job explicitly defines a different image.

**`gitlab-ci.yml` Section:**

```yaml
image: docker:20.10.24
```
