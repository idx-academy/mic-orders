@ignore
Feature: Get order by id

  Background:
    * url urls.retailApiUrl

  Scenario Outline: getOrderById <status> and role <role>
    Given def testDataFile = call utils.readTestData <testDataFile>
    * if (<role> == 'USER') {karate.set('credentials.username', 'user@mail.com'); karate.set('credentials.password', 'User_1234');}
    * if (<role> == 'ADMIN') {karate.set('credentials.username', 'admin@mail.com'); karate.set('credentials.password', 'Admin_1234');}
    * if (<role> == 'MANAGER') {karate.set('credentials.username', 'manager@mail.com'); karate.set('credentials.password', 'Manager_1234');}
    And def authHeader = call read('classpath:karate-auth.js') testDataFile.auth
    And path '/v1/orders', <orderId>
    And headers authHeader

    When method Get

    Then match responseStatus == <status>
    And match response == testDataFile

    Examples:
      | status | testDataFile                                            | role      | orderId                                |
      | 200    | 'classpath:apis/orders/test-data/getOrderById_200.json' | 'MANAGER' | '550e8400-e29b-41d4-a716-446655440000' |
      | 404    | 'classpath:apis/orders/test-data/response_4xx.json'     | 'MANAGER' | '550e8400-e29b-41d4-a716-446655440001' |
      | 403    | 'classpath:apis/orders/test-data/response_4xx.json'     | 'USER'    | '550e8400-e29b-41d4-a716-446655440000' |
      | 403    | 'classpath:apis/orders/test-data/response_4xx.json'     | 'ADMIN'   | '550e8400-e29b-41d4-a716-446655440000' |
