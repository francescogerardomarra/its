# Quick Troubleshooting

- to verify if a container is connected to a network, inspect the network and look under the `Containers` section to see which containers are attached.
- to test if one container can reach another over a Docker network, follow these steps:

  1. start a shell session in a container that is connected to the same network as the target container:

     ```bash
     docker exec -it <container-name> sh  # or 'bash' depending on the container's shell
     ```

  2. use tools like `ping`, `curl`, or `dig` to check if the container can reach the target:

     ```bash
     ping <target-container-name or IP>  # Use the container name for bridge/user-defined networks
     ```

- check `/etc/hosts` and `/etc/resolv.conf` inside the container to understand how DNS and hostname resolution is set up (useful if containers cannot reach each other using names):

  - `/etc/hosts`: 
    - Docker injects host-to-IP mappings here, especially for containers on user-defined bridge networks;
    - this allows containers to resolve each other's names.
  - `/etc/resolv.conf`: 
    - contains DNS configuration (often pointing to Docker's internal DNS server), which handles name resolution in the container network.
