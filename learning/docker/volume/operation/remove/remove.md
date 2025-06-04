# Remove

- this command deletes the Docker volume named `my_volume` from your system:

    ```commandline
    docker volume rm my_volume
    ```

- **you can only remove a volume if no container is currently using it**;
- if it's in use, Docker will throw an error:

    ```commandline
    Error response from daemon: remove my_volume: volume is in use - [03c2ff788325de5a636bfca61c062a256498339a271b9e38b7f1146d7abfccd5, 891e5f9c6e88be5820339d48035ac1b755891179edbb198c87d56832f8f10035]
    ```

    the two long numbers are the container ids that are currently using the volume.
