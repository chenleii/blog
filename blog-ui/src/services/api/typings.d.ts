declare namespace API {
  type AccountLoginInputDTO = {
    /** 手机号 */
    phoneNo?: string;
    /** 验证码 */
    verificationCode?: string;
  };

  type accountPageQueryParams = {
    /** 搜索关键字 */
    searchKeyword?: string;
    /** 查询的状态 */
    statuses?: ('DRAFT' | 'PUBLICATION' | 'VIEWABLE' | 'INVISIBLE')[];
    /** 是否开启拼音搜索 */
    isOpenPinyin?: boolean;
    /** 是否开启同义词搜索 */
    isOpenSynonym?: boolean;
    /** 是否开启跨语言搜索 */
    isOpenCrossLanguage?: boolean;
    /** 当前页数 */
    pageIndex?: number;
    /** 每页条数 */
    pageSize?: number;
    /** 分页查询的最后一条记录排序字段值 */
    lastValues?: Record<string, any>[];
  };

  type AccountResult = {
    id?: number;
    avatar?: string;
    name?: string;
    phoneNo?: string;
    password?: string;
    introduction?: string;
    influenceValue?: number;
    status?: 'ENABLED' | 'DISABLED';
    createdAt?: string;
    updatedAt?: string;
  };

  type AccountSendLoginVerificationCodeInputDTO = {
    /** 手机号 */
    phoneNo?: string;
  };

  type AccountUpdateInputDTO = {
    /** 头像 */
    avatar?: string;
    /** 名称 */
    name?: string;
    /** 密码 */
    password?: string;
    /** 简介 */
    introduction?: string;
  };

  type ArticleCommentDO = {
    id?: number;
    accountId?: number;
    content?: string;
    commentedAt?: number;
    subComments?: ArticleSubCommentDO[];
    account?: AccountResult;
  };

  type ArticleCommentInputDTO = {
    /** 文章ID */
    articleId?: number;
    /** 评论内容 */
    content?: string;
  };

  type ArticleLikeInputDTO = {
    /** 文章ID */
    articleId?: bigint;
  };

  type ArticleLikeRecordDO = {
    accountId?: number;
    isLiked?: boolean;
    likedAt?: string;
  };

  type ArticleReplySubCommentInputDTO = {
    /** 文章ID */
    articleId?: number;
    /** 评论ID */
    commentId?: number;
    /** 回复的子评论ID */
    replyCommentId?: number;
    /** 评论内容 */
    content?: string;
  };

  type ArticleReportInputDTO = {
    /** 文章ID */
    articleId?: number;
    /** 备注 */
    remark?: string;
  };

  type ArticleReportRecordDO = {
    accountId?: number;
    remark?: string;
    reportedAt?: string;
  };

  type ArticleResult = {
    id?: number;
    accountId?: string;
    title?: string;
    tags?: string[];
    content?: string;
    status?: 'DRAFT' | 'PUBLICATION' | 'VIEWABLE' | 'INVISIBLE';
    likes?: ArticleLikeRecordDO[];
    reports?: ArticleReportRecordDO[];
    comments?: ArticleCommentDO[];
    createdAt?: string;
    updatedAt?: string;
    account?: AccountResult;
    likedNumber?: number;
    reportedNumber?: number;
    isLiked?: boolean;
    isReported?: boolean;
    customContent?: string;
    cover?: string;
  };

  type ArticleSaveAndPublishInputDTO = {
    /** 标题 */
    title?: string;
    /** 标签 */
    tags?: string[];
    /** 内容 */
    content?: string;
    /** 是否发布 */
    isPublish?: boolean;
  };

  type ArticleSubCommentDO = {
    id?: number;
    accountId?: number;
    content?: string;
    commentedAt?: number;
    account?: AccountResult;
    replyId?: number;
  };

  type ArticleUpdateAndPublishInputDTO = {
    /** 文章ID */
    articleId?: number;
    /** 标题 */
    title?: string;
    /** 标签 */
    tags?: string[];
    /** 内容 */
    content?: string;
    /** 是否发布 */
    isPublish?: boolean;
  };

  type ErrorResult = {
    errorCode?: string;
    errorMessage?: string;
    error?: Record<string, any>;
  };

  type headlinesPageQueryParams = {
    /** 当前页数 */
    pageIndex?: number;
    /** 每页条数 */
    pageSize?: number;
    /** 分页查询的最后一条记录排序字段值 */
    lastValues?: Record<string, any>[];
  };

  type hotSearchPageQueryParams = {
    /** 当前页数 */
    pageIndex?: number;
    /** 每页条数 */
    pageSize?: number;
    /** 分页查询的最后一条记录排序字段值 */
    lastValues?: Record<string, any>[];
  };

  type LoggedInAccount = {
    id?: number;
    avatar?: string;
    name?: string;
    phoneNo?: string;
    introduction?: string;
    influenceValue?: number;
    status?: 'ENABLED' | 'DISABLED';
    createdAt?: string;
    updatedAt?: string;
    loggedInAt?: string;
  };

  type pageQueryParams = {
    /** 搜索关键字 */
    searchKeyword?: string;
    /** 账户ID */
    accountId?: number;
    /** 是否开启拼音搜索 */
    isOpenPinyin?: boolean;
    /** 是否开启同义词搜索 */
    isOpenSynonym?: boolean;
    /** 是否开启跨语言搜索 */
    isOpenCrossLanguage?: boolean;
    /** 当前页数 */
    pageIndex?: number;
    /** 每页条数 */
    pageSize?: number;
    /** 分页查询的最后一条记录排序字段值 */
    lastValues?: Record<string, any>[];
  };

  type PaginationArticleResult = {
    pageIndex?: number;
    pageSize?: number;
    total?: number;
    list?: ArticleResult[];
    lastValues?: Record<string, any>[];
  };

  type PaginationString = {
    pageIndex?: number;
    pageSize?: number;
    total?: number;
    list?: string[];
    lastValues?: Record<string, any>[];
  };

  type queryParams = {
    articleId: number;
  };
}
