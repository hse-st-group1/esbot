# Pipeline

## Exercise 9.1 Implementation
### 1. Triggers
### 2. Runner
### 3. Environment
### 4. Jobs and steps
### 5. Parity with local

## Exercise 9.2 Enhancements
### Action / Tool Implemented
- The OWASP Dependency Check was implemented as an enhancement to the CI Pipline.
### Benefit
- The benefit to ESBot is that this allows a dependency check in terms of vulnerabilities in the current build. This allows for a fast check if any dependency is vulnerable.
### Value vs Cost
- The dependency check in the Docker image gets updated every night. This reduces the cost of updating the CVE database inside the CI, which can cause a long waiting due to limited download rates without an api-key. Because this is done by the Docker image maintainer it is faster overall. The test itself takes several seconds which is acceptable for the CI.
### Locally and CI
- The same check can still run locally, because the database update gets downloaded once and can be used locally. If some updates are required it takes a few seconds to minutes to download the CVEs. To ensure maximum coverage it is recommended to run both in sync.