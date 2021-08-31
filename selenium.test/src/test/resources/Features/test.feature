Feature: Test Scenarios

  Scenario: Order T-Shirt and Verify in Order History
    Given Login to the site with "SeleniumTesting@gmail.com" and "Selenium123."
    When I order Tshirt
    Then I am able to see the Tshirt in Order History


  Scenario: Update Personal Information (First Name) in My Account
    Given Login to the site with "SeleniumTesting@gmail.com" and "Selenium123."
    When I update the First Name
    Then My Name should be updated successfully

