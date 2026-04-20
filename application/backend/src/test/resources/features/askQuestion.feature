Feature: Ask questions and get answer

Scenario: Student gets an answer
    Given I have a session, too
    And a Student asks "What is the most op animal in the world"
    And the AI service is available
    When student send the question
    Then the response should be "The most op Animal is the Rabbit"


Scenario: Ai service fails
    Given I have a session, too
    And a Student asks "What is the most op animal in the world"
    And the AI service is unavailable
    When student send the question
    Then the error should be "Error: Message service is currently unavailable"