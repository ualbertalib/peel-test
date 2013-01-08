Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search the peel portal

Scenario: Simple search from front page default index

Given visitor is on the front page
When user enters <query> in the form
And user clicks 'search'
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits>

Examples:     
|type|query|hits|
|en|horse|2741|
|fr|Vérendrye|86|
|cr|Kwayask ê-kî-pê-kiskinowâpahtihicik|13|
|phrase|"rocky mountains"|1409|
|boolean|horse dog|3105|
|boolean|horse OR dog|3105|
|boolean|horse AND dog|1375|
|boolean|horse -dog|1377|
|boolean|horse NOT dog|1377|
|boolean|(horse OR dog) AND cat|1147|
|fuzzy|horse~|6736|
|proximity|"horse dog"~100|339|
|truncation|horse*|3366|

Scenario: Simple search from front page default index

Given visitor is on the front page
When user enters <query> in the form
When user selects <name> <value> in the form
And user clicks 'search'
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits>

Examples:     
|type|query|hits|index|
|en|horse|2741|newspapers|
|fr|Vérendrye|86|newspapers|
|cr|Kwayask ê-kî-pê-kiskinowâpahtihicik|13|newspapers|
|phrase|"rocky mountains"|1409|newspapers|
|boolean|horse dog|3105|newspapers|
|boolean|horse OR dog|3105|newspapers|
|boolean|horse AND dog|1375|newspapers|
|boolean|horse -dog|1377|newspapers|
|boolean|horse NOT dog|1377|newspapers|
|boolean|(horse OR dog) AND cat|1147|newspapers|
|fuzzy|horse~|6736|newspapers|
|proximity|"horse dog"~100|339|newspapers|
|truncation|horse*|3366|newspapers|