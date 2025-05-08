Feature: End-to-End Purchase Flow

  Scenario:Successful purchase from login to order completion
    Given I am on the login page
    When  I login with valid credentials
    Then  I should be redirected to the home page

    When  I add All products to the cart
    Then  the cart icon should reflect the correct number of selected products

    When  I proceed to checkout
    And   I fill in my shipping information:

  Scenario: Verify Checkout Order Without Products in Cart
    Given The user is logged in with valid credentials
    And   The user navigates to the cart without adding products
    When  The user proceeds to checkout and enters valid personal information
    Then  The total price on the overview page should be "0.00"


  Scenario: Successfully remove a product from cart after adding random products
    Given I am logged in with valid credentials
    When  I add between 2 and 5 random products to the cart
    And   I navigate to the cart page
    And   I remove one product from the cart
    Then  The cart count should decrease by one


  Scenario: Successful logout after adding a product to the cart
    Given I am on the login page
    When I login with valid credentials
    And I add the first product to the cart
    And I open the side menu
    And I click on the logout button
    Then I should be redirected back to the login page


  Scenario: Reset app state after adding a product
    Given I am on the login page
    When  I login with valid credentials
    And   I add one product to the cart
    Then  the cart icon should reflect the correct number of items
    When  I perform reset app state from the side menu
    Then  the cart should be empty
    And   all buttons should be reset to "ADD TO CART"

  Scenario: Verify User Can Continue Shopping After adding product to the cart
    Given I am on the login page
    When  I login with valid credentials
    And   the user adds the first product to the cart
    Then  the cart icon should reflect the correct number of selected products

    When the user clicks on the cart icon
    And clicks on the Continue Shopping button
    And adds more products to the cart
    Then the cart icon should reflect the updated number of selected products

    When the user proceeds to checkout
    And enters valid checkout information
    Then the total price should be correctly calculated
    And the user finishes the purchase
    Then a thank you message should be displayed

