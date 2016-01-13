Sample story

Narrative:
In order to 普通預金口座から当座預金口座への振替えの挙動を確認
As a 受け入れテストチーム
I want to use Behaviour-Driven Development

Scenario: 正常な口座振替シナリオ
Given 普通預金口座に 1000 円が預金されている。
And 当座預金口座に 500 円が預金されている。
When 普通預金口座から当座預金口座に 300 円を振り替えたならば
Then 普通預金口座の残高は 700 円となる。
And 当座預金口座の残高は 800 円となる。


Scenario: 振替金額不足シナリオ
Then 普通預金口座の残高は 0 円となる。
Given 普通預金口座に 1000 円が預金されている。
And 当座預金口座に 500 円が預金されている。
When 普通預金口座から当座預金口座に 2000 円を振り替えたならば
Then 振替処理はエラーとなる。
And 普通預金口座の残高は 1000 円となる。
And 当座預金口座の残高は 500 円となる。

Scenario: 正常な口座振替2
Given 普通預金口座に <savingAmount> 円が預金されている。
And 当座預金口座に <cashingAmount> 円が預金されている。
When 普通預金口座から当座預金口座に <transferAmount> 円を振り替えたならば
Then 普通預金口座の残高は <savingBalance> 円となる。
And 当座預金口座の残高は <cashingBalance> 円となる。
When 普通預金口座から当座預金口座に <transferAmount2> 円を振り替えたならば
Then 普通預金口座の残高は <savingBalance2> 円となる。
And 当座預金口座の残高は <cashingBalance2> 円となる。
Examples:
|savingAmount|cashingAmount|transferAmount|savingBalance|cashingBalance|transferAmount2|savingBalance2|cashingBalance2|
|       1000 |      500 |          300 |         700 |       800 | 100 | 600 | 900 |
|       1000 |        0 |          300 |         700 |       300 | 100 | 600 | 400 |
|       1000 |     -200 |          300 |         700 |       100 | 100 | 600 | 200 |
