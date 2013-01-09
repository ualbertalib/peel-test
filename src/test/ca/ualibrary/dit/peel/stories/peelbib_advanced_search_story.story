Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search for monographs in the peel portal


Scenario: Advanced search using most clauses

Given visitor is on the 'find books' page
When user enters McClintock, Walter in the form id authordisplay
And user enters Life, legends, and religion of the Blackfeet Indians in the form id titledisplay
And user enters Indians of North America--Folklore in the form id subjectdisplay
And user enters 1870 TO 1949 in the form name pubyear
And user enters 1910 in the form name actyear
And user selects en in the form name language
And user clicks 'go'
Then title is 'Search Results'
And hits 1
And first result is Peel 3461

Scenario: Simplest advanced search

Given visitor is on the 'find books' page
When user enters <keywords> in the form
And user clicks 'go'
Then title is 'Search Results'
And hits <hits>

Examples:     
|keywords|hits|
|horse|2741|

Scenario: Advanced search with keyword and status

Given visitor is on the 'find books' page
When user enters <keywords> in the form
And user selects fulltext
And user clicks 'submit' at the bottom of form
Then title is 'Search Results'
And hits <hits>
And first result is <peelbib>

Examples:     
|keywords|hits|peelbib|
|horse|2443|Peel 9204|

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

Scenario: Advanced search only one id clause

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

Scenario: Advanced search only one named clause

Given visitor is on the 'find books' page
When user enters <name> <value> in the form
And user clicks 'go'
Then title is 'Search Results'
And hits <hits> 

Examples:     
|name|value|hits|
|bibrecord|Alberta|0|
|peelnum|3459|1|
|pubyear|1945 TO 1950|839|
|actyear|1945 TO 1950|840|

Scenario: Advanced search only one selection clause

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