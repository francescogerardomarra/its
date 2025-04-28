# Guide to Installing a Virtual Machine (VM) on Windows with Ubuntu

This guide walks you through the process of installing a virtual machine (VM) on a Windows OS using free software and installing the latest version of Ubuntu as the guest operating system. By the end, you will have a fully functional Ubuntu VM running on your Windows machine.

---

## Prerequisites

1. **System Requirements:**
   - A Windows PC with at least 8 GB of RAM (16 GB recommended).
   - At least 50 GB of free disk space.
   - A stable internet connection.

2. **Software Needed:**
   - Oracle VirtualBox: Free and open-source virtualization software.
   - Ubuntu ISO file: The latest Ubuntu desktop image.

---

## Step 1: Download Required Software

### 1.1 Download VirtualBox
- Visit the [VirtualBox website](https://www.virtualbox.org/).
- Navigate to the **Downloads** section.
- Select the **Windows Hosts** option and download the installer.

### 1.2 Download Ubuntu ISO
- Go to the [Ubuntu website](https://ubuntu.com/download/desktop).
- Click the **Download** button for the latest Ubuntu LTS (Long Term Support) version.
- Save the ISO file to a known location.

---

## Step 2: Install VirtualBox

### 2.1 Run the Installer
- Locate the VirtualBox installer you downloaded (e.g. `VirtualBox-x.x.x-xxxx-Win.exe`).
- Double-click the installer to launch it.

### 2.2 Follow Installation Steps
- Click **Next** to proceed through the setup wizard.
- Select installation options (default options are usually fine).
- Accept any prompts about network interfaces and proceed.
- Click **Install** and wait for the installation to complete.
- Once done, click **Finish** to launch VirtualBox.

---

## Step 3: Create a Virtual Machine

### 3.1 Open VirtualBox
- Launch VirtualBox from the Start Menu or desktop shortcut.

### 3.2 Create a New VM
1. Click the **New** button in the VirtualBox Manager.
2. Fill out the details:
   - **Name:** `Ubuntu` (or any name of your choice).
   - **Machine Folder:** Leave as default or choose a location.
   - **Type:** `Linux`.
   - **Version:** `Ubuntu (64-bit)`.
3. Click **Next**.

### 3.3 Allocate Resources
1. **Memory Size:** Allocate at least 4 GB (4096 MB). Use 8 GB or more if your system allows.
2. **Hard Disk:**
   - Select **Create a virtual hard disk now**.
   - Choose **VDI (VirtualBox Disk Image)** and click **Next**.
   - Select **Dynamically allocated** and click **Next**.
   - Set the disk size to at least 25 GB (50 GB recommended).
   - Click **Create**.

---

## Step 4: Configure VM Settings

1. Select your newly created VM in the VirtualBox Manager.
2. Click **Settings**.
3. Adjust the following settings:

### 4.1 System
- Navigate to the **System** tab.
- Under **Processor**, allocate at least 2 CPUs (4 recommended if available).

### 4.2 Storage
- Click the **Storage** tab.
- Under **Controller: IDE**, select the empty optical drive.
- Click the disk icon and choose **Choose a disk file**.
- Locate and select the Ubuntu ISO file you downloaded.

### 4.3 Network
- Go to the **Network** tab.
- Ensure **Attached to** is set to **NAT** (default setting).

Click **OK** to save your settings.

---

## Step 5: Install Ubuntu

### 5.1 Start the VM
- Select your VM in the VirtualBox Manager.
- Click **Start** to launch the VM.
- VirtualBox will boot from the Ubuntu ISO.

### 5.2 Install Ubuntu
1. Follow the on-screen instructions to install Ubuntu:
   - Select your preferred language.
   - Click **Install Ubuntu**.
   - Choose **Normal installation**.
   - Check **Download updates while installing Ubuntu**.
   - Click **Continue**.
2. Disk Setup:
   - Select **Erase disk and install Ubuntu** (this only applies to the virtual disk, not your physical drive).
   - Click **Install Now** and confirm.
3. Configure Ubuntu:
   - Choose your time zone.
   - Create a user account (set a username and password).
4. Wait for the installation to complete.
5. Click **Restart Now** when prompted.

### 5.3 Remove Installation Media
- When prompted to remove the installation media, go to the VirtualBox window:
  - Click **Devices > Optical Drives > Remove Disk from Virtual Drive**.
  - Press **Enter** to reboot.

---

## Step 6: Finalize Setup

### 6.1 Update Ubuntu
- After logging into Ubuntu, open a terminal.
- Run the following commands to update the system:
  ```bash
  sudo apt update && sudo apt upgrade -y
  ```

### 6.2 Install VirtualBox Guest Additions
1. In the VirtualBox menu, click **Devices > Insert Guest Additions CD Image**.
2. Follow the on-screen prompts in Ubuntu to install the Guest Additions.
3. Restart the VM after installation.

---

## Step 7: Using Your Ubuntu VM

Congratulations! You now have a fully functional Ubuntu virtual machine running on your Windows PC. Use it for programming, testing, or exploring the Linux environment.

---

## Troubleshooting

- **Performance Issues:**
  - Allocate more memory or CPU cores to the VM.
  - Enable hardware virtualization (VT-x/AMD-V) in your BIOS.

- **Network Issues:**
  - Ensure the network adapter is set to NAT or Bridged Adapter.

- **Boot Issues:**
  - Verify the ISO file integrity and ensure it is correctly attached to the VM.

For further assistance, consult the [VirtualBox Documentation](https://www.virtualbox.org/wiki/Documentation) or the [Ubuntu Community Help Wiki](https://help.ubuntu.com/community).

---

*End of Guide*

