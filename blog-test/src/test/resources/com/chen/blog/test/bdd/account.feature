Feature: 账户功能（账户生命周期）

  @isolated
  @transaction
  Scenario: 账户登录流程
    When 发送手机 '15677779999' 验证码
    When 手机号 '15677779999' 验证码登录
    Then 当前登录账户的手机号为 '15677779999'
    Then 查询账户手机号为 '15677779999' 有1条记录
    When 当前登录账户 '15677779999' 更新信息，name: 'chen1'， avatar：'chen', introduction:'简介'
    Then 当前登录账户 '15677779999' 名称为 'chen1'