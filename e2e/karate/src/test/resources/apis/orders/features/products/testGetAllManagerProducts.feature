Feature: Get all products for manager
  Background:
    * url urls.retailApiUrl
    * eval karate.set('credentials.username', manager.username)
    * eval karate.set('credentials.password', manager.password)
    * def authHeader = callonce read('classpath:karate-auth.js')
    * def pageResponseSchema = callonce utils.readTestData 'classpath:apis/orders/test-data/page_200.json'

    Scenario: getAllManagerProducts
      Given headers authHeader
      And path '/v1/management/products'
      And params {size: 10, page: 0, priceMore: 500, priceLess: 2000}
      When method GET
      Then status 200
      And match response ==
      """
      {
        "totalElements": "#number",
        "totalPages": "#number",
        "first": true,
        "last": false,
        "number": 0,
        "numberOfElements": 10,
        "size": 10,
        "empty": false,
        "content": "##array"
      }
      """