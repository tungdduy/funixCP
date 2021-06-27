import {Component, ViewChildren} from '@angular/core';
import {XeForm} from "../../_core/abstract/xe-form.abstract";
import {XeInputComponent} from "../../@theme/components/xe-input/xe-input.component";
import {XeNotifierService} from "../../_core/service/xe-notifier.service";

@Component({
  selector: 'xe-forgot-password',
  styles: [],
  templateUrl: 'forgot-password.component.html'
})
export class ForgotPasswordComponent extends XeForm {

  @ViewChildren(XeInputComponent) formControls;
  getFormControls = () => this.formControls;

  constructor(private notifier: XeNotifierService) {
    super();
  }

  doSubmitAfterBasicValidate(model: any): void {
    this.notifier.success("valid to submit");
  }
}
