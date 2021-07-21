import {Component, QueryList, ViewChildren} from '@angular/core';
import {XeFormComponent} from "../../../abstract/xe-form.abstract";
import {XeInputComponent} from "../../../../framework/components/xe-input/xe-input.component";

@Component({
  selector: 'xe-buss-stop',
  styles: [],
  templateUrl: 'buss-stop.component.html',
})
export class BussStopComponent extends XeFormComponent {
  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
  getFormControls = () => this.formControls;

  getObservable(model: any) {

  }

  onSubmitSuccess(response: any) {

  }
}
