
Feature: Sign up feature

  Background:
    * url urls.retailApiUrl
    * def generator = Java.type('com.academy.orders.karate.generator.DataGenerator')
    * def requestData  = call utils.readTestData 'classpath:apis/orders/test-data/signup-request.json'

  Scenario: Sign up 201
    * set requestData.email = generator.generateEmail()
    * print requestData
    And path '/auth/sign-up'
    And request requestData

    When method Post

    Then status 201
    And  match response.token == "#string"

  Scenario: Sign up 409
    * set requestData.email = credentials.username
    * print requestData
    And path '/auth/sign-up'
    And request requestData

    When method Post

    Then status 409
    And match response.status == 409
    And match response.title == 'Conflict'
    And match response.detail == 'Account with email ' + credentials.username + ' already exists'

  Scenario Outline: Sign up 400
    * set requestData.email = credentials.username
    * print requestData
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


  Scenario Outline: Sign in 4xx
    And path '/auth/sign-in'
    And request {"email" : "<email>", "password" : "<password>"}

    When method Post

    Then status <status>
    And match response.status == <status>
    And match response.title == '<title>'

    Examples:
    | status  | email                        | password   | title        |
    | 401     | some@mail.com                | Test_1234  | Unauthorized |
    | 400     | some@mail.com                | test_1234  | Bad Request  |
    | 400     | incorrect.mail.com           | Test_1234  | Bad Request  |
    | 400     | some@mail.com                | Test@      | Bad Request  |