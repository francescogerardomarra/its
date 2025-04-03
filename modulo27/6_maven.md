# Guide to Installing Apache Maven on Ubuntu (Latest Version)

Apache Maven is a powerful build automation tool primarily used for Java projects. This guide provides two methods for installing Maven on the latest Ubuntu operating system. Ensure you have Java installed, as Maven requires Java to function properly. We'll first verify Java installation before proceeding.

## Prerequisites

- Ubuntu (latest version)
- Administrative privileges (sudo access)

---

## Step 1: Verify Java Installation

Apache Maven requires Java. To verify if Java is installed, run the following command in your terminal:

```bash
java -version
```

If Java is installed, you should see output similar to:

```plaintext
openjdk version "17.x.x"
OpenJDK Runtime Environment (build 17.x.x)
OpenJDK 64-Bit Server VM (build 17.x.x, mixed mode)
```

If you do not see such output, install Java by following Ubuntu’s official Java installation guide.

---

## Method 1: Installing Maven Using `apt`

The `apt` package manager allows you to install Maven directly from the Ubuntu repositories. However, the version available via `apt` may not always be the latest.

### Steps:

1. **Update the Package Index:**

   ```bash
   sudo apt update
   ```

2. **Install Maven:**

   ```bash
   sudo apt install maven -y
   ```

3. **Verify the Installation:**

   Check the installed Maven version to confirm:

   ```bash
   mvn -version
   ```

   Example output:

   ```plaintext
   Apache Maven 3.6.x
   Maven home: /usr/share/maven
   Java version: 17.x.x, vendor: Private Build, runtime: /usr/lib/jvm/java-17-openjdk-amd64
   Default locale: en_US, platform encoding: UTF-8
   OS name: "linux", version: "5.x.x", arch: "amd64", family: "unix"
   ```

This confirms that Maven is successfully installed and linked to your Java installation.

---

## Method 2: Manual Installation from Apache Maven Official Website

For the latest version of Maven, it’s recommended to download it directly from the official website.

### Steps:

1. **Download the Latest Maven Binary Archive:**

   Visit the [Apache Maven Download Page](https://maven.apache.org/download.cgi) and copy the URL for the latest binary tar.gz file. Use `wget` to download it:

   ```bash
   wget https://dlcdn.apache.org/maven/maven-3/X.X.X/binaries/apache-maven-X.X.X-bin.tar.gz
   ```

   Replace `X.X.X` with the latest version number (e.g. `3.9.0`).

2. **Extract the Archive:**

   Extract the downloaded file to the `/opt` directory:

   ```bash
   sudo tar -xvzf apache-maven-X.X.X-bin.tar.gz -C /opt
   ```

3. **Create a Symlink for Easy Access:**

   Rename the extracted folder for convenience:

   ```bash
   sudo ln -s /opt/apache-maven-X.X.X /opt/maven
   ```

4. **Set Up Environment Variables:**

   Edit the `~/.bashrc` or `~/.zshrc` file to configure Maven's environment variables:

   ```bash
   nano ~/.bashrc
   ```

   Add the following lines at the end of the file:

   ```bash
   export M2_HOME=/opt/maven
   export PATH=$M2_HOME/bin:$PATH
   ```

   Save and close the file, then reload it:

   ```bash
   source ~/.bashrc
   ```

5. **Verify the Installation:**

   Check the installed Maven version:

   ```bash
   mvn -version
   ```

   Example output:

   ```plaintext
   Apache Maven 3.9.0
   Maven home: /opt/maven
   Java version: 17.x.x, vendor: Private Build, runtime: /usr/lib/jvm/java-17-openjdk-amd64
   Default locale: en_US, platform encoding: UTF-8
   OS name: "linux", version: "5.x.x", arch: "amd64", family: "unix"
   ```

---

## Conclusion

You have successfully installed Apache Maven on Ubuntu using two methods:

1. Via the `apt` package manager (easier but might not provide the latest version).
2. Manually from the official Apache Maven website (provides the latest version).

For most users, the `apt` method suffices. However, if your project requires the latest Maven features, opt for the manual installation method.

