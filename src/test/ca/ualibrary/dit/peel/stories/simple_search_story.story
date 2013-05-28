Narrative:
In order to advance my knowledge of the prairie provinces 
As a peel visitor
I want to search for peel resources

Scenario: Simple search from front page newspaper index

Given visitor is on the front page
When user enters <query> in the header form
And user selects <name> <value> in the form
And user clicks 'search'
Then title is Search Results
And breadcrumbs contain <query>
And hits <hits>

Examples:     
|type|query|hits|name|value|
|en|horse|2|index|newspapers|
|fr|français|18|index|newspapers|
|phrase|"junior football"|1|index|newspapers|
|boolean|horse hand|9|index|newspapers|
|boolean|horse OR hand|9|index|newspapers|
|boolean|+horse +hand|1|index|newspapers|
|boolean|horse AND hand|1|index|newspapers|
|boolean|horse -hand|1|index|newspapers|
|boolean|horse NOT hand|1|index|newspapers|
|boolean|(horse OR dog) AND cat|1|index|newspapers|
|fuzzy|horse~|68|index|newspapers|
|proximity|"alberta saskatchewan"~100|2|index|newspapers|

Scenario: Simple search from front page default index

Given visitor is on the front page
When user enters <query> in the header form
And user clicks 'search'
Then title is Search Results
And breadcrumbs contain <query>
And hits <hits>

Examples:     
|type|query|hits|
|en|horse|262|
|fr|français|2|
|cr|Kwayask ê-kî-pê-kiskinowâpahtihicik|1|
|uk|Канада|0|
|phrase|"rocky mountains"|124|
|boolean|horse dog|275|
|boolean|horse OR dog|275|
|boolean|+horse +dog|160|
|boolean|horse AND dog|160|
|boolean|horse -dog|102|
|boolean|horse NOT dog|102|
|boolean|(horse OR dog) AND cat|61|
|fuzzy|horse~|282|
|proximity|"horse dog"~100|35|
|truncation|horse*|273|
|geneology|"j. k. fildes"|1|
|geneology|"j k fildes"|1|
|geneology|j k fildes|284|
