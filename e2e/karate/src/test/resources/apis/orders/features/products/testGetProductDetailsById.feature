Feature: Get Product details by ID

  Background:
    * url urls.retailApiUrl

  Scenario Outline: Get Product details by ID
    Given def testDataFile = call utils.readTestData <testDataFile>
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')(credentials)
    And path '/v1/products', <productId>
    Given params {lang: <lang>}
    And headers authHeader

    When method GET

    Then match responseStatus == <response>
    And match response == testDataFile

    Examples:
      | response        | username                  | password                  | productId                               | role      |testDataFile                                                        |lang     |
      | 200             | '#(user.username)'        | '#(user.password)'        | '1417d9a9-d883-419e-9856-163bbdf1c47a'  | 'USER'    |'classpath:apis/orders/test-data/responses/getProductById_200.json' |uk       |
      | 404             | '#(user.username)'        | '#(user.password)'        | '9583956f-5ee4-4c37-880f-33f0ed925c0b'  | 'USER'    |'classpath:apis/orders/test-data/responses/response_4xx.json'       |en       |
      | 404             | '#(user.username)'        | '#(user.password)'        | '1417d9a9-d883-419e-9856-163bbdf1c47a'  | 'USER'    |'classpath:apis/orders/test-data/responses/response_4xx.json'       |ennn     |
