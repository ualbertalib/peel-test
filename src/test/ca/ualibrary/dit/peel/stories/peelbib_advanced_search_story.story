Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search for monographs in the peel portal

Scenario: Simplest advanced search

Given visitor is on the 'find books' page
When user enters <keywords> in the form
And user clicks 'go'
Then title is 'Search Results'
And hits <hits>

Examples:     
|keywords|hits|
|horse|2741|

Scenario: Advanced search with keyword and sort

Given visitor is on the 'find books' page
When user enters <keywords> in the form
And user selects <sort>
And user clicks 'submit' at the bottom of form
Then title is 'Search Results'
And hits <hits>
And first result is <peelbib>

Examples:     
|keywords|sort|hits|peelbib|
|horse|sort-score|2741|Peel 9204|
|horse|sort-peelnum|2741|Peel 6|
|horse|sort-pubyear-asc|2741|Peel 10243|
|horse|sort-pubyear-desc|2741|Peel 10452|

