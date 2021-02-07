@notAuthorized
Feature: Try to use the API as a non-authorized application

  Background:
    Given there is a Badges server
    Given the server is running
    Given my application is register

  @getBadgesNN
  Scenario: Try to get non existing badges
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    And I GET a badge with non existing ID
    Then I receive a 404 status code

  @getBadgesNN
  Scenario: Try to get non existing rules
    Given I have a rule payload
    When I POST the rule payload to the /rules endpoint
    And I GET a rule with non existing ID
    Then I receive a 404 status code

  @getBadgesNN
  Scenario: Try to get non existing event
    Given I have a event payload
    When I POST the event payload to the /events endpoint
    And I GET a event with non existing ID
    Then I receive a 404 status code

  @getBadgesNN
  Scenario: Try to get non existing eventTypes
    Given I have an eventType payload
    When I POST the eventType payload to the /eventTypes endpoint
    And I GET a eventType with non existing ID
    Then I receive a 404 status code