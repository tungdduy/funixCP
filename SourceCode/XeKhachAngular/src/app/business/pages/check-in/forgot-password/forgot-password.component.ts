import {Component, OnInit, ViewChildren} from '@angular/core';
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {HttpErrorResponse} from '@angular/common/http';
import {AuthService} from "../../../../framework/auth/auth.service";

@Component({
  selector: 'xe-forgot-password',
  styles: [],
  templateUrl: 'forgot-password.component.html',
})
export class ForgotPasswordComponent extends XeForm {
  getObservable(model: any) {
      throw new Error('Method not implemented.');
  }
  onSubmitSuccess(response: any) {
      throw new Error('Method not implemented.');
  }

  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;

  constructor(authService: AuthService) {
    super();
  }
}
