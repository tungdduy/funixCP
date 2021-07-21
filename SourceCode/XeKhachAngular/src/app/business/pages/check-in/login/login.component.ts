import {Component, OnInit, ViewChildren} from '@angular/core';
import {XeFormComponent} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {AuthService} from "../../../../framework/auth/auth.service";
import {XeRouter} from "../../../service/xe-router";
import {Url} from "../../../../framework/url/url.declare";
import {AuthUtil} from "../../../../framework/auth/auth.util";

@Component({
  selector: 'xe-login',
  styles: [],
  templateUrl: 'login.component.html',
})
export class LoginComponent extends XeFormComponent implements OnInit {

  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;

  getObservable(model: any) {
    return this.authService.login(model);
  }
  onSubmitSuccess(response: any) {
    AuthUtil.saveResponse(response);
    Url.DEFAULT_URL_AFTER_LOGIN().go();
  }

  ngOnInit(): void {
    if (AuthUtil.isUserLoggedIn()) {
      Url.DEFAULT_URL_AFTER_LOGIN().go();
    }
  }

  constructor(private authService: AuthService) {
    super();
  }

}
