Feature: invoice functionality
  Scenario: get all the invoices and validate if response if more or equals to 1
    Given the user is on base url "https://backend.cashwise.us"
    Given the user have valid token
    Given the user hits get API with endpoint "/api/myaccount/invoices/seller" and with params "page" and "size" x
    Then the user validates if response is more or equal to 1

  Scenario: Create a new product
    Given the user is on base url "https://backend.cashwise.us"
    Given the user have valid token
    When the user sends a POST request to create a product with the following details:
    Then the response status code should be 201
    And the response should contain the product name "Product 1"


Scenario:
  Given the user is on base url "https://backend.cashwise.us"
  Given the user have valid token
  And provide request body
  Then i hit POST endpoint ""
  And verify status code is 200

