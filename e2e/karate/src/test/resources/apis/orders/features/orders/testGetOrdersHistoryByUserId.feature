Feature: Get orders history by user id
  Background:
    * url urls.retailApiUrl

  Scenario Outline: getOrdersHistoryByUserId <status> and role <role>
    Given def responseDataFile = call utils.readTestData <responseDataFile>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')(credentials)
    And path '/v1/users', <userId>, 'orders'
    And headers authHeader

    When method Get

    Then match responseStatus == <status>
    And match response == responseDataFile

    Examples:
      | status | userId | role    | username                  | password                  | responseDataFile                                             |
      | 200    | 2      | 'USER'  | '#(user.username)'        | '#(user.password)'        | 'classpath:apis/orders/test-data/responses/page_200.json'    |
      | 403    | 3      | 'USER'  | '#(user.username)'        | '#(user.password)'        | 'classpath:apis/orders/test-data/responses/response_4xx.json'|
      | 200    | 2      | 'ADMIN' | '#(credentials.username)' | '#(credentials.password)' | 'classpath:apis/orders/test-data/responses/page_200.json'    |

