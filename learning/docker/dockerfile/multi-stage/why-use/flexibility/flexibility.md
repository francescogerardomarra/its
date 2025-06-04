# Flexibility with Multiple Targets

- you can define different stages for various environments (e.g., development, testing, and production) and selectively copy only the necessary files into the final image;
- this ensures that unnecessary dependencies or debug tools are not included in the production build;
- each stage is independent, allowing environment-specific optimizations;


- for example, you can use a full-fledged Node.js image for development (with debugging tools) but switch to a minimal Alpine-based image for production to keep it lightweight;
- this approach improves build efficiency and security by ensuring that only the required artifacts make it into the final image, reducing attack surfaces and avoiding unnecessary bloating.
