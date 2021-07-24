import {Component} from '@angular/core';
import {AuthService} from "../../../../framework/auth/auth.service";
import {FormAbstract} from "../../../abstract/form.abstract";
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

  handlers = () => ({
    start: {
      processor: (data) => this.authService.forgotPassword(data),
      success: (response) => this.showForm('secret')
    },
    secret: {
      processor: (data) => this.authService.forgotPasswordSecretKey(data),
      success: (response) => this.showForm('newPassword')
    },
    newPassword: {
      processor: (data) => this.authService.changePassword(data),
      success: (response) => this.showForm('success')
    }
  })

}


