Feature: Post an event and get a badge
  Background:
    Given there is a Badges server
    Given my application is register
    Given I register a rule with a threshold of 2

  Scenario: post an event
    Given I have a event payload
    When I POST the event payload to the /events endpoint
    Then I receive a 200 status code
    When I POST the event payload to the /events endpoint
    Then I receive a 200 status code
    Then I receive the badge of the rule