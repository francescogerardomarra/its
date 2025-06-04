# Create the Secrets

To create the db credentials as secrets, open a teminal and run these commands:

```commandline
echo "postgres" | docker secret create db_user -
```

```commandline
echo "postgres1234" | docker secret create db_password -
```
