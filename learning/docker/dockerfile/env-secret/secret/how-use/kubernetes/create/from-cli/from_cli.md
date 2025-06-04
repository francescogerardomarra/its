# Create From CLI

You can create a **secret** using `kubectl` from literal values or from files.

**Using `kubectl create secret` (from literals):**

- this will create a secret named `my-secret` with two key-value pairs (`username=admin` and `password=Pa$$w0rd`):

    ```sh
    kubectl create secret generic my-secret --from-literal=username=admin --from-literal=password=Pa$$w0rd
    ```

**Using `kubectl create secret` (from files):**

- this will create a secret with keys derived from file names (`username.txt` and `password.txt`);
- the keys in the secret will be the filenames of the files you provided (`username` and `password`):

    ```sh
    echo -n "admin" > username.txt
    echo -n "Pa$$w0rd" > password.txt
    
    kubectl create secret generic my-secret --from-file=username.txt --from-file=password.txt
    ```
