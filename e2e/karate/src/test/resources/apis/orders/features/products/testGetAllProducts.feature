@test
Feature: Get list of all products

  Background:
    * url urls.retailApiUrl

  Scenario: Get all products
    Given path '/v1/products'
    Given params {lang: en, page: 0, size: 8, sort: [product.price,desc]}
    When method Get
    Then status 200
