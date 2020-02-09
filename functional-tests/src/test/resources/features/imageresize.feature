Feature: Image resize

#  @ignore
  @ok
  Scenario Outline: get the image resized with valid data (non-random input image)
    Given AppMC is a client of the media-converter module
    When AppMC requests to resize an image
      | originalImage   | width   | height   |
      | <originalImage> | <width> | <height> |
    Then the media-converter module returns the image resized
      | expectedWidth   | expectedHeight   |
      | <expectedWidth> | <expectedHeight> |
    Examples:
      | originalImage | width | height | expectedWidth | expectedHeight |
      | fullHD.jpg    | 100   | 100    | 100           | 100            |
      | xing.BMP      | 150   | 75     | 150           | 75             |
#      |    fullHD.png   |   150   |    75      |      150      |       75       |
#      |   fullHD.poster |   75    |    50      |      75       |       50       |

#  @ignore
  @ok
  Scenario Outline: get the image resized with valid data (random input image)
    Given AppMC is a client of the media-converter module
    When AppMC requests to resize an image
      | originalImage   | width   | height   |
      | <originalImage> | <width> | <height> |
    Then the media-converter module returns the image resized
      | expectedWidth   | expectedHeight   |
      | <expectedWidth> | <expectedHeight> |
    Examples:
      | originalImage | width | height | expectedWidth | expectedHeight |
      | randomImage   | 100   | 100    | 100           | 100            |



#  @ignore
  @ko
  Scenario Outline: get the image resized with invalid data
    Given AppMC is a client of the media-converter module
    When AppMC requests to resize an image
      | originalImage   | width   | height   |
      | <originalImage> | <width> | <height> |
    Then the request fails with a bad request
    Examples:
      | originalImage | width | height |
      | fullHD.jpg    | -1    | 100    |
      | fullHD.jpg    | 100   | -1     |
      | fullHD.jpg    | 7680  | 100    |
      | fullHD.jpg    | 100   | 4320   |
      | fullHD.jpg    | -1    | 4320   |
      | index.html    | 50    | 50     |

#  @ignore
  @ko
  Scenario: get the image resized with invalid api Key
    Given AppMC is a client of the media-converter module
    And AppMC has an invalid api key
    When AppMC requests to resize an image
      | originalImage | width | height |
      | fullHD.jpg    | 100   | 100    |
    Then the request fails with a bad request

#  @ignore
  @ko
  Scenario: get the image resized with empty data
    Given AppMC is a client of the media-converter module
    When AppMC requests to resize an image
      | originalImage | width | height |
      |               |       |        |
    Then the request fails with a bad request