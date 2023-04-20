import api from "@/services/api";
import {Badge, Card, Divider, Dropdown, List, Skeleton, Typography} from 'antd';
import {parseInt} from 'lodash';
import moment from 'moment';
import React, {useEffect, useState} from 'react';
import styles from './index.less';
import classNames from 'classnames';
import {BellOutlined, ClearOutlined} from '@ant-design/icons';
import InfiniteScroll from "react-infinite-scroll-component";
import {history} from "@@/core/history";
import Markdown from "@/components/Markdown";
import {useIntl} from "@@/exports";

const NoticeIconView: React.FC = () => {
  const intl = useIntl();
  const [loading, setLoading] = useState<boolean>(false);
  const [open, setOpen] = useState<boolean>(false);
  const [notificationPageQueryInputDTO, setNotificationPageQueryInputDTO] = useState<API.pageQueryParams>({
    pageIndex: 1,
    pageSize: 5,
  });
  const [page, setPage] = useState<API.PaginationNotificationRepresentation>({
    pageIndex: 0,
    pageSize: 10,
    list: [],
    total: 0,
  });

  const loadMore = async () => {
    setLoading(true);

    try {
      const res: API.PaginationNotificationRepresentation = await api.notificationApi.pageQuery(notificationPageQueryInputDTO);
      notificationPageQueryInputDTO.pageIndex = (notificationPageQueryInputDTO.pageIndex || 0) + 1;
      notificationPageQueryInputDTO.lastValues = res.lastValues;
      setNotificationPageQueryInputDTO(notificationPageQueryInputDTO);

      const tempPage = {
        pageIndex: res.pageIndex,
        pageSize: res.pageSize,
        list: [...page.list || [], ...res.list || []],
        total: res.total,
        lastValues: res.lastValues,
      };
      await setPage(tempPage);

    } finally {
      setLoading(false);
    }
  };

  const refresh = async () => {
    setLoading(true);

    // 重置参数
    notificationPageQueryInputDTO.pageIndex = 1;
    notificationPageQueryInputDTO.lastValues = [];
    setNotificationPageQueryInputDTO(notificationPageQueryInputDTO);

    // 重置结果
    page.pageIndex = 1;
    page.list = [];
    page.lastValues = [];
    await setPage(page);

    await loadMore();
  };

  const notificationHandle = async (item: API.NotificationRepresentation) => {
    if (item?.details?.type === 'ACCOUNT_DISABLED') {
      history.push(`/account/center`);
    } else if (item?.details?.type === 'ARTICLE_LIKE') {
      history.push(`/article/${item?.details?.additionalInfo?.articleId}`);
    } else if (item?.details?.type === 'ARTICLE_COMMENT') {
      history.push(
        `/article/${item?.details?.additionalInfo?.articleId}`
        + `#${item?.details?.additionalInfo?.articleSubCommentId || item?.details?.additionalInfo?.articleCommentId}`);
    }
  };
  const onClick = async (item: API.NotificationRepresentation) => {
    setOpen(false);
    await notificationHandle(item);
    await api.notificationApi.read({notificationId: item.id || 0,})
    await refresh();
  };
  const clear = async (item: API.NotificationRepresentation) => {
    await api.notificationApi.clear({notificationId: item.id || 0,})
    await refresh();
  };


  useEffect(() => {
    refresh();
  }, []);


  return (
    <Dropdown
      placement="bottomRight"
      overlayClassName={styles.popover}
      open={open}
      onOpenChange={setOpen}
      // trigger={['click']}
      menu={{items: []}}
      dropdownRender={(menus) => (
        <>
          <Card
            bordered={false}
            headStyle={{padding: 0, paddingLeft: 15, paddingRight: 15}}
            bodyStyle={{padding: 0}}
            className={styles.card}
            title={<div>{intl.formatMessage({id: "component.globalHeader.Notification.Dropdown.card.title"})}</div>}
            extra={<a
              onClick={refresh}>{intl.formatMessage({id: "component.globalHeader.Notification.Dropdown.card.extra"})}</a>}
          >
            <div
              id="notification-card"
              style={{overflow: "auto", maxHeight: 500,}}
            >
              <InfiniteScroll
                dataLength={page?.list?.length || 0}
                hasMore={(page?.list?.length || 0) < (page?.total || 0)}
                next={loadMore}

                pullDownToRefresh={true}
                refreshFunction={refresh}

                loader={<Skeleton avatar paragraph={{rows: 1}} active/>}
                endMessage={<Divider plain>{intl.formatMessage({id: 'component.endMessage'})}</Divider>}

                scrollableTarget="notification-card"
              >
                <List<API.NotificationRepresentation>
                  className={styles.list}
                  bordered={false}
                  loading={loading}
                  // itemLayout={'vertical'}
                  dataSource={page.list}
                  renderItem={(item, i) => {
                    return (
                      <List.Item
                        className={classNames(styles.item, {[styles.read]: item?.status === 'HAS_READ',})}
                        style={{paddingLeft: 15, paddingRight: 15}}
                        key={item.id}
                        actions={[(<ClearOutlined onClick={() => clear(item)}/>)]}
                      >
                        <Typography onClick={() => onClick(item)}>
                          <Typography.Title ellipsis={{rows: 1,}} level={5} style={{margin:0}}>
                            {item?.details?.title}
                          </Typography.Title>
                          <Typography.Paragraph ellipsis={{rows: 2, tooltip: item?.details?.content}}
                                                style={{padding: 0, margin: 0}}>
                            <Markdown content={item?.details?.content}/>
                          </Typography.Paragraph>
                          <Typography.Text style={{color: "rgba(0, 0, 0, 0.45)", fontSize: 12}}>
                            {moment(parseInt(item?.createdAt || '0'), undefined, intl.locale).fromNow()}
                          </Typography.Text>
                        </Typography>
                      </List.Item>
                    );
                  }}
                />
              </InfiniteScroll>
            </div>
          </Card>
        </>
      )}
    >
           <span className={classNames(styles.action, styles.account, {opened: open})}>
             <Badge count={page.total} style={{boxShadow: 'none'}} className={styles.badge}>
               <BellOutlined className={styles.icon}/>
             </Badge>
           </span>
    </Dropdown>
  );
};

export default NoticeIconView;
