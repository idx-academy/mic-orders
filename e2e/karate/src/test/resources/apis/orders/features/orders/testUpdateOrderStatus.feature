Feature: Update Order Status
  Background:
    * url urls.retailApiUrl

  Scenario Outline: Update Order Status <orderStatus> and role <role>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/orders', <orderId>, 'status'
    And headers authHeader
    And request { orderStatus: <orderStatus>, isPaid: <isPaid> }

    When method PATCH
    Then match responseStatus == <response>

    Examples:
      | response   | orderStatus    | username                  | password                  | orderId                                 | role      | isPaid   |
      | 200        | 'SHIPPED'      | '#(manager.username)'     | '#(manager.password)'     | '9583956f-5ee4-4c37-880f-33f0ed925c0b'  | 'MANAGER' | 'false'  |
      | 400        | 'COMPLETED'    | '#(manager.username)'     | '#(manager.password)'     | '9583956f-5ee4-4c37-880f-33f0ed925c0b'  | 'MANAGER' | 'false'  |
      | 403        | 'SHIPPED'      | '#(user.username)'        | '#(user.password)'        | '9583956f-5ee4-4c37-880f-33f0ed925c0b'  | 'USER'    | 'false'  |
      | 404        | 'SHIPPED'      | '#(manager.username)'     | '#(manager.password)'     | '84b7e490-0dcf-44c3-beb6-7496dc6ef3b0'  | 'MANAGER' | 'false'  |

