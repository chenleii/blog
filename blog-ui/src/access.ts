import {InitialState} from "@/app";

/**
 * @see https://umijs.org/zh-CN/plugins/plugin-access
 */
export default function access(initialState: InitialState | undefined) {

  const {isLoggedIn, loggedInAccount} = initialState ?? {};

  return {
    isAccountLoggedIn: isLoggedIn && loggedInAccount,
    isAccountEnabled: isLoggedIn && loggedInAccount && loggedInAccount.status === 'ENABLED',
    canArticleUpdate: (article: any) => {
      return isLoggedIn && loggedInAccount
        && loggedInAccount.status === 'ENABLED'
        && loggedInAccount.id === article.accountId;
    },
  };
}
