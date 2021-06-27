import {Component} from '@angular/core';

import {MENU_ITEMS} from './demo-menu';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['demo.component.scss'],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class DemoComponent {

  menu = MENU_ITEMS;
}
