@api	
Feature: API Automation Tests
  # This feature validates ReqRes public APIs using Rest Assured.
  # 
  # Base URL: https://reqres.in/

  # -------------------------------------------------
  # Validate pagination and total user count
  # -------------------------------------------------
  Scenario: List all users on first page
    Given I request the list of users on page 1
    When I fetch all users across all pages
    Then total users count should equal the number of user ids

  # -------------------------------------------------
  # Validate single user data by ID
  # -------------------------------------------------
  Scenario: Get single user data
    Given I request user with id 3
    Then the response should contain:
      | first_name | email               |
      | Emma       | emma.wong@reqres.in |

  # -------------------------------------------------
  # Validate behavior when user does not exist
  # -------------------------------------------------
  Scenario: Single user not found
    Given I request user with id 55
    Then I should receive HTTP status code 403

  # -------------------------------------------------
  # Validate user creation with multiple data sets
  # -------------------------------------------------
  Scenario Outline: Create a new user
    Given I create a user with name "<Name>" and job "<Job>"
    Then the response should contain the following fields:
      | name | job | id | createdAt |

    Examples:
      | Name  | Job     |
      | Peter | Manager |
      | Liza  | Sales   |

  # -------------------------------------------------
  # Validate successful login flow
  # -------------------------------------------------
  Scenario: Login successful
    Given I login with credentials:
      | Email              | Password   |
      | eve.holt@reqres.in | cityslicka |
    Then the response status code should be 200
    And response should contain a token

  # -------------------------------------------------
  # Validate login failure when password is missing
  # -------------------------------------------------
  Scenario: Login unsuccessful
    Given I login with credentials:
      | Email              | Password |
      | eve.holt@reqres.in |          |
    Then the response status code should be 400
    And the response should contain error message:
      | error | Missing password |

  # -------------------------------------------------
  # Validate delayed API response and data integrity
  # -------------------------------------------------
  Scenario: Delayed response user list
    Given I request the list of users with a delay of 3 seconds
    Then each user in the response should have a unique id
