# Equivalence Class Partitioning

## Topic
| Parameter | Class ID  | Class Type    | Description                                       | Representative Value                                                                                                                           |
|-----------|-----------|---------------|---------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| topic     | EC-T-1    | Valid         | topic length 2-100 (inclusive) are valid inputs   | <ul><li>length=2: "IT"</li><li>length=99: "..."</li><li>boundary values:<ul><li>length=3: "car"</li><li>length=100: "..."</li></ul></li></ul>  |
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

# Justification

## Topic
The question topic is relevant for QuizRequest because without a topic the LLM would be unable to generate QuizItems(Questions) for the requested topic. To limit the complexity a upper boundary value of 100 characters is selected, which must not be exceeded. The lower value was selected that general topics can be selected, and abreviations can be used. Single letters can mean anything, therefore they are excluded. This is mapped to the Requirement REQ-3.

## Count
The question count is relevant for QuizRequest because without a count the LLM would be unable to generate a fixed number of questions. This could happen non-deterministic so a diffrent number of questions is generated each time. To counteract that QuizRequest needs a fixed number of questions that can be generated. The lower boundary of 1 was selected, because you can't generate 0 questions for any given topic, so that case should be ignored. A upper boundary of 10 was selected to reduce the probability of duplicate questions. This is mapped to the Requirement REQ-3.

## Difficulty
The question difficulty is relevant for QuizRequest because without a difficulty the questions could be too easy or too difficult for the user. This enables the user to select the difficulty accordingly to his knowledge. No boundaries can be selected here because there is no value that is lower than easy and no value thats higher than hard. This is mapped to Requirement REQ-3.

# Decision Table
| Answer correct?     | Answer empty/blank? | QuizItem still exists? | Valid action? | Expected System Action                               | Requirement reference                                                 |
|---------------------|:-------------------:|:----------------------:|:-------------:|------------------------------------------------------|:----------------------------------------------------------------------|
| "correct"           | no                  | yes                    | yes           | Feedback: "Answer is correct"                        | REQ-04                                                                |
| "partially correct" | no                  | yes                    | yes           | Feedback: "Answer is partially correct: +CORRECTION" | REQ-04                                                                |
| "incorrect"         | no                  | yes                    | yes           | Feedback: "Answer is incorrect: +CORRECTION"         | REQ-04                                                                |
| -                   | yes                 | yes                    | no            | Error: "You can't submit an empty answer"            | Datamodel: QuizAnswer is not null                                     |
| -                   | -                   | no                     | no            | Error: "QuizItem(=Question) not found"               | Datamodel: QuizEvaluation and QuizAnswer can't exist without QuizItem |