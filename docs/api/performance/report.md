# Performance Report

## 1. Selected Tool:
JMeter was selected as a performance test tool with the "Custom Thread Groups" Plugin. The reason behind that decision is, that JMeter is easy to setup and is easily extendable via plugins. This combination allowed a fast and intuitive development for diffrent testing scenerios which was rated as overall sufficient for our ESBot application. In bigger and more advanced projects, gatling could deliver more precise control and test results. Therefore gatling should be considered for future performance tests.

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
The database was running in a docker container. Started via the [docker-compose-file](https://github.com/hse-st-group1/esbot/blob/main/docker-compose.yml) in the git repository. After each test the database was manually reset.

## 3. Results Table

| **Test**      | Throughput            | 90th percentile   | 95th percentile | 99th percentile | Error rate | Peak concurrency reached |
|---------------|-----------------------|-------------------|-----------------|-----------------|------------|--------------------------|
|**Smoke Test** | 57.03  Transactions/s | 12.40ms           | 13.90ms         | 15.00ms         | 0.00%      | 3                        |
|**Load Test**  | 159.55 Transactions/s | 1093.00ms         | 1297.95ms       | 1721.98ms       | 0.00%      | 50                       |
|**Stress Test**| 119.56 Transactions/s | 8725.80ms         | 11121.65ms      | 16341.86ms      | 0.00%      | 275                      |

## 4. Interpretation of Test Results

## 5. Observation and Recommendations