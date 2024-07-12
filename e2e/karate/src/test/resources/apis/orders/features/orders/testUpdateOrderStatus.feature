Feature: Update Order Status

  Background:
    * def managerAuthHeader = call read('classpath:karate-auth.js') { auth: 'managerCredentials' }
    * def defaultAuthHeader = call read('classpath:karate-auth.js')
    * url urls.retailApiUrl
    * def orderId = '6fa459ea-ee8a-3ca4-894e-db77e160355e'
    * def nonExistingOrderId = '84b7e490-0dcf-44c3-beb6-7496dc6ef3b0'

  Scenario: Update Order Status, Successfully | OK
    Given headers managerAuthHeader
    And path '/v1/orders', orderId, 'status'
    And param orderStatus = 'COMPLETED'
    When method PATCH
    Then status 200

  Scenario: Update Order Status with Insufficient Access Rights | FORBIDDEN
    Given headers defaultAuthHeader
    And path '/v1/orders', orderId, 'status'
    And param orderStatus = 'COMPLETED'
    When method PATCH
    Then status 403

  Scenario: Update Order Status with Incorrect Status | BAD REQUEST
    Given headers managerAuthHeader
    And path '/v1/orders', orderId, 'status'
    And param orderStatus = 'COMPLETEDDDD'
    When method PATCH
    Then status 400

  Scenario: Update Order Status with Non-existing Order ID | NOT FOUND
    Given headers managerAuthHeader
    And path '/v1/orders', nonExistingOrderId, 'status'
    And param orderStatus = 'COMPLETED'
    When method PATCH
    Then status 404