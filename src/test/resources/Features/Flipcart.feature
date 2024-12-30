Feature: Validate Sort functionality for products displayed on Flipcart

  @flipcart1 
  Scenario: Validate Sort functionality for products displayed.
    Given User open the Flipkart website
    When User search for "shoes"
    And User sort the results by "Price - Low to High"
    Then User verify that prices are sorted in ascending order up to page 2
    
    
   @flipcart1 
   Scenario: Add to Cart and Validate Cart Details
   	Given User open the Flipkart website
    When User search for "shoes"
    And User sort the results by "Price - Low to High"
    
    And User click on the second available product in the list and add it to the cart
    And User click on the third available product in the list and add it to the cart
   
    And User validate the correct products are added with the correct price
    And User validate the Total sum