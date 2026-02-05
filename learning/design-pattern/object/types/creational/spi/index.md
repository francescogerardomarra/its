# Service Provider Interface (SPI)

- [Definition](definition/definition.md)
- [Benefits](benefit/benefit.md)
- Example
  - [Definition](example/definition/definition.md)
  - [Project structure](example/project-structure/project_structure.md)
  - Service
    - [`PaymentProcessor` interface](example/service/paymentprocessor/paymentprocessor.md)
  - Providers
    - [`CreditCardProcessor` class](example/provider/creditcardprocessor/creditcardprocessor.md)
    - [`PayPalProcessor` class](example/provider/paypalprocessor/paypalprocessor.md)
  - Register the service providers
    - [Definition](example/register-provider/definition/definition.md)
    - [`PaymentProcessor` file creation](example/register-provider/file-creation/file_creation.md)
    - File conditions
      - [Definition](example/register-provider/file-condition/definition/definition.md)
      - [File naming](example/register-provider/file-condition/file-naming/file_naming.md)
      - [File location](example/register-provider/file-condition/file-location/file_location.md)
      - [Content](example/register-provider/file-condition/content/content.md)
      - [Correct implementation](example/register-provider/file-condition/implementation/implementation.md)
  - [Load the service providers](example/load-provider/load_provider.md)
  - [Output](example/output/output.md)

// TODO: how to extract the specific provider
