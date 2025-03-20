# Installing IntelliJ IDEA Community Edition on Ubuntu

This guide provides step-by-step instructions to install IntelliJ IDEA Community Edition, a free and powerful IDE for Java and other programming languages, on the latest version of Ubuntu. 

---

## Prerequisites
Before starting, ensure the following:

- You have administrative access to your Ubuntu system.
- Your system is connected to the internet.
- Java Development Kit (JDK) is installed (IntelliJ IDEA requires a JDK).

---

## Step 1: Install Java Development Kit (JDK)
See steps to install Java and verify the installation:

   ```bash
   java -version
   ```
   
This should display the installed version of Java.

---

## Step 2: Download IntelliJ IDEA Community Edition
1. Open a web browser and navigate to the official JetBrains download page:
   [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
2. Under the **Community** edition, click the **Download** button for Linux.

Alternatively, you can use the Terminal to download IntelliJ IDEA directly:
   ```bash
   wget https://download.jetbrains.com/idea/ideaIC-latest.tar.gz
   ```

---

## Step 3: Extract the IntelliJ IDEA Archive
1. Navigate to the directory where the file was downloaded. For example:
   ```bash
   cd ~/Downloads
   ```
2. Extract the downloaded `.tar.gz` file:
   ```bash
   tar -xvzf ideaIC-latest.tar.gz
   ```
3. Move the extracted folder to `/opt`, a standard location for third-party applications:
   ```bash
   sudo mv idea-IC-* /opt/intellij
   ```

---

## Step 4: Launch IntelliJ IDEA
1. Navigate to the `bin` directory of IntelliJ IDEA:
   ```bash
   cd /opt/intellij/bin
   ```
2. Start IntelliJ IDEA:
   ```bash
   ./idea.sh
   ```
3. Follow the on-screen instructions to complete the initial setup:
   - Choose your theme (e.g., Light or Dark).
   - Skip or configure plugins as needed.

---

## Step 5: Create a Desktop Shortcut (Optional)
1. Create a `.desktop` file for IntelliJ IDEA:
   ```bash
   sudo nano /usr/share/applications/intellij-idea-ce.desktop
   ```
2. Add the following content to the file:
   ```plaintext
   [Desktop Entry]
   Name=IntelliJ IDEA Community Edition
   Comment=Free IDE for Java and other languages
   Exec=/opt/intellij/bin/idea.sh
   Icon=/opt/intellij/bin/idea.png
   Terminal=false
   Type=Application
   Categories=Development;IDE;
   ```
3. Save and close the file (`Ctrl+O`, then `Ctrl+X`).
4. Refresh the desktop database:
   ```bash
   sudo update-desktop-database
   ```
5. You can now search for IntelliJ IDEA in the Applications menu.

---

## Step 6: Verify Installation
1. Launch IntelliJ IDEA from the Applications menu or by running `./idea.sh` from the terminal.
2. Open or create a project to ensure the IDE is functioning correctly.

---

## Additional Notes
- For frequent updates, consider using **Snap** to install IntelliJ IDEA Community Edition:
  ```bash
  sudo snap install intellij-idea-community --classic
  ```
- Consult the official documentation for troubleshooting or advanced configuration: [IntelliJ IDEA Documentation](https://www.jetbrains.com/help/idea/).

---

With these steps, you should have IntelliJ IDEA Community Edition successfully installed and running on your Ubuntu system. Happy coding!

