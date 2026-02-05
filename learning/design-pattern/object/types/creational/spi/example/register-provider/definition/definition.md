# Register the Service Providers

- in order for the SPI mechanism to discover the service providers, you need to register them;
- **before Java 9** this is done by creating a file in the `META-INF/services` directory;
- **after Java 9**, with JPMS, this is done using the [provides](../../../../../../../../java/chapter-x/versions/java9/features/jpms/after/module/how-work/module-info/directive/use-provide/provide/definition/definition.md) directive, no more file within `META-INF/services` directory is required;


- see how to create the file within `META-INF/services` directory [here](../file-creation/file_creation.md).
