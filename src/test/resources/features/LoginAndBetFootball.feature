Feature: Login and add a football selection to Betslip

  Scenario: Sign in and sign out
    When I open website
    And I sign in
    And I get balance
    And I navigate to football
    And I add selection to betslip
    And I open betslip
    And I check selection in betslip
    And I place bet
    And I goto openbets
    And I check bet in openbets
    Then I sign out
