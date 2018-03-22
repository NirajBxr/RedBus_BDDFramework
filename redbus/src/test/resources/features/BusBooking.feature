Feature: verify that user is able to book bus ticket.

@SmokeTest
Scenario: verify that user is able to search bus between two cities for future data in redbus
Given user is on redbus home Page
When user enters source city as "Bangalore"
And user enters destination city as "Chennai"
And select the onward journey data as "25-Apr-2018"
And clicks on "Search Buses" button
Then "Search Bus Tickets" Page should appear