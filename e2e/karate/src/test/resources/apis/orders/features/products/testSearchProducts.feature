@test
Feature: Search products by search query

  Background:
    * url urls.retailApiUrl

  Scenario: Get product search results
    Given def pageSchema = call utils.readTestData 'classpath:apis/orders/test-data/page_200.json'
    Given def resultSchema = call utils.readTestData 'classpath:apis/orders/test-data/productSearchResults_200.json'
    Given path '/v1/products/search'
    Given params {lang: 'en', page: 0, size: 8, sort: ['name,desc'], searchQuery: 'phone'}
    When method Get
    Then status 200
    And match response == pageSchema
    And match each response.content == resultSchema

  Scenario: Get product search results with empty query
    Given def schema = call utils.readTestData 'classpath:apis/orders/test-data/response_4xx.json'
    Given path '/v1/products/search'
    Given params {lang: 'en', page: 0, size: 8, sort: ['name,desc'], searchQuery: ''}
    When method Get
    Then status 400
    And match response == schema
