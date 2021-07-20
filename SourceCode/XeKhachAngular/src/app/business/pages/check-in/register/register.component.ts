import {Component, OnInit, QueryList, ViewChildren} from '@angular/core';
import {Notifier} from "../../../../framework/notify/notify.service";
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {XeRouter} from "../../../service/xe-router";
import {AuthService} from "../../../../framework/auth/auth.service";
import {AppMessages} from "../../../i18n";
import {Url} from "../../../../framework/url/url.declare";


@Component({
  selector: 'xe-register',
  styles: [],
  templateUrl: './register.component.html'
})
export class RegisterComponent extends XeForm implements OnInit {

  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
  getFormControls = () => this.formControls;

  constructor(private authService: AuthService) {
    super();
  }

  ngOnInit(): void {
    if (AuthService.isUserLoggedIn()) {
      XeRouter.navigate(Url.DEFAULT_URL_AFTER_LOGIN());
    }
  }

  getObservable(model: any) {
    return this.authService.register(model);
  }

  onSubmitSuccess(response: any) {
    Notifier.success(AppMessages.REGISTER_ACCOUNT_SUCCESS(response.email));
    XeRouter.navigate(Url.app.CHECK_IN.LOGIN);
  }

}


