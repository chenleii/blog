(self.webpackChunkblog_ui=self.webpackChunkblog_ui||[]).push([[98],{54298:function(e,t,r){"use strict";r.r(t),r.d(t,{default:function(){return C}});var n=r(17061),a=r.n(n),s=r(42122),i=r.n(s),l=r(17156),u=r.n(l),c=r(27424),o=r.n(c),d=r(5463),p=r(59652),f=r(95239),h=r(30124),g=r(34041),m=r(32808),x=r(71577),b=r(89790),v=r(67294),j=r(72241),Z=r(54442),k=r(27170),M=(r(59135),r(85893)),y=function(e){var t=e.content,r=e.onChange;return(0,M.jsx)(M.Fragment,{children:(0,M.jsx)(k.Z,{style:{height:"100%"},value:t||"",onChange:r})})},C=function(){var e=(0,v.useState)(!1),t=o()(e,2),r=t[0],n=t[1],s=(0,Z.useParams)(),l=(0,v.useState)({}),c=o()(l,2),k=c[0],C=c[1],w=(0,Z.useIntl)(),A=function(){var e=u()(a()().mark((function e(t){var r,s;return a()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(n(!0),e.prev=1,null!=k&&k.id){e.next=9;break}return e.next=5,j.Z.articleApi.save(t);case 5:r=e.sent,Z.history.push("/article/".concat(r)),e.next=13;break;case 9:return e.next=11,j.Z.articleApi.update(i()({articleId:k.id},t));case 11:s=e.sent,Z.history.push("/article/".concat(s));case 13:return e.prev=13,n(!1),e.finish(13);case 16:case"end":return e.stop()}}),e,null,[[1,,13,16]])})));return function(t){return e.apply(this,arguments)}}(),I=function(){var e=u()(a()().mark((function e(){var t,r;return a()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(e.prev=0,t=s.articleId){e.next=6;break}C({}),e.next=10;break;case 6:return e.next=8,j.Z.articleApi.query({articleId:t});case 8:r=e.sent,C(r);case 10:return e.prev=10,n(!1),e.finish(10);case 13:case"end":return e.stop()}}),e,null,[[0,,10,13]])})));return function(){return e.apply(this,arguments)}}();return(0,v.useEffect)((function(){n(!0),I()}),[s]),(0,M.jsxs)(d._z,{header:{title:""},children:[(0,M.jsx)(p.Z,{bordered:!1,style:{marginBottom:24},loading:r,children:(0,M.jsxs)(f.Z,{name:"basic",layout:"vertical",labelCol:{span:24},wrapperCol:{span:24},initialValues:i()(i()({},k),{},{isPublish:!0}),onFinish:function(e){return A(e)},onValuesChange:function(e,t){var r=i()(i()({},k),t);C(r)},autoComplete:"off",children:[(0,M.jsx)(f.Z.Item,{label:w.formatMessage({id:"pages.ArticleEditor.title",defaultMessage:"标题"}),name:"title",rules:[{required:!0}],children:(0,M.jsx)(h.Z,{})}),(0,M.jsx)(f.Z.Item,{label:w.formatMessage({id:"pages.ArticleEditor.tags",defaultMessage:"标签"}),name:"tags",tooltip:w.formatMessage({id:"pages.ArticleEditor.tags.tooltip",defaultMessage:"自定义输入任意标签"}),rules:[{required:!0}],children:(0,M.jsx)(g.Z,{mode:"tags",tokenSeparators:[","]})}),(0,M.jsx)(f.Z.Item,{label:w.formatMessage({id:"pages.ArticleEditor.content",defaultMessage:"内容"}),name:"content",rules:[{required:!0}],children:(0,M.jsx)(y,{content:k.content||""})}),(0,M.jsx)(f.Z.Item,{name:"isPublish",hidden:void 0!==k.id,valuePropName:"checked",wrapperCol:{offset:0,span:24},children:(0,M.jsx)(m.Z,{children:w.formatMessage({id:"pages.ArticleEditor.isPublish",defaultMessage:"是否发布"})})}),(0,M.jsx)(f.Z.Item,{wrapperCol:{offset:0,span:24},children:(0,M.jsx)(x.Z,{type:"primary",htmlType:"submit",loading:r,children:w.formatMessage({id:"pages.ArticleEditor.submitButton",defaultMessage:"保存"})})})]})}),(0,M.jsx)(b.Z,{})]})}},62562:function(){}}]);