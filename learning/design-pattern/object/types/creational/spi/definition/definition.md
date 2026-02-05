# Service Provider Interface (SPI) Definition

- the service provider interface (SPI) in Java is a mechanism that allows developers to define and use pluggable service implementations;
- it involves creating an interface (the service) and one or more classes that implement this interface (the service providers);
- the Java module system or the `META-INF/services` directory is used to register these providers, enabling runtime discovery and loading of the implementations without hardcoding them in the application.
