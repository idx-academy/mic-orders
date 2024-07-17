@ignore
Feature: Update Order Status

  Background:
    * url urls.retailApiUrl

  Scenario Outline: Update Order Status <status> and role <role>
    * if (<role> == 'MANAGER') {karate.set('credentials.username', 'manager@mail.com'); karate.set('credentials.password', 'Manager_1234');}
    * if (<role> == 'USER') {karate.set('credentials.username', 'user@mail.com'); karate.set('credentials.password', 'User_1234');}
    Given def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/orders', <orderId>, 'status'
    And param orderStatus = <status>
    And headers authHeader
    When method PATCH
    Then match responseStatus == <response>

    Examples:
      | response   | status         | orderId                                 | role      |
      | 200        | 'COMPLETED'    | '6fa459ea-ee8a-3ca4-894e-db77e160355e'  | 'MANAGER' |
      | 400        | 'COMPLETEDDD'  | '6fa459ea-ee8a-3ca4-894e-db77e160355e'  | 'MANAGER' |
      | 403        | 'COMPLETED'    | '6fa459ea-ee8a-3ca4-894e-db77e160355e'  | 'USER'    |
      | 404        | 'COMPLETED'    | '84b7e490-0dcf-44c3-beb6-7496dc6ef3b0'  | 'MANAGER' |