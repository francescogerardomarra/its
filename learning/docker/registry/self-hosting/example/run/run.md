# Run the Registry Container

Use the following command to start a basic registry:

   ```commandline
   docker run -d -p 5000:5000 --name registry registry:2
   ```

**Command Overview:**

- `-d`: run the container in detached mode;
- `-p 5000:5000`: map port 5000 of the container to port 5000 on the host;
- `registry:2`: use the official Docker Registry image (version 2).
