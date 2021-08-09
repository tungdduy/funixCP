import {Component, Input, OnInit} from '@angular/core';

export class XeScreen {
  homeTitle: () => string;
  constructor(private _home: string,
              private _homeIcon: string,
              private _homeTitle: undefined | (() => string)) {
    this.homeTitle = _homeTitle;
  }

  current = this._home;

  moveToItem(item: any) {
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

  _stack = [];
  get stack() {
    if (!this._stack) {
      this._stack = [];
    }
    return this._stack;
  }

  get homeIcon() {
    return this._homeIcon;
  }

  get home() {
    return this._home;
  }

  back = () => {
    if (this.stack.length === 0) {
      this.goHome();
    }
    this.stack.shift();
    let result = this.home;
    if (this.stack.length > 0) {
      result = this.stack[0];
    }
    this.current = result;
  }
  goHome() {
    this.current = this.home;
  }
  go(item: any) {
    this.current = this.moveToItem(item);
  }
}

@Component({
  selector: 'xe-nav',
  templateUrl: './xe-nav.component.html',
  styleUrls: ['./xe-nav.component.scss']
})
export class XeNavComponent implements OnInit {

  @Input() screen: XeScreen;

  constructor() { }

  ngOnInit(): void {
  }

}
