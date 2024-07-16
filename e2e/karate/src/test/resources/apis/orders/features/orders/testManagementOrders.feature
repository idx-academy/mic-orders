Feature: Get all orders
  Background:
    * url urls.retailApiUrl

  Scenario Outline: getOrders <status> and role <role>
    Given def testData  = call utils.readTestData <testDataFile>
    * if (<role> == 'USER') {karate.set('credentials.username', 'user@mail.com'); karate.set('credentials.password', 'User_1234');}
    * if (<role> == 'ADMIN') {karate.set('credentials.username', 'admin@mail.com'); karate.set('credentials.password', 'Admin_1234');}
    * if (<role> == 'MANAGER') {karate.set('credentials.username', 'manager@mail.com'); karate.set('credentials.password', 'Manager_1234');}
    And def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/orders'
    And headers authHeader

    When method Get

    Then match responseStatus == <status>
    And match response == testData

    Examples:
      | status |  role   | testDataFile                       |
      | 200    | 'MANAGER' |'classpath:apis/orders/test-data/page_200.json'|
      | 403    | 'USER'  |'classpath:apis/orders/test-data/response_4xx.json'|
      | 403    | 'USER'  |'classpath:apis/orders/test-data/response_4xx.json'|
      | 403    | 'ADMIN' |'classpath:apis/orders/test-data/response_4xx.json'|