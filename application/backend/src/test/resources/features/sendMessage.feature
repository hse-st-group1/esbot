Feature: Send and receive messages

Scenario: Send a message and receive a reply
    Given I have a session
    And AI messaging is available
    When I send a message
    Then a message gets send back to me