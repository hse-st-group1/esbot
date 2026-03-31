# Use Case Diagram

<img src="./diagrams/use_case.drawio.svg" width=1000px/>

# Use Case Descriptions
## UseCase-001
### Title: Ask Question
### Summary: 
As a user, I want to interact with ESBot via a chat-based interface. I want to ask questions and receive answers that help me better understand a concept or study or otherwise support my learning progress.
### Primary Actor:
- **User**
### Stakeholders and Interests:
- **User:** wants support learning/studying/understanding
### Triggering Event: 
- User submits question/request 
### Input: 
- Message of the user
### Main Success Scenario: 
- ESbot forwards the users message to the LLM, which processes the request.
### Preconditions: 
- The user is authenticated. 
- The user has a working internet connection which connects him to the ESBot.
- ESBot has a working internet connection to the LLM.
- The user has formulated a question or request.
### Outputs and Post-Conditions: 
- The LLM’s answer is sent to the user. 
- The request and answer, as well as the current user and session, is stored in the database.
### Extensions (Alternate Flows): 
- No working internet connection between ESBot and LLM
    - An error message is displayed to the User. 
    - The request can be stored in the ESBot to be sent later, when the internet connection is reestablished. 
- No working internet connection between User and ESBot
    - An error message is displayed to the User. 
### Special Requirements: 
- None 
### Frequency of Use: 
- Very frequent (main feature)


## UseCase-002
### Title: Generate Explanation
### Summary: 
As a user, I want the system to generate structured explanations to help me learn and improve my understanding of concepts. 
### Primary Actor: 
- **LLM**
### Stakeholders and Interests:
- **User:** wants support learning/studying/understanding
### Triggering Event: 
- User submits request for explanation 
### Input: 
- Scenario 1: User requests explanation of concept
    - Input: User request (containing concept) 
- Scenario 2: User requests explanation of previous LLM answer
    - Input: User request + previous LLM message
### Main Success Scenario: 
- ESbot forwards the users message to the LLM, which processes the request and generates an explanation. 
### Preconditions:
- The user is authenticated. 
- The user has a working internet connection which connects him to the ESBot.
- ESBot has a working internet connection to the LLM.
- The user has formulated a request for explanation regarding a concept. 
### Outputs and Post-Conditions:
- The explanation is sent to the user.
- The request and answer, as well as the current user and session, is stored in the database.
### Extensions (Alternate Flows): 
- No working internet connection between ESBot and LLM
    - Error Message is displayed to the User. 
    - The request can be stored in the ESBot to be sent later, when the internet connection is reestablished. 
- No working internet connection between User and ESBot
    - Error Message is displayed to the User. 
### Special Requirements:
- The concept to be explained must be sent alongside the user's request.
### Frequency of Use: 
- Very frequent (main feature)

## UseCase-003
### Title: Generate Example
### Summary: 
As a user, I want the system to generate examples to clarify concepts.
### Primary Actor:
- **LLM**
### Stakeholders and Interests:
- **User:** wants support learning/studying/understanding
### Triggering Event: 
- User submits request for example 
### Input: 
- Scenario 1: User requests example of concept
    - Input: User request (containing concept) 
- Scenario 2: User requests example in regard to previous LLM answer
    - Input: User request + previous LLM message
### Main Success Scenario:
- ESbot forwards the users message to the LLM, which processes the request and generates an example. 
### Preconditions: 
- The user is authenticated. 
- The user has a working internet connection which connects him to the ESBot.
- ESBot has a working internet connection to the LLM.
- The user has formulated a request for an example of a concept. 
### Outputs and Post-Conditions:
- The example is generated and sent to the user. 
### Extensions (Alternate Flows): 
- No working internet connection between ESBot and LLM
    - Error Message is displayed to the User. 
    - The request can be stored in the ESBot to be sent later, when the internet connection is reestablished. 
- No working internet connection between User and ESBot
    - Error Message is displayed to the User. 
### Special Requirements: 
- The concept must be sent alongside the user's request for an example.
### Frequency of Use: 
- Very frequent (supports main feature)


## UseCase-004
### Title: Generate Exercise 
### Summary:
As a user, I want the system to generate questions or quizzes or exercises to test and improve my understanding of concepts. 
### Primary Actor: 
- **LLM**
### Stakeholders and Interests: 
- **User:** wants support learning/studying/understanding
### Triggering Event: 
- User submits request for quiz as well as the topic 
### Input: 
- Users request for a quiz or exercise
- Topic of quiz or exercise
### Main Success Scenario:
- ESbot forwards the users message to the LLM, which processes the request and generates a quiz or an exercise. 
### Preconditions: 
- The user is authenticated. 
- The user has a working internet connection which connects him to the ESBot.
- ESBot has a working internet connection to the LLM.
- The user has formulated a request for a quiz about a concept. 
### Outputs and Post-Conditions:
- The quiz is generated and sent to the user. 
### Extensions (Alternate Flows):** 
- No working internet connection between ESBot and LLM
    - Error Message is displayed to the User. 
    - The request can be stored in the ESBot to be sent later, when the internet connection is reestablished. 
- No working internet connection between User and ESBot
    - Error Message is displayed to the User. 
### Special Requirements: 
- The concept/topic of the quiz must be sent alongside the user's request.
### Frequency of Use: 
- Frequent 

## UseCase-005
### Title: Evaluate Answer of Exercise
### Summary:
As a user, I want the system to evaluate or correct my answers to questions or quizzes or exercises. 
### Primary Actor: 
- **LLM**
### Stakeholders and Interests: 
- **User:** wants support learning/studying/understanding
### Triggering Event: 
- User submits answers 
### Input: 
- Quiz or exercise
- Users answers
### Main Success Scenario:
- ESbot forwards the users message to the LLM, which processes the request and evaluates the answers and formulates feedback/corrections. 
### Preconditions: 
- The user is authenticated. 
- The user has a working internet connection which connects him to the ESBot.
- ESBot has a working internet connection to the LLM.
- A quiz has been generated.
- The user has formulated answers for a quiz. 
### Outputs and Post-Conditions:
- Feedback or corrections are generated and sent to the user. 
### Extensions (Alternate Flows): 
- No working internet connection between ESBot and LLM
    - Error Message is displayed to the User. 
    - The request can be stored in the ESBot to be sent later, when the internet connection is reestablished. 
- No working internet connection between User and ESBot
    - Error Message is displayed to the User. 
### Special Requirements:
- The questions of the quiz must be sent alongside the user's answers.
### Frequency of Use: 
- Frequent 
