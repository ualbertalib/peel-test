Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search the peel portal

Scenario: Simple Search with single term

Given visitor is on the front page
When user enters <query> in the form
And user clicks 'search'
Then title is 'Search Results'
And hits <hits>

!-- |uk|Канада|0|
Examples:     
|type|query|hits|
|en|horse|2741|
|fr|Vérendrye|86|
|cr|Kwayask ê-kî-pê-kiskinowâpahtihicik|13|

Scenario: Simple Search with phrase

Given visitor is on the front page
When user enters <query> in the form
And user clicks 'search'
Then title is 'Search Results'
And hits <hits>

Examples:     
|type|query|hits|
|phrase|"rocky mountains"|1409|

Scenario: Simple Search with boolean terms

Given visitor is on the front page
When user enters <query> in the form
And user clicks 'search'
Then title is 'Search Results'
And hits <hits>

!-- |boolean|+horse +dog|1375|
!-- |boolean|(horse OR dog) AND cat|1147|
Examples:     
|type|query|hits|
|boolean|horse dog|3105|
|boolean|horse OR dog|3105|
|boolean|horse AND dog|1375|
|boolean|horse -dog|1377|
|boolean|horse NOT dog|1377|

Scenario: Simple Search with truncation

Given visitor is on the front page
When user enters <query> in the form
And user clicks 'search'
Then title is 'Search Results'
And hits <hits>

Examples:     
|type|query|hits|
|truncation|horse*|3366|

Scenario: Simple Search with fuzzy

Given visitor is on the front page
When user enters <query> in the form
And user clicks 'search'
Then title is 'Search Results'
And hits <hits>

Examples:     
|type|query|hits|
|truncation|horse~|6736|
