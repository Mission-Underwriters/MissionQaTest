@ui
Feature: Checkout items in the basket
  Please use home page of https://www.saucedemo.com/

  Scenario: Check item total cost and tax
    Given I am on the home page
    And I login in with the following details
      | userName      | Password     |
      | standard_user | secret_sauce |
