import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../framework/auth/auth.service";
import {Url} from "../../../../framework/url/url.declare";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {FormAbstract} from "../../../abstract/form.abstract";

@Component({
  selector: 'xe-login',
  styles: [],
  templateUrl: 'login.component.html'
})
export class LoginComponent extends FormAbstract implements OnInit {

  constructor(private authService: AuthService) {
    super();
  }

  ngOnInit(): void {
    if (AuthUtil.isUserLoggedIn()) Url.DEFAULT_URL_AFTER_LOGIN().go();
  }

  handlers = () => ({
      processor: (data) => this.authService.login(data),
      success: (response) => {
        AuthUtil.saveResponse(response);
        Url.DEFAULT_URL_AFTER_LOGIN().go();
      }
  })
}
