Feature: Flock login function

  Scenario Outline: Login username not exist
    Given I click Sign In button
    When I input username as "<username>"
    And I input password as "<password>"
    And I click Login button
    Then Show error message "<message>"
    Examples:
      |username   |password   |message  |
      |c@dc.com   |123asjdh   |Incorrect email or password|

  Scenario Outline: Login valid username and password
    Given I click Sign In button
    When I input username as "<username>"
    And I input password as "<password>"
    And I click Login button
    Then Show error message "<message>"
    Examples:
      |username   |password   |message  |
      |skeysi218@gmail.com   |12345abc@A   |Incorrect email or password|
