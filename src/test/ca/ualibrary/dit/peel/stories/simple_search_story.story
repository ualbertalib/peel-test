Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search the peel portal

Scenario: Simple search from front page newspaper index

Given visitor is on the front page
When user enters <query> in the form
When user selects <name> <value> in the form
And user clicks 'search'
Then title is 'Search Results'
And breadcrumbs contain <query>
And hits <hits>

Examples:     
|type|query|hits|name|value|
|en|horse|109529|index|newspapers|
|fr|Vérendrye|2849|index|newspapers|
|phrase|"rocky mountains"|3310|index|newspapers|
|boolean|horse dog|155364|index|newspapers|
|boolean|horse OR dog|155364|index|newspapers|
|boolean|horse AND dog|3881|index|newspapers|
|boolean|horse -dog|105737|index|newspapers|
|boolean|horse NOT dog|105737|index|newspapers|
|boolean|(horse OR dog) AND cat|6086|index|newspapers|
|proximity|"horse dog"~100|2193|index|newspapers|

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
