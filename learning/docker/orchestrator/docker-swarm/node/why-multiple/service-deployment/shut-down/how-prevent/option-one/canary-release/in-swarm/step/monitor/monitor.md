# Monitor the Canary

- use logs, metrics, or monitoring tools (like Prometheus + Grafana) to observe:
  - errors;
  - latency;
  - resource usage;
  - user feedback.

- if everything looks good, gradually increase the traffic weight directed to `myapp-v2`.
