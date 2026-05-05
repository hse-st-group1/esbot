Feature:
    As a user
    I want to the LLM to create quizzes and exercises for me
    So that I can practice and improve my understanding of a given concept or topic. 

Scenario: Request a Quiz
    Given I have a session
    And I have a topic I want to be quizzed on 
    And I have a number of questions i want the quiz to have
    When I send a message requesting a quiz, which contains the topic and the number of questions i want
    Then a list of questions should be shown to me 


Scenario: Request for Quiz or Exercise without providing topic
    Given I have a session
    When I request a quiz
    But do not provide the topic of the quiz or exercise
    Then the LLM should inform me that I need to clarify the topic of the quiz

