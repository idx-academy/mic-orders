Feature: Update Product
  Background:
    * url urls.retailApiUrl

  Scenario Outline: Update Product
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/products', <productId>
    And headers authHeader
    And request { status: <status>, productTranslations: [ { name: 'APPLE iPhone', languageCode: <languageCode> } ] }

    When method PATCH
    Then match responseStatus == <response>

    Examples:
      | response   | status         | username                  | password                  | productId                               | role      | languageCode |
      | 200        | 'VISIBLE'      | '#(manager.username)'     | '#(manager.password)'     | '1417d9a9-d883-419e-9856-163bbdf1c47a'  | 'MANAGER' | uk           |
      | 403        | 'VISIBLE'      | '#(user.username)'        | '#(user.password)'        | '1417d9a9-d883-419e-9856-163bbdf1c47a'  | 'USER'    | en           |
      | 404        | 'VISIBLE'      | '#(manager.username)'     | '#(manager.password)'     | '9583956f-5ee4-4c37-880f-33f0ed925c0b'  | 'MANAGER' | ukkk         |

