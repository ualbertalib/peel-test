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