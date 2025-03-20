# Guide: Using Postman for Spring Boot Projects

## 1. **Introduction**
Postman is a popular tool for testing and interacting with RESTful APIs. It provides a user-friendly interface to send HTTP requests without needing to use `curl` in the terminal.

This guide will cover:
- Installing Postman on Ubuntu.
- Configuring Postman to interact with the three Spring Boot projects from `22_spring_boot_auto_datasource`.
- Performing the same `POST` requests using Postman instead of `curl`.

### **Advantages of Postman**
- **User-friendly Interface**: Unlike `curl`, Postman provides a graphical user interface that makes API testing more intuitive.
- **Supports Multiple Formats**: Postman supports different data formats including:
  - JSON
  - XML
  - Plain Text
  - Form-data
  - GraphQL
  - Protocol Buffers (Protobuf)
- **Request History**: Easily manage and store previous requests for reuse.
- **Automation & Testing**: Supports scripting and automation through Postman Collections.
- **Collaboration**: Teams can share API requests and test cases.
- **Built-in Authentication**: Supports OAuth, JWT, Basic Auth, and API Keys.
- **Environment Variables**: Allows configuration of multiple environments for different API stages (development, staging, production).

## 2. **Installing Postman on Ubuntu**
### **What is Snap and How is it Different from APT?**
Snap is a package management system that allows applications to be installed as self-contained packages with all dependencies bundled. It works across different Linux distributions and provides automatic updates.

APT (Advanced Package Tool) is the traditional package manager for Debian-based distributions like Ubuntu. It installs software from the official system repositories and manages dependencies separately.

| Feature  | Snap | APT |
|----------|------|-----|
| **Source** | Snap Store | Ubuntu/Debian Repositories |
| **Updates** | Automatic | Manual |
| **Dependency Handling** | Bundled in the package | Managed separately |
| **Compatibility** | Works across distributions | Specific to Debian-based systems |

### Dependency handling: Snap vs APT

Package managers handle software dependencies differently, affecting installation success, system efficiency, and compatibility. This article explains how **Snap** and **APT** manage dependencies and what happens when a required dependency is missing.

## **APT (Advanced Package Tool)**
APT is the package manager for Debian-based systems (e.g., Ubuntu). It manages dependencies by resolving them from official repositories.

### **How APT Handles Dependencies**
1. When installing a package, APT checks its dependency list.
2. If dependencies are missing, APT downloads and installs them automatically from repositories.
3. If a required dependency is already installed, APT skips it to avoid redundancy.
4. Dependencies are shared across packages, optimizing disk space and system efficiency.

### **What Happens When a Dependency is Missing in APT?**
- **Automatic Resolution:** APT attempts to install missing dependencies.
  ```bash
  sudo apt install some-package
  ```
  Output:
  ```
  The following additional packages will be installed: dependency1 dependency2
  ```
- **Failure Due to Unavailable Dependency:** If a dependency is not found or conflicts with another package, the installation fails.
  ```
  The following packages have unmet dependencies:
  some-package : Depends: missing-package but it is not installable
  E: Unable to correct problems, you have held broken packages.
  ```
  **Solutions:**
  - Add missing repositories (`sudo add-apt-repository`)
  - Use `apt --fix-broken install`
  - Manually install the missing dependency

## **Snap**
Snap packages are self-contained and include all necessary dependencies within the package.

### **How Snap Handles Dependencies**
1. Each Snap package bundles its dependencies inside the package itself.
2. There is **no dependency resolution during installation**.
3. Applications run in isolation (sandboxed) with their dependencies.
4. Snap packages do not share dependencies with the system or other Snaps.

### **What Happens When a Dependency is Missing in Snap?**
- **It Never Happens!** Since Snap packages include all their dependencies, missing dependencies do not occur during installation.
- **Potential Issues Instead:**
  - **Large File Size:** Snaps are bigger because they contain all dependencies.
  - **Older Dependencies:** A package may ship with outdated libraries that do not get system-wide updates.
  - **Slower Startup Times:** Due to sandboxing and bundled dependencies.

### **Comparison Table**
| Feature  | APT  | Snap  |
|----------|------|------|
| Handles Missing Dependencies | Downloads and installs from repositories | Bundles everything, no missing dependencies |
| Installation Failure | Can fail if dependencies are unavailable | Does not fail due to missing dependencies |
| Disk Usage | Lower (shared dependencies) | Higher (dependencies duplicated) |
| Compatibility | May break if system dependencies change | Always works since dependencies are included |

### **Final Thoughts**
- **APT is best for system efficiency**, as it shares dependencies and minimizes disk usage.
- **Snap ensures application stability**, as each package contains everything it needs.
- **Missing dependencies in APT can cause failures**, whereas Snap eliminates this issue at the cost of storage and performance trade-offs.

Understanding these differences helps in choosing the right package management system for your needs.

### **Advantages and Disadvantages of Snap**
#### **Advantages:**
- **Cross-Distribution Support**: Works on multiple Linux distributions without modification.
- **Self-Contained Packages**: No dependency conflicts as all necessary libraries are included.
- **Automatic Updates**: Keeps applications up to date without manual intervention.
- **Security Sandboxing**: Provides isolation for applications, improving security.

#### **Disadvantages:**
- **Larger Package Size**: Snap packages include dependencies, making them larger than APT packages.
- **Slower Startup Time**: Due to sandboxing, Snap applications may take longer to start.
- **Less System Integration**: Snap applications do not always integrate seamlessly with system themes and configurations.
- **Limited Package Availability**: Some applications are not yet available in the Snap store.

### **Checking if Snap is Installed**
To check if Snap is installed, run:
```bash
snap --version
```
If Snap is not installed, install it with:
```bash
sudo apt update
sudo apt install snapd -y
```

### **Method 1: Installing via Snap (Recommended)**
```bash
sudo snap install postman
```

### **Method 2: Installing via Direct Download**
```bash
wget https://dl.pstmn.io/download/latest/linux64 -O postman.tar.gz
sudo tar -xzf postman.tar.gz -C /opt
sudo ln -s /opt/Postman/Postman /usr/bin/postman
```
After installation, run Postman:
```bash
postman
```

## 3. **Configuring Postman for API Requests**
### **Step 1: Open Postman and Create a New Request**
1. Launch Postman.
2. Click **New Request**.
3. Select **POST** as the HTTP method.
4. Enter the URL of your running Spring Boot application.

### **Step 2: Configuring the Request Body**
- Go to the **Body** tab.
- Select **x-www-form-urlencoded**.
- Add the parameters used in the `curl` command.

### **What is x-www-form-urlencoded?**
The `x-www-form-urlencoded` format is a way of encoding key-value pairs in a request body. It is commonly used when submitting form data in HTTP requests.

#### **Alternatives to x-www-form-urlencoded**
- **JSON (`application/json`)**: Used for APIs that expect JSON payloads.
- **Form-Data (`multipart/form-data`)**: Used for file uploads and complex data structures.
- **Plain Text (`text/plain`)**: Sends raw text data.
- **XML (`application/xml`)**: Used for XML-based APIs.

## 4. **Performing POST Requests for Each Project**

### **Project 1: Plain JDBC Example**
#### **Equivalent cURL Command:**
```bash
curl -X POST "http://localhost:8080/api/person/add" \
     -d "name=John" \
     -d "surname=Doe" \
     -d "city=NewYork" \
     -d "age=30"
```
#### **Postman Configuration:**
- **Method:** `POST`
- **URL:** `http://localhost:8080/api/person/add`
- **Body (x-www-form-urlencoded):**
  - `name`: `John`
  - `surname`: `Doe`
  - `city`: `NewYork`
  - `age`: `30`
- Click **Send**.
- Expected response: `Person added successfully`.

## 5. **Verifying Data in PostgreSQL**
After making the requests, verify that the data was inserted correctly.
```bash
sudo -u postgres psql -d my_app_db
```
```sql
SELECT * FROM my_schema.person;
```
Expected output:
```
 id |  name  | surname |      city      | age 
----+--------+---------+----------------+-----
  1 | John   | Doe     | NewYork        | 30 
  2 | Jane   | Smith   | Chicago        | 25 
  3 | Alice  | Brown   | SanFrancisco   | 28 
```

## 6. **Conclusion**
You have successfully installed Postman and used it to interact with the three Spring Boot projects. This approach provides a more user-friendly and efficient way to test REST APIs compared to `curl`. 🎯


