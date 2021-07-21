import {Component, QueryList, ViewChildren} from '@angular/core';
import {XeFormComponent} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";

@Component({
  selector: 'xe-company-manager',
  styles: [],
  templateUrl: 'company-manager.component.html',
})
export class CompanyManagerComponent extends XeFormComponent {
  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
  getFormControls = () => this.formControls;

  getObservable(model: any) {

  }

  onSubmitSuccess(response: any) {

  }
}
