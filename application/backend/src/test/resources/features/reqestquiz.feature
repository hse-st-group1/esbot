Feature:
    As a user
    I want to the LLM to create quizzes and exercises for me
    So that I can practice and improve my understanding of a given concept or topic. 

Scenario: Request a Quiz
    Given I have a session where i can request a quiz
    And I have a topic I want to be quizzed on 
    And I have a number of questions i want the quiz to have
    When I send a message requesting a quiz, which contains the topic and the number of questions i want
    Then a list of questions should be shown to me 

Scenario: AI cannot generate quiz about this topic
    Given I have a session where i can request a quiz 
    And I have a topic I want to be quizzed on 
    And I have a number of questions i want the quiz to have
    But the AI is unable to generate a quiz about the requested topic
    When I send a message requesting a quiz, which contains the topic and the number of questions i want
    Then an error message should be shown stating that the LLM cannot generate a quiz about this topic 

Scenario: Request for Quiz or Exercise without providing topic
    Given I have a session where i can request a quiz 
    When I request a quiz
    But do not provide the topic of the quiz or exercise
    Then the LLM should ask me to clarify the topic of the quiz

