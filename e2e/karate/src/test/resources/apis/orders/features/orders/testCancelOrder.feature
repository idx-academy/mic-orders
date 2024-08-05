Feature: Cancel an order
  Background:
    * url urls.retailApiUrl
    * def productFeature = callonce read('classpath:apis/orders/helpers/getProductId.feature')
    * def productId = productFeature.productId
    * def requestData = call utils.readTestData 'classpath:apis/orders/test-data/createOrderRequest.json'
    * def userId = credentials.id
    * def authHeader = callonce read('classpath:karate-auth.js')

  Scenario: Cancel an order
    Given headers authHeader
    And path 'v1/users', userId, 'cart', productId
    When method POST
    Then status 201

    Given headers authHeader
    And path 'v1/users', userId ,'orders'
    And request requestData
    When method POST
    Then status 201
    * def orderId = response.orderId

    Given headers authHeader
    And path 'v1/users', userId, 'orders', orderId, 'cancel'
    And headers authHeader
    When method PATCH
    Then status 200