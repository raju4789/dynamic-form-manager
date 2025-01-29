Feature: Authentication API
  As a user
  I want to authenticate using the API
  So that I can access protected resources

  @scenario_Authentication
  Scenario Outline: Authentication with different credentials
    Given the user provides request:
      """
      {
        "username": "<username>",
        "password": "<password>"
      }
      """
    When the user sends a POST request to "<endpoint>"
    Then the response status should be <statusCode>
    And the response should contain error "<errorMessage>"

    Examples:
      | endpoint                     | username      | password       | statusCode | errorMessage     |
      | /api/v1/auth/authenticate    | admin         | admin@4789     | 200        | null             |
      | /api/v1/auth/authenticate    | admin         | wrongPassword  | 400        | Bad credentials  |