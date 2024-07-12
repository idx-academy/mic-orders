Feature: Update Order Status

  Background:
    * url urls.retailApiUrl
    * def orderId = '6fa459ea-ee8a-3ca4-894e-db77e160355e'
    * def nonExistingOrderId = '84b7e490-0dcf-44c3-beb6-7496dc6ef3b0'

  Scenario Outline: Update Order Status, Successfully OK with Role MANAGER and FORBIDDEN with Role USER
    * if (<role> == 'MANAGER') {karate.set('credentials.username', 'manager@mail.com'); karate.set('credentials.password', 'Manager_1234');}
    * if (<role> == 'USER') {karate.set('credentials.username', 'user@mail.com'); karate.set('credentials.password', 'User_1234');}
    Given def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/orders', orderId, 'status'
    And param orderStatus = 'COMPLETED'
    And headers authHeader
    When method PATCH
    Then match responseStatus == <status>

    Examples:
      | status |  role     |
      | 200    | 'MANAGER' |
      | 403    | 'USER'    |


  Scenario Outline: Update Order Status with Incorrect Status | BAD REQUEST
    * if (<role> == 'MANAGER') {karate.set('credentials.username', 'manager@mail.com'); karate.set('credentials.password', 'Manager_1234');}
    Given def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/orders', orderId, 'status'
    And param orderStatus = 'COMPLETEDDDD'
    And headers authHeader
    When method PATCH
    Then match responseStatus == <status>

    Examples:
      | status |  role     |
      | 400    | 'MANAGER' |

  Scenario Outline: Update Order Status with Non-existing Order ID | NOT FOUND
    * if (<role> == 'MANAGER') {karate.set('credentials.username', 'manager@mail.com'); karate.set('credentials.password', 'Manager_1234');}
    Given def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/orders', nonExistingOrderId, 'status'
    And param orderStatus = 'COMPLETED'
    And headers authHeader
    When method PATCH
    Then match responseStatus == <status>

    Examples:
      | status |  role     |
      | 404    | 'MANAGER' |