import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../framework/auth/auth.service";
import {AppMessages} from "../../../i18n";
import {Url} from "../../../../framework/url/url.declare";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {FormAbstract} from "../../../abstract/form.abstract";


@Component({
  selector: 'xe-register',
  styles: [],
  templateUrl: './register.component.html'
})
export class RegisterComponent extends FormAbstract implements OnInit {

  constructor(private authService: AuthService) {
    super();
  }

  ngOnInit(): void {
    if (AuthUtil.isUserLoggedIn()) {
      Url.DEFAULT_URL_AFTER_LOGIN().go();
    }
  }

  successMessage;
  handlers = () => ({
    processor: (data) => this.authService.register(data),
    success: (response) => {
      this.successMessage = AppMessages.REGISTER_ACCOUNT_SUCCESS(response.email);
      this.showForm('success');
    }
  })

  gotoLogin() {
    Url.app.CHECK_IN.LOGIN.go();
  }
}


