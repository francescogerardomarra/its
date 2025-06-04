# Public Registry

- **definition:**
  - a public registry is a platform where container images are stored and made accessible to the public;
  - example: [Docker Hub](https://hub.docker.com/), which is the default public registry used by Docker.

- **access control:**
  - open to everyone;
  - anyone can pull public images;
  - limited control over who can access or download images.

- **use case:**
  - ideal for sharing open-source projects or images meant for the general public;
  - useful for developers looking to share or discover widely used images (e.g., `nginx`, `redis`).


- **cost:**
  - free to use for public images;
  - private repositories on public registries (e.g., Docker Hub private repos) often require a subscription.

- **security:**
  - limited control over security, though public registries like Docker Hub provide automated vulnerability scanning for images.

- **scalability:**
  - highly scalable; 
  - managed by large organizations (e.g., Docker Hub, Google Container Registry).

- **example:**
  - Docker Hub, Google Container Registry (public), Quay.io (public).
