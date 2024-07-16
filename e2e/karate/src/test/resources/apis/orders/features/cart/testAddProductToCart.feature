Feature: Add product to cart

  Background:
    * def productFeature = callonce read('classpath:apis/orders/helpers/getProductId.feature')
    * def productId = productFeature.productId
    * def authHeader = callonce read('classpath:karate-auth.js')
    * def userId = credentials.id
    * url urls.retailApiUrl

  Scenario: Add product to the cart (Created 201)
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + productId
    When method POST
    Then status 201

    Given headers authHeader
    And path 'v1/users/' + userId +'/cart/items/' + productId
    When method DELETE
    Then status 204

  Scenario:  Add Duplicate Product to Cart (Conflict 409)
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

  Scenario: Add Non-Existent Product to Cart (Not Found 404)
    * def generator = Java.type('com.academy.orders.karate.generator.DataGenerator')
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + generator.generateUUID()
    When method POST
    Then status 404