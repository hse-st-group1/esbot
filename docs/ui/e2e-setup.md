# Automated End-to-End Test

## End-to-End Tests
**Framework:** Playwright
---
### Install Playwright
**Navigate to frontend folder and create folder for playwright**
```bash
cd ./esbot/application/frontend
```
```bash 
mkdir playwright
```
```bash 
cd playwright
```

**Install Playwright:**
```bash 
npm init playwright@latest
```
-	for TypeScript
-	put end-to-end tests in folder tests
-	no GitHub Actions workflows
-	install Playwright browsers
-	install Playwright OS dependencies (if host system is Linux)

**Install Dependencies:**
```bash 
npm install
```
---	
### Playwright Tests
---
#### Start Full Stack App
1.	Start Database in Docker
```bash
cd ./esbot
```
```bash
docker compose up
```

2.	In a new Terminal, start the Backend
```bash
cd ./esbot/application/backend
```
```bash
mvn spring-boot:run
```

3.	In a new Terminal, create the Default User
```bash
cd utils/script
```
```bash
./createTestUser.sh
```

4.	In a new Terminal, start the Frontend
```bash
cd ./esbot/application/frontend/esbot
```
```bash
ng serve
```

Base URL: 
http://localhost:4200


With the Database, Backend and Frontend running, Playwright Test can now be run with the command below:

---
#### Run Playwright Tests
**Playwright Commands:**

Headless run (CI-style): 
--> Runs the end-to-end tests.  
```bash 
npx playwright test 
```

Interactive / headed run:
--> Starts the interactive UI mode:
```bash 
npx playwright test --ui
```
    
Runs the tests in a specific file:
```bash 
npx playwright test example
```
    
Runs the tests in debug mode:
```bash 
npx playwright test --debug
```

