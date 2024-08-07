Feature: Get all users

  Background:
    * url urls.retailApiUrl
    * def authHeader = callonce read('classpath:karate-auth.js')

  Scenario Outline: Get all users
    Given headers authHeader
    And path '/v1/management/users'
    And params {page: 0, size: 8}
    When method get
    Then status 200
    And match response ==
      """
      {
        "totalElements": "#number",
        "totalPages": "#number",
        "first": true,
        "last": false,
        "number": 0,
        "numberOfElements": 8,
        "size": 8,
        "empty": false,
        "content": "##array"
      }
      """

    Examples:
      | status | role      | username                  | password                  | testDataFile                                                 |
      | 200    | 'ADMIN'   | '#(credentials.username)' | '#(credentials.password)' |'classpath:apis/orders/test-data/responses/page_200.json'     |
      | 403    | 'MANAGER' | '#(manager.username)'     | '#(manager.password)'     |'classpath:apis/orders/test-data/responses/response_4xx.json' |
      | 403    | 'USER'    | '#(user.username)'        | '#(user.password)'        |'classpath:apis/orders/test-data/responses/response_4xx.json' |

  Scenario: Get users filtered by status
    Given headers authHeader
    And path '/v1/management/users'
    And params {page: 0, size: 8}
    And params {accountFilter: {status: 'ACTIVE'}}
    When method get
    Then status 200
    And match response.content[0].status == 'ACTIVE'
    And match response.totalElements == '#number'

  Scenario: Get users filtered by role
    Given headers authHeader
    And path '/v1/management/users'
    And params {page: 0, size: 8}
    And params {accountFilter: {role: 'USER'}}
    When method get
    Then status 200
    And match response.content[0].role == 'ROLE_USER'
    And match response.totalElements == '#number'

  Scenario: Get users filtered by multiple criteria
    Given headers authHeader
    And path '/v1/management/users'
    And params {page: 0, size: 8}
    And params {accountFilter: {status: 'ACTIVE', role: 'USER'}}
    When method get
    Then status 200
    And match response.content[0].status == 'ACTIVE'
    And match response.content[0].role == 'ROLE_USER'
    And match response.totalElements == '#number'

  Scenario: Get users sorted by email
    Given headers authHeader
    And path '/v1/management/users'
    And params {page: 0, size: 8, sort: ['email,desc']}
    When method get
    Then status 200
