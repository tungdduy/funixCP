import {Component, Input} from '@angular/core';
import {XeScreen} from "../../../framework/components/xe-nav/xe-nav.component";

@Component({
  selector: 'ngx-one-column-layout',
  styleUrls: ['./one-column.layout.scss'],
  template: `
    <nb-layout windowMode>
      <nb-layout-header fixed>
        <ngx-header></ngx-header>
      </nb-layout-header>

      <nb-sidebar class="menu-sidebar" tag="menu-sidebar" responsive>
        <ng-content select="nb-menu"></ng-content>
      </nb-sidebar>

      <nb-layout-column>
        <ng-container *ngIf="screen.isHome">
          <ng-content select="router-outlet"></ng-content>
        </ng-container>
        <ng-container *ngIf="!screen.isHome">
          <ng-content select="[manualContent]"></ng-content>
        </ng-container>
      </nb-layout-column>

      <nb-layout-footer fixed>
        <ngx-footer></ngx-footer>
      </nb-layout-footer>
    </nb-layout>
  `,
})
export class OneColumnLayoutComponent {
  @Input() screen: XeScreen;
}
