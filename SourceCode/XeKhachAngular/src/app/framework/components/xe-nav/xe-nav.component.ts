import {Component, Input, OnInit} from '@angular/core';
import {XeLbl} from "../../../business/i18n";

export interface ScreenConfig {
  preGo?: (screen) => any;
  preHome?: (screen) => any;
  home: string;
  homeIcon?: string;
  homeTitle?: () => string;
}

class Stack {
  private readonly stack;
  constructor() {
    this.stack = [];
  }

  push(item) {
    return this.stack.push(item);
  }

  pop() {
    return this.length > 0 ? this.stack.pop() : undefined;
  }

  get peek() {
    return this.length > 0 ? this.stack[this.length - 1] : undefined;
  }

  isContain(item: string) {
    return this.stack.includes(item);
  }

  get length() {
    return this.stack.length;
  }

  get isEmpty() {
    return this.length === 0;
  }

  clear() {
    this.stack.length = 0;
  }

  get reverse() {
    return this.stack.reverse();
  }
}

export class XeScreen {
  config: ScreenConfig;
  current;
  stack = new Stack();

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
    this.stack.pop();
    const prev = this.stack.peek ? this.stack.peek : this.home;
    if (this.config.preGo) this.config.preGo(prev);
    this.current = prev;
  }

  goHome = () => {
    if (this.isHome) return;
    if (this.config.preHome) this.config.preHome(this.config.home);
    if (this.config.preGo) this.config.preGo(this.config.home);
    this.stack.clear();
    this.current = this.config.home;
  }

  go = (item: string) => {
    if (this.config.preGo) this.config.preGo(item);
    this.current = this._move(item);
  }

  private _move = (item: string) => {
    if (this.stack.isContain(item)) {
      while (this.stack.peek() !== item) {
        this.stack.pop();
      }
    } else {
      this.stack.push(item);
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
