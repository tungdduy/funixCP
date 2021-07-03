import {Component, ViewChildren} from '@angular/core';
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";
import {XeNotifierService} from "../../../../framework/notify/xe.notifier.service";
import {XeLabel} from "../../../i18n/xe-label";

@Component({
  selector: 'xe-forgot-password',
  styles: [],
  templateUrl: 'forgot-password.component.html'
})
export class ForgotPasswordComponent extends XeForm {

  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;
  label = XeLabel;

  constructor(private notifier: XeNotifierService) {
    super();
  }

  doSubmitAfterBasicValidate(model: any): void {
    this.notifier.success("valid to submit");
  }
}
