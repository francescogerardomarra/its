# How It Works

- each container connected to the bridge network gets:
  - its own IP address;
  - a virtual Ethernet interface.
- Docker uses a virtual switch (bridge) on the host machine to connect these containers.

**Image:**

<img src="img/bridge_network.png" width="550">
