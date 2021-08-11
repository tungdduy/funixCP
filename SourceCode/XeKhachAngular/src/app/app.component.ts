import {Component, OnInit} from '@angular/core';
import {UrlBuilder} from "./framework/url/url.builder";
import {XeRouter} from "./business/service/xe-router";
import {Notifier} from "./framework/notify/notify.service";
import {CommonUpdateService} from "./business/service/common-update.service";

@Component({
  selector: 'ngx-app',
  template: `
    <router-outlet></router-outlet>
  `,
})
export class AppComponent implements OnInit {
  constructor(private router: XeRouter, private notifier: Notifier, private commonUpdateService: CommonUpdateService) {}

  ngOnInit(): void {
    UrlBuilder.start();
  }
}
