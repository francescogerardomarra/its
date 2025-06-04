# Update a Service

**Command:**

```commandline
docker service update --image <new-image> <service-id>
```

**Command explanation:**

* updates a running Docker service to use a new image version;
* replaces the current image with `<new-image>` for all tasks in the service;
* `<service-id>` specifies which service to update;


* triggers a [rolling update](), ensuring minimal downtime; <!-- todo: link to rolling update -->
* useful for deploying new versions of an application;
* this is just an example, you can update a service for other reasons as well, such as changing the number of replicas, environment variables, or resource limits.
