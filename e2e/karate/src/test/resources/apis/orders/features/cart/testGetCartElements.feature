Feature: Get cart items
  Background:
    * def authHeader = callonce read('classpath:karate-auth.js')
    * def productFeature = callonce read('classpath:apis/orders/helpers/getProductId.feature')
    * def productId = productFeature.productId
    * def userId = credentials.id
    * url urls.retailApiUrl

  Scenario: View Cart Items (Ok 200)
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + productId
    When method POST
    Then status 201

    Given headers authHeader
    And path 'v1/users/' + userId +'/cart/items'
    When method GET
    Then status 200
    And match response.items[*].productId contains productId
    And match response ==
    """
    {
      "items": [
        {
          "productId": "#string",
          "image": "#string",
          "name": "#string",
          "productPrice": "#number",
          "quantity": "#number",
          "calculatedPrice": "#number"
        }
      ],
      "totalPrice": "#number"
    }
    """

    Given headers authHeader
    And path 'v1/users/' + userId +'/cart/items/' + productId
    When method DELETE
    Then status 204
