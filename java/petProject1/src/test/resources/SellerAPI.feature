Feature: test seller API

  @getsellerverifyemailnotempty @regression
  Scenario: get a single seller and verify seller email is not empty
  Given user hits get single seller api with "/api/myaccount/sellers/"
    Then  verify seller email is not empty

    @getallsellers @regression
    Scenario: get all sellers and verify seller id is not 0
      Given user hit get all seller api with "/api/myaccount/sellers"
      Then verify seller ids are not equal to 0


      @updateseller @regression
      Scenario: get single seller, update the same seller, verify seller was updated
        Given user hits get single seller api with "/api/myaccount/sellers/"
        Then  verify seller email is not empty
        Then user hits put api with "/api/myaccount/sellers/"
        Then verify user email was updated
        And verify user first name was updated


  @archiveseller @regression
  Scenario: get single seller, archive the same seller, verify seller was updated
    Given user hits get single seller api with "/api/myaccount/sellers/"
    Then user hits post api with "/api/myaccount/sellers/archive/unarchive"
    Then user hits get single seller api with "/api/myaccount/sellers/"
    And user hit get all seller api with "/api/myaccount/sellers"

    @createseller @regression
    Scenario: create seller, delete a seller, verify seller was deleted
      Given user hits get single seller api with "/api/myaccount/sellers/"
      Then verify seller id was created "/api/myaccount/sellers"
      Then verify seller name is not empty
      And verify seller email is not empty
      Then delete the seller with "/api/myaccount/sellers/"
      Then user hits get all sellers with "/api/myaccount/sellers/"
      Then verify deleted seller is not in the list
