Feature: Basic operations on rules
  Background:
    Given there is a Badges server
    Given the server is running
    Given my application is register

  Scenario: post a rule
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Given I have an eventType payload
    When I POST the eventType payload to the /eventTypes endpoint
    Given i have a rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code
    Then I GET a rule with ID 1
    Then I receive a rule that is the same as the payload
