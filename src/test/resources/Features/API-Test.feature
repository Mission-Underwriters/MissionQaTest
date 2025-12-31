Feature: API Automation Tests
  # Base URL: https://reqres.in/
  
  Scenario: List all users on first page
    Given I request the list of users on page 1
    When I fetch all users across all pages
    Then total users count should equal the number of user ids

  Scenario: Get single user data
    Given I request user with id 3
    Then the response should contain:
      | first_name | email               |
      | Emma       | emma.wong@reqres.in |

Scenario: Single user not found
  Given I request user with id 55
  Then I should receive HTTP status code 403


  Scenario Outline: Create a new user
    Given I create a user with name "<Name>" and job "<Job>"
    Then the response should contain the following fields:
      | name | job | id | createdAt |

    Examples:
      | Name  | Job     |
      | Peter | Manager |
      | Liza  | Sales   |

  Scenario: Login successful
    Given I login with credentials:
      | Email              | Password   |
      | eve.holt@reqres.in | cityslicka |
    Then the response status code should be 200
    And response should contain a token

Scenario: Login unsuccessful
  Given I login with credentials:
    | Email              | Password |
    | eve.holt@reqres.in |          |
  Then the response status code should be 400
  And the response should contain error message:
    | error | Missing password |
    
  Scenario: Delayed response user list
    Given I request the list of users with a delay of 3 seconds
    Then each user in the response should have a unique id
