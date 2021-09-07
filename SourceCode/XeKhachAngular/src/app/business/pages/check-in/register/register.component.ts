import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../../framework/auth/auth.service";
import {AppMessages} from "../../../i18n";
import {Url} from "../../../../framework/url/url.declare";
import {AuthUtil} from "../../../../framework/auth/auth.util";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";


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
    if (AuthUtil.instance.isUserLoggedIn) {
      Url.DEFAULT_URL_AFTER_LOGIN().go();
    }
  }

  screens = {
    register: "register",
    success: "success"
  };
  screen = new XeScreen({home: this.screens.register});

  successMessage;
  handlers = [{
    name: "register",
    processor: (data) => this.authService.register(data),
    success: {
      call: (response) => {
        this.successMessage = AppMessages.REGISTER_ACCOUNT_SUCCESS(response.email);
        this.screen.go(this.screens.success);
      }
    }
  }];

  gotoLogin() {
    Url.app.CHECK_IN.LOGIN.go();
  }
}


