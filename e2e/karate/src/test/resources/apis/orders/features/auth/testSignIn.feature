Feature: SignIn feature

  Background:
    * url urls.retailApiUrl

  Scenario: Sign in 200
    And path '/auth/sign-in'
    And request {"email" : "#(credentials.username)", "password" : "#(credentials.password)"}

    When method Post

    Then status 200
    And response.token == "#string"

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