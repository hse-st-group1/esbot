# E2E Test Report

## 1. Test execution summary
| Test Count | Number of tests passed | Number of tests failed | total runtime | framework | framework version |
|:----------:|------------------------|------------------------|---------------|-----------|-------------------|
| 21         | 21                     | 0                      | 25.6s         | playwright| 1.0.0             |

## 2. Headless output
![alt text](headless-style-test.png)

## 3. Interactive run
![alt text](interactive_test_runner.png)

## 4. Flakiness observations
In the first runs of our tests, flakiness in our tests were observed. The cause was a timing error in displaying our sessions in the frontend. This has been fixed by adding the following code to the testfile.
```typescript
await page.waitForLoadState('networkidle'); // wait for sessions to actually load
```

## 5. Reflection
