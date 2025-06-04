# Comparison with VM

- in this chapter, we show a comparison on the host kernel usage between Docker and VMs;
- as you can see from the below pictures, containers rely on host kernel;
- while VMs rely on their own OS kernel;


- **containers contain only apps, some lightweight operating system APIs and services that run in user mode**.

**Image:**

_Containers:_

<img src="img/container_os_kernel.svg" width="1200">

_VMs:_

<img src="img/vm_os_kernel.svg" width="1200">
