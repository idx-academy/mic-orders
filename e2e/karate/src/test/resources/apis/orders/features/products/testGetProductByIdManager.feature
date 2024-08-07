Feature: Get Product by ID Manager

  Background:
    * url urls.retailApiUrl

  Scenario Outline: Get Product by ID Manager
    Given def testDataFile = call utils.readTestData <testDataFile>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')(credentials)
    And path '/v1/management/products', <productId>
    And headers authHeader

    When method GET

    Then match responseStatus == <response>
    And match response == testDataFile

    Examples:
      | response        | username                  | password                  | productId                               | role      |testDataFile                                                               |
      | 200             | '#(manager.username)'     | '#(manager.password)'     | '1417d9a9-d883-419e-9856-163bbdf1c47a'  | 'MANAGER' |'classpath:apis/orders/test-data/responses/getProductByIdManager_200.json' |
      | 403             | '#(user.username)'        | '#(user.password)'        | '1417d9a9-d883-419e-9856-163bbdf1c47a'  | 'USER'    |'classpath:apis/orders/test-data/responses/response_4xx.json'              |
      | 404             | '#(manager.username)'     | '#(manager.password)'     | '9583956f-5ee4-4c37-880f-33f0ed925c0b'  | 'MANAGER' |'classpath:apis/orders/test-data/responses/response_4xx.json'              |
      | 200             | '#(credentials.username)' | '#(credentials.password)' | '1417d9a9-d883-419e-9856-163bbdf1c47a'  | 'ADMIN'   |'classpath:apis/orders/test-data/responses/getProductByIdManager_200.json' |

