import {Component, Input} from '@angular/core';
import {UrlConfig} from "../../url/url.config";

@Component({
  selector: 'xe-link',
  template: `
    <a [routerLink]="xeUrl.full" class="{{class}} {{hideThis()}}" [routerLinkActive]="routerLinkActive">{{label}}</a>
  `,
  styles: []
})
export class XeLinkComponent {

  @Input() label: string;
  @Input() routerLinkActive;
  @Input() class;

  @Input() xeUrl: UrlConfig;

  hideThis() {
    return "d-none";
  }

}

