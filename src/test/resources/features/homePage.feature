@TestComponent:main_page
@TestSuite:main_page
@Smoke
@Integration
Feature: Open home page of Alza shop and verify common content

  Background:
    Given I open Alza shop page

  @TC:MPC001
  Scenario: Verify category list is displayed on Alza shop page
    Then I see product categories:
      | Deal WeeksSPECIAL OFFERS          |
      | Toys, for Kids and Babies         |
      | Computers and Laptops             |
      | Phones, Smartwatches, Tablets     |
      | Gaming and Entertainment          |
      | Major Appliances                  |
      | TV, Photo, Audio & Video          |
      | Household and Personal Appliances |
      | Household Supplies                |
      | Beauty                            |
      | Drugstore                         |
      | Hobby and Garden                  |
      | Pet Supplies                      |
      | Sport and Outdoors                |
      | Car & Moto                        |
      | Office Supplies and Stationery    |
      | Books                             |
      | Food and Alcohol                  |
      | Health                            |
      | Our brands                        |
    And I see that each category has leading image
    And I see that each category has valid link


  @TC:MPW002
  Scenario: Search field shows rich autocomplete on user's input
    When I type term "xbox" to search field
    Then I see autocomplete sections:
      | Items    |
      | Category |
      | Article  |
    And  I see that all items in section "Category" contains "xbox"
    And  I see that all items in section "Suggestions" contains "xbox"
    And  I see that all items in section "Items" contains "xbox"
    And  I see that section has no more than items count:
      | sectionName | maxCount |
      | Suggestions | 3        |
      | Items       | 3        |
      | Category    | 5        |
      | Article     | 5        |


  @TC:MPW003
  Scenario: Search field shows rich autocomplete on user's input
    When I type term "xboxa" to search field
    Then I see that wisperer contains only "Show all results"
