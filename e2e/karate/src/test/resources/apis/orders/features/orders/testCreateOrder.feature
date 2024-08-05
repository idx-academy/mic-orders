Feature: Create Order

  Background:
    * def DbUtils = Java.type('com.academy.orders.karate.db.DbUtils')
    * def db = new DbUtils(datasource)
    * def productFeature = callonce read('classpath:apis/orders/helpers/getProductId.feature')
    * def productId = productFeature.productId
    * def authHeader = callonce read('classpath:karate-auth.js')
    * def userId = credentials.id
    * def requestData = call utils.readTestData 'classpath:apis/orders/test-data/requests/createOrderRequest.json'
    * url urls.retailApiUrl

  Scenario: Create order (Created 201)
    * def quantityBeforeCreation = db.getProductQuantity(productId)
    * eval db.increaseProductQuantity(productId)

    Given headers authHeader
    And path 'v1/users/' + userId + '/cart/' + productId
    When method POST
    Then status 201

    Given headers authHeader
    And path 'v1/users/' + userId + '/orders'
    And request requestData
    When method POST
    Then status 201
    And match response.orderId == "#string"
    * def quantityAfterCreation = db.getProductQuantity(productId)
    And match quantityBeforeCreation == quantityAfterCreation

    Given headers authHeader
    And path 'v1/users/' + userId +'/cart/items'
    When method GET
    Then status 200
    And match response.items == '#[0]'

  Scenario: Create order (Bad Request 201 - empty cart)
    Given headers authHeader
    And path 'v1/users/' + userId + '/orders'
    And request requestData
    When method POST
    Then status 400

  Scenario Outline: Create order (Bad Request 400 - incorrect <message>)
    * set requestData.firstName = '<firstName>'
    * set requestData.lastName = '<lastName>'
    * set requestData.email = '<email>'
    * set requestData.city = '<city>'
    * set requestData.department = '<department>'

    Given headers authHeader
    And path 'v1/users/' + userId + '/orders'
    And request requestData
    When method POST
    Then status 400

  Examples:
    | firstName | lastName | email         | city | department          | message    |
    | F         | LastName | mail@mail.com | Lviv | №1 Franka street, 7 | firstName  |
    | firstName | L        | mail@mail.com | Lviv | №1 Franka street, 7 | lastName   |
    | firstName | LastName | mail-mail.com | Lviv | №1 Franka street, 7 | email      |
    | firstName | LastName | mail@mail.com | L    | №1 Franka street, 7 | city       |
    | firstName | LastName | mail@mail.com | Lviv | 1                   | department |
