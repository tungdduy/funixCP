import {Component, OnInit} from '@angular/core';
import {UrlBuilder} from "./framework/url/url.builder";

@Component({
  selector: 'ngx-app',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
    UrlBuilder.start();
  }
}
