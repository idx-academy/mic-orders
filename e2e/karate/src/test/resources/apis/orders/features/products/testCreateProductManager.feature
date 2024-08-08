Feature: Create Product
  Background:
    * url urls.retailApiUrl

  Scenario Outline: Create Product
    * def credentials = { username: <username>, password: <password> }
    * def authHeader = call read('classpath:karate-auth.js')
    And path '/v1/management/products'
    And headers authHeader
    And request read('classpath:apis/orders/test-data/requests/createProductRequest.json')

    When method POST
    Then match responseStatus == <response>
    And match response == read(<testDataFile>)

    Examples:
      | response   | username                  | password                  | role      |testDataFile                                                         |
      | 201        | '#(manager.username)'     | '#(manager.password)'     | 'MANAGER' |'classpath:apis/orders/test-data/responses/createProduct_201.json'   |
      | 403        | '#(user.username)'        | '#(user.password)'        | 'USER'    |'classpath:apis/orders/test-data/responses/response_4xx.json'        |

