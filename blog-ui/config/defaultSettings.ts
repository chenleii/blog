import { Settings as LayoutSettings } from '@ant-design/pro-components';

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  title: '人人博客',
  logo: '/logo.svg',
  // 整体风格设置
  navTheme: 'light',
  // 主题颜色 拂晓蓝
  colorPrimary: '#1890ff',
  // 导航模式
  layout: 'top',
  // 内容布局
  contentWidth: 'Fixed',
  // 固定头部
  fixedHeader: true,
  // 固定侧边菜单
  fixSiderbar: true,
  pwa: false,
};

export default Settings;
