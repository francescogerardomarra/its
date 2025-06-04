# Kubernetes Definition

- Kubernetes is an open-source container orchestration platform that automates the deployment, scaling, and management of containerized applications across a cluster of machines;
- Kubernetes organizes applications into **pods**, schedules them on **nodes**, and uses controllers like **Deployments** and **StatefulSets** to maintain desired state, handle scaling, and ensure high availability;
- Kubernetes **secrets** are objects used to store sensitive data, such as passwords, API keys, or TLS certificates, in an encrypted and secure manner rather than in plain text in environment variables or configuration files;


- secrets can be created manually (`kubectl create secret`), referenced in **pods** as environment variables or mounted as volumes, and are encrypted at rest if configured with the appropriate security settings (e.g., encryption with KMS providers).
