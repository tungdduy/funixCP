import {Component} from '@angular/core';
import {XeUrl} from "../_core/static/url.declare";
import {XeLabel} from "../_core/static/xe-label";

@Component({
  selector: 'xe-auth',
  styleUrls: ['./check-in.component.scss'],
  templateUrl: './check-in.component.html'
})
export class CheckInComponent {
  shortUrl = XeUrl.short.app.ACCOUNT;
  label = XeLabel;
  constructor() {
  }
}
