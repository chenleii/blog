"use strict";(self.webpackChunkblog_ui=self.webpackChunkblog_ui||[]).push([[602],{16587:function(e,t,n){n.r(t);var a=n(17061),r=n.n(a),s=n(42122),i=n.n(s),u=n(17156),c=n.n(u),l=n(27424),o=n.n(l),d=n(87659),p=n(4393),f=n(80442),g=n(26713),h=n(98293),m=n(69677),v=n(71577),x=n(93410),Z=n(67294),j=n(79912),b=n(72241),w=n(85893);t.default=function(){var e=(0,j.useModel)("@@initialState"),t=e.initialState,n=e.setInitialState,a=(0,Z.useState)(!1),s=o()(a,2),u=s[0],l=s[1],I=(0,Z.useState)({}),M=o()(I,2),y=M[0],C=M[1],k=(0,j.useIntl)(),A=function(){var e=c()(r()().mark((function e(a){var s;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return l(!0),e.prev=1,e.next=4,b.Z.accountApi.update1(i()({},a));case 4:return e.next=6,b.Z.accountApi.getLoggedInAccount();case 6:s=e.sent,n(i()(i()({},t),{},{loggedInAccount:s}));case 8:return e.prev=8,l(!1),e.finish(8);case 11:case"end":return e.stop()}}),e,null,[[1,,8,11]])})));return function(t){return e.apply(this,arguments)}}(),S=function(){var e=c()(r()().mark((function e(){var t;return r()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,b.Z.accountApi.getLoggedInAccount();case 3:t=e.sent,C(t);case 5:return e.prev=5,l(!1),e.finish(5);case 8:case"end":return e.stop()}}),e,null,[[0,,5,8]])})));return function(){return e.apply(this,arguments)}}();return(0,Z.useEffect)((function(){l(!0),S()}),[]),(0,w.jsxs)(d._z,{header:{title:""},children:[(0,w.jsx)(p.Z,{bordered:!1,style:{marginBottom:24},loading:u,children:(0,w.jsxs)(f.Z,{name:"basic",layout:"vertical",labelCol:{span:24},wrapperCol:{span:24},initialValues:i()({},y),onFinish:function(e){return A(e)},onValuesChange:function(e,t){var n=i()(i()({},y),t);C(n)},autoComplete:"off",children:[(0,w.jsx)(f.Z.Item,{label:k.formatMessage({id:"pages.account.update.avatar",defaultMessage:"头像"}),name:"avatar",rules:[{required:!0}],children:(0,w.jsxs)(g.Z,{direction:"vertical",align:"center",style:{width:"100%"},children:[(0,w.jsx)(h.C,{src:null==y?void 0:y.avatar,alt:"",size:128}),(0,w.jsx)(m.Z,{value:null==y?void 0:y.avatar})]})}),(0,w.jsx)(f.Z.Item,{label:k.formatMessage({id:"pages.account.update.name",defaultMessage:"昵称"}),name:"name",rules:[{required:!0}],children:(0,w.jsx)(m.Z,{})}),(0,w.jsx)(f.Z.Item,{label:k.formatMessage({id:"pages.account.update.introduction",defaultMessage:"个人简介"}),name:"introduction",rules:[{required:!0}],children:(0,w.jsx)(m.Z,{})}),(0,w.jsx)(f.Z.Item,{wrapperCol:{offset:0,span:24},children:(0,w.jsx)(v.ZP,{type:"primary",htmlType:"submit",loading:u,children:k.formatMessage({id:"pages.ArticleEditor.submitButton",defaultMessage:"保存"})})})]})}),(0,w.jsx)(x.Z.BackTop,{})]})}}}]);