Feature: Update Product Status

  Background:
    * url urls.retailApiUrl

  Scenario Outline: Update product status <status> and role <role>
    * if (<role> == 'MANAGER') {karate.set('credentials.username', 'manager@mail.com'); karate.set('credentials.password', 'Manager_1234');}
    * if (<role> == 'USER') {karate.set('credentials.username', 'user@mail.com'); karate.set('credentials.password', 'User_1234');}
    Given def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/products', <productId>, 'status'
    And param status = <status>
    And headers authHeader
    When method PATCH
    Then match responseStatus == <response>

    Examples:
      | response   | status         | productId                                | role      |
      | 200        | 'VISIBLE'      | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'MANAGER' |
      | 200        | 'HIDDEN'       | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'MANAGER' |
      | 400        | 'COMPLETEDDD'  | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'MANAGER' |
      | 403        | 'VISIBLE'      | '52d4f45c-70a8-4f36-99d0-d645c5f704b2'   | 'USER'    |
      | 404        | 'VISIBLE'      | '84b7e490-0dcf-44c3-beb6-7496dc6ef3b2'   | 'MANAGER' |