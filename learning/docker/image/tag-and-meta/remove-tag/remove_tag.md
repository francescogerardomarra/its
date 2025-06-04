# Remove a Tag

docker tag myapp:2.0 myapp:latest

myapp:1.0 → still points to abc123.
myapp:2.0 → points to the new image (e.g., def456).
myapp:latest → now points to the same image as myapp:2.0 (def456).

docker rmi myapp:latest

This removes the latest tag, but the image still exists in your local system because it is also tagged as myapp:1.0.

docker images
