Feature:
    As a user
    I want to send messages, quizrequests or answers to the LLM
    So that I can start a conversation, receive a quiz, or get my answer evaluated.

Scenario: Get a message
    Given I have a session
    When I send a message
    Then a message gets send back to me

Scenario: Get a quiz
    Given I have a session
    When I send a quizrequest
    Then a quiz gets send back

Scenario: AI failure when message gets send
    Given I have a session
    When I send a message
    And AI is not available
    Then a error message gets sent back stating the service is currently unavailable

Scenario: AI failure when quizrequest gets send
    Given I have a session
    When I send a quizrequest
    And AI is not available
    Then a error message gets sent back stating the service is currently unavailable
