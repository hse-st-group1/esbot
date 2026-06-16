# Performance Report

## 1. Selected Tool:
Apache JMeter was selected as a performance test tool with the "Custom Thread Groups" Plugin. The reason behind that decision is, that JMeter is easy to setup and is easily extendable via plugins. This combination allowed a fast and intuitive development for different testing scenerios which was rated as overall sufficient for our ESBot application. In bigger and more advanced projects, gatling could deliver more precise control and test results. Therefore gatling should be considered for future performance tests.

## 2. Test Environment:
The performance tests where conducted in a virtual environment. The settings of the VM and the hardware of the host system are documented in the tables below:

### 2.1 VM Settings:
| Number of Processors  | Number of cores per processor  | Memory  | Hard Disk  | OS                   |
|:---------------------:|:------------------------------:|:-------:|:----------:|:--------------------:|
| 1                     | 2                              | 8GB     | 45GB       | Linux Mint Zena 22.3 |

### 2.2 Host VM Settings:
| Processor            | Memory    | Hard Disk      | OS                 |
|:--------------------:|:---------:|:--------------:|:------------------:|
| Intel Core i7-12700k | 32GB DDR5 | 500GB m.2 NVMe | Debian 13.5 Trixie |

### 2.3 Backend Configuration
No changes to the backend configuration where made.

### 2.4 Database
The database was running in a docker container. Started via the [docker-compose-file](https://github.com/hse-st-group1/esbot/blob/main/docker-compose.yml) in the git repository. After each test the database was manually reset. Using `docker compose down -v` and `docker compose up -d`. The test user was created with [createTestUser.sh](https://github.com/hse-st-group1/esbot/blob/main/utils/script/createTestUser.sh).

## 3. Results Table

| **Test**      | Throughput            | 90th percentile   | 95th percentile | 99th percentile | Error rate | Peak concurrency reached |
|---------------|-----------------------|-------------------|-----------------|-----------------|------------|--------------------------|
|**Smoke Test** | 57.03  Transactions/s | 12.40ms           | 13.90ms         | 15.00ms         | 0.00%      | 3                        |
|**Load Test**  | 159.55 Transactions/s | 1093.00ms         | 1297.95ms       | 1721.98ms       | 0.00%      | 50                       |
|**Stress Test**| 119.56 Transactions/s | 8725.80ms         | 11121.65ms      | 16341.86ms      | 0.00%      | 300                      |

## 4. Interpretation of Test Results
The Load Test showed that the given Non-Functional Requirement with 50 virtual users and a response time < 2000ms gets fulfilled even in the 99th percentile. In our case the Non-Functional Requirement was not specified enough for concurrent users. In the performance aspect it was ambiguous of how the time is measured for a data base query and API processing time. When following our non-functional performance requirements our actual requirement is a response time of <700ms. That showed that our performance requirement gets not fulfilled and more ressources have to be applied to test environment to ensure a faster processing time.
The Stress Test showed that the system slowed down tremendously when exceeding 119 virtual users. At that point the response time exceeded 2000ms in the POST session request. Even though the backend and database was able to handle the requests at 300 virtual users with no errors, the response time got frustratingly slow by exceeding the Non-Functional requirement of < 2000ms by 100%. This is also validated by the fact that our throughput was reduced by about 25%.

## 5. Observation and Recommendations
One of our main limiting factors in our performance tests was the database inside the docker container. In this case it would be more efficient to use chaches to reduce the load on the database. In addition to server requests better it is highly recommended to use more ressources on the system to ensure fast processing times when combining the backend and the database on one single machine.