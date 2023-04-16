"use strict";(self.webpackChunkblog_ui=self.webpackChunkblog_ui||[]).push([[704],{71986:function(e,t,n){n.r(t);var r=n(861),i=n.n(r),a=n(17061),s=n.n(a),l=n(42122),u=n.n(l),c=n(17156),o=n.n(c),d=n(27424),p=n.n(d),x=n(99913),h=n(65429),f=n(74796),v=n(28058),g=n(38545),m=n(87659),Z=n(4393),j=n(99559),k=n(96074),w=n(52691),y=n(26713),I=n(74627),b=n(50261),M=n(96365),A=n(71577),S=n(50255),L=n(71230),z=n(15746),P=n(4960),V=n(71511),C=n(67294),D=n(54442),K=n(58533),N=n(72241),T=n(60419),B=n(85893);t.default=function(){var e,t,n=(0,C.useState)(!1),r=p()(n,2),a=r[0],l=r[1],c=(0,D.useLocation)(),d=(0,D.useSearchParams)(),R=p()(d,1)[0],_=(0,D.useIntl)(),F=(0,C.useState)({pageIndex:1,pageSize:10,searchKeyword:R.get("searchKeyword")||""}),Q=p()(F,2),E=Q[0],H=Q[1],q=(0,C.useState)({pageIndex:0,pageSize:10,list:[],total:0}),G=p()(q,2),J=G[0],O=G[1],U=c.pathname.includes("/headlines"),W=function(){var e=o()(s()().mark((function e(t){var n,r;return s()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,N.Z.articleApi.like({articleId:t.id});case 2:n=e.sent,(J.list||[]).filter((function(e){return(null==e?void 0:e.id)===t.id})).map((function(e){return e.isLiked=!t.isLiked,e.likedNumber=n,e})),r=u()(u()({},J),{},{list:J.list}),O(r);case 6:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),X=function(){var e=o()(s()().mark((function e(t,n){var r,i;return s()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,N.Z.articleApi.report({articleId:t.id,remark:n.remark});case 2:r=e.sent,(J.list||[]).filter((function(e){return(null==e?void 0:e.id)===t.id})).map((function(e){return e.isReported=!0,e.reportedNumber=r,e})),i=u()(u()({},J),{},{list:J.list}),O(i);case 6:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}(),Y=function(){var e=o()(s()().mark((function e(){var t,n;return s()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(l(!0),e.prev=1,t={},!U){e.next=9;break}return e.next=6,N.Z.articleApi.headlinesPageQuery(E);case 6:t=e.sent,e.next=12;break;case 9:return e.next=11,N.Z.articleApi.pageQuery(E);case 11:t=e.sent;case 12:return E.pageIndex=(E.pageIndex||0)+1,E.lastValues=t.lastValues,H(E),n={pageIndex:t.pageIndex,pageSize:t.pageSize,list:[].concat(i()(J.list||[]),i()(t.list||[])),total:t.total,lastValues:t.lastValues},e.next=18,O(n);case 18:return e.prev=18,l(!1),e.finish(18);case 21:case"end":return e.stop()}}),e,null,[[1,,18,21]])})));return function(){return e.apply(this,arguments)}}(),$=function(){var e=o()(s()().mark((function e(){return s()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return l(!0),E.pageIndex=1,E.lastValues=[],E.searchKeyword=R.get("searchKeyword")||"",H(E),J.pageIndex=1,J.list=[],J.lastValues=[],e.next=10,O(J);case 10:return e.next=12,Y();case 12:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}();return(0,C.useEffect)((function(){$()}),[R]),(0,B.jsxs)(m._z,{header:{title:""},children:[(0,B.jsx)(Z.Z,{bordered:!1,style:{marginBottom:24},children:(0,B.jsx)(K.Z,{dataLength:(null==J||null===(e=J.list)||void 0===e?void 0:e.length)||0,next:Y,hasMore:((null==J||null===(t=J.list)||void 0===t?void 0:t.length)||0)<((null==J?void 0:J.total)||0),pullDownToRefresh:!0,refreshFunction:$,loader:(0,B.jsx)(j.Z,{avatar:!0,paragraph:{rows:1},active:!0}),endMessage:(0,B.jsx)(k.Z,{plain:!0,children:_.formatMessage({id:"pages.ArticleList.endMessage"})}),children:(0,B.jsx)(w.ZP,{itemLayout:"vertical",size:"default",loading:a,dataSource:null==J?void 0:J.list,renderItem:function(e){var t,n,r,i;return(0,B.jsxs)(w.ZP.Item,{actions:[(0,B.jsxs)(y.Z,{onClick:function(){return W(e)},children:[null!=e&&e.isLiked?(0,B.jsx)(x.Z,{}):(0,B.jsx)(h.Z,{}),null==e?void 0:e.likedNumber]}),(0,B.jsxs)(y.Z,{children:[(0,B.jsx)(I.Z,{title:_.formatMessage({id:"pages.ArticleDetails.report.title",defaultMessage:"确认举报吗？"}),content:(0,B.jsxs)(b.Z,{onFinish:function(t){return X(e,t)},layout:"vertical",children:[(0,B.jsx)(b.Z.Item,{label:_.formatMessage({id:"pages.ArticleDetails.report.remark",defaultMessage:"举报原因"}),name:"remark",children:(0,B.jsx)(M.Z,{placeholder:_.formatMessage({id:"pages.ArticleDetails.report.remark",defaultMessage:"举报原因"})})}),(0,B.jsx)(b.Z.Item,{children:(0,B.jsx)(A.ZP,{type:"primary",htmlType:"submit",children:_.formatMessage({id:"pages.ArticleDetails.report.submitButton",defaultMessage:"举报"})})})]}),trigger:"click",children:null!=e&&e.isReported?(0,B.jsx)(f.Z,{}):(0,B.jsx)(v.Z,{})}),null==e?void 0:e.reportedNumber]}),(0,B.jsxs)(y.Z,{children:[(0,B.jsx)(g.Z,{}),null!=e&&e.comments?null==e?void 0:e.comments.length:0]})],extra:(null!=e&&e.cover,null),children:[(0,B.jsx)(w.ZP.Item.Meta,{avatar:(0,B.jsx)(V.Z,{src:null==e||null===(t=e.account)||void 0===t?void 0:t.avatar,size:"large"}),title:(0,B.jsx)("a",{href:null==e||null===(n=e.account)||void 0===n?void 0:n.avatar,children:null==e||null===(r=e.account)||void 0===r?void 0:r.name}),description:null==e||null===(i=e.account)||void 0===i?void 0:i.introduction}),(0,B.jsxs)(S.Z,{onClick:function(){return D.history.push("/article/".concat(e.id))},children:[(0,B.jsx)(S.Z.Title,{ellipsis:{rows:1,tooltip:null==e?void 0:e.title},level:1,children:(0,B.jsx)(T.Z,{content:null==e?void 0:e.title})}),(0,B.jsxs)(L.Z,{children:[(0,B.jsx)(z.Z,{lg:16,xs:24,children:(0,B.jsx)(S.Z.Paragraph,{ellipsis:{rows:5},style:{maxHeight:"200px"},children:(0,B.jsx)(T.Z,{content:(null==e?void 0:e.customContent)||""})})}),(0,B.jsx)(z.Z,{lg:8,xs:0,children:(0,B.jsx)("img",{width:"100%",style:{paddingLeft:16},alt:"cover",src:null==e?void 0:e.cover})})]})]})]},null==e?void 0:e.id)}})})}),(0,B.jsx)(P.Z.BackTop,{})]})}},15746:function(e,t,n){var r=n(21584);t.Z=r.Z},71230:function(e,t,n){var r=n(92820);t.Z=r.Z}}]);