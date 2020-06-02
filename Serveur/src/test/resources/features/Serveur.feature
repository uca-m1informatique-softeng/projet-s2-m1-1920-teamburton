Feature: Winner can be recorded and retrieved
  Scenario: client makes call to POST /statistiques/save
    When the client calls /statistiques/save
    Then the client receives status code of 200
    And the server saves the winner's name BotTest

  Scenario: client makes call to GET /statistiques
    Given The list of winners has 2 winners
    When the client calls /statistiques
    Then the client receives status code of 200
    And the client receives the winners list containing 2 winners
    And the client receives the name of the two winners recorded by the server

  Scenario: client makes call to GET /statistiques/0
    Given the fitst winner recorded by the server is FirstWinnerBot
    When the client calls /statistiques/0
    Then the client receives status code of 200
    And the client receives the name of the first winner recorded by the server FirstWinnerBot