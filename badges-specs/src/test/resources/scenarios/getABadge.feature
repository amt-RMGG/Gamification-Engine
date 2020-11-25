Feature: Create and get a badge

  Background:
    Given there is a Badges server
    Given my application is register

  Scenario: the expiration date and time are managed
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code with a location header
    When I GET a badge with ID 1
    Then I receive a 200 status code
    And I receive a payload that is the same as the badge payload