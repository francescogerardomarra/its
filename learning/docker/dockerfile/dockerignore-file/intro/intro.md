# `.dockerignore` File Introduction

- when you run the docker build command, Docker uses the contents of the current directory (or the specified build context) to build the image;
- the `.dockerignore` file helps optimize the build process by excluding unnecessary files and directories from the build context that are:
  - **not needed in the image:** files like development tools, temporary files, or version control metadata (e.g., `.git` or `.svn` folders);
  - **large files:** avoids adding large files to reduce build time;
  - **sensitive data:** prevents inclusion of credentials or private files (e.g., `.env` files).
