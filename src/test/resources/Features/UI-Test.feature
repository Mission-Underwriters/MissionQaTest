Feature: SauceDemo Application Functionality

  Background:
    Given I am logged into SauceDemo with valid credentials

  Scenario: Add product to cart
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge count should be "1"

  Scenario: Remove product from cart
    When I add "Sauce Labs Backpack" to the cart
    And I remove "Sauce Labs Backpack" from the cart
    Then the cart badge count should be "0"

  Scenario: Sort products by price low to high
    When I sort products by "Price (low to high)"
    Then products should be sorted by price in ascending order

  Scenario: Complete checkout successfully
    When I add "Sauce Labs Backpack" to the cart
    And I proceed to checkout
    And I enter checkout details
      | firstName | lastName | postalCode |
      | John      | Doe      | 560001     |
    And I finish the checkout
    Then I should see the order confirmation message "Thank you for your order!"

  Scenario: Logout from application
    When I logout from the application
    Then I should be logged out successfully
