Feature: Try to use the API as a non-authorized application

  Background:
    Given there is a Badges server
    Given my application is not registered

  Scenario: Try to get my badges as a non-authorized application
    When I send a GET to the /badges endpoint
    Then I receive a 403 status code

  Scenario: Try to post a badge as a non-authorized application
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 403 status code

  Scenario: Try to post a rule as a non-authorized application
    Given I have a rule payload
    When I POST the rule payload to the /rules endpoint
    Then I receive a 403 status code

  Scenario: Try to post an event type as a non-authorized application
    Given I have an eventType payload
    When I POST the eventType payload to the /eventTypes endpoint
    Then I receive a 403 status code

  Scenario: Try to post an event as a non-authorized application
    Given I have a event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 403 status code