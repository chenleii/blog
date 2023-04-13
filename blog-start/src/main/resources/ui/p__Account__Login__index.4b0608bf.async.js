"use strict";(self.webpackChunkblog_ui=self.webpackChunkblog_ui||[]).push([[423],{37166:function(e,n,a){a.r(n),a.d(n,{default:function(){return b}});var t=a(17061),r=a.n(t),s=a(42122),i=a.n(s),o=a(17156),c=a.n(o),l=a(66116),u=a(72241),d=a(24454),g=a(94149),p=a(5463),f=a(29599),h=a(5966),m=a(16434),x=a(63434),v=a(54442),M=a(12461),j=a(59652),_=(a(67294),{container:"container___KqtO3",lang:"lang___ZsPRm",content:"content___vQddq",icon:"icon___zU1l_"}),w=a(85893),b=function(){var e=(0,v.useModel)("@@initialState"),n=e.initialState,a=e.setInitialState,t=(0,v.useIntl)(),s=function(){var e=c()(r()().mark((function e(){var t,s;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,null==n||null===(t=n.fetchLoggedInAccount)||void 0===t?void 0:t.call(n);case 2:if(!(s=e.sent)){e.next=6;break}return e.next=6,a((function(e){return i()(i()({},e),{},{isLoggedIn:!0,loggedInAccount:s})}));case 6:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}(),o=function(){var e=c()(r()().mark((function e(n){var a,i,o;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,u.Z.accountApi.login({phoneNo:n.phoneNo,verificationCode:n.verificationCode});case 3:return a=t.formatMessage({id:"pages.login.success",defaultMessage:"登录成功！"}),M.ZP.success(a),e.next=7,s();case 7:i=new URL(window.location.href).searchParams,window.location.href=i.get("redirect")||"/",e.next=15;break;case 11:e.prev=11,e.t0=e.catch(0),o=t.formatMessage({id:"pages.login.failure",defaultMessage:"登录失败，请重试！"}),M.ZP.error(o);case 15:case"end":return e.stop()}}),e,null,[[0,11]])})));return function(n){return e.apply(this,arguments)}}();return(0,w.jsx)(p._z,{header:{title:""},children:(0,w.jsx)(j.Z,{bordered:!1,children:(0,w.jsxs)("div",{className:_.container,children:[(0,w.jsx)("div",{className:_.content,children:(0,w.jsxs)(f.U,{logo:(0,w.jsx)("img",{alt:"logo",src:"/logo.svg"}),title:t.formatMessage({id:"app.copyright.produced"}),subTitle:" ",initialValues:{},actions:[],onFinish:function(){var e=c()(r()().mark((function e(n){return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,o(n);case 2:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}(),children:[(0,w.jsxs)(w.Fragment,{children:[(0,w.jsx)(h.Z,{fieldProps:{size:"large",prefix:(0,w.jsx)(d.Z,{className:_.prefixIcon})},name:"phoneNo",placeholder:t.formatMessage({id:"pages.login.phoneNumber.placeholder",defaultMessage:"手机号"}),rules:[{required:!0,message:(0,w.jsx)("div",{children:t.formatMessage({id:"pages.login.phoneNumber.required",defaultMessage:"请输入手机号！"})})},{pattern:/^1\d{10}$/,message:(0,w.jsx)("div",{children:t.formatMessage({id:"pages.login.phoneNumber.invalid",defaultMessage:"手机号格式错误！"})})}]}),(0,w.jsx)(m.Z,{fieldProps:{size:"large",prefix:(0,w.jsx)(g.Z,{className:_.prefixIcon})},captchaProps:{size:"large"},placeholder:t.formatMessage({id:"pages.login.captcha.placeholder",defaultMessage:"请输入验证码"}),captchaTextRender:function(e,n){return e?"".concat(n," ").concat(t.formatMessage({id:"pages.getCaptchaSecondText",defaultMessage:"获取验证码"})):t.formatMessage({id:"pages.login.phoneLogin.getVerificationCode",defaultMessage:"获取验证码"})},name:"verificationCode",rules:[{required:!0,message:(0,w.jsx)("div",{children:t.formatMessage({id:"pages.login.captcha.required",defaultMessage:"请输入验证码！"})})}],onGetCaptcha:function(){var e=c()(r()().mark((function e(n){var a;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:a=t.formatMessage({id:"pages.login.captcha.getMessage",defaultMessage:"获取验证码成功！验证码为：123123"},{code:"123123"}),M.ZP.success(a);case 2:case"end":return e.stop()}}),e)})));return function(n){return e.apply(this,arguments)}}()})]}),(0,w.jsx)("div",{style:{marginBottom:24},children:(0,w.jsx)(x.Z,{noStyle:!0,name:"autoLogin",children:t.formatMessage({id:"pages.login.rememberMe",defaultMessage:"自动登录"})})})]})}),(0,w.jsx)(l.Z,{})]})})})}}}]);