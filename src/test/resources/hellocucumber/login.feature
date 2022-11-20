Feature: Login

  @SmokeTest @RegressionTest
  Scenario: Login as admin
    Given I am on the OrangeHRM login page
    When I input "Admin" as "username"
    And I input "admin123" as "password"
    And I click login button
    Then login success and profile dropdown should be displayed
