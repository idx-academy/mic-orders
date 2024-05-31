@ignore
Feature: Karate Auth / Login

  Background:

  @fetchAuthToken
  Scenario: fetchAuthHeader
    * def credentials = __arg
    Given url urls.retailApiUrl
    Given path '/auth/token'
    And request { username: '#(credentials.username)', password: '#(credentials.password)' }
    When method POST
