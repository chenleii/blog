"use strict";(self.webpackChunkblog_ui=self.webpackChunkblog_ui||[]).push([[931],{9229:function(e,t,n){var l=n(97857),i=n.n(l),a=(n(67294),n(95925)),r=n(34768),s=n(85893);t.Z=function(e){var t=e.content,n=e.isHtml,l=void 0===n||n;return(0,s.jsx)(s.Fragment,{children:(0,s.jsx)(a.D,{rehypePlugins:l?[r.Z]:[],components:{img:function(e){return(0,s.jsx)("img",i()(i()({},e),{},{style:{maxWidth:"100%"}}))}},children:t||""})})}},40905:function(e,t,n){n.r(t);var l=n(15009),i=n.n(l),a=n(19632),r=n.n(a),s=n(99289),u=n.n(s),o=n(5574),c=n.n(o),d=n(49693),x=n(59652),v=n(26713),p=n(84485),g=n(69677),h=n(95507),f=n(27049),j=n(71577),m=n(44057),Z=n(71511),w=n(67294),y=n(57328),k=n(9832),S=n(9229),I=n(68795),M=n(99913),b=n(65429),z=n(10844),P=n(28058),C=n(38545),V=n(85893);t.default=function(){var e=((0,y.useModel)("@@initialState").initialState||{}).loggedInAccount,t=(0,w.useState)(!1),n=c()(t,2),l=n[0],a=n[1],s=(0,w.useState)(!1),o=c()(s,2),L=o[0],A=o[1],T=(0,w.useState)(!1),_=c()(T,2),D=_[0],E=_[1],H=(0,y.useParams)(),K=(0,w.useState)({}),N=c()(K,2),B=N[0],F=N[1],J=(0,w.useState)({}),Q=c()(J,2),R=Q[0],W=Q[1],q=(0,w.useState)({pageIndex:1,pageSize:10}),G=c()(q,2),O=G[0],U=G[1],X=(0,y.useIntl)(),Y=function(){var e=u()(i()().mark((function e(){var t,n,l;return i()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return A(!0),e.prev=1,e.next=4,k.Z.articleApi.accountPageQuery(O);case 4:n=e.sent,O.pageIndex=((null==O?void 0:O.pageIndex)||0)+1,O.lastValues=null==n?void 0:n.lastValues,U(O),l={pageIndex:n.pageIndex,pageSize:n.pageSize,list:[].concat(r()(R.list||[]),r()(n.list||[])),total:n.total,lastValues:n.lastValues},W(l),((null==n||null===(t=n.list)||void 0===t?void 0:t.length)||0)<(O.pageSize||0)&&E(!0);case 11:return e.prev=11,a(!1),A(!1),e.finish(11);case 15:case"end":return e.stop()}}),e,null,[[1,,11,15]])})));return function(){return e.apply(this,arguments)}}(),$=function(){var e=u()(i()().mark((function e(t){return i()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return O.pageIndex=1,O.lastValues=[],O.searchKeyword=t,U(O),R.pageIndex=1,R.list=[],R.lastValues=[],W(R),e.next=10,Y();case 10:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}();return(0,w.useEffect)((function(){a(!0),A(!0),F(e||{}),Y()}),[H]),(0,V.jsxs)(d._zJ,{header:{title:""},loading:l,children:[(0,V.jsx)(x.Z,{bordered:!1,style:{marginBottom:24},loading:l,title:"",children:(0,V.jsxs)(v.Z,{direction:"vertical",align:"center",style:{width:"100%"},children:[(0,V.jsx)(Z.Z,{src:null==B?void 0:B.avatar,alt:"",size:128}),(0,V.jsx)(p.Z.Title,{level:3,children:null==B?void 0:B.name}),(0,V.jsx)(p.Z.Paragraph,{children:null==B?void 0:B.introduction})]})}),(0,V.jsx)(x.Z,{title:X.formatMessage({id:"pages.account.center.articleList.title",defaultMessage:"文章列表"}),extra:(0,V.jsx)(g.Z,{size:"middle",allowClear:!0,suffix:(0,V.jsx)(I.Z,{}),onKeyDown:function(){var e=u()(i()().mark((function e(t){return i()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if("Enter"!==t.key){e.next=3;break}return e.next=3,$(null==t?void 0:t.target.value);case 3:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}()}),children:(0,V.jsx)(h.ZP,{itemLayout:"vertical",size:"default",loading:l||L,dataSource:null==R?void 0:R.list,loadMore:D?(0,V.jsx)(f.Z,{plain:!0,children:X.formatMessage({id:"pages.ArticleList.endMessage"})}):(0,V.jsx)("div",{style:{textAlign:"center",marginTop:12},children:(0,V.jsx)(j.Z,{onClick:Y,children:X.formatMessage({id:"pages.account.center.articleList.loadMore",defaultMessage:"加载更多"})})}),renderItem:function(e){var t,n,l,i;return(0,V.jsxs)(h.ZP.Item,{actions:[(0,V.jsxs)(v.Z,{children:[null!=e&&e.isLiked?(0,V.jsx)(M.Z,{}):(0,V.jsx)(b.Z,{}),null==e?void 0:e.likedNumber]}),(0,V.jsxs)(v.Z,{children:[null!=e&&e.isReported?(0,V.jsx)(z.Z,{}):(0,V.jsx)(P.Z,{}),null==e?void 0:e.reportedNumber]}),(0,V.jsxs)(v.Z,{children:[(0,V.jsx)(C.Z,{}),null!=e&&e.comments?null==e?void 0:e.comments.length:0]})],extra:null!=e&&e.cover?(0,V.jsx)("img",{width:272,alt:"cover",src:null==e?void 0:e.cover}):null,children:[(0,V.jsx)(h.ZP.Item.Meta,{avatar:(0,V.jsx)(Z.Z,{src:null==e||null===(t=e.account)||void 0===t?void 0:t.avatar}),title:(0,V.jsx)("a",{href:null==e||null===(n=e.account)||void 0===n?void 0:n.avatar,children:null==e||null===(l=e.account)||void 0===l?void 0:l.name}),description:null==e||null===(i=e.account)||void 0===i?void 0:i.introduction}),(0,V.jsxs)("div",{onClick:function(){return y.history.push("/article/".concat(e.id))},children:[(0,V.jsx)(p.Z.Title,{ellipsis:{rows:1,tooltip:null==e?void 0:e.title},level:1,children:(0,V.jsx)(S.Z,{content:null==e?void 0:e.title})}),(0,V.jsx)(p.Z.Paragraph,{ellipsis:{rows:5},style:{maxHeight:"200px"},children:(0,V.jsx)(S.Z,{content:(null==e?void 0:e.customContent)||""})})]})]},null==e?void 0:e.id)}})}),(0,V.jsx)(m.Z,{})]})}}}]);