# Roll Forward or Roll Back

- if the canary is stable, eventually:
  - remove `myapp-v1`;
  - scale up `myapp-v2` to take over.

- if issues are found, simply remove or scale down the canary (`myapp-v2`).
