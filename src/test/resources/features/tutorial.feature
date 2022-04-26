Feature: Open Google

  Scenario Outline: Cucumber web test with dynamic keyword
    When Open Google
    And Input "<keyword>" to search
    And Click Search
    And Press Enter
    Then Show Result

  Examples:
    |keyword  |
    | canh1   |
    |aaaaasss |

  Scenario Outline: Cucumber second Scenario
    When Open Google
    And Input "<keyword>" to search
    And Click Search
    And Press Enter
    Then Show Result

    Examples:
      |keyword  |
      | Canh d   |