import {CloseCircleFilled, SearchOutlined} from '@ant-design/icons';
import {Badge, Card, Dropdown, Input, InputRef, Space, Tag, Typography} from 'antd';
import React, {useEffect, useRef, useState} from 'react';
import {history, useIntl} from "@@/exports";
import api from '@/services/api';

export type HeaderSearchProps = {};

// 搜索历史缓存key
const BLOG_ARTICLE_SEARCH_HISTORY_KEY = 'blog_article_search_history';

const HeaderSearch: React.FC<HeaderSearchProps> = (props) => {
  const {} = props;

  const inputRef = useRef<InputRef | null>(null);
  const intl = useIntl();
  const [searchValue, setSearchValue] = useState<string>("");
  const [blogArticleSearchHistory, setBlogArticleSearchHistory] = useState<string[]>([]);
  const [hotArticleList, setHotArticleList] = useState<API.ArticleRepresentation[]>([]);
  const [hotSearchList, setHotSearchList] = useState<string[]>([]);
  const [isOpenDropdown, setIsOpenDropdown] = useState<boolean>(false);


  let openDropdown = async () => {
    setIsOpenDropdown(true);
  }
  let resetDropdown = async () => {
    setIsOpenDropdown(false);
  }

  let loadBlogArticleSearchHistory = async () => {
    let localStorageBlogArticleSearchHistory: string[] = JSON.parse(localStorage.getItem(BLOG_ARTICLE_SEARCH_HISTORY_KEY) || '[]');
    localStorageBlogArticleSearchHistory = localStorageBlogArticleSearchHistory.reverse();

    setBlogArticleSearchHistory(localStorageBlogArticleSearchHistory);
  }

  let loadHotArticleList = async () => {
    let hotArticlePage = await api.articleApi.headlinesPageQuery({
      pageIndex: 1,
      pageSize: 5,
    });

    setHotArticleList(hotArticlePage?.list || []);
  }
  let loadHotSearchList = async () => {
    let hotSearchPage = await api.articleApi.hotSearchPageQuery({
      pageIndex: 1,
      pageSize: 10,
    });

    setHotSearchList(hotSearchPage?.list || []);
  }


  let onSearch = async (searchValue: string) => {
    if (searchValue !== '' && !blogArticleSearchHistory.includes(searchValue)) {
      blogArticleSearchHistory.push(searchValue);
      setBlogArticleSearchHistory(blogArticleSearchHistory);
      localStorage.setItem(BLOG_ARTICLE_SEARCH_HISTORY_KEY, JSON.stringify(blogArticleSearchHistory));
    }

    history.push(`/latest?searchKeyword=${searchValue}`);
    setSearchValue('');
    await resetDropdown();
  }
  let removeSearchHistory = async (searchValue: string) => {
    setBlogArticleSearchHistory(blogArticleSearchHistory.filter((item: string) => item !== searchValue));
    localStorage.setItem(BLOG_ARTICLE_SEARCH_HISTORY_KEY, JSON.stringify(blogArticleSearchHistory));

    await resetDropdown();
  }
  let cleanSearchHistory = async () => {
    setBlogArticleSearchHistory([]);
    localStorage.removeItem(BLOG_ARTICLE_SEARCH_HISTORY_KEY);

    await resetDropdown();
  }

  let toArticlePage = async (articleId: number | undefined) => {
    history.push(`/article/${articleId}`);

    await resetDropdown();
  }

  useEffect(() => {
    loadBlogArticleSearchHistory();
    loadHotArticleList();
    loadHotSearchList();
  }, []);


  return (
    <Space>
      <Dropdown
        // className={inputClass}

        open={isOpenDropdown}
        autoFocus={true}
        getPopupContainer={trigger => trigger.parentElement || document.body}
        trigger={['click']}
        placement="bottomLeft"
        overlayStyle={{width: "390px"}}
        onOpenChange={async (open: boolean) => {
          if (open) {
            await openDropdown();
          } else {
            await resetDropdown();
          }
        }}
        dropdownRender={(menus) => {
            return (<>
              <Card
                title={intl.formatMessage({
                  id: 'component.globalHeader.HeaderSearch.searchHistory.title',
                  defaultMessage: '搜索历史',
                })}
                extra={<a onClick={cleanSearchHistory}>
                  {intl.formatMessage({
                    id: 'component.globalHeader.HeaderSearch.searchHistory.extra',
                    defaultMessage: '清空',
                  })}
                </a>}
              >
                <Space wrap={true}>
                  {
                    blogArticleSearchHistory.map((item: string) => {
                      return (
                        <Badge key={item} count={<CloseCircleFilled style={{color: '#9a9a9a', fontSize: '10px'}}/>}
                               offset={[-5, 0]}>
                          <Tag
                            key={item}
                            closable
                            onClose={() => removeSearchHistory(item)}
                            onClick={() => onSearch(item)}>
                            {item}
                          </Tag>
                        </Badge>
                      )
                    })
                  }
                </Space>
              </Card>
              <Card
                title={intl.formatMessage({
                  id: 'component.globalHeader.HeaderSearch.hotSearch.title',
                  defaultMessage: '热搜',
                })}
              >
                <Space wrap={true}>
                  {
                    hotSearchList.map((item: string) => {
                      return (
                        <Tag
                          key={item}
                          onClick={() => onSearch(item)}>
                          {item}
                        </Tag>
                      )
                    })
                  }
                </Space>
              </Card>
              <Card
                title={intl.formatMessage({
                  id: 'component.globalHeader.HeaderSearch.hotArticle.title',
                  defaultMessage: '热门文章',
                })}
                bodyStyle={{padding: '0', margin: '0'}}
              >
                {
                  hotArticleList.map((item: API.ArticleRepresentation) => {
                    return (
                      <Badge.Ribbon key={item?.id} text='Hot' color="red">
                        <Card.Grid
                          key={item?.id}
                          style={{width: '100%',}}
                          onClick={(value) => toArticlePage(item?.id)}
                        >
                          <Typography.Paragraph
                            ellipsis={{rows: 1}}
                            style={{width: '100%', margin: '0'}}
                          >
                            {item?.title}
                          </Typography.Paragraph>
                        </Card.Grid>
                      </Badge.Ribbon>
                    )
                  })
                }
              </Card>
            </>)
          }
        }
      >
        <Input
          style={
            !isOpenDropdown
              ? {width: '230px'}
              : {width: '390px'}
          }
          allowClear={true}
          size="middle"
          suffix={<SearchOutlined/>}
          ref={inputRef}
          placeholder={
            intl.formatMessage({
              id: 'component.globalHeader.HeaderSearch.search.placeholder',
              defaultMessage: '搜索',
            })
          }
          value={searchValue}
          onChange={(v) => {
            setSearchValue(v.target.value);
          }}
          onKeyDown={async (e) => {
            if (e.key === 'Enter') {
              await onSearch(searchValue);
            }
          }}
          onFocus={async () => {

          }}
          onBlur={async () => {

          }}
        />
      </Dropdown>
    </Space>
  );
};

export default HeaderSearch;
