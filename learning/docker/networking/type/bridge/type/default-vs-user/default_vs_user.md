# Default VS User-Defined Network

**Default bridge network:**
- **name and creation:**
  - automatically created when Docker is installed;
  - named `bridge`.

- **container DNS resolution:**
  - containers **can't resolve** each other by name (only by IP);
  - you need to manually link containers or use IP addresses.
  
- **isolation and security:**
  - less isolated;
  - all containers connected to it can talk to each other;
  - not great for more complex or secure setups.

- **network scoping:**
  - shared by all containers unless specified otherwise.

- **customization options:**
  - very limited configuration.

- **Docker Compose compatibility:**
  - not used by default in Compose.

**User-defined network:**
- **name and creation:**
  - created manually using `docker network create`;
  - you choose the name.

- **container DNS resolution:**
  - containers **can resolve** each other by name using Docker's internal DNS;
  - much easier to manage multi-container setups.

- **isolation and security:**
  - better isolation;
  - only containers on the same custom network can talk;
  - you can define multiple networks for better control.

- **network scoping:**
  - containers must explicitly be added to it, which helps avoid accidental exposure.

- **customization options:**
  - you can define subnet, gateway, IP range, etc.

- **Docker Compose compatibility:**
  - Docker Compose automatically creates a custom bridge network for the app, which means containers in the same service can easily communicate by name.
