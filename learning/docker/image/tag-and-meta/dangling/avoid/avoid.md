# How to Avoid Creating Them

1. tag images appropriately:
   - when building images, make sure to tag them properly with meaningful names and versions;
   - this reduces the likelihood of creating dangling images when new versions of an image are built:

        ```commandline
        docker build -t test-image:1.0 .
        docker build -t test-image:1.1 .
        ```
2. clean up regularly: 
   - use the following Docker commands to clean up dangling images periodically:

        ```commandline
        docker image prune
        ```
