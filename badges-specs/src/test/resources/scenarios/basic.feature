Feature: Basic operations on badges

  Background:
    Given there is a Badges server
    Given the server is running
    Given my application is register

  Scenario: create a badge
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

  Scenario: get the list of badges
    When I send a GET to the /badges endpoint
    Then I receive a 200 status code