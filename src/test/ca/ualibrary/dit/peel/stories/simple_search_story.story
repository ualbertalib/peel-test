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
|language|query|hits|
|en|horse|2741|
|fr|Vérendrye|86|
|cr|Kwayask ê-kî-pê-kiskinowâpahtihicik|13|
