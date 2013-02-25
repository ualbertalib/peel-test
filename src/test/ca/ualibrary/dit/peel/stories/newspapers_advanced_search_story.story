Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search for newspapers in the peel portal

Scenario: Advanced search using most clauses

Given visitor is on the 'find newspapers advanced search' page
When user enters 1913 in the form name pubyear
And user selects fr in the form name language
And user clicks 'go'
Then title is 'Search Results'
And breadcrumbs contain Publication year: 1913 AND Language: French AND Item size: large OR medium OR xlarge OR small AND Item type: ad OR picture OR article
And hits 15

Scenario: Simplest advanced search

Given visitor is on the 'find newspapers advanced search' page
When user enters <query> in the form
And user clicks 'go'
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits>

Examples:     
|query|hits|
|horse|2|

Scenario: Advanced search with keyword and sort

Given visitor is on the 'find newspapers advanced search' page
When user enters <query> in the form
And user selects <sort>
And user clicks 'submit' at the bottom of form
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits>
And results are sorted by <sort>
And first newspaper result is <title>

Examples:     
|query|sort|hits|title|
|alberta|sort_score|18|The Gateway, November 9, 1934, p.3, Ar00311|
|alberta|sort_date-asc|18|The Calgary Eye-Opener, October 3, 1908, p.3, Ad00305_2|
|alberta|sort_date-desc|18|La Liberté et le patriote, August 10, 1956, p.6, Ar00610|