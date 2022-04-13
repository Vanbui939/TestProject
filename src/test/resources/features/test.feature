Feature: Open Google

  Scenario: Cucumber web test - Open 2 tabs
    When Open Google
    And Input keyword to search
    And Click Search
    Then Show Result

