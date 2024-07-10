Feature: Cart endpoints
  Background:
    * def authHeader = callonce read('classpath:karate-auth.js')
    * def productFeature = callonce read('classpath:apis/orders/helpers/getProductId.feature')
    * def productId = productFeature.productId
    * def userId = credentials.id
    * url urls.retailApiUrl

  Scenario: View Cart Items (Successful Addition and Retrieval)
    * print userId
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + productId
    When method POST
    Then status 201

    Given headers authHeader
    And path 'v1/users/' + userId +'/cart/items'
    When method GET
    Then status 200
    And match response.items[*].productId contains productId
    And match response.items == "#array"
    And match response.totalPrice == "#number"

    Given headers authHeader
    And path 'v1/users/' + userId +'/cart/items/' + productId
    When method DELETE
    Then status 204

  Scenario:  Add Duplicate Product to Cart (Conflict)
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + productId
    When method POST
    Then status 201

    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + productId
    When method POST
    Then status 409

    Given headers authHeader
    And path 'v1/users/' + userId +'/cart/items/' + productId
    When method DELETE
    Then status 204

  Scenario: Add Non-Existent Product to Cart (Not Found)
    * def generator = Java.type('com.academy.orders.karate.generator.DataGenerator')
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + generator.generateUUID()
    When method POST
    Then status 404

  Scenario: Delete Non-Existent Product from Cart (Not Found)
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/items/' + productId
    When method DELETE
    Then status 404