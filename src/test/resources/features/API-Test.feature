@api
Feature: ReqRes API validation

  Background:
    Given the ReqRes API is available

  #LIST USERS
  Scenario: Validate list users API returns success
    When I call the list users API for page 1
    Then the response status code should be 200

  Scenario: Validate list users API contains complete user data
    When I fetch all users from the API
    Then the total users count should match the number of returned users
    And all user ids should be unique

  Scenario: Validate list users API with delayed response
    When I call the list users API with delay 3
    Then the response status code should be 200
    And the response time should be less than 5 seconds

  #SINGLE USER
  Scenario Outline: Validate single user details
    When I fetch user with id <userId>
    Then the response status code should be <statusCode>

	Examples:
      | userId | statusCode |
      | 3      | 200        |
      | 55     | 404        |

  Scenario: Validate single user data fields
    When I fetch user with id 4
    Then the response status code should be 200
    And the user first name should be "Emma"
    And the user email should be "emma.wong@reqres.in"

  #LOGIN
  Scenario Outline: LOGIN - SUCCESSFUL by a user
    When I login with email "<email>" and password "<password>"
    Then the response status code should be 200
    And the login response should contain token

    Examples:
      | email              | password |
      | eve.holt@reqres.in | cityslicka |

  Scenario Outline: LOGIN - UNSUCCESSFUL by a user
    When I login with email "<email>" and password "<password>"
    Then the response status code should be 400
    And the error message should be "<errorMessage>"

    Examples:
      | email              | password | errorMessage        |
      | eve.holt@reqres.in |          | Missing password   |

  #CRUD
  Scenario: Validate create user API
    When I create a new user with name "morpheus" and job "leader"
    Then the response status code should be 201
    And the response should contain user name "morpheus"

  Scenario: Validate update user API
    When I update user with id 2 using name "neo" and job "the one"
    Then the response status code should be 200
    And the response should contain user name "neo"

  Scenario: Validate delete user API
    When I delete user with id 2
    Then the response status code should be 204
