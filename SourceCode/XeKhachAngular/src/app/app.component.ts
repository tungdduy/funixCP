/**
 * @license
 * Copyright Akveo. All Rights Reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import {Component, OnInit} from '@angular/core';
import {AnalyticsService, SeoService} from './@core/utils';

import {ApiUrlBuilder} from "./framework/url/api.url.builder";
import {UrlConfig} from "./framework/url/url.config";
import {Url} from "./framework/url/url.declare";

@Component({
  selector: 'ngx-app',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnInit {

  constructor(private analytics: AnalyticsService, private seoService: SeoService) {
  }


  ngOnInit(): void {
    this.analytics.trackPageViews();
    // this.seoService.trackCanonicalChanges();
    UrlConfig.buildUrlConfig(UrlConfig.root(Url.APP_HOST), Url.app);
    UrlConfig.buildUrlConfig(UrlConfig.root(Url.API_HOST), Url.api);
    ApiUrlBuilder.start();
  }
}
