# Expose port 8080 (if needed)

- make sure your server firewall or cloud settings allow incoming traffic on port `8080`;
- you may want to add basic security measures, such as a secret token in the webhook URL or filtering by IP address;
- to ensure that only trusted sources (like Docker Hub) can trigger the deployment and to prevent unauthorized or malicious requests.

