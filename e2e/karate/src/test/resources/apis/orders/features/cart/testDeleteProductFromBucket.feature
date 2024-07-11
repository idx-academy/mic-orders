Feature: Delete product form cart \

  Background:
    * def generator = Java.type('com.academy.orders.karate.generator.DataGenerator')
    * def productId = generator.generateUUID()
    * def authHeader = callonce read('classpath:karate-auth.js')
    * def userId = credentials.id
    * url urls.retailApiUrl

  Scenario: Delete Non-Existent Product from Cart (Not Found 404)
    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/items/' + productId
    When method DELETE
    Then status 404