Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search for monographs in the peel portal

Scenario: Advanced search using most clauses

Given visitor is on the 'find books' page
When user enters Hunter, James in the form id author
And user enters Book of Common Prayer in the form id title
And user enters Indians of North America--Languages in the form id subject
And user enters 1850 TO 1860 in the form name pubyear
And user enters 1856 in the form name actyear
And user selects cre in the form name language
And user clicks 'go'
Then title is 'Search Results'
And breadcrumbs contain Title: Book of Common Prayer AND Publication year: [1850 TO 1860] AND Language: Cree AND Activity year: 1856 AND Subject: Indians of North America--Languages AND Author: Hunter, James
And hits 1
And first result is Peel 329

Scenario: Simplest advanced search

Given visitor is on the 'find books' page
When user enters <keywords> in the form
And user clicks 'go'
Then title is 'Search Results'
And breadcrumbs contain <keywords>
And hits <hits>

Examples:     
|keywords|hits|
|horse|262|

Scenario: Advanced search with keyword and status

Given visitor is on the 'find books' page
When user enters <keywords> in the form
And user selects fulltext
And user clicks 'submit' at the bottom of form
Then title is 'Search Results'
And breadcrumbs contain <keywords>
And hits <hits>
And first result is <peelbib>

Examples:     
|keywords|hits|peelbib|
|languages|51|Peel 9021.15.1|

Scenario: Advanced search with keyword and sort

Given visitor is on the 'find books' page
When user enters <keywords> in the form
And user selects <sort>
And user clicks 'submit' at the bottom of form
Then title is 'Search Results'
And breadcrumbs contain <keywords>
And hits <hits>
And first result is <peelbib>

Examples:     
|keywords|sort|hits|peelbib|
|languages|sort-score|51|Peel 2490|
|languages|sort-peelnum|51|Peel 315|
|languages|sort-pubyear-asc|51|Peel 9021.Index.1953-1977|
|languages|sort-pubyear-desc|51|Peel 10571.71|
|languages|sort-author-asc|51|Peel 9021.24.2|
|languages|sort-author-desc|51|Peel 10571.35|
|languages|sort-title-asc|51|Peel 9021.24.2|
|languages|sort-title-desc|51|Peel 10571.35|

Scenario: Advanced search only one id clause

Given visitor is on the 'find books' page
When user enters <id> <value> in the form
And user clicks 'go'
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits> 

Examples:     
|id|value|query|hits|
|author|Hunter, James|Author: Hunter, James|21|
|title|Book of Common Prayer|Title: Book of Common Prayer|4|
|subject|Indians of North America--Languages|Subject: Indians of North America--Languages|4|

Scenario: Advanced search only one named clause

Given visitor is on the 'find books' page
When user enters <name> <value> in the form
And user clicks 'go'
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits> 

Examples:     
|name|value|query|hits|
|peelnum|329|Peel: 329|1|
|pubyear|1850 TO 1860|Publication year: [1850 TO 1860]|2|
|actyear|1900 TO 1920|Activity year: [1900 TO 1920]|4|

Scenario: Advanced search only one selection clause

Given visitor is on the 'find books' page
When user selects <name> <value> in the form
And user clicks 'go'
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits> 

Examples:     
|name|value|query|hits|
|language|en|Language: English|283|
|language|fr|Language: French|2|
|language|cre|Language: Cree|3|
|language|uk|Language: Ukrainian|2|