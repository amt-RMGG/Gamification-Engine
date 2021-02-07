@getAll
Feature: Basic operations on badges

  Background:
    Given there is a Badges server
    Given the server is running
    Given my application is register

  @badgePost
  Scenario: create a badge
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

  @twoSameBadge
  Scenario: create the same badge 2 times
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code

  @getList
  Scenario: get the list of badges
    When I send a GET to the /badges endpoint
    Then I receive a 200 status code

  @getListApp
  Scenario: get the list of my application badges only
    Given my application is register
    Given I have a badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code
    Given another application is register
    Given I have another badge payload
    When I POST the badge payload to the /badges endpoint
    Then I receive a 201 status code
    When I send a GET to the /badges endpoint
    Then I receive badges of my application only