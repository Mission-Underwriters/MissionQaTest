@ui
Feature: SauceDemo Login and Checkout tests

Background:
	Given I open SauceDemo application

  #LOGIN VALIDATION
  Scenario Outline: Login validation for success users
    When I login with username "<username>" and password "<password>"
    Then I should see "<status>"

    Examples:
      | username                | password       | loginResult        |
      | standard_user           | secret_sauce   | login success      |
      | problem_user            | secret_sauce   | login success      |
      | performance_glitch_user | secret_sauce   | login success      |
      | error_user | secret_sauce   | login success      |
      | visual_user | secret_sauce   | login success      |
      | locked_out_user         | secret_sauce   | user locked error  |

  Scenario: Checkout items and verify total and tax

  #CHECKOUT FLOW
  Scenario: Checkout items and verify total and tax
    And I login with username "standard_user" and password "secret_sauce"

    And I add the following items to the cart
      | Sauce Labs Backpack      |
      | Sauce Labs Fleece Jacket |
      | Sauce Labs Bolt T-Shirt  |
      | Sauce Labs Onesie        |

    Then I should see 4 items in the cart

    When I remove item "Sauce Labs Fleece Jacket"
    Then I should see 3 items in the cart

    When I proceed to checkout
    And I enter checkout details
      | firstName | lastName | zipCode |
      | John      | Doe      | 560001  |

    Then item total should be correct
    And tax should be calculated as 8 percent