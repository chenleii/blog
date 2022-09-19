Feature: 文章功能（文章生命周期）

  @isolated
    @transaction
  Scenario Outline: 创建文章流程
    Given 登录账户 <phoneNo>
    Given 更新登录账户信息 name:<name>, avatar:<avatar>
    When 保存文章 isPublish:<isPublish>, title:<title>, tags:<tags>, content:<content>
    Then 登录账户下是否有文章 <isSaved>
    Then 文章搜索 <isSearched>, <searchKeyword>, <phoneNo>
    When 更新文章，isPublish:<isUpdatePublish>, title:<title>, tags:<tags>, content:<content>
    Then 登录账户下是否有文章 <isUpdated>
    Then 文章搜索 <isUpdateSearched>, <searchKeyword>, <phoneNo>

    Examples:
      | phoneNo       | name    | avatar  | title        | tags          | content             | searchKeyword | isPublish | isSaved | isSearched | isUpdatePublish | isUpdated | isUpdateSearched |
      | '15677779991' | 'chen1' | 'chen1' | 'testTitle1' | 'tag11,tag12' | 'titleContent1测试'   | '测试'          | false     | true    | false      | false           | true      | false            |
      | '15677779992' | 'chen2' | 'chen2' | 'testTitle2' | 'tag21,tag22' | 'titleContent2测试'   | '测试'          | false     | true    | false      | true            | true      | true             |
      | '15677779993' | 'chen3' | 'chen3' | 'testTitle3' | 'tag31,tag32' | 'titleContent3测试'   | '测试'          | true      | true    | true       | true            | true      | true             |
      | '15677779995' | 'chen5' | 'chen5' | 'testTitle5' | 'tag51,tag52' | 'titleContent5测试'   | '测试'          | true      | true    | true       | true            | true      | true             |
      | '15677779996' | 'chen6' | 'chen6' | 'testTitle5' | 'tag61,tag62' | 'titleContent6脏话测试' | '测试'          | false     | true    | false      | true            | true      | false            |
      | '15677779997' | 'chen7' | 'chen7' | 'testTitle5' | 'tag71,tag72' | 'titleContent7脏话测试' | '测试'          | true      | false   | false      | false           | false     | false            |

  @transaction
  Scenario: 查看文章流程