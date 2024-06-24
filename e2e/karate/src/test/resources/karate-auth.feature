@ignore
Feature: Karate Auth / Login

  Background:

  @fetchAuthToken
  Scenario: fetchAuthHeader
    * def credentials = __arg
    Given url urls.retailApiUrl
    Given path '/auth/sign-in'
    And request { email: '#(credentials.username)', password: '#(credentials.password)' }
    When method POST
