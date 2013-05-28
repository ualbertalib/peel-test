Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search for newspapers in the peel portal

Scenario: Advanced search using most clauses

Given visitor is on the 'find newspapers advanced search' page
When user enters 1913 in the form name pubyear
And user selects fr in the form name language
And user clicks 'go'
Then title is Search Results
And breadcrumbs contain Publication year: 1913 AND Language: French
And hits 15

Scenario: Simplest advanced search

Given visitor is on the 'find newspapers advanced search' page
When user enters <query> in the form
And user clicks 'go'
Then title is Search Results
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
Then title is Search Results
And breadcrumbs contain <query>
And hits <hits>
And results are sorted by <sort>
And first newspaper result is <newstitle>

Examples:     
|query|sort|hits|newstitle|
|alberta|sort_score|18|The Gateway, November 9, 1934, p.3, Ar00311|
|alberta|sort_date-asc|18|The Calgary Eye-Opener, October 3, 1908, p.1, Ar00103|
|alberta|sort_date-desc|18|La Liberté et le patriote, August 10, 1956, p.8, Ar00811|

Scenario: Advanced search with all size and type
Given visitor is on the 'find newspapers advanced search' page
When user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain <query>
And hits <hits>

Examples:
|query|hits|
||299|

Scenario: Advanced search with none type
Given visitor is on the 'find newspapers advanced search' page
When user checks id newspapers_type_article
And user checks id newspapers_type_ad
And user checks id newspapers_type_picture
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: small OR xlarge OR large OR medium
And hits 323


Scenario: Advanced search with picture type
Given visitor is on the 'find newspapers advanced search' page
When user checks id newspapers_type_article
And user checks id newspapers_type_ad
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: small OR xlarge OR large OR medium & Item type: picture
And hits 13

Scenario: Advanced search with ad type
Given visitor is on the 'find newspapers advanced search' page
When user checks id newspapers_type_article
And user checks id newspapers_type_picture
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: small OR xlarge OR large OR medium & Item type: ad
And hits 93

Scenario: Advanced search with article type
Given visitor is on the 'find newspapers advanced search' page
When user checks id newspapers_type_ad
And user checks id newspapers_type_picture
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: small OR xlarge OR large OR medium & Item type: article
And hits 193

Scenario: Advanced search with none size
Given visitor is on the 'find newspapers advanced search' page
When user checks name newspapers_size_xlarge
And user checks name newspapers_size_large
And user checks name newspapers_size_medium
And user checks name newspapers_size_small
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item type: article OR picture OR ad
And hits 299

Scenario: Advanced search with small size
Given visitor is on the 'find newspapers advanced search' page
When user checks name newspapers_size_xlarge
And user checks name newspapers_size_large
And user checks name newspapers_size_medium
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: small & Item type: article OR picture OR ad
And hits 255

Scenario: Advanced search with medium size
Given visitor is on the 'find newspapers advanced search' page
When user checks name newspapers_size_xlarge
And user checks name newspapers_size_large
And user checks name newspapers_size_small
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: medium & Item type: article OR picture OR ad
And hits 24

Scenario: Advanced search with large size
Given visitor is on the 'find newspapers advanced search' page
When user checks name newspapers_size_xlarge
And user checks name newspapers_size_medium
And user checks name newspapers_size_small
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: large & Item type: article OR picture OR ad
And hits 14

Scenario: Advanced search with xlarge size
Given visitor is on the 'find newspapers advanced search' page
When user checks name newspapers_size_large
And user checks name newspapers_size_medium
And user checks name newspapers_size_small
And user clicks 'submit' at the bottom of form
Then title is Search Results
And breadcrumbs contain Item size: xlarge & Item type: article OR picture OR ad
And hits 6
