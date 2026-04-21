# ESbot Setup Instructions

This setup manual only covers Linux Mint for other distributions or operating systems please visit the website or manual of the maintainer.

## Prerequisites
- Git
- node.js v24
- npm v11.12.0
- Angular CLI
- Docker
- Postgres v17.9 Docker Image

For a installation guide for Linux Mint please see **Install Dependencies** below.

## Install Dependencies
**1. Install git**
```bash
sudo apt install -y git
```

**2. Install node.js v24 and npm**
```bash 
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.4/install.sh | bash
export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion" 
\. "$HOME/.nvm/nvm.sh"
nvm install 24
node -v
npm -v
```
**3. Install Angular CLI**
```bash
npm install -g npm@11.12.0
npm install -g @angular/cli
```

**4. Install JDK 21**
```bash
sudo apt install -y openjdk-21-jdk
```

**5. Install Docker and get the Postgres Image**
- **Add the Docker repository and update APT**
    ```bash
    echo -e "\nAdding Repositories\n"
    sudo apt install -y ca-certificates curl
    sudo install -m 0755 -d /etc/apt/keyrings
    sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
    sudo chmod a+r /etc/apt/keyrings/docker.asc

    sudo tee /etc/apt/sources.list.d/docker.sources <<EOF
    Types: deb
    URIs: https://download.docker.com/linux/ubuntu
    Suites: $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}")
    Components: stable
    Architectures: amd64,arm64
    Signed-By: /etc/apt/keyrings/docker.asc
    EOF

    sudo apt update
    ```
- **Install Docker with dependencies**
    ```bash
    sudo apt install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    ```
- **Start the Docker service and set permissions**
    ```bash
    sudo systemctl start docker
    sudo groupadd docker
    sudo usermod -aG docker $USER
    ```
- **Get the Postgres 17.9 image**
    ```bash
    sudo docker pull postgres:17.9
    ```

**6. Install Maven**
```bash
sudo apt install -y maven
```

## Clone the repository

**- Using ssh:**
```bash
git clone ssh://git@github.com:/hse-st-group1/esbot
```

**- Using https:**
```bash
git clone https://github.com/hse-st-group1/esbot
```

### Start the Postgres container
**- Navigate into esbot**
```bash
cd ./esbot
```
**- Start the container**
```bash
sudo docker compose up -d
```

## Perform a clean install

**- Navigate into the backend folder**
```bash
cd ./application/backend/
```
**- Perform a clean install**
```bash
mvn clean install
```

### Start Spring-Boot using Maven
```bash
mvn spring-boot:run
```

### Test the backend using Maven - Full Test Suite
```bash
mvn test
```

### Test the backend using Maven - Unit Tests
```bash
mvn surefire:test -Dtest=*Test.java
```

### Test the backend using Maven - BDD Tests
```bash
mvn surefire:test -Dtest=*IT.java
```

## Create a fresh Springboot Setup from scratch

### Get the Springboot Framework
**1. Visit Spring Initializr and download the package.**
- Manual Configuration - Please Select:
    - Project: Maven
    - Language: Java
    - Spring Boot: 4.0.5
    - Project Metadata:
        - Group: hse-st-group
        - Artifact: esbot
        - Packagename: hse-st-group1.esbot
        - Packaging: JAR
        - Configuration: Properties
        - Java: 21
    - Dependencies:
        - Lombok
        - Spring Web
        - PostgreSQL Driver
        - Spring Data JPA
- Preconfigured Package: [Spring-Boot-Esbot-Package](https://start.spring.io/#!type=maven-project&language=java&platformVersion=4.0.5&packaging=jar&configurationFileFormat=properties&jvmVersion=21&groupId=hse-st-group1&artifactId=esbot&packageName=hse-st-group1.esbot&dependencies=lombok,web,postgresql,data-jpa)
- Press `Generate` to download the package

**2. Unzip the archive in the directory you want**
```bash
unzip esbot.zip
```

### Start the Postgres Container
**- Create a docker-compose.yaml**
```bash
tee docker-compose.yml <<EOF
services:
  db:
    image: postgres:17.9
    container_name: esbot-db
    restart: unless-stopped
    environment:
      POSTGRES_DB: esbot
      POSTGRES_USER: esbot_user
      POSTGRES_PASSWORD: esbot_password
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

volumes:
  postgres-data:
EOF
```
**- Start the container using docker compose**
- Verify that you are in the folder where the docker-compose.yml is located.
- Start the Postgres container
```bash
sudo docker compose up
```

### Configure Spring-Boot properties
**- Navigate into the esbot directory previously extracted from esbot.zip**
**- Navigate to ./src/main/resources**
**- Edit the application.properties file**
**- Add the following lines to the file**
```application.properties
spring.application.name=esbot

spring.datasource.url=jdbc:postgresql://localhost:5432/esbot
spring.datasource.username=esbot_user
spring.datasource.password=esbot_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### Start Spring-Boot using Maven
```bash
mvn spring-boot:run
```

### Additional Notes:
**- If starting spring-boot fails try to clean install using maven**
```bash
mvn clean install
```
