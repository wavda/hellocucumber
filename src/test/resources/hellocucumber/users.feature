Feature: User Management

  @E2eTest @ApiTest
  Scenario: Add and remove user
    Given I want to create new user
    Then verify user "exist" in database
    When I login as user
    Then I login successfully
    When I delete user
    Then verify user "not exist" in database
