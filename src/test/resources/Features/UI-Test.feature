@ui
Feature: SauceDemo Application Functionality
  # This feature covers core UI flows of the SauceDemo application.
  # 
  # Login validations, Product actions (add/remove/sort), Checkout flow, Logout

  Background:
    # Background ensures the user is already logged in
    # for scenarios that require authenticated access
    Given I am logged into SauceDemo with valid credentials

  # -------------------------------------------------
  # CART FUNCTIONALITY
  # -------------------------------------------------
  Scenario: Add product to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge count should be "1"

  Scenario: Remove product from cart
    When I add "Sauce Labs Backpack" to the cart
    And I remove "Sauce Labs Backpack" from the cart
    Then the cart badge count should be "0"

  # -------------------------------------------------
  # PRODUCT SORTING
  # -------------------------------------------------
  Scenario: Sort products by price low to high
    When I sort products by "Price (low to high)"
    Then products should be sorted by price in ascending order

  # -------------------------------------------------
  # CHECKOUT FLOW
  # -------------------------------------------------
  Scenario: Complete checkout successfully
    When I add "Sauce Labs Backpack" to the cart
    And I proceed to checkout
    And I enter checkout details
      | firstName | lastName | postalCode |
      | John      | Doe      | 560001     |
    And I finish the checkout
    Then I should see the order confirmation message "Thank you for your order!"

  # -------------------------------------------------
  # LOGIN VALIDATION (NEGATIVE SCENARIOS)
  # -------------------------------------------------
  Scenario Outline: Login validation scenarios
    # This scenario outline validates different invalid login combinations
    # and ensures proper error messages are displayed
    Given I am on SauceDemo login page
    When I login with username "<username>" and password "<password>"
    Then I should see login error message "<errorMessage>"

    Examples:
      | username        | password     | errorMessage                          |
      | locked_out_user | secret_sauce | Sorry, this user has been locked out. |
      | invalid_user    | invalid_pass | Username and password do not match    |
      | standard_user   |              | Password is required                  |
      |                 | secret_sauce | Username is required                  |

  # -------------------------------------------------
  # LOGIN WITH SPECIAL USERS
  # -------------------------------------------------
  Scenario: Login with problem user
    # Problem user is used to validate UI behavior issues
    Given I am on SauceDemo login page
    When I login with username "problem_user" and password "secret_sauce"
    Then I should be logged in successfully

  Scenario: Login with performance glitch user
    # Performance glitch user helps validate application behavior
    # under slower response conditions
    Given I am on SauceDemo login page
    When I login with username "performance_glitch_user" and password "secret_sauce"
    Then I should be logged in successfully

  # -------------------------------------------------
  # LOGOUT
  # -------------------------------------------------
  Scenario: Logout from application
    When I logout from the application
    Then I should be logged out successfully
