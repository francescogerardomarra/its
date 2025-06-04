# Db

- Db service uses a PostgreSQL image;
- the service sets the config parameters (user, password and db) as env variables;
- this image is used to persist the text inserted by the user;


- a named volume is used to not lose the data between container restarts.
