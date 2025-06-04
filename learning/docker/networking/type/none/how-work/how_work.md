# How It Works

- the container has no network interfaces other than the loopback interface (`lo`);
- it cannot communicate with other containers, services, or external networks, including the internet;
- this is basically network isolation, the container is running in complete network silence.
