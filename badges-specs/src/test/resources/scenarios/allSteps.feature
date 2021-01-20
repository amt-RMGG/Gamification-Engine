Feature: All Steps

  Background:
    Given there is a Badges server
    Given my application is register

  Scenario:
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code with a location header
    Given I have an eventType payload
    When I POST the eventType payload to the /eventTypes endpoint
    Then I receive a 201 status code with a location header
    Given I have a rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 201 status code with a location header
    When I have a event payload
    When I POST the event payload 2 times to the /events endpoint
    Then I receive the award-badge
