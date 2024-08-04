@run
Feature: Update Product Status
  Background:
    * url urls.retailApiUrl

  Scenario Outline: Update product status <status> and role <role>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/products', <productId>, 'status'
    And param status = <status>
    And headers authHeader
    When method PATCH
    Then match responseStatus == <response>

    Examples:
      | response   | status         | username                  | password              | productId                                | role      |
      | 200        | 'VISIBLE'      | '#(manager.username)'     | '#(manager.password)' | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'MANAGER' |
      | 200        | 'HIDDEN'       | '#(manager.username)'     | '#(manager.password)' | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'MANAGER' |
      | 400        | 'COMPLETEDDD'  | '#(manager.username)'     | '#(manager.password)' | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'MANAGER' |
      | 403        | 'VISIBLE'      | '#(user.username)'        | '#(user.password)'    | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'USER'    |
      | 404        | 'VISIBLE'      | '#(manager.username)'     | '#(manager.password)' | '84b7e490-0dcf-44c3-beb6-7496dc6ef3b2'   | 'MANAGER' |