# Decode Secret Data

- **extracting a specific secret value:** the command retrieves the `username` field from the `my-secret` Kubernetes secret in a JSON format using `kubectl get secret my-secret -o jsonpath="{.data.username}"`;
- **Base64 decoding:** since Kubernetes stores secret values in base64 encoding, the output is piped (`|`) to `base64 --decode` to convert it back into human-readable text;
- **secure access:** this method allows viewing specific secret values without exposing the entire secret YAML, reducing the risk of unintended data leaks;


- **`-o jsonpath="{.data.username}"` flag:** this flag formats the output using JSONPath, extracting only the `username` field from the `data` section of the secret, which is stored in base64 encoding;
- **output without the `-o jsonpath="{.data.username}"` flag:** if the flag is omitted, the command will return the entire secret object in YAML or JSON format, including all metadata and encoded data fields, instead of just the `username` value.

**Command to Decode Secret Data:**

```commandline
kubectl get secret my-secret -o jsonpath="{.data.username}" | base64 --decode
```
