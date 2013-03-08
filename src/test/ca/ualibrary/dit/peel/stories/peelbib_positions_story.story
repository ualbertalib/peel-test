Narrative:
In order to find the most relevant content to my query 
As a peel visitor
I want to visit pages with my query term

Scenario: Hits found on page(s) in result set
Given visitor is on the front page
When user enters alberta in the header form
And user clicks 'search'
Then results contain hits on page

Scenario: Hits found on page(s) in item
Given visitor is on a peelbib results page for alberta
When user clicks through to item
Then something is highlighted