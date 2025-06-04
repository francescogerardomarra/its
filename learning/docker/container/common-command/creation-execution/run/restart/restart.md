# Restart Policy

**Command:**

```commandline
docker run -d \
--restart=always \
-p 32000:5000 \
--name registry \
registry:2
```

**Command explanation:**

* the `--restart=always` option ensures the container will always restart if it stops unexpectedly or if the Docker daemon restarts (usually after machine reboot); 
* by default, containers do not restart automatically in either of these cases;
* it helps maintain container uptime by automatically recovering from crashes or system reboots;


* with `--restart=always`, the container will restart when the Docker daemon restarts (usually after a machine reboot), even if you manually stopped it earlier, unless you also remove it with `docker rm`;
* this option is useful for long-running services that need high availability.

**All restarts:**

Here are all the possible `--restart` options in Docker:

* `no`: the default behavior, the container will not restart automatically under any circumstances;
* `on-failure`: restarts the container **only if it exits with a non-zero exit code**, indicating an error;
* `on-failure:N`: same as `on-failure`, but limits the number of restart attempts to **N times** before giving up;


* `always`: always restarts the container **regardless of the exit status**, including after Docker daemon restarts;
* `unless-stopped`: like `always`, but **won’t restart** the container if it was **manually stopped** by the user.
