import {Component, Input, OnInit} from '@angular/core';
import {XeLbl} from "../../../business/i18n";

export interface ScreenConfig {
  preGo?: (screen) => any;
  preHome?: (screen) => any;
  home: string;
  homeIcon?: string;
  homeTitle?: () => string;
}

export class XeScreen {
  config: ScreenConfig;
  current;
  stack = [];

  constructor(config: ScreenConfig) {
    this.config = config;
    this.current = config.home;
    if (!config.homeTitle) this.config.homeTitle = () => this.config.home;
  }

  get isHome() {
    return this.current === this.config.home;
  }

  get home() {
    return this.config.home;
  }

  get title() {
    return this.current === this.config.home
      ? XeLbl(`screen.${this.config.home}`)
      : XeLbl(this.config.homeTitle());
  }

  isThisHome(checkHome: string) {
    return this.config.home === checkHome;
  }

  is(checkScreen: string) {
    return this.current === checkScreen;
  }

  back = () => {
    if (this.stack.length === 0) {
      this.goHome();
    }
    this.stack.shift();
    let result = this.config.home;
    if (this.stack.length > 0) {
      result = this.stack[0];
    }
    if (this.config.preGo) this.config.preGo(result);
    this.current = result;
  }

  goHome = () => {
    if (this.config.preHome) this.config.preHome(this.config.home);
    if (this.config.preGo) this.config.preGo(this.config.home);
    this.stack = [];
    this.current = this.config.home;
  }

  go = (item: any) => {
    if (this.config.preGo) this.config.preGo(item);
    this.current = this._move(item);
  }

  private _move = (item: any) => {
    let foundItem = -1;
    for (let i = this.stack.length - 1; i >= 0; i--) {
      if (this.stack[i] === item) {
        foundItem = i;
        break;
      }
    }
    if (foundItem >= 0) {
      this.stack.splice(0, foundItem);
    } else {
      this.stack.unshift(item);
    }
    return item;
  }
}

@Component({
  selector: 'xe-nav',
  templateUrl: './xe-nav.component.html',
  styleUrls: ['./xe-nav.component.scss']
})
export class XeNavComponent implements OnInit {

  @Input() screen: XeScreen;

  constructor() {
  }

  ngOnInit(): void {
  }

}
