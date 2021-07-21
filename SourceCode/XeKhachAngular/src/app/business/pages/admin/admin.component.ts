import {Component} from '@angular/core';
import {ADMIN_MENU} from "./admin-menu";
import {NbIconLibraries} from "@nebular/theme";

@Component({
  selector: 'xe-admin',
  styles: [''],
  templateUrl: './admin.component.html'
})
export class AdminComponent {
  menu = ADMIN_MENU;
  constructor(iconsLibrary: NbIconLibraries) {
    iconsLibrary.registerFontPack('fa', { packClass: 'fa', iconClassPrefix: 'fa' });
    iconsLibrary.registerFontPack('far', { packClass: 'far', iconClassPrefix: 'fa' });
  }
}

