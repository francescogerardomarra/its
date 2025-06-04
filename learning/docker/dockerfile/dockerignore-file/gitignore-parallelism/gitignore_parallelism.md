# Parallelism with `.gitignore` File

In this chapter, we show a parallelism between `.dockerignore` and `.gitignore` files.

**Table:**

| Feature/Aspect           | `.dockerignore`                                                                             | `.gitignore`                                                                               |
|--------------------------|---------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| Purpose                  | excludes files and directories from being sent to Docker during a build context transfer.   | excludes files and directories from being tracked by Git.                                  |
| Context                  | used specifically for Docker to optimize build processes and reduce the build context size. | used in Git to prevent unnecessary or sensitive files from being added to version control. |
| Scope                    | affects files during the Docker build process.                                              | affects files during Git add/commit processes.                                             |
| Syntax                   | shares the same syntax as `.gitignore`.                                                     | follows its specific syntax.                                                               |
| Location                 | must be located in the root of the Docker build context.                                    | can exist in any directory in a Git repository.                                            |
| Use of wildcards         | supports patterns like `*`, `?`, and `**`.                                                  | supports patterns like `*`, `?`, and `**`.                                                 |
| Purpose of ignored files | reduces build context size to improve Docker build efficiency.                              | prevents unnecessary files from being added to Git version control.                        |
| Default ignored files    | none are ignored by default unless specified.                                               | some files are ignored by default (e.g., `.git`).                                          |
| Key use case             | ignoring large files or sensitive data in Docker images.                                    | ignoring build artifacts, logs, or sensitive data in Git repositories.                     |
