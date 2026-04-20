Feature:
    As a user
    I want to the LLM to create quizzes and exercises for me
    So that I can practice and improve my understanding of a given concept or topic. 

Scenario: Request a Quiz
    Given I have a session 
        AND a topic I want to be quizzed on 
    When I send a message requesting a quiz 
        AND provide the topic I want to be quizzed about 
        AND the number of questions
    Then a list of questions should be shown to me 

Scenario: Request an Exercise
    Given I have a session 
        AND a concept I want to exercise my understanding of to test it
    When I send a request to generate an exercise 
        AND provide the concept the exercise should be about
    Then an exercise should be shown to me

Scenario: Request for Quiz or Exercise without providing topic
    Given I have a session 
    When I request a quiz or an exercise
        BUT do not provide the topic of the quiz or exercise
    Then the LLM should ask me to clarify the topic of the quiz

Scenario: AI cannot generate quiz about this topic
    Given I have a session 
        AND send a request for a quiz about a certain topic or concept
    When the AI is unable to generate a quiz about the requested topic
    Then an error message should be shown stating that the LLM cannot generate a quiz about this topic 
        AND it should suggest a related topic I can request a quiz about instead
