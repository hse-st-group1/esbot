# Equivalence Class Partitioning

## Topic
| Parameter | Class ID  | Class Type    | Description                                       | Representative Value                                                                                                                           |
|-----------|-----------|---------------|---------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| topic     | EC-T-1    | Valid         | topic length 2-100 (inclusive) are valid inputs   | <ul><li>length=4: "test"</li><li>length=99: "..."</li><li>boundary values:<ul><li>length=2: "IT"</li><li>length=100: "..."</li></ul></li></ul>  |
| topic     | EC-T-2    | Invalid       | topic length <2 is too short                      | <ul><li>length=1: "x" (outside boundary value)</li></ul>                                                                                       |
| topic     | EC-T-3    | Invalid       | topic length >100 is too long                     | <ul><li>length=101: "..." (outside boundary value)</li></ul>                                                                                   |
| topic     | EC-T-4    | Invalid       | empty string                                      | <ul><li>""</li></ul>                                                                                                                           |
| topic     | EC-T-5    | Invalid       | not a string                                      | <ul><li>0</li><li>1</li></ul>                                                                                                                  |

## Count:
| Parameter | Class ID  | Class Type | Description                                               | Representative Value                                                                |
|-----------|-----------|------------|-----------------------------------------------------------|-------------------------------------------------------------------------------------|
| count     | EC-C-1    | Valid      | number of questions between 1-10 (inclusive) are valid    | <ul><li>2</li><li>9</li><li>boundary values:<ul><li>1</li><li>10</li></ul></li></ul>|
| count     | EC-C-2    | Invalid    | number of questions <1                                    | <ul><li>0 (outside boundary value)</li></ul>                                        |
| count     | EC-C-3    | Invalid    | number of questions >10                                   | <ul><li>11 (outside boundary value)</li></ul>                                       |
| count     | EC-C-4    | Invalid    | not a number                                              | <ul><li>null</li><li>a</li></ul>                                                    |

## Difficulty
| Parameter     | Class ID  | Class Type | Description                               | Representative Value                              |
|---------------|-----------|------------|-------------------------------------------|---------------------------------------------------|
| difficulty    | EC-D-1    | Valid      | "easy", "medium", "hard" are valid inputs | <ul><li>easy</li><li>medium</li><li>hard</li></ul>|
| difficulty    | EC-D-2    | Invalid    | empty string                              | <ul><li>""</li></ul>                              |
| difficulty    | EC-D-3    | Invalid    | not a string                              | <ul><li>null</li><li>123</li></ul>                |
| difficulty    | EC-D-4    | Invalid    | diffrent string                           | <ul><li>abc</li></ul>                             |

## Justification

### Topic
The question topic is relevant for QuizRequest because without a topic the LLM would be unable to generate QuizItems(Questions) for the requested topic. To limit the complexity a upper boundary value of 100 characters is selected, which must not be exceeded. The lower value was selected that general topics can be selected, and abreviations can be used. Single letters can mean anything, therefore they are excluded. This is mapped to the Requirement REQ-3.

### Count
The question count is relevant for QuizRequest because without a count the LLM would be unable to generate a fixed number of questions. This could happen non-deterministic so a diffrent number of questions is generated each time. To counteract that QuizRequest needs a fixed number of questions that can be generated. The lower boundary of 1 was selected, because you can't generate 0 questions for any given topic, so that case should be ignored. A upper boundary of 10 was selected to reduce the probability of duplicate questions. This is mapped to the Requirement REQ-3.

### Difficulty
The question difficulty is relevant for QuizRequest because without a difficulty the questions could be too easy or too difficult for the user. This enables the user to select the difficulty accordingly to his knowledge. No boundaries can be selected here because there is no value that is lower than easy and no value thats higher than hard. This is mapped to Requirement REQ-3.

# Decision Table
| Answer correct?     | Answer empty/blank? | QuizItem still exists? | Valid action? | Expected System Action                               | Requirement reference                                                 |
|---------------------|:-------------------:|:----------------------:|:-------------:|------------------------------------------------------|:----------------------------------------------------------------------|
| "correct"           | no                  | yes                    | yes           | Feedback: "Answer is correct"                        | REQ-4                                                                |
| "partially correct" | no                  | yes                    | yes           | Feedback: "Answer is partially correct: +CORRECTION" | REQ-4                                                                |
| "incorrect"         | no                  | yes                    | yes           | Feedback: "Answer is incorrect: +CORRECTION"         | REQ-4                                                                |
| -                   | yes                 | yes                    | no            | Error: "You can't submit an empty answer"            | Datamodel: QuizAnswer is not null                                     |
| -                   | -                   | no                     | no            | Error: "QuizItem(=Question) not found"               | Datamodel: QuizEvaluation and QuizAnswer can't exist without QuizItem |

# State Transition Testing
## State Diagram
<img src="./diagrams/black-box-testing/esbot_states.svg" width=500px/>

## State Transition Table
| Current State | Event | Next State | Output/Action |
| :---- | :---- | :---- | :---- |
| NEW | submit\_message | ACTIVE | store message generate response |
| ACTIVE | 5min\_inactivity | IDLE | create summary for AI interaction update last accessed |
| IDLE | submit\_new\_message | ACTIVE | reactivate Session load summary for AI prompts load old Messages update message context |
| ACTIVE | delete\_session | (termination) | delete session and date |
| IDLE | delete\_session | (termination) | delete session data  |
| NEW | 5min\_inactivity | (termination) | session is not persisted  |

## Test Case Derivation
| Start State | Events Applied | Expected State Afterwards | Expected System Output | Requirement |
| :---- | :---- | :---- | :---- | :---- |
| START | create session | NEW | None | None |
| NEW | submit message | ACTIVE | LLM answers users first message | REQ-1 |
| ACTIVE | inactivity \> 5min | IDLE | The system notifies the user that no interaction took place for more than 5 minutes (?) | REQ-5 |
| IDLE | submit new message | ACTIVE | LLM answers users message | REQ-5 |
| ACTIVE | User deletes session | DELETED | Session deletion is confirmed to user | None |
| Start State | Events Applied | Expected State Afterwards | Expected System Output | Requirement |


| Start State | Events Applied | Expected State Afterwards | Expected System Output | Requirement |
| :---- | :---- | :---- | :---- | :---- |
| START | create session | NEW | None | None |
| NEW | inactivity \> 5min | (invalid) | **Invalid Transition  (NEW → IDLE)** (Given that the session contains no content, it is deleted in case of inactivity.) | None |


# Reflection — Test Design Technique Comparison

## Complementarity
**Which scenarios are best covered by ECP/BVA, which by decision tables, and which by state transition testing? Give a concrete ESBot example for each.**

- **Equivalence Class Partitioning & Boundary Value Analysis:**  
  - Best for testing numeric parameters  
  - e.g. QuizRequest: count, length of topic  
- **Decision Tables:**  
  - Best for testing logic behind input and expected action, especially in case of several input parameters  
  - e.g. Transition between active and idle  
- **State Transition Testing:**  
  - Best for testing how events impact system state  
  - e.g. session state

## Gaps
**Are there ESBot behaviours that none of the three techniques cover well? What alternative technique would you apply and why?**

- Testing of entire scenarios/workflows from users point of view (Black-Box testing)  
  - Use Case Testing  
  - Usability testing  
- Tests all possible/valid paths through the code (White-Box testing)  
  - Path Coverage Testing

## Effort vs. value
**For the ESBot project specifically, which technique produced the highest defect-detection value relative to the design effort? Justify your answer with reference to at least one requirement from the specification.**

- **State Transitioning Testing:** Caused us to evaluate how the system works, and which states are possible. However, this resulted in adapting the data model and implementation, given that this functionality was not clear from the initial requirements. Previous to this, the system would have been working without this addition as well. Therefore no actual defects were found.   
- **Decision Table:** The given context of the decision table did not allow for a lot of possible scenarios/decisions. Therefore it was not effective in finding defects.   
- **Equivalence Class Partitioning & Boundary Value Analysis:** This proved to be most effective and took relatively little time. Our data model and implementation were adjusted accordingly. 
