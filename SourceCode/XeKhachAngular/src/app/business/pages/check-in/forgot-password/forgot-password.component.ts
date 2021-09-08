import {Component} from '@angular/core';
import {AuthService} from "../../../../framework/auth/auth.service";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {Url} from "../../../../framework/url/url.declare";
import {XeScreen} from "../../../../framework/components/xe-nav/xe-nav.component";

@Component({
  selector: 'xe-forgot-password',
  styles: [],
  templateUrl: 'forgot-password.component.html',
})
export class ForgotPasswordComponent extends FormAbstract {

  constructor(private authService: AuthService) { super(); }

  screens = {
    start: 'start',
    secret: 'secret',
    newPassword: 'newPassword',
    success: 'success'
  };
  screen = new XeScreen({home: this.screens.start});
  gotoLogin() {
    Url.app.CHECK_IN.LOGIN.go();
  }

  handlers = [
    {
      name: "start",
      processor: (data) => this.authService.forgotPassword(data),
      success: {call: () => this.screen.go(this.screens.secret)}
    },
    {
      name: "secret",
      processor: (data) => this.authService.forgotPasswordSecretKey(data),
      success: {call: () => this.screen.go(this.screens.newPassword)}
    },
    {
      name: "newPassword",
      processor: (data) => this.authService.changePassword(data),
      success: {call: () => this.screen.go(this.screens.success)}
    }
  ];
  email: string;
  secretKey: string;

}


