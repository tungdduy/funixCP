import {Component, QueryList, ViewChildren} from '@angular/core';
import {XeForm} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";

@Component({
  selector: 'xe-my-account',
  styles: [],
  templateUrl: 'my-account.component.html',
})
export class MyAccountComponent extends XeForm {

  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
  getFormControls = () => this.formControls;

  getObservable(model: any) {
  }

  onSubmitSuccess(response: any) {
  }

}
