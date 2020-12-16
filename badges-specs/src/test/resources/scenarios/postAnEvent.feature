Feature: Post an event and get a badge
  Background:
    Given there is a Badges server
    Given my application is register

  Scenario: create a badge
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

  Scenario: post an event
    Given I have a event payload
    When I POST the event payload to the /events endpoint
    Then I GET a badge with ID 1