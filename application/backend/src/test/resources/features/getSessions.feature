Feature: Create new sessions and get sessions

Scenario: Create and receive a new session
    Given I have an account
    And I am logged in
    When I create a new session
    Then I receive a new session

Scenario: Get old sessions
    Given I have an account
    And I am logged in
    And I have old sessions
    When I request my old sessions
    Then I receive my old sessions