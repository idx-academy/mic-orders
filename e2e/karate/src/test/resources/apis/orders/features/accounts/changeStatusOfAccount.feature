Feature: Change status of account

  Background:
    * url urls.retailApiUrl
    * def auth = callonce read('classpath:karate-auth.js')

  Scenario: Change status
    Given path '/v1/management/users'
    And headers auth
    And params {role: 'USER', status: 'ACTIVE'}
    When method GET
    Then status 200
    * def userId = response.content[0].id

    Given path '/v1/management/users', userId, 'status'
    And headers auth
    And param status = 'DEACTIVATED'
    When method PATCH
    Then status 204

    Given path '/v1/management/users'
    And headers auth
    And params {role: 'USER', status: 'DEACTIVATED'}
    When method GET
    Then status 200
    And match response..id contains userId

    Given path '/v1/management/users', userId, 'status'
    And headers auth
    And param status = 'ACTIVE'
    When method PATCH
    Then status 204

    Given path '/v1/management/users'
    And headers auth
    And params {role: 'USER', status: 'ACTIVE'}
    When method GET
    Then status 200
    And match response..id contains userId