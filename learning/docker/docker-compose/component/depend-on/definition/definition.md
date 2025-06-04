# `depends_on` Definition


- when building multi-container apps, starting containers in the right order isn’t enough; 
- sometimes you need to know when a service is truly **ready** to be used;
- that’s where Docker Compose’s `depends_on` with `condition:` and `healthcheck` come in.

**Example:**

```yaml
version: "3.9"

services:
  web:
    build: .
    depends_on:
      db:
        condition: service_healthy

  db:
    # db setup here ...
```

- this tells Docker Compose:
  - "don’t start this service (`web`) until another one (`db`) is *healthy*".
- but here’s the twist: Compose doesn’t define what "healthy" means, that's up to you;
- see the next [healthcheck](../healthcheck/healthcheck.md) chapter.
