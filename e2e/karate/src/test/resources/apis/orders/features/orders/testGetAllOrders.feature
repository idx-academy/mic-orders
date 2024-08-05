Feature: Get all orders
  Background:
    * url urls.retailApiUrl

  Scenario Outline: getOrders <status> and role <role>
    Given def testData = call utils.readTestData <testDataFile>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/orders'
    And headers authHeader
    And param isPaid = 'false'
    And param statuses = 'CANCELED'
    And param statuses = 'IN_PROGRESS'
    And param deliveryMethods = 'NOVA'
    And param createdBefore = '2025-01-30T16:55:00Z'
    And param createdAfter = '2019-01-30T16:55:00Z'
    And param totalLess = '500'
    And param totalMore = '50'
    And param sort = 'id'
    And param size = 6

    When method Get

    Then match responseStatus == <status>
    And match response == testData

    Examples:
      | status | role      | username                  | password                  | testDataFile                                                 |
      | 200    | 'MANAGER' | '#(manager.username)'     | '#(manager.password)'     |'classpath:apis/orders/test-data/responses/page_200.json'     |
      | 403    | 'USER'    | '#(user.username)'        | '#(user.password)'        |'classpath:apis/orders/test-data/responses/response_4xx.json' |
      | 200    | 'ADMIN'   | '#(credentials.username)' | '#(credentials.password)' |'classpath:apis/orders/test-data/responses/page_200.json'     |