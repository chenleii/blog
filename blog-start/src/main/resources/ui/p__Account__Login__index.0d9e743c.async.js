"use strict";(self.webpackChunkblog_ui=self.webpackChunkblog_ui||[]).push([[423],{58707:function(e,a,n){n.r(a),n.d(a,{default:function(){return z}});var t=n(15009),r=n.n(t),s=n(97857),i=n.n(s),c=n(99289),o=n.n(c),l=n(66116),u=n(9832),d=n(1413),g=n(67294),f={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M744 62H280c-35.3 0-64 28.7-64 64v768c0 35.3 28.7 64 64 64h464c35.3 0 64-28.7 64-64V126c0-35.3-28.7-64-64-64zm-8 824H288V134h448v752zM472 784a40 40 0 1080 0 40 40 0 10-80 0z"}}]},name:"mobile",theme:"outlined"},p=n(84089),h=function(e,a){return g.createElement(p.Z,(0,d.Z)((0,d.Z)({},e),{},{ref:a,icon:f}))};h.displayName="MobileOutlined";var m=g.forwardRef(h),v={icon:{tag:"svg",attrs:{viewBox:"64 64 896 896",focusable:"false"},children:[{tag:"path",attrs:{d:"M832 464h-68V240c0-70.7-57.3-128-128-128H388c-70.7 0-128 57.3-128 128v224h-68c-17.7 0-32 14.3-32 32v384c0 17.7 14.3 32 32 32h640c17.7 0 32-14.3 32-32V496c0-17.7-14.3-32-32-32zM332 240c0-30.9 25.1-56 56-56h248c30.9 0 56 25.1 56 56v224H332V240zm460 600H232V536h560v304zM484 701v53c0 4.4 3.6 8 8 8h40c4.4 0 8-3.6 8-8v-53a48.01 48.01 0 10-56 0z"}}]},name:"lock",theme:"outlined"},x=function(e,a){return g.createElement(p.Z,(0,d.Z)((0,d.Z)({},e),{},{ref:a,icon:v}))};x.displayName="LockOutlined";var M=g.forwardRef(x),w=n(2039),j=n(57328),b=n(12461),_=n(59652),Z={container:"container___KqtO3",lang:"lang___ZsPRm",content:"content___vQddq",icon:"icon___zU1l_"},k=n(85893),z=function(){var e=(0,j.useModel)("@@initialState"),a=e.initialState,n=e.setInitialState,t=(0,j.useIntl)(),s=function(){var e=o()(r()().mark((function e(){var t,s;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,null==a||null===(t=a.fetchLoggedInAccount)||void 0===t?void 0:t.call(a);case 2:if(!(s=e.sent)){e.next=6;break}return e.next=6,n((function(e){return i()(i()({},e),{},{isLoggedIn:!0,loggedInAccount:s})}));case 6:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),c=function(){var e=o()(r()().mark((function e(a){var n,i,c;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,u.Z.accountApi.login({phoneNo:a.phoneNo,verificationCode:a.verificationCode});case 3:return n=t.formatMessage({id:"pages.login.success",defaultMessage:"登录成功！"}),b.ZP.success(n),e.next=7,s();case 7:i=new URL(window.location.href).searchParams,window.location.href=i.get("redirect")||"/",e.next=15;break;case 11:e.prev=11,e.t0=e.catch(0),c=t.formatMessage({id:"pages.login.failure",defaultMessage:"登录失败，请重试！"}),b.ZP.error(c);case 15:case"end":return e.stop()}}),e,null,[[0,11]])})));return function(a){return e.apply(this,arguments)}}();return(0,k.jsx)(w._zJ,{header:{title:""},children:(0,k.jsx)(_.Z,{bordered:!1,children:(0,k.jsxs)("div",{className:Z.container,children:[(0,k.jsx)("div",{className:Z.content,children:(0,k.jsxs)(w.U0H,{logo:(0,k.jsx)("img",{alt:"logo",src:"/logo.svg"}),title:t.formatMessage({id:"app.copyright.produced"}),subTitle:" ",initialValues:{},actions:[],onFinish:function(){var e=o()(r()().mark((function e(a){return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,c(a);case 2:case"end":return e.stop()}}),e)})));return function(a){return e.apply(this,arguments)}}(),children:[(0,k.jsxs)(k.Fragment,{children:[(0,k.jsx)(w.VaQ,{fieldProps:{size:"large",prefix:(0,k.jsx)(m,{className:Z.prefixIcon})},name:"phoneNo",placeholder:t.formatMessage({id:"pages.login.phoneNumber.placeholder",defaultMessage:"手机号"}),rules:[{required:!0,message:(0,k.jsx)("div",{children:t.formatMessage({id:"pages.login.phoneNumber.required",defaultMessage:"请输入手机号！"})})},{pattern:/^1\d{10}$/,message:(0,k.jsx)("div",{children:t.formatMessage({id:"pages.login.phoneNumber.invalid",defaultMessage:"手机号格式错误！"})})}]}),(0,k.jsx)(w.BXt,{fieldProps:{size:"large",prefix:(0,k.jsx)(M,{className:Z.prefixIcon})},captchaProps:{size:"large"},placeholder:t.formatMessage({id:"pages.login.captcha.placeholder",defaultMessage:"请输入验证码"}),captchaTextRender:function(e,a){return e?"".concat(a," ").concat(t.formatMessage({id:"pages.getCaptchaSecondText",defaultMessage:"获取验证码"})):t.formatMessage({id:"pages.login.phoneLogin.getVerificationCode",defaultMessage:"获取验证码"})},name:"verificationCode",rules:[{required:!0,message:(0,k.jsx)("div",{children:t.formatMessage({id:"pages.login.captcha.required",defaultMessage:"请输入验证码！"})})}],onGetCaptcha:function(){var e=o()(r()().mark((function e(a){var n;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:n=t.formatMessage({id:"pages.login.captcha.getMessage",defaultMessage:"获取验证码成功！验证码为：123123"},{code:"123123"}),b.ZP.success(n);case 2:case"end":return e.stop()}}),e)})));return function(a){return e.apply(this,arguments)}}()})]}),(0,k.jsx)("div",{style:{marginBottom:24},children:(0,k.jsx)(w.V2E,{noStyle:!0,name:"autoLogin",children:t.formatMessage({id:"pages.login.rememberMe",defaultMessage:"自动登录"})})})]})}),(0,k.jsx)(l.Z,{})]})})})}}}]);