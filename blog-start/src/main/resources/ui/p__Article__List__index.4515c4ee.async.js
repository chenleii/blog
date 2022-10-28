"use strict";(self.webpackChunkblog_ui=self.webpackChunkblog_ui||[]).push([[704],{9229:function(e,t,n){var r=n(97857),o=n.n(r),s=(n(67294),n(95925)),i=n(34768),l=n(85893);t.Z=function(e){var t=e.content,n=e.isHtml,r=void 0===n||n;return(0,l.jsx)(l.Fragment,{children:(0,l.jsx)(s.D,{rehypePlugins:r?[i.Z]:[],components:{img:function(e){return(0,l.jsx)("img",o()(o()({},e),{},{style:{maxWidth:"100%"}}))}},children:t||""})})}},2466:function(e,t,n){n.r(t),n.d(t,{default:function(){return V}});var r=n(19632),o=n.n(r),s=n(15009),i=n.n(s),l=n(97857),a=n.n(l),c=n(99289),u=n.n(c),h=n(5574),p=n.n(h),d=n(99913),f=n(65429),m=n(10844),v=n(28058),g=n(38545),x=n(49693),w=n(59652),y=n(26303),T=n(27049),b=n(95507),S=n(26713),E=n(55241),L=n(81579),j=n(69677),D=n(71577),M=n(84485),Z=n(44057),_=n(71511),k=n(67294),Y=n(57328),R=function(e,t){return R=Object.setPrototypeOf||{__proto__:[]}instanceof Array&&function(e,t){e.__proto__=t}||function(e,t){for(var n in t)t.hasOwnProperty(n)&&(e[n]=t[n])},R(e,t)};var A=function(){return A=Object.assign||function(e){for(var t,n=1,r=arguments.length;n<r;n++)for(var o in t=arguments[n])Object.prototype.hasOwnProperty.call(t,o)&&(e[o]=t[o]);return e},A.apply(this,arguments)};var P="Pixel",I="Percent",C={unit:I,value:.8};function H(e){return"number"==typeof e?{unit:I,value:100*e}:"string"==typeof e?e.match(/^(\d*(\.\d+)?)px$/)?{unit:P,value:parseFloat(e)}:e.match(/^(\d*(\.\d+)?)%$/)?{unit:I,value:parseFloat(e)}:(console.warn('scrollThreshold format is invalid. Valid formats: "120px", "50%"...'),C):(console.warn("scrollThreshold should be string or number"),C)}var O=function(e){function t(t){var n=e.call(this,t)||this;return n.lastScrollTop=0,n.actionTriggered=!1,n.startY=0,n.currentY=0,n.dragging=!1,n.maxPullDownDistance=0,n.getScrollableTarget=function(){return n.props.scrollableTarget instanceof HTMLElement?n.props.scrollableTarget:"string"==typeof n.props.scrollableTarget?document.getElementById(n.props.scrollableTarget):(null===n.props.scrollableTarget&&console.warn("You are trying to pass scrollableTarget but it is null. This might\n        happen because the element may not have been added to DOM yet.\n        See https://github.com/ankeetmaini/react-infinite-scroll-component/issues/59 for more info.\n      "),null)},n.onStart=function(e){n.lastScrollTop||(n.dragging=!0,e instanceof MouseEvent?n.startY=e.pageY:e instanceof TouchEvent&&(n.startY=e.touches[0].pageY),n.currentY=n.startY,n._infScroll&&(n._infScroll.style.willChange="transform",n._infScroll.style.transition="transform 0.2s cubic-bezier(0,0,0.31,1)"))},n.onMove=function(e){n.dragging&&(e instanceof MouseEvent?n.currentY=e.pageY:e instanceof TouchEvent&&(n.currentY=e.touches[0].pageY),n.currentY<n.startY||(n.currentY-n.startY>=Number(n.props.pullDownToRefreshThreshold)&&n.setState({pullToRefreshThresholdBreached:!0}),n.currentY-n.startY>1.5*n.maxPullDownDistance||n._infScroll&&(n._infScroll.style.overflow="visible",n._infScroll.style.transform="translate3d(0px, "+(n.currentY-n.startY)+"px, 0px)")))},n.onEnd=function(){n.startY=0,n.currentY=0,n.dragging=!1,n.state.pullToRefreshThresholdBreached&&(n.props.refreshFunction&&n.props.refreshFunction(),n.setState({pullToRefreshThresholdBreached:!1})),requestAnimationFrame((function(){n._infScroll&&(n._infScroll.style.overflow="auto",n._infScroll.style.transform="none",n._infScroll.style.willChange="unset")}))},n.onScrollListener=function(e){"function"==typeof n.props.onScroll&&setTimeout((function(){return n.props.onScroll&&n.props.onScroll(e)}),0);var t=n.props.height||n._scrollableNode?e.target:document.documentElement.scrollTop?document.documentElement:document.body;n.actionTriggered||((n.props.inverse?n.isElementAtTop(t,n.props.scrollThreshold):n.isElementAtBottom(t,n.props.scrollThreshold))&&n.props.hasMore&&(n.actionTriggered=!0,n.setState({showLoader:!0}),n.props.next&&n.props.next()),n.lastScrollTop=t.scrollTop)},n.state={showLoader:!1,pullToRefreshThresholdBreached:!1,prevDataLength:t.dataLength},n.throttledOnScrollListener=function(e,t,n,r){var o,s=!1,i=0;function l(){o&&clearTimeout(o)}function a(){var a=this,c=Date.now()-i,u=arguments;function h(){i=Date.now(),n.apply(a,u)}function p(){o=void 0}s||(r&&!o&&h(),l(),void 0===r&&c>e?h():!0!==t&&(o=setTimeout(r?p:h,void 0===r?e-c:e)))}return"boolean"!=typeof t&&(r=n,n=t,t=void 0),a.cancel=function(){l(),s=!0},a}(150,n.onScrollListener).bind(n),n.onStart=n.onStart.bind(n),n.onMove=n.onMove.bind(n),n.onEnd=n.onEnd.bind(n),n}return function(e,t){function n(){this.constructor=e}R(e,t),e.prototype=null===t?Object.create(t):(n.prototype=t.prototype,new n)}(t,e),t.prototype.componentDidMount=function(){if(void 0===this.props.dataLength)throw new Error('mandatory prop "dataLength" is missing. The prop is needed when loading more content. Check README.md for usage');if(this._scrollableNode=this.getScrollableTarget(),this.el=this.props.height?this._infScroll:this._scrollableNode||window,this.el&&this.el.addEventListener("scroll",this.throttledOnScrollListener),"number"==typeof this.props.initialScrollY&&this.el&&this.el instanceof HTMLElement&&this.el.scrollHeight>this.props.initialScrollY&&this.el.scrollTo(0,this.props.initialScrollY),this.props.pullDownToRefresh&&this.el&&(this.el.addEventListener("touchstart",this.onStart),this.el.addEventListener("touchmove",this.onMove),this.el.addEventListener("touchend",this.onEnd),this.el.addEventListener("mousedown",this.onStart),this.el.addEventListener("mousemove",this.onMove),this.el.addEventListener("mouseup",this.onEnd),this.maxPullDownDistance=this._pullDown&&this._pullDown.firstChild&&this._pullDown.firstChild.getBoundingClientRect().height||0,this.forceUpdate(),"function"!=typeof this.props.refreshFunction))throw new Error('Mandatory prop "refreshFunction" missing.\n          Pull Down To Refresh functionality will not work\n          as expected. Check README.md for usage\'')},t.prototype.componentWillUnmount=function(){this.el&&(this.el.removeEventListener("scroll",this.throttledOnScrollListener),this.props.pullDownToRefresh&&(this.el.removeEventListener("touchstart",this.onStart),this.el.removeEventListener("touchmove",this.onMove),this.el.removeEventListener("touchend",this.onEnd),this.el.removeEventListener("mousedown",this.onStart),this.el.removeEventListener("mousemove",this.onMove),this.el.removeEventListener("mouseup",this.onEnd)))},t.prototype.componentDidUpdate=function(e){this.props.dataLength!==e.dataLength&&(this.actionTriggered=!1,this.setState({showLoader:!1}))},t.getDerivedStateFromProps=function(e,t){return e.dataLength!==t.prevDataLength?A(A({},t),{prevDataLength:e.dataLength}):null},t.prototype.isElementAtTop=function(e,t){void 0===t&&(t=.8);var n=e===document.body||e===document.documentElement?window.screen.availHeight:e.clientHeight,r=H(t);return r.unit===P?e.scrollTop<=r.value+n-e.scrollHeight+1:e.scrollTop<=r.value/100+n-e.scrollHeight+1},t.prototype.isElementAtBottom=function(e,t){void 0===t&&(t=.8);var n=e===document.body||e===document.documentElement?window.screen.availHeight:e.clientHeight,r=H(t);return r.unit===P?e.scrollTop+n>=e.scrollHeight-r.value:e.scrollTop+n>=r.value/100*e.scrollHeight},t.prototype.render=function(){var e=this,t=A({height:this.props.height||"auto",overflow:"auto",WebkitOverflowScrolling:"touch"},this.props.style),n=this.props.hasChildren||!!(this.props.children&&this.props.children instanceof Array&&this.props.children.length),r=this.props.pullDownToRefresh&&this.props.height?{overflow:"auto"}:{};return k.createElement("div",{style:r,className:"infinite-scroll-component__outerdiv"},k.createElement("div",{className:"infinite-scroll-component "+(this.props.className||""),ref:function(t){return e._infScroll=t},style:t},this.props.pullDownToRefresh&&k.createElement("div",{style:{position:"relative"},ref:function(t){return e._pullDown=t}},k.createElement("div",{style:{position:"absolute",left:0,right:0,top:-1*this.maxPullDownDistance}},this.state.pullToRefreshThresholdBreached?this.props.releaseToRefreshContent:this.props.pullDownToRefreshContent)),this.props.children,!this.state.showLoader&&!n&&this.props.hasMore&&this.props.loader,this.state.showLoader&&this.props.hasMore&&this.props.loader,!this.props.hasMore&&this.props.endMessage))},t}(k.Component),B=O,F=n(9832),N=n(9229),z=n(85893),V=function(){var e,t,n=(0,k.useState)(!1),r=p()(n,2),s=r[0],l=r[1],c=(0,Y.useLocation)(),h=(0,Y.useSearchParams)(),R=p()(h,1)[0],A=(0,Y.useIntl)(),P=(0,k.useState)({pageIndex:1,pageSize:10,searchKeyword:R.get("searchKeyword")||""}),I=p()(P,2),C=I[0],H=I[1],O=(0,k.useState)({pageIndex:0,pageSize:10,list:[],total:0}),V=p()(O,2),K=V[0],U=V[1],W=c.pathname.includes("/headlines"),Q=function(){var e=u()(i()().mark((function e(t){var n,r;return i()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,F.Z.articleApi.like({articleId:t.id});case 2:n=e.sent,(K.list||[]).filter((function(e){return(null==e?void 0:e.id)===t.id})).map((function(e){return e.isLiked=!t.isLiked,e.likedNumber=n,e})),r=a()(a()({},K),{},{list:K.list}),U(r);case 6:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}(),$=function(){var e=u()(i()().mark((function e(t,n){var r,o;return i()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,F.Z.articleApi.report({articleId:t.id,remark:n.remark});case 2:r=e.sent,(K.list||[]).filter((function(e){return(null==e?void 0:e.id)===t.id})).map((function(e){return e.isReported=!0,e.reportedNumber=r,e})),o=a()(a()({},K),{},{list:K.list}),U(o);case 6:case"end":return e.stop()}}),e)})));return function(t,n){return e.apply(this,arguments)}}(),q=function(){var e=u()(i()().mark((function e(){var t,n;return i()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(l(!0),e.prev=1,t={},!W){e.next=9;break}return e.next=6,F.Z.articleApi.headlinesPageQuery(C);case 6:t=e.sent,e.next=12;break;case 9:return e.next=11,F.Z.articleApi.pageQuery(C);case 11:t=e.sent;case 12:return C.pageIndex=(C.pageIndex||0)+1,C.lastValues=t.lastValues,H(C),n={pageIndex:t.pageIndex,pageSize:t.pageSize,list:[].concat(o()(K.list||[]),o()(t.list||[])),total:t.total,lastValues:t.lastValues},e.next=18,U(n);case 18:return e.prev=18,l(!1),e.finish(18);case 21:case"end":return e.stop()}}),e,null,[[1,,18,21]])})));return function(){return e.apply(this,arguments)}}(),J=function(){var e=u()(i()().mark((function e(){return i()().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return l(!0),C.pageIndex=1,C.lastValues=[],C.searchKeyword=R.get("searchKeyword")||"",H(C),K.pageIndex=1,K.list=[],K.lastValues=[],e.next=10,U(K);case 10:return e.next=12,q();case 12:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}();return(0,k.useEffect)((function(){J()}),[R]),(0,z.jsxs)(x._zJ,{header:{title:""},children:[(0,z.jsx)(w.Z,{bordered:!1,style:{marginBottom:24},children:(0,z.jsx)(B,{dataLength:(null==K||null===(e=K.list)||void 0===e?void 0:e.length)||0,next:q,hasMore:((null==K||null===(t=K.list)||void 0===t?void 0:t.length)||0)<((null==K?void 0:K.total)||0),pullDownToRefresh:!0,refreshFunction:J,loader:(0,z.jsx)(y.Z,{avatar:!0,paragraph:{rows:1},active:!0}),endMessage:(0,z.jsx)(T.Z,{plain:!0,children:A.formatMessage({id:"pages.ArticleList.endMessage"})}),children:(0,z.jsx)(b.ZP,{itemLayout:"vertical",size:"default",loading:s,dataSource:null==K?void 0:K.list,renderItem:function(e){var t,n,r,o;return(0,z.jsxs)(b.ZP.Item,{actions:[(0,z.jsxs)(S.Z,{onClick:function(){return Q(e)},children:[null!=e&&e.isLiked?(0,z.jsx)(d.Z,{}):(0,z.jsx)(f.Z,{}),null==e?void 0:e.likedNumber]}),(0,z.jsxs)(S.Z,{children:[(0,z.jsx)(E.Z,{title:A.formatMessage({id:"pages.ArticleDetails.report.title",defaultMessage:"确认举报吗？"}),content:(0,z.jsxs)(L.Z,{onFinish:function(t){return $(e,t)},layout:"vertical",children:[(0,z.jsx)(L.Z.Item,{label:A.formatMessage({id:"pages.ArticleDetails.report.remark",defaultMessage:"举报原因"}),name:"remark",children:(0,z.jsx)(j.Z,{placeholder:A.formatMessage({id:"pages.ArticleDetails.report.remark",defaultMessage:"举报原因"})})}),(0,z.jsx)(L.Z.Item,{children:(0,z.jsx)(D.Z,{type:"primary",htmlType:"submit",children:A.formatMessage({id:"pages.ArticleDetails.report.submitButton",defaultMessage:"举报"})})})]}),trigger:"click",children:null!=e&&e.isReported?(0,z.jsx)(m.Z,{}):(0,z.jsx)(v.Z,{})}),null==e?void 0:e.reportedNumber]}),(0,z.jsxs)(S.Z,{children:[(0,z.jsx)(g.Z,{}),null!=e&&e.comments?null==e?void 0:e.comments.length:0]})],extra:(null!=e&&e.cover,null),children:[(0,z.jsx)(b.ZP.Item.Meta,{avatar:(0,z.jsx)(_.Z,{src:null==e||null===(t=e.account)||void 0===t?void 0:t.avatar,size:"large"}),title:(0,z.jsx)("a",{href:null==e||null===(n=e.account)||void 0===n?void 0:n.avatar,children:null==e||null===(r=e.account)||void 0===r?void 0:r.name}),description:null==e||null===(o=e.account)||void 0===o?void 0:o.introduction}),(0,z.jsxs)("div",{onClick:function(){return Y.history.push("/article/".concat(e.id))},children:[(0,z.jsx)(M.Z.Title,{ellipsis:{rows:1,tooltip:null==e?void 0:e.title},level:1,children:(0,z.jsx)(N.Z,{content:null==e?void 0:e.title})}),(0,z.jsx)(M.Z.Paragraph,{ellipsis:{rows:5},style:{maxHeight:"200px"},children:(0,z.jsx)(N.Z,{content:(null==e?void 0:e.customContent)||""})})]})]},null==e?void 0:e.id)}})})}),(0,z.jsx)(Z.Z,{})]})}}}]);