# How It Works

- an `overlay` network is like a magic LAN that spans multiple physical machines;
- containers on different hosts can talk to each other as if they were on the same subnet;
- Docker does this using VXLAN tunneling (which is a complex network tunneling protocol that we aren't going to explain in this documentation).
