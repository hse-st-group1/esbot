# Pipeline

## Exercise 9.1 Implementation
### 1. Triggers
- **push an all branches:** to detect breaking changes early
- **pull request on all branches:** so every contribution is verified before merge

### 2. Runner
- ```ubuntu-latest: ```
    - default   
        - no specific config needed
        - widely used
    - our local development environment is linux mint which is based on ubuntu

### 3. Environment
As specified in our tech stack: 
- ```distribution: temurin```
- ```java-version: "21" ```
- ```cache: maven ```

### 4. Jobs and steps
- **Unit Tests**
    - Set up job
    - Checkout
    - Set up Java
    - Run Unit Tests
    - Post Set up Java
    - Post Checkout
    - Complete Job
- **BDDTest**
    - Set up job
    - Checkout
    - Set up Java
    - Dowload Dependencies
    - Run BDD Tests
    - Post Setup Java
    - Post Checkout
    - Complete Job
- **PMD Linting**
    - Set up job
    - Checkout
    - Set up Java
    - Run PMD Linter
    - Post Set up Java
    - Post Checkout
    - Complete Job
- **Dependency Check**
    - Set up job
    - Set up Docker Container
    - Checkout
    - Set up Java
    - Run Dependency Check
    - Upload Test results
    - Post Set up Java
    - Post Checkout
    - Complete Job 

### 5. Parity with local
Same Test that are run in the CI Pipeline are also run locally (see [local-verification.md](https://github.com/hse-st-group1/esbot/blob/001-esbot-application/docs/ci/local-verification.md))

| Test | Job | Local Command |
| :--- | :--- | :--- |
| Unit Tests | UnitTest   | `mvn test` |
| BDD Tests | BDDTest | `mcn surefire:test -Dtest=*Cucumber*` |
| PMD Linter | PMDLinting | `mvn verify -P pmd-linter` |
| OWASP Dependency Check | DependencyCheck | `mvn verify -P dependency-check` |

In case the local tests run fine but the CI Pipeline Tests fail: Debug error message and try to repilicate error locally by using `mvn clean`and then manually replicate the steps in the local environment.  
If the local dependency check fails while the CI-Pipeline goes through fix the local dependency findings.

## Exercise 9.2 Enhancements
### Action / Tool Implemented
- The OWASP Dependency Check was implemented as an enhancement to the CI Pipline.
### Benefit
- The benefit to ESBot is that this allows a dependency check in terms of vulnerabilities in the current build. This allows for a fast check if any dependency is vulnerable.
### Value vs Cost
- The dependency check in the Docker image gets updated every night. This reduces the cost of updating the CVE database inside the CI, which can cause a long waiting due to limited download rates without an api-key. Because this is done by the Docker image maintainer it is faster overall. The test itself takes several seconds which is acceptable for the CI.
### Locally and CI
- The same check can still run locally, because the database update gets downloaded once and can be used locally. If some updates are required it takes a few seconds to minutes to download the CVEs. To ensure maximum coverage it is recommended to run both in sync.