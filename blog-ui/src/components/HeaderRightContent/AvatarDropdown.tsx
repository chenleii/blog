import {LoginOutlined, LogoutOutlined, SettingOutlined, UserOutlined} from '@ant-design/icons';
import {history, useModel} from '@umijs/max';
import {Avatar, Dropdown, Menu, Spin} from 'antd';
import type {ItemType} from 'antd/lib/menu/hooks/useItems';
import type {MenuInfo} from 'rc-menu/lib/interface';
import React, {useCallback} from 'react';
import styles from './index.less';
import api from "@/services/api";
import {useIntl} from "@@/exports";
import {stringify} from "querystring";

export type HeaderRightContentProps = {};

const AvatarDropdown: React.FC<HeaderRightContentProps> = ({}) => {
  const {initialState, setInitialState} = useModel('@@initialState');
  let intl = useIntl();

  const onMenuClick = useCallback(
    async (event: MenuInfo) => {
      const {key} = event;
      if (key === 'logout') {
        setInitialState((s) => ({...s, isLoggedIn: false, loggedInAccount: undefined}));
        // 退出登录
        await api.accountApi.logout();
        return;
      }
      if (key === 'login') {
        history.push({
          pathname: `/account/login`,
          search: stringify({
            redirect: window.location.href,
          }),
        });
        return;
      }
      history.push(`/account/${key}`);
    },
    [initialState],
  );

  const loading = (
    <span className={`${styles.action} ${styles.account}`}>
      <Spin
        size="small"
        style={{
          marginLeft: 8,
          marginRight: 8,
        }}
      />
    </span>
  );

  if (!initialState) {
    return loading;
  }

  const {
    isLoggedIn,
    loggedInAccount,
  } = initialState;

  const loggedInMenuItems: ItemType[] = [
    {
      key: 'center',
      icon: <UserOutlined/>,
      label: intl.formatMessage({
        id: 'component.globalHeader.HeaderRightContent.AvatarDropdown.accountCenter',
        defaultMessage: '账户中心',
      }),
    },
    {
      key: 'settings',
      icon: <SettingOutlined/>,
      label: intl.formatMessage({
        id: 'component.globalHeader.HeaderRightContent.AvatarDropdown.accountSettings',
        defaultMessage: '账户设置',
      }),
    },
    {
      type: 'divider' as const,
    },
    {
      key: 'logout',
      icon: <LogoutOutlined/>,
      label: intl.formatMessage({
        id: 'component.globalHeader.HeaderRightContent.AvatarDropdown.logout',
        defaultMessage: '退出登录',
      }),
    },
  ];
  const notLoggedInMenuItems: ItemType[] = [
    {
      key: 'login',
      icon: <LoginOutlined/>,
      label: intl.formatMessage({
        id: 'component.globalHeader.HeaderRightContent.AvatarDropdown.login',
        defaultMessage: '去登录',
      }),
    },
  ];

  const menuHeaderDropdown = (
    <Menu className={styles.menu} selectedKeys={[]} onClick={onMenuClick}
          items={isLoggedIn ? loggedInMenuItems : notLoggedInMenuItems}/>
  );

  return (
    <Dropdown overlay={menuHeaderDropdown} trigger={['hover','click']}>
      <span className={`${styles.action} ${styles.account}`}>
        <Avatar size="small" className={styles.avatar} src={loggedInAccount?.avatar} alt="avatar"/>
        <span className={`${styles.name} anticon`}>
          {loggedInAccount?.name || intl.formatMessage({
            id: 'component.globalHeader.HeaderRightContent.AvatarDropdown.notLoggedIn',
            defaultMessage: '未登录',
          })}
        </span>
      </span>
    </Dropdown>
  );
};

export default AvatarDropdown;
