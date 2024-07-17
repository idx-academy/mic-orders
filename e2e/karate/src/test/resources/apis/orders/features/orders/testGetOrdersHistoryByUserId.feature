Feature: Get orders history by user id
  Background:
    * url urls.retailApiUrl

  Scenario Outline: getOrdersHistoryByUserId <status> and role <role>
    Given def responseDataFile = call utils.readTestData <responseDataFile>
    * if (<role> == 'USER') {karate.set('credentials.username', 'user@mail.com'); karate.set('credentials.password', 'User_1234');}
    * if (<role> == 'ADMIN') {karate.set('credentials.username', 'admin@mail.com'); karate.set('credentials.password', 'Admin_1234');}
    And def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/users', <userId>, 'orders'
    And headers authHeader

    When method Get

    Then match responseStatus == <status>
    And match response == responseDataFile

    Examples:
      | status | userId | role    | responseDataFile                                            |
      | 200    | 2      | 'USER'  | 'classpath:apis/orders/test-data/page_200.json' |
      | 403    | 3      | 'USER'  | 'classpath:apis/orders/test-data/response_4xx.json'         |
      | 200    | 2      | 'ADMIN' | 'classpath:apis/orders/test-data/page_200.json' |

