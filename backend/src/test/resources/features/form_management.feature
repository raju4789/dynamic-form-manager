Feature: Form Management APIs
  As a user
  I want to manage services, fields, and languages
  So that I can retrieve and use them in the application

  @scenario_FormManagement
  Scenario Outline: Retrieve resources with a valid token
    Given the user has a valid token
    When the user sends a GET request to "<endpoint>"
    Then the response status should be 200
    And the response should has length of <length>

    Examples:
      | endpoint                             | length       |
      | /api/v1/manage/service               | 4            |
      | /api/v1/manage/service/fields/101    | 4            |
      | /api/v1/manage/language              | 2            |
      | /api/v1/manage/service/fields/9999   | 0            |

  @scenario_FormManagement_ErrorCases
  Scenario Outline: Retrieve resources with an invalid token
    Given the user has an invalid token
    When the user sends a GET request to "<endpoint>"
    Then the response status should be 500
    And the response should contain an error message

    Examples:
      | endpoint
      | /api/v1/manage/service


  @scenario_FormManagement_ErrorCases
  Scenario Outline: Retrieve resources with an invalid token
    Given the user sends a GET request with no token to "<endpoint>"
    Then the response status should be 400
    And the response should contain an error message

    Examples:
      | endpoint
      | /api/v1/manage/service/fields/101

