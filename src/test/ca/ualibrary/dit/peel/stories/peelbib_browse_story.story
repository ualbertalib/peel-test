Narrative:
In order to seredipitously find a resource 
As a peel visitor
I want to browse the peel portal

Scenario: Search browse by category
Given visitor is on the browse page
When user enters A in the <categoryType> searchbox
Then entries match A

Examples:
|categoryType|
|authorprefix|
|titleprefix|
|subjectprefix|

Scenario: Browse
Given visitor is on the browse page
When user clicks <position> entry of <category>
Then entry match hits
And entry match breadcrumbs

Examples:
|position|category|
|first|author|
|last|author|
|random|author|
|first|title|
|last|title|
|random|title|
|first|subject|
|last|subject|
|random|subject|

Scenario: Browse Category search
Given visitor is on the <category> page
When user enters A in the prefix searchbox
Then entries match A

Examples:
|category|
|authordisplay|
|titledisplay|
|subjectdisplay_en|

Scenario: Browse Category
Given visitor is on the <category> page
When user clicks <position> entry
Then entry match hits
And entry match breadcrumbs

Examples:
|position|category|
|first|authordisplay|
|last|authordisplay|
|random|authordisplay|
|first|titledisplay|
|last|titledisplay|
|random|titledisplay|
|first|subjectdisplay_en|
|last|subjectdisplay_en|
|random|subjectdisplay_en|