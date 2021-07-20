import {NbMenuItem} from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {title: 'E-commerce', icon: 'shopping-cart-outline', link: '/demo/dashboard', home: true},
  {title: 'IoT Dashboard', icon: 'home-outline', link: '/demo/iot-dashboard'},
  {title: 'FEATURES', group: true},
  {title: 'Layout', icon: 'layout-outline',
    children: [
      {title: 'Stepper', link: '/demo/layout/stepper'},
      {title: 'List', link: '/demo/layout/list'},
      {title: 'Infinite List', link: '/demo/layout/infinite-list'},
      {title: 'Accordion', link: '/demo/layout/accordion'},
      {title: 'Tabs', pathMatch: 'prefix', link: '/demo/layout/tabs',
      },
    ],
  },
  {title: 'Forms', icon: 'edit-2-outline',
    children: [
      {title: 'Form Inputs', link: '/demo/forms/inputs'},
      {title: 'Form Layouts', link: '/demo/forms/layouts'},
      {title: 'Buttons', link: '/demo/forms/buttons'},
      {title: 'Datepicker', link: '/demo/forms/datepicker'},
    ],
  },
  {title: 'UI Features', icon: 'keypad-outline', link: '/demo/ui-features',
    children: [
      {title: 'Grid', link: '/demo/ui-features/grid'},
      {title: 'Icons', link: '/demo/ui-features/icons'},
      {title: 'Typography', link: '/demo/ui-features/typography'},
      {title: 'Animated Searches', link: '/demo/ui-features/search-fields'},
    ],
  },
  {title: 'Modal & Overlays', icon: 'browser-outline',
    children: [
      {title: 'Dialog', link: '/demo/modal-overlays/dialog'},
      {title: 'Window', link: '/demo/modal-overlays/window'},
      {title: 'Popover', link: '/demo/modal-overlays/popover'},
      {title: 'Toastr', link: '/demo/modal-overlays/toastr'},
      {title: 'Tooltip', link: '/demo/modal-overlays/tooltip'},
    ],
  },
  {title: 'Extra Components', icon: 'message-circle-outline',
    children: [
      {title: 'Calendar', link: '/demo/extra-components/calendar'},
      {title: 'Progress Bar', link: '/demo/extra-components/progress-bar'},
      {title: 'Spinner', link: '/demo/extra-components/spinner'},
      {title: 'Alert', link: '/demo/extra-components/alert'},
      {title: 'Calendar Kit', link: '/demo/extra-components/calendar-kit'},
      {title: 'Chat', link: '/demo/extra-components/chat'},
    ],
  },
  {title: 'Maps', icon: 'map-outline',
    children: [
      {title: 'Google Maps', link: '/demo/maps/gmaps'},
      {title: 'Leaflet Maps', link: '/demo/maps/leaflet'},
      {title: 'Bubble Maps', link: '/demo/maps/bubble'},
      {title: 'Search Maps', link: '/demo/maps/searchmap'},
    ],
  },
  {title: 'Charts', icon: 'pie-chart-outline',
    children: [
      {title: 'Echarts', link: '/demo/charts/echarts'},
      {title: 'Charts.js', link: '/demo/charts/chartjs'},
      {title: 'D3', link: '/demo/charts/d3'},
    ],
  },
  {title: 'Editors', icon: 'text-outline',
    children: [
      {title: 'TinyMCE', link: '/demo/editors/tinymce'},
      {title: 'CKEditor', link: '/demo/editors/ckeditor'},
    ],
  },
  {title: 'Tables & Data', icon: 'grid-outline',
    children: [
      {title: 'Smart Table', link: '/demo/tables/smart-table'},
      {title: 'Tree Grid', link: '/demo/tables/tree-grid'},
    ],
  },
  {title: 'Miscellaneous', icon: 'shuffle-2-outline',
    children: [
      {title: '404', link: '/demo/miscellaneous/404'},
    ],
  },
  {title: 'Auth', icon: 'lock-outline',
    children: [
      {title: 'Login', link: '/auth/login'},
      {title: 'Register', link: '/auth/register'},
      {title: 'Request Password', link: '/auth/request-password'},
      {title: 'Reset Password', link: '/auth/reset-password',
      },
    ],
  },
];
