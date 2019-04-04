import { OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'nb-grid-b-outline',
    link: '/greenstarui/pages/dashboard'
  },
  {
    title: 'School',
    icon: 'nb-home',
    link: '/greenstarui/pages/school'
  },
  {
    title: 'Student',
    icon: 'nb-person',
    link: '/greenstarui/pages/student'
  },
  {
    title: 'Performance Data',
    icon: 'nb-bar-chart',
    link: '/greenstarui/pages/performancedata'
  },
  {
    title: 'Performance Star',
    icon: 'nb-star',
    link: '/greenstarui/pages/performancestar'
  },
  {
    title: 'Performance Metrics',
    icon: 'nb-collapse',
    link: '/greenstarui/pages/performancemetrics'
  },
  {
    title: 'Admin',
    icon: 'nb-gear',
    link: '/greenstarui/pages/admin'
  },
  {
    title: 'Logout',
    icon: 'ion-log-out',
    link: '/greenstarui/login'
  }
];

export class PageMenu implements OnInit {

  private static finalMenu: any = [];
  private uiMenuList = [];

  ngOnInit(): void {

  }

  public static getMenus(): NbMenuItem[] {

    this.finalMenu = [];
    let menu = localStorage.getItem('uiMenuList');

    if ((typeof menu !== 'undefined') && null !== menu && '' !== menu) {
      let uiMenuList = menu.split('~');

      for (let menuObj of MENU_ITEMS) {
        if (uiMenuList.includes(menuObj.title)) {
          this.finalMenu.push(menuObj);
        }
      }
    }
    return this.finalMenu;
  }

}
