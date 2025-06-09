# DNS Resolution

- Docker provides automatic DNS-based container name resolution for user-defined `bridge` networks;
- containers connected to the same user-defined bridge can reference each other by name thanks to DNS;
- containers connected to default `bridge` network must reference each-other by IP.
