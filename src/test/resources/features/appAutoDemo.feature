Feature: Init driver

  Scenario: Test open Flock app
    Given Start driver
    When Click App
    And Click Signin
    Then Assert result