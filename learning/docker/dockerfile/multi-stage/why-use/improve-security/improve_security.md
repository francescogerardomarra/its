# Improve Security

- by excluding unnecessary build tools and dependencies from the final image, you reduce the attack surface;
- the final image contains only the runtime essentials, making it less vulnerable to exploits;
- use secrets to securely pass sensitive data to a running container without storing it in the image.
