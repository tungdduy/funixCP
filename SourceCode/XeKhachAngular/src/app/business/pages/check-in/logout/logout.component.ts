import {Component, OnInit} from '@angular/core';
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {Url} from "../../../../framework/url/url.declare";

@Component({
  selector: 'xe-logout',
  template: '',
  styles: []
})
export class LogoutComponent implements OnInit {

  constructor() {}

  ngOnInit(): void {
    AuthUtil.instance.logout(Url.app.CHECK_IN.LOGIN);
  }

}
