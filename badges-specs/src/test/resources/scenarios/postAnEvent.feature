@postEventBadge
Feature: Post an event and get a badge
  Background:
    Given there is a Badges server
    Given my application is register

  @createBadge
  Scenario: create a badge
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

  @postEvent
  Scenario: post an event
    Given I have a event payload
    When I POST the event payload to the /events endpoint
    Then I GET a badge with ID 1

  @getEvent
  Scenario: get en event
    Given I have an eventType payload
    When I POST the eventType payload to the /eventTypes endpoint
    And I send a GET to the /eventTypes endpoint
    Then I receive a 200 status code
    And I receive a payload that is the same as the eventType payload