Feature: Get products on sale

  Background:
    * url urls.retailApiUrl

  Scenario:
    Given path '/v1/products/sales'
    When method GET
    Then status 200
    And match response ==
    """
      {
        totalElements: '#number',
        totalPages: '#number',
        first: '#boolean',
        last: '#boolean',
        number: '#number',
        numberOfElements: '#number',
        size: '#number',
        empty: '#boolean',
        content: '##array'
      }
    """
    And match each response.content ==
    """
      {
        id: '#string? _.length > 0',
        name: '#string? _.length > 0',
        description: '#string',
        status: '#regex (AVAILABLE|END_SOON|ENDED)',
        tags: '##array',
        image: '#string? _.length > 0',
        price: '#number? _ >= 0',
        discount: '#number? _ >= 0 && _ <= 100',
        priceWithDiscount: '#number'
      }
    """
