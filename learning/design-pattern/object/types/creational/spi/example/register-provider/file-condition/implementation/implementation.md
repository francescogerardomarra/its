# Correct Implementation

- the file should not contain extra content, it should only list the fully qualified names of the implementation classes (service providers);
- the classes listed in the file should correctly implement the service interface;
- if a listed class does not properly implement the interface or cannot be instantiated, it will result in a `ServiceConfigurationError` when `ServiceLoader` tries to load it.
