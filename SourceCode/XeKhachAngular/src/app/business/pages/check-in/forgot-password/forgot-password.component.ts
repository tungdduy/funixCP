import {Component} from '@angular/core';
import {AuthService} from "../../../../framework/auth/auth.service";
import {FormAbstract} from "../../../../framework/model/form.abstract";
import {Url} from "../../../../framework/url/url.declare";

@Component({
  selector: 'xe-forgot-password',
  styles: [],
  templateUrl: 'forgot-password.component.html',
})
export class ForgotPasswordComponent extends FormAbstract {

  constructor(private authService: AuthService) { super(); }

  gotoLogin() {
    Url.app.CHECK_IN.LOGIN.go();
  }

  handlers = [
    {
      name: "start",
      processor: (data) => this.authService.forgotPassword(data),
      success: {call: () => this.showForm('secret')}
    },
    {
      name: "secret",
      processor: (data) => this.authService.forgotPasswordSecretKey(data),
      success: {call: () => this.showForm('newPassword')}
    },
    {
      name: "newPassword",
      processor: (data) => this.authService.changePassword(data),
      success: {call: () => this.showForm('success')}
    }
  ];

}


