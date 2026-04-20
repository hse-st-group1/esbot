Feature: Quiz Evaluation
    As a user, 
    I want the system to evaluate and give feedback regarding my answers
    So that I can test and improve my knowledge

Scenario: Correct answer is submitted
    Given I have a session
    And I have a question
    When I give an as correct interpretable answer
    Then the system evaluates my answer as correct

Scenario: Incorrect answer is submitted
    Given I have a session
    And I have a question
    When I give an as incorrect interpretable answer
    Then the system evaluates my answer as incorrect

Scenario: Invalid input is submitted
    Given I have a session
    And I have a question
    When I give an invalid string as answer
    Then the system asks me to make a valid input.