Narrative:
In order to focus my query 
As a peel visitor
I want to refine my search by clicking on facets

Scenario: Modify search by sort
Given visitor is on a peelbib results page for 'canada'
When user clicks on <display-sort>
Then <display-sort> is fixed
And hits 284
And results are sorted by <sort>

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
Given visitor is on a peelbib results page for 'canada'
When user selects first range of publication
Then first range match breadcrumbs year
And first range match hits
And results have peelbib years of publication in first range

Scenario: Modify search by last Years of Publication
Given visitor is on a peelbib results page for 'canada'
When user selects last range of publication
Then last range match breadcrumbs year
And last range match hits
And results have peelbib years of publication in last range

Scenario: Modify search by random Years of Publication
Given visitor is on a peelbib results page for 'canada'
When user selects random range of publication
Then random range match breadcrumbs year
And results have peelbib years of publication in random range

Scenario: Modify search by google map
Given visitor is on a peelbib results page for 'canada'
When user selects location on map
Then location match breadcrumbs
And location match hits

Scenario: Modify search by first Language
Given visitor is on a peelbib results page for 'canada'
When user selects peelbib first language
Then first language match breadcrumbs
And first language match hits
And results have first language

Scenario: Modify search by last Language
Given visitor is on a peelbib results page for 'canada'
When user selects peelbib last language
Then last language match breadcrumbs
And last language match hits
And results have last language

Scenario: Modify search by random Language
Given visitor is on a peelbib results page for 'canada'
When user selects peelbib random language
Then random language match breadcrumbs
And random language match hits
And results have random language

Scenario: Modify search by fulltext available
Given visitor is on a peelbib results page for 'canada'
When user selects fulltext available
Then facet match breadcrumbs
And facet match hits
And results have fulltext available

Scenario: Modify search by first Subject
Given visitor is on a peelbib results page for 'canada'
When user selects peelbib first subject
Then first subject match breadcrumbs
And first subject match hits

Scenario: Modify search by last Subject
Given visitor is on a peelbib results page for 'canada'
When user selects peelbib last subject
Then last subject match breadcrumbs
And last subject match hits

Scenario: Modify search by random Subject
Given visitor is on a peelbib results page for 'canada'
When user selects peelbib random subject
Then random subject match breadcrumbs
And random subject match hits