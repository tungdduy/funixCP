import {Component, OnInit, ViewChildren} from '@angular/core';
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {AuthService} from "../../../../framework/auth/auth.service";
import {XeRouter} from "../../../service/xe-router";
import {Url} from "../../../../framework/url/url.declare";

@Component({
  selector: 'xe-login',
  styles: [],
  templateUrl: 'login.component.html',
})
export class LoginComponent extends XeForm implements OnInit {

  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;

  getObservable(model: any) {
    return this.authService.login(model);
  }
  onSubmitSuccess(response: any) {
    AuthService.saveResponse(response);
    XeRouter.navigate(Url.DEFAULT_URL_AFTER_LOGIN());
  }

  ngOnInit(): void {
    if (AuthService.isUserLoggedIn()) {
      XeRouter.navigate(Url.DEFAULT_URL_AFTER_LOGIN());
    }
  }

  constructor(private authService: AuthService) {
    super();
  }
}
