@op.getOrdersHistoryByUserId
Feature: Get orders history by user id
  Background:
    * url urls.retailApiUrl

  Scenario Outline: getOrdersHistoryByUserId <status> and role <role>
    * if (<role> == 'USER') {karate.set('credentials.username', 'user@mail.com'); karate.set('credentials.password', 'User_1234');}
    * if (<role> == 'ADMIN') {karate.set('credentials.username', 'admin@mail.com'); karate.set('credentials.password', 'Admin_1234');}
    Given def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/users/' + <userId> + '/orders'
    And headers authHeader

    When method Get

    Then match responseStatus == <status>

    Examples:
      | status | userId |   role   |
      | 200    | 2      |  'USER'  |
      | 403    | 3      |  'USER'  |
      | 200    | 1      |  'ADMIN' |
      | 200    | 2      |  'ADMIN' |

