export default [
  {
    path: '/account',
    layout: false,
    routes: [
      {
        name: 'account.login',
        path: '/account/login',
        component: './Account/Login',
      },
    ],
  },
  {
    // name: 'account.center',
    path: '/account/center',
    component: './Account/Center',
  },
  {
    // name: 'account.settings',
    path: '/account/settings',
    component: './Account/Update',
  },
  {
    path: '/article',
    routes: [
      {
        path: '/article/:articleId',
        component: './Article/Details',
      },
      {
        path: '/article/editor',
        component: './Article/Editor',
      },
      {
        path: '/article/:articleId/editor',
        component: './Article/Editor',
      }
    ],
  },

  {
    name: 'latest',
    path: '/latest',
    icon: 'read',
    // icon: 'global',
    // icon: 'history',
    // icon: 'heart',
    component: './Article/List',
  },
  {
    name: 'headlines',
    path: '/headlines',
    icon: 'fire',
    component: './Article/List',
  },

  {
    path: '/',
    redirect: '/latest',
  },
  {
    path: '/*',
    component: './404',
  },
];
