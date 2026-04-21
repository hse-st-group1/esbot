# Test-Strategy

## Test Strategy Reflection

> **When should BDD/acceptance tests be executed relative to unit tests?**
> Should they always run together, or should they be treated differently
> (e.g., executed less frequently, in a separate CI stage)?

### Purpose of Unit and Acceptance Tests

Unit tests are testing individual components of the system like methods, functions, and entities. The focus of these tests is to verify the implemented logic and functional behaviour in isolation. 
Therefore the scope is very limited in comparison to the acceptance tests. Acceptance Tests are focusing on complete systems or subsystems. The goal of acceptance tests is to verify and validate entire 
requirements on a functional level. Therefore the scope of BDD/Acceptance Test is much broader. The broader approach of the acceptance tests is resulting in a slower execution time for the individual 
tests. The reason behind is that more systems and functions have to be executed compared to a simple unit tests. In simple applications without much interaction between different components, subsystems 
and interfaces the acceptance tests can be executed fast, without much interruptions. In more complex systems the acceptance test execution can be much slower. Therefore acceptance tests, shouldn't be 
xecuted in every test cycle. In contrast, unit tests are executed very fast because the focus is on individual components rather then systems. Because of the fast execution time the tests are not causing 
disturbance in the development process and should be executed frequently to verify the functional implementation.

### Test Strategy for ESBot

The test strategy for ESBot is derived from the scope and speed differences mentioned above. Because of the speed difference in test execution, unit tests will be executed for every commit to quickly verify 
the funcional implementation is correct. Defects and Bugs can be spotted quickly and the issues resolved quickly. For Pull requests the test coverage needs to be broader to not only cover functional 
implementation, but also functional requirements and interactions between components. Therefore it is recommended to execute BDD/Acceptance Tests whenever new features get added. This can be done for pull
requests. For ESBot there is the possibility to verify acceptance via mocking of AI components. The reason why this is done in our tests is because the LLM delivers non-deterministic output which cant be verified in
acceptance tests. That's why it is enough to mock the LLM with a deterministic output to simulate the LLM-API. In addition the process of testing AI Behaviour via mocks speeds up the testing process significantly.
Besides the speed and deterministic output, the LLM is not required to process requests and instead can handle actual requests.