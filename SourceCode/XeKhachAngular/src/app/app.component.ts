import {Component, OnInit} from '@angular/core';
import {UrlBuilder} from "./framework/url/url.builder";
import {XeRouter} from "./business/service/xe-router";
import {Notifier} from "./framework/notify/notify.service";

@Component({
  selector: 'ngx-app',
  template: `
    <router-outlet></router-outlet>
  `,
})
export class AppComponent implements OnInit {
  constructor(private router: XeRouter, private notifier: Notifier) {}

  ngOnInit(): void {
    UrlBuilder.start();
  }
}
