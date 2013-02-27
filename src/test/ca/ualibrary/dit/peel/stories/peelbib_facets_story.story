Narrative:
In order to focus my query 
As a peel visitor
I want to refine my search by clicking on facets

Scenario: Modify search by sort
Given visitor is on a peelbib results page for 'alberta'
When user clicks on <display-sort>
Then hits 272
And results are sorted by <sort>
And <display-sort> is fixed

Examples:
|display-sort|sort|
|Peel Number|sort-peelnum|
|Date (asc)|sort-pubyear-asc|
|Date (desc)|sort-pubyear-desc|
|Author (asc)|sort-author-asc|
|Author (desc)|sort-author-desc|
|Title (asc)|sort-title-asc|
|Title (desc)|sort-title-desc|

Scenario: Modify search by first Years of Publication
Given visitor is on a peelbib results page for 'alberta'
When user selects first range of publication
Then first range match hits
And first range match breadcrumbs
And results have peelbib years of publication in first range

Scenario: Modify search by last Years of Publication
Given visitor is on a peelbib results page for 'alberta'
When user selects last range of publication
Then last range match hits
And last range match breadcrumbs
And results have peelbib years of publication in last range

Scenario: Modify search by random Years of Publication
Given visitor is on a peelbib results page for 'alberta'
When user selects random range of publication
Then random range match hits
And random range match breadcrumbs
And results have peelbib years of publication in random range

Scenario: Modify search by first facet
Scenario: Modify search by last facet
Scenario: Modify search by random facet
