Feature: Basic operations on rules
  Background:
    Given there is a Badges server
    Given the server is running
    Given my application is register

  Scenario: post a rule
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Given I have an eventCounter payload
    When I POST the eventCounter payload to the /eventCounters endpoint
    Given I have a rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code
    Then I GET the created rule
    Then I receive a rule that is the same as the payload
