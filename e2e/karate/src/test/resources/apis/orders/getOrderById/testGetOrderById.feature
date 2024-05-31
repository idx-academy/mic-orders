@op.getOrderById
Feature: Get order by id

  Background:
    * url urls.retailApiUrl

  @JIRA_ISSUE_1
  Scenario Outline: getOrderById <status>
    Given def testDataFile  = call utils.readTestData <testDataFile>
    And def authHeader = call read('classpath:karate-auth.js') testDataFile.auth
    And path '/v1/orders/8efbee82-8a0c-407a-a4c0-16bbad40a23e'
    And headers authHeader

    When method Get

    Then match responseStatus == <status>
    And  match response == testDataFile.expectedResponse

    Examples:
      | status | testDataFile                       |
      | 200    | 'test-data/getOrderById_200.json'  |
