# Security and Access Control

- **secure integration**: 
  - GitHub and Docker Hub are linked securely using **access tokens** or **OAuth**, ensuring that only authorized connections are established.
- **repository access control**: 
  - you can specify which **GitHub repositories** Docker Hub can access for automated builds.
- **triggering builds**: 
  - automated builds on Docker Hub are triggered by changes (e.g., code pushes) made in the linked GitHub repository;
  - therefore, controlling who can trigger these builds depends on managing **GitHub repository permissions**, such as restricting push access to specific branches or enforcing branch protection rules.
 

- **image access management**: 
  - once Docker images are built and stored in Docker Hub, you control who can access or pull these images by setting permissions for teams, collaborators, or deployment systems.
- **enhanced security**: 
  - these measures ensure that only authorized users or systems can push code that triggers builds, view, or pull images, protecting your resources from unauthorized access.  
