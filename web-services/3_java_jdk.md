# Installing the Latest Java JDK on Ubuntu

This guide provides step-by-step instructions to install the latest free Java Development Kit (JDK) on the latest Ubuntu operating system. Two distinct methods are covered: using the official package repository and downloading directly from Oracle.

---

## Prerequisites

1. **Ensure Ubuntu is up to date**:
   ```bash
   sudo apt update && sudo apt upgrade -y
   ```
2. **Confirm Ubuntu version** (to verify compatibility):
   ```bash
   lsb_release -a
   ```
3. **Check if Java is already installed**:
   ```bash
   java -version
   ```
   If Java is installed, uninstall it if necessary:
   ```bash
   sudo apt remove --purge openjdk-* -y
   ```

---

## Method 1: Installing OpenJDK via APT Repository

### Step 1: Add the Official Ubuntu Repository

OpenJDK is an open-source implementation of the Java Platform and is included in Ubuntu's default package repository. To ensure you're installing the latest version:

1. Update your package lists:
   ```bash
   sudo apt update
   ```
2. Search for the latest available version of OpenJDK:
   ```bash
   apt search openjdk
   ```
   Example output:
   ```
   openjdk-17-jdk - OpenJDK Development Kit (JDK) 17
   openjdk-20-jdk - OpenJDK Development Kit (JDK) 20
   ```

### Step 2: Install OpenJDK

1. Install the desired version. For example, to install OpenJDK 20:
   ```bash
   sudo apt install openjdk-20-jdk -y
   ```
2. Verify the installation:
   ```bash
   java -version
   ```
   Example output:
   ```
   openjdk version "20.0.1" 2023-04-18
   ```

### Step 3: Set Default Java Version (Optional)

If multiple Java versions are installed, configure the default version:
```bash
sudo update-alternatives --config java
```
Follow the prompts to select the appropriate version.

---

## Method 2: Installing Oracle JDK from Oracle's Website

Oracle provides the official JDK with advanced features. This section explains how to download and install the Oracle JDK manually.

### Step 1: Download Oracle JDK

1. Visit the [Oracle Java Downloads](https://www.oracle.com/java/technologies/javase-downloads.html).
2. Select the latest JDK version (e.g. JDK 20) and choose the Linux `.deb` package.
3. Download the file directly or use `wget` in the terminal. Replace `<download-link>` with the link from the Oracle site:
   ```bash
   wget <download-link>
   ```

### Step 2: Install the Downloaded Package

1. Use `dpkg` to install the package:
   ```bash
   sudo dpkg -i jdk-20_linux-x64_bin.deb
   ```
   If there are missing dependencies, fix them using:
   ```bash
   sudo apt --fix-broken install
   ```

### Step 3: Configure Java Environment Variables

1. Add the JDK to your PATH. Open the environment file:
   ```bash
   sudo nano /etc/profile
   ```
2. Append the following lines at the end of the file (adjust the paths as necessary):
   ```bash
   export JAVA_HOME=/usr/lib/jvm/jdk-20
   export PATH=$JAVA_HOME/bin:$PATH
   ```
3. Apply the changes:
   ```bash
   source /etc/profile
   ```
4. Verify the installation:
   ```bash
   java -version
   ```

---

## Additional Notes

- **Keeping Java Updated**:
  - For OpenJDK, run `sudo apt update && sudo apt upgrade` regularly.
  - For Oracle JDK, monitor updates on Oracle's website and repeat the download and installation steps as needed.

- **Uninstalling Java**:
  - To remove OpenJDK:
    ```bash
    sudo apt remove --purge openjdk-* -y
    ```
  - To remove Oracle JDK:
    ```bash
    sudo dpkg -r jdk-20
    ```

---

## Multiple JDK Installation and IntelliJ Project SDK Setup

This guide explains how to install Java 8, 11, and 21 using `wget`, `tar`, and `mv`, placing them under `/usr/lib/jvm`.

### 1. Download and Extract Java Versions
Run the following commands to install Java 8, 11, and 21:

```sh
# Create the JVM directory if it does not exist
sudo mkdir -p /usr/lib/jvm

# Install Java 8
wget https://github.com/adoptium/temurin8-binaries/releases/download/jdk8u382-b05/OpenJDK8U-jdk_x64_linux_hotspot_8u382b05.tar.gz
tar -xvzf OpenJDK8U-jdk_x64_linux_hotspot_8u382b05.tar.gz
sudo mv jdk8u382-b05 /usr/lib/jvm/java-8-temurin

# Install Java 11
wget https://github.com/adoptium/temurin11-binaries/releases/download/jdk-11.0.15+10/OpenJDK11U-jdk_x64_linux_hotspot_11.0.15_10.tar.gz
tar -xvzf OpenJDK11U-jdk_x64_linux_hotspot_11.0.15_10.tar.gz
sudo mv jdk-11.0.15+10 /usr/lib/jvm/java-11-temurin

# Install Java 21
wget https://github.com/adoptium/temurin21-binaries/releases/download/jdk-21.0.2+13/OpenJDK21U-jdk_x64_linux_hotspot_21.0.2_13.tar.gz
tar -xvzf OpenJDK21U-jdk_x64_linux_hotspot_21.0.2_13.tar.gz
sudo mv jdk-21.0.2+13 /usr/lib/jvm/java-21-temurin
```

### 2. Configure IntelliJ IDEA to Use a Specific JDK

1. **Open IntelliJ IDEA**.
2. **Go to** `File` > `Project Structure` (`Ctrl + Alt + Shift + S` on Windows/Linux).
3. **Select** `SDKs` under `Platform Settings`.
4. **Click** the `+` button and select `JDK`.
5. **Navigate to** `/usr/lib/jvm/java-8-temurin` (or the version you want) and select it.
6. **Apply** and **OK**.

### 3. Change JDK for a Specific Project

1. **Open IntelliJ IDEA**.
2. **Go to** `File` > `Project Structure` (`Ctrl + Alt + Shift + S`).
3. **Under** `Project`, find `Project SDK` and select the desired Java version.
4. **Apply** and **OK**.

### 4. Configure Maven to Use a Specific JDK

1. **Go to** `File` > `Settings` (`Ctrl + Alt + S` on Windows/Linux).
2. **Navigate to** `Build, Execution, Deployment` > `Build Tools` > `Maven`.
3. **In the JDK field**, select the desired Java version.
4. **Apply** and **OK**.

Now your system and IntelliJ IDEA are configured to use Java 8, 11, or 21 as needed.

