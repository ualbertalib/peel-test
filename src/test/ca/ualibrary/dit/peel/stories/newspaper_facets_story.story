Narrative:
In order to focus my query 
As a peel visitor
I want to refine my search by clicking on facets

Scenario: Modify search by sort
Given visitor is on a newspapers results page for 'alberta'
When user clicks on <display-sort>
Then hits 18
And results are sorted by <sort>
And <display-sort> is fixed

Examples:
|display-sort|sort|
|Date (asc)|sort_date-asc|
|Date (desc)|sort_date-desc|

Scenario: Modify search by first Years of Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects first range of publication
Then first range match hits
And first range match breadcrumbs year
And results have newspaper years of publication in first range

Scenario: Modify search by last Years of Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects last range of publication
Then last range match hits
And last range match breadcrumbs year
And results have newspaper years of publication in last range

Scenario: Modify search by random Years of Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects random range of publication
Then random range match hits
And random range match breadcrumbs year
And results have newspaper years of publication in random range

Scenario: Modify search by first Language
Given visitor is on a newspapers results page for 'alberta'
When user selects newspaper first language
Then first language match hits
And first language match breadcrumbs
And results have first language

Scenario: Modify search by last Language
Given visitor is on a newspapers results page for 'alberta'
When user selects newspaper last language
Then last language match hits
And last language match breadcrumbs
And results have last language

Scenario: Modify search by first Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects publication first 
Then first publication match hits
And results have first publication

Scenario: Modify search by last Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects publication last 
Then last publication match hits
And results have last publication

Scenario: Modify search by random Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects publication random 
Then random publication match hits
And results have random publication