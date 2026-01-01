@ui
Feature: Checkout flow

  Background:
    Given I am on the home page

  Scenario Outline: Validate login for a user
    Given I login with username "<userName>" and password "<Password>"
    Then I should see "<status>"

    Examples:
      | userName                | Password     | status  |
      | standard_user           | secret_sauce | Success |
      | locked_out              | secret_sauce | Fail    |
      | problem_user            | secret_sauce | Success |
      | performance_glitch_user | secret_sauce | Success |
      | error_user              | secret_sauce | Success |
      | visual_user             | secret_sauce | Success |

  Scenario: Add items to cart
    And I login with the user details
      | userName      | Password     |
      | standard_user | secret_sauce |
    When I add the following items to the basket
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Bolt T-Shirt  |
      | Sauce Labs Onesie        |
    Then I should see 4 items added to the shopping cart

  Scenario: Remove item from cart
    And I login with the user details
      | userName      | Password     |
      | standard_user | secret_sauce |
    Given I have added items to the basket
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Bolt T-Shirt  |
      | Sauce Labs Onesie        |
    And I open the shopping cart
    When I remove the following item:
      | Sauce Labs Fleece Jacket |
    Then I should see 3 items added to the shopping cart

  Scenario: Verify item quantity in cart is 1
    And I login with the user details
      | userName      | Password     |
      | standard_user | secret_sauce |
    Given I have added items to the basket
      | Sauce Labs Backpack |
      | Sauce Labs Onesie   |
    And I open the shopping cart
    Then quantity for each item should be 1

  Scenario: Verify item total and tax calculation
    And I login with the user details
      | userName      | Password     |
      | standard_user | secret_sauce |
    Given I have added items to the basket
      | Sauce Labs Backpack |
      | Sauce Labs Onesie   |
    And I open the shopping cart
    And I proceed to checkout
    And I enter checkout information with First Name "Shivangi", Last Name "Pathak", and ZIP "380080"
    When I continue checkout
    Then item total should match sum of item prices
    And tax should be calculated at 8 percent
    And total amount should be item total plus tax

  Scenario: Complete checkout successfully
    And I login with the user details
      | userName      | Password     |
      | standard_user | secret_sauce |
    Given I have added items to the basket
      | Sauce Labs Backpack |
    And I open the shopping cart
    And I proceed to checkout
    And I enter checkout information with First Name "Shivangi", Last Name "Pathak", and ZIP "380080"
    And I continue checkout
    When I finish checkout
    Then I should see the order confirmation message