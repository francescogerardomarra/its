# Check Service Logs

You can view the logs of all replicas of the `app` service using the following command:

```commandline
docker service logs compose-example-stack_app
```

output:

```commandline
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 1
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 2
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 3
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 4
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 5
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 6
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 7
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 8
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 9
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 10
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 11
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 12
compose-example-stack_app.2.pl6bouhy1lmi@avolpe-pc    | Hello World 1
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 13
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 14
compose-example-stack_app.1.nioogjduij3z@avolpe-pc    | Hello World 15
compose-example-stack_app.2.pl6bouhy1lmi@avolpe-pc    | Hello World 2
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 1
compose-example-stack_app.2.pl6bouhy1lmi@avolpe-pc    | Hello World 3
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 2
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 3
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 4
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 5
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 6
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 7
compose-example-stack_app.2.pl6bouhy1lmi@avolpe-pc    | Hello World 4
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 8
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 9
compose-example-stack_app.2.pl6bouhy1lmi@avolpe-pc    | Hello World 5
compose-example-stack_app.3.kyafweof8c8x@avolpe-pc    | Hello World 10
```
