import Footer from '@/components/Footer';
import api from '@/services/api';
import {LockOutlined, MobileOutlined,} from '@ant-design/icons';
import {LoginForm, ProFormCaptcha, ProFormCheckbox, ProFormText,} from '@ant-design/pro-components';
import {SelectLang, useIntl, useModel} from '@umijs/max';
import {message} from 'antd';
import React from 'react';
import styles from './index.less';

const Login: React.FC = () => {
  const {initialState, setInitialState} = useModel('@@initialState');
  const intl = useIntl();

  const fetchLoggedInAccount = async () => {
    const loggedInAccount = await initialState?.fetchLoggedInAccount?.();
    if (loggedInAccount) {
      await setInitialState((s) => ({
        ...s,
        isLoggedIn:true,
        loggedInAccount: loggedInAccount,
      }));
    }
  };

  const handleSubmit = async (values: any) => {
    try {
      // 登录
      await api.accountApi.login({
        phoneNo: values.phoneNo,
        verificationCode: values.verificationCode,
      });
      const defaultLoginSuccessMessage = intl.formatMessage({
        id: 'pages.login.success',
        defaultMessage: '登录成功！',
      });
      message.success(defaultLoginSuccessMessage);
      await fetchLoggedInAccount();

      const urlParams = new URL(window.location.href).searchParams;
      window.location.href = urlParams.get('redirect') || '/';

    } catch (error) {
      const defaultLoginFailureMessage = intl.formatMessage({
        id: 'pages.login.failure',
        defaultMessage: '登录失败，请重试！',
      });
      message.error(defaultLoginFailureMessage);

    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang/>}
      </div>
      <div className={styles.content}>
        <LoginForm
          logo={<img alt="logo" src="/logo.svg"/>}
          title={intl.formatMessage({id: 'app.copyright.produced'})}
          subTitle={' '}
          initialValues={{}}
          actions={[]}
          onFinish={async (values) => {
            await handleSubmit(values);
          }}
        >

          <>
            <ProFormText
              fieldProps={{
                size: 'large',
                prefix: <MobileOutlined className={styles.prefixIcon}/>,
              }}
              name="phoneNo"
              placeholder={intl.formatMessage({
                id: 'pages.login.phoneNumber.placeholder',
                defaultMessage: '手机号',
              })}
              rules={[
                {
                  required: true,
                  message: (
                    <div>
                      {intl.formatMessage({
                        id: "pages.login.phoneNumber.required",
                        defaultMessage: '请输入手机号！',
                      })}
                    </div>
                  ),
                },
                {
                  pattern: /^1\d{10}$/,
                  message: (
                    <div>
                      {intl.formatMessage({
                        id: "pages.login.phoneNumber.invalid",
                        defaultMessage: '手机号格式错误！',
                      })}
                    </div>
                  ),
                },
              ]}
            />
            <ProFormCaptcha
              fieldProps={{
                size: 'large',
                prefix: <LockOutlined className={styles.prefixIcon}/>,
              }}
              captchaProps={{
                size: 'large',
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.captcha.placeholder',
                defaultMessage: '请输入验证码',
              })}
              captchaTextRender={(timing, count) => {
                if (timing) {
                  return `${count} ${intl.formatMessage({
                    id: 'pages.getCaptchaSecondText',
                    defaultMessage: '获取验证码',
                  })}`;
                }
                return intl.formatMessage({
                  id: 'pages.login.phoneLogin.getVerificationCode',
                  defaultMessage: '获取验证码',
                });
              }}
              name="verificationCode"
              rules={[
                {
                  required: true,
                  message: (
                    <div>
                      {intl.formatMessage({
                        id: "pages.login.captcha.required",
                        defaultMessage: '请输入验证码！',
                      })}
                    </div>
                  ),
                },
              ]}
              onGetCaptcha={async (phone) => {
                let captchaGetMessage = intl.formatMessage({
                  id: "pages.login.captcha.getMessage",
                  defaultMessage: '获取验证码成功！验证码为：123123',
                },{
                  code:'123123'
                });

                message.success(captchaGetMessage);
              }}
            />
          </>
          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              {intl.formatMessage({
                id: "pages.login.rememberMe",
                defaultMessage: '自动登录',
              })}
            </ProFormCheckbox>
          </div>
        </LoginForm>
      </div>
      <Footer/>
    </div>
  );
};

export default Login;
