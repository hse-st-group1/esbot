# Automated API-Testing

### Framework
The automated API-Tests for our Springboot backend is written with the frame work Rest Assured. Rest Assured tests the API endpoints like a black box by calling them and asserting the response http codes and the send data. This leads to the tests beeing close to a realistic use case but they are somewhat slow because the whole backend needs to boot. The setup is easy and the code itself is self explainatory with a gherkin like structure (given, when, then).

### Test execution
For running the API test suite an instance of the backend is created with an in Memory H2 Database.
For every test the needed data to test the functionality of an Endpoint is created beforehand. After Each test the Data is cleared. 
The tests are run together with the unit tests by using following command in the application/backend subdirectory:
```bash
mvn test
```
To run just the API tests use the following command instead:
```bash
mvn test -Dtest=ApiTest
```
or
```bash
mvn test -Dgroups="api"
```
Additionally it can be filtered by test groups with
```bash
mvn test -Dgroups="api & happy-path"
```
test groups are: happy-path, unhappy-path, smoke  
to switch exchange happy-path with either one in the command.

### Test groups:
- **smoke**: Tests the health check endpoint
- **happy-path:** Testing of correct system behavior when API-endpoints are called correctly
- **unhappy-path:** Testing of error conditions their responses and edge cases
