@ignore
Feature: get id of the product

  Background:
    * url urls.retailApiUrl

  Scenario: get id of the product
    Given path '/v1/products'
    When method GET
    Then status 200
    * def productId = response.content[Math.floor(Math.random() * response.numberOfElements)].id
