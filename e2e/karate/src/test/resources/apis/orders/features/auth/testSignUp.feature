
Feature: Sign up feature

  Background:
    * url urls.retailApiUrl
    * def generator = Java.type('com.academy.orders.karate.generator.DataGenerator')
    * def requestData  = call utils.readTestData 'classpath:apis/orders/test-data/requests/signupRequest.json'

  Scenario: Sign up 201
    * set requestData.email = generator.generateEmail()
    And path '/auth/sign-up'
    And request requestData

    When method Post

    Then status 201
    And  match response.token == "#string"

  Scenario: Sign up 409
    * set requestData.email = credentials.username
    And path '/auth/sign-up'
    And request requestData

    When method Post

    Then status 409
    And match response.status == 409
    And match response.title == 'Conflict'
    And match response.detail == 'Account with email ' + credentials.username + ' already exists'

  Scenario Outline: Sign up 400
    * set requestData.email = credentials.username
    * set requestData.email = '<email>'
    * set requestData.firstName = '<firstName>'
    * set requestData.lastName = '<lastName>'
    * set requestData.password = '<password>'

    And path '/auth/sign-up'
    And request requestData

    When method Post

    Then status 400
    And match response.status == 400
    And match response.title == 'Bad Request'

    Examples:
      | email               | firstName | lastName | password  |
      | incorrect.mail.com  | User      | User     | Test_1234 |
      | some@mail.com       | User@     | User     | Test_1234 |
      | some@mail.com       | User      | User@    | Test_1234 |
      | some@mail.com       | User      | User     | password@ |