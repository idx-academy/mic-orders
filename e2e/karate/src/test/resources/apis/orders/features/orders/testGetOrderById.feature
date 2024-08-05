Feature: Get order by id

  Background:
    * url urls.retailApiUrl

  Scenario Outline: getOrderById <status> and role <role>
    Given def testDataFile = call utils.readTestData <testDataFile>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')(credentials)
    And path '/v1/management/orders', <orderId>
    And headers authHeader

    When method Get

    Then match responseStatus == <status>
    And match response == testDataFile

    Examples:
      | status | role      | username                  | password                  | orderId                               | testDataFile                                                     |
      | 200    | 'MANAGER' | '#(manager.username)'     | '#(manager.password)'     |'550e8400-e29b-41d4-a716-446655440015' |'classpath:apis/orders/test-data/responses/getOrderById_200.json' |
      | 404    | 'MANAGER' | '#(manager.username)'     | '#(manager.password)'     |'11111111-e29b-41d4-a716-446655440001' |'classpath:apis/orders/test-data/responses/response_4xx.json'     |
      | 403    | 'USER'    | '#(user.username)'        | '#(user.password)'        |'550e8400-e29b-41d4-a716-446655440015' |'classpath:apis/orders/test-data/responses/response_4xx.json'     |
      | 200    | 'ADMIN'   | '#(credentials.username)' | '#(credentials.password)' |'550e8400-e29b-41d4-a716-446655440015' |'classpath:apis/orders/test-data/responses/getOrderById_200.json' |