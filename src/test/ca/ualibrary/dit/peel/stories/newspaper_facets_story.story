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
And first range match breadcrumbs
And results have publication in first range

Scenario: Modify search by last Years of Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects last range of publication
Then last range match hits
And last range match breadcrumbs
And results have publication in last range

Scenario: Modify search by random Years of Publication
Given visitor is on a newspapers results page for 'alberta'
When user selects random range of publication
Then random range match hits
And random range match breadcrumbs
And results have publication in random range