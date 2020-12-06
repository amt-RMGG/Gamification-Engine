Feature: Basic operations on rules
  Background:
    Given there is a Badges server
    Given the server is running
    Given my application is register
    Given I register a eventType

  Scenario: post a rule
    Given i have a rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code
    Then I GET a rule with ID 1
    Then I receive a rule that is the same as the payload
