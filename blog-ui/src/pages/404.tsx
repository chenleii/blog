import {history, useIntl} from '@umijs/max';
import {Button, Result} from 'antd';
import React from 'react';

const NoFoundPage: React.FC = () => {
  let intl = useIntl();

  return (
    <Result
      status="404"
      title="404"
      subTitle={
        intl.formatMessage({id: 'pages.404.subTitle'})
      }
      extra={
        <Button type="primary" onClick={() => history.push('/')}>
          {intl.formatMessage({id: 'pages.404.extra'})}
        </Button>
      }
    />
  )
};

export default NoFoundPage;
