# Unit Testing of Models

## Description of analysation methodology
All data-models have been manually analyzed. In this analysis it was determined if the boundary values have been set correctly and are in fact reasonable. After the entity and data-model, the implemented unit tests have been reviewed.
In that analysis it was also determined if all tests for the boundary values have been implemented and tested. Any
missing boundary values and each implemented test have been listed below.

## Findings
### UserTest.java
- **UserID - Checks:**
    - Ensure User with UserID is valid
    - Ensure User without UserID is invalid (`null`)
- **UserName - Checks:**
    - Ensure UserName cant be a `single character`
    - Ensure UserName cant be `blank`
    - Ensure UserName cant be `null`
- **Session - Checks:**
    - Ensure User with session is valid
    - Ensure User without session is valid (`null`)
- **Missing: Not all boundary values have been checked in the user unit test**
    - Ensure UserName can be 2 characters
    - Ensure UserName can be 16 characters
    - Ensure UserName cant be 17 characters
### Session.java
- **SessionID - Checks:**
    - Ensure Session with SessionID is valid
    - Ensure Session without SessionID is invalid (`null`)
- **User - Checks:**
    - Ensure Session with User is valid
    - Ensure Session without User is invalid (`null`)
- **StartedAt - Checks:**
    - Ensure Session with StartedAt TimeStamp is valid
    - Ensure Session without StartedAt TimeStamp is invalid (`null`)
- **LastAccessed - Checks:**
    - Ensure Session with LastAccessed TimeStamp is valid
    - Ensure Session without LastAccessed TimeStamp is invalid (`null`)
- **Missing: Session State has not been accounted for and therefore invalid transitions and states have not been tested.**
### Message.java
- **MessageID - Checks:**
    - Ensure Message with MessageID is valid
    - Ensure Message without MessageID is invalid (`null`)
- **Session - Checks:**
    - Ensure Message with Session is valid
    - Ensure Message without Session is invalid (`null`)
- **MessageContent - Checks:**
    - Ensure Message with MessageContent is valid
    - Ensure Message without MessageContent is invalid (`blank`)
    - Ensure Message without MessageContent is invalid (`null`)
- **TimeStamp - Checks:**
    - Ensure Message with TimeStamp is valid
    - Ensure Message without TimeStamp is invalid (`null`)
- **Sender - Checks:**
    - Ensure Message with Sender is valid
    - Ensure Message without Sender is invalid (`null`)
- **MessageType - Checks:**
    - Ensure Message with MessageType is valid
    - Ensure Message without MessageType is invalid (`null`)
    - Ensure Max Length of MessageType (32) is not exceeded
- **Missing:** 
    - Boundary values for MessageContent are missing in the data-model. Therefore these constraints have not been tested.
### QuizRequest.java
- **QuizID - Checks:**
    - Ensure QuizRequest with QuizID is valid
    - Ensure QuizRequest without QuizID is invalid (`null`)
- **Session - Cheks:**
    - Ensure QuizRequest with Session is valid
    - Ensure QuizRequest without Session is invalid (`null`)
- **QuizRequestContent - Checks:**
    - Ensure QuizRequest with QuizRequestContent is valid
    - Ensure QuizRequest without QuizRequestContent is invalid (`null`)
- **QuizItems - Checks:**
    - Ensure QuizRequest with QuizItems is valid
    - Ensure QuizRequest without QuizItems is valid (`null`)
- **Missing:**
    - QuizRequestContent boundary values are missing in the data-model. Therefore these constraints have not been tested.
    - QuizRequestContent blank values have not been implemented and tested.
    - QuizItemCount has been added at a later stage. No boundary values have been checked and implemented.
    - QuizItemDifficulty has been added at a later stage. No boundary values have been checked and implemented.
### QuizItem.java
- **QuizItemID - Checks:**
    - Ensure QuizItem with QuizItemID is valid
    - Ensure QuizItem without QuizItemID is invalid (`null`)
- **QuizRequest - Checks:**
    - Ensure QuizItem with QuizRequest is valid
    - Ensure QuizItem without QuizRequest is invalid (`null`)
- **Question - Checks:**
    - Ensure QuizItem with Question is valid
    - Ensure QuizItem without Question is invalid (`null`)
- **QuizAnswers - Checks:**
    - Ensure QuizItem with QuizAnswers is valid
    - Ensure QuizItem without QuizAnswers is valid (`null`)
- **QuizEvaluations - Checks:**
    - Ensure QuizItem with QuizEvaluations is valid
    - Ensure QuizItem without QuizEvaluations is valid (`null`)
- **Missing:**
    - Question (Topic) boundary values are missing in the data-model. Therefore these constraints have not been tested.
    - Question blank values have not been implemented and tested.
### QuizAnswer.java
- **QuizAnswerID - Checks:**
    - Ensure QuizAnswer with QuizAnswerID is valid
    - Ensure QuizAnswer without QuizAnswerID is invalid (`null`)
- **QuizItem - Checks:**
    - Ensure QuizAnswer with QuizItem is valid
    - Ensure QuizAnswer without QuizItem is invalid (`null`)
- **Answer - Checks:**
    - Ensure QuizAnswer with Answer is valid
    - Ensure QuizAnswer without Answer is invalid (`blank`)
    - Ensure QuizAnswer without Answer is invalid (`null`)
- **TimeStamp - Checks:**
    - Ensure QuizAnswer with TimeStamp is valid
    - Ensure QuizAnswer without TimeStamp is invalid (`null`)
- **Missing:**
    - Answer boundary values have not been definied in the data-model. Therefore thes constraints gave not been tested.
### QuizEvaluation.java
- **QuizEvaluationID - Checks:**
    - Ensure QuizEvaluation with QuizEvaluationID is valid
    - Ensure QuizEvaluation without QuizEvaluationID is invalid (`null`)
- **QuizItem - Checks:**
    - Ensure QuizEvaluation with QuizItem is valid
    - Ensure QuizEvaluation without QuizItem is invalid (`null`)
- **QuizAnswer - Checks:**
    - Ensure QuizEvaluation with QuizAnswer is valid
    - Ensure QuizEvaluation without QuizAnswer is invalid (`null`)
- **Evaluation - Checks:**
    - Ensure QuizEvaluation with Evaluation is valid
    - Ensure QuizEvaluation without Evaluation is invalid (`null`)
    - Ensure QuizEvaluation without Evaluation is invalid (`blank`)
- **Missing:**
    - Evaluation boundary values have not been definied in the data-model. Therefore thes constraints gave not been tested.
