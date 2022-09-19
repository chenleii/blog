import {SelectLang, useModel} from '@umijs/max';
import {Button, Space} from 'antd';
import React from 'react';
import AvatarDropdown from './AvatarDropdown';
import styles from './index.less';
import {history, useIntl} from "@@/exports";
import {PlusOutlined} from '@ant-design/icons';

export type HeaderRightContentProps = {
  smallScreen?: boolean;
};

const HeaderRightContent: React.FC<HeaderRightContentProps> = ({smallScreen}) => {
  const {initialState} = useModel('@@initialState');
  let intl = useIntl();
  if (!initialState || !initialState.settings) {
    return null;
  }

  const {navTheme, layout} = initialState.settings;
  let className = styles.right;

  if ((navTheme === 'realDark' && layout === 'top') || layout === 'mix') {
    className = `${styles.right}  ${styles.dark}`;
  }
  return (
    <Space className={className}>
      {
        !smallScreen
          ? <Button htmlType="submit" type="primary" icon={<PlusOutlined/>}
                    onClick={() => history.push("/article/editor")}>
            {intl.formatMessage({
              id: 'component.globalHeader.HeaderRightContent.createArticle',
              defaultMessage: '写文章',
            })}
          </Button>
          : undefined
      }
      <AvatarDropdown/>
      <SelectLang className={styles.action}/>
    </Space>
  );
};
export default HeaderRightContent;
