# LABEL (Metadata)

- **purpose:**
  - adds **metadata** (key-value pairs) to a Docker image to store information like author, version, or description;
  - helps in **organizing, searching, and managing** images using `docker inspect` or external tools;
  - supports multiple labels and can be updated in later image versions.

- **syntax:**

    ```dockerfile
    LABEL <key>=<value> <key2>=<value2> ...
    ```
    all key, values are completely arbitrary, meaning you can define any key-value pairs you want.

- **example:**
  - **add basic metadata to an image:**
  
    ```dockerfile
    LABEL maintainer="John Doe" version="1.0" description="Example application"
    ```
  - **use multiple `LABEL` instructions (equivalent to the above):**
  
    ```dockerfile
    LABEL maintainer="John Doe"
    LABEL version="1.0"
    LABEL description="Example application"
    ```
  - **check labels in a built image:**
  
    ```sh
    docker inspect my-container | grep Label
    ```
