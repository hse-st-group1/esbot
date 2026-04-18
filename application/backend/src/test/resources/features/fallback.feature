Feature:
    As a user
    I want to send messages, quizrequests or answers to the LLM
    So that I can start a conversation, receive a quiz, or get my answer evaluated.

Scenario: Get a message
    Given I have a session
    And AI messaging is available
    When I send a message
    Then a message gets send back to me

Scenario: AI failure when message gets send
    Given I have a session
    And AI messaging is not available
    When I send a message
    Then a error message gets sent back stating the message service is currently unavailable

Scenario: Get a quiz
    Given I have a session
    And AI quiz generation is available
    When I send a quizrequest
    Then a quiz gets send back

Scenario: AI failure when quizrequest gets send
    Given I have a session
    And AI quiz generation is not available
    When I send a quizrequest
    Then a error message gets sent back stating the quiz service is currently unavailable
