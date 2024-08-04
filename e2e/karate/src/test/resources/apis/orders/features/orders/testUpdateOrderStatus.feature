@ignore
Feature: Update Order Status

  Background:
    * url urls.retailApiUrl

  Scenario Outline: Update Order Status <status> and role <role>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/orders', <orderId>, 'status'
    And param orderStatus = <status>
    And headers authHeader
    When method PATCH
    Then match responseStatus == <response>

    Examples:
      | response   | status         | username                  | password                  | orderId                                 | role      |
      | 204        | 'COMPLETED'    | '#(manager.username)'     | '#(manager.password)'     | '6fa459ea-ee8a-3ca4-894e-db77e160355e'  | 'MANAGER' |
      | 400        | 'COMPLETEDDD'  | '#(manager.username)'     | '#(manager.password)'     | '6fa459ea-ee8a-3ca4-894e-db77e160355e'  | 'MANAGER' |
      | 403        | 'COMPLETED'    | '#(user.username)'        | '#(user.password)'        | '6fa459ea-ee8a-3ca4-894e-db77e160355e'  | 'USER'    |
      | 404        | 'COMPLETED'    | '#(manager.username)'     | '#(manager.password)'     | '84b7e490-0dcf-44c3-beb6-7496dc6ef3b0'  | 'MANAGER' |