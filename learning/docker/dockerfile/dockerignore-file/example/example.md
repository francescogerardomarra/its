# Example

In this chapter, we show an example of a `.dockerignore` file.

**`.dockerignore` File Example:**

```dockerignore
# Ignore all .log files
*.log

# Ignore node_modules directory
node_modules/

# Ignore all build artifacts except final binary
build/
!build/final-binary

# Ignore all files starting with temp
temp*
```
