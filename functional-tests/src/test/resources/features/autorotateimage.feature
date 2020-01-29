Feature: Image auto-rotate

#  @ignore
  @ok
  Scenario Outline: get the image auto-rotated with valid data (non-random input image)
    Given AppMC is a client of the media-converter module
    When AppMC requests to auto-rotate an image
      | originalImage   |
      | <originalImage> |
    Then the media-converter module returns the image auto-rotated
      | expectedOrientation   |
      | <expectedOrientation> |
    Examples:
      | originalImage        | expectedOrientation |
      | monalisaWithEXIF.jpg | AUTOROTATED         |

#  @ignore
    @ok
  Scenario Outline: get the image auto-rotated with valid data (random input image)
    Given AppMC is a client of the media-converter module
    When AppMC requests to auto-rotate an image
      | originalImage   |
      | <originalImage> |
    Then the media-converter module returns the image auto-rotated
      | expectedOrientation   |
      | <expectedOrientation> |
    Examples:
      | originalImage | expectedOrientation |
      | randomImage   | AUTOROTATED         |


#  @ignore
  @ko
  Scenario Outline: get the image auto-rotated with invalid data
    Given AppMC is a client of the media-converter module
    When AppMC requests to auto-rotate an image
      | originalImage   |
      | <originalImage> |
    Then the request fails with a bad request
    Examples:
      | originalImage |
      | index.html    |


#  @ignore
  @ko
  Scenario: get the image auto-rotated with invalid api Key
    Given AppMC is a client of the media-converter module
    And AppMC has an invalid api key
    When AppMC requests to auto-rotate an image
      | originalImage |
      | fullHD.jpg    |
    Then the request fails with a bad request

#  @ignore
  @ko
  Scenario: get the image auto-rotated with empty data
    Given AppMC is a client of the media-converter module
    When AppMC requests to auto-rotate an image
      | originalImage |
      |               |
    Then the request fails with a bad request

