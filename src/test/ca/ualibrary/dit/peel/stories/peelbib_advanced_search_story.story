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

Scenario: Advanced search only one clause

Given visitor is on the 'find books' page
When user enters <id> <value> in the form
And user clicks 'go'
Then title is 'Search Results'
And hits <hits> 

Examples:     
|id|value|hits|
|authordisplay|Morice, Adrien|37|
|titledisplay|Manitoba and confederation|1176|
|subjectdisplay|Immigrants--Canada|2029|

Scenario: Advanced search only one clause others

Given visitor is on the 'find books' page
When user enters <name> <value> in the form
And user clicks 'go'
Then title is 'Search Results'
And hits <hits> 

!-- TODO |bibrecord|Alberta|0|
Examples:     
|name|value|hits|
|peelnum|3459|1|
|pubyear|1945 TO 1950|839|
|actyear|1945 TO 1950|840|

Scenario: Advanced search only one clause selection

Given visitor is on the 'find books' page
When user selects <name> <value> in the form
And user clicks 'go'
Then title is 'Search Results'
And hits <hits> 

Examples:     
|name|value|hits|
|language|en|8603|
|language|fr|2235|
|language|cre|197|
|language|uk|97|
