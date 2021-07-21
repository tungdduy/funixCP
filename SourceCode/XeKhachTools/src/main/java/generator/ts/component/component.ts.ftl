import {Component, QueryList, ViewChildren} from '@angular/core';
import {XeFormComponent} from "${root.pathToAbstract}abstract/xe-form.abstract";
import {XeInputComponent} from "${root.pathToFramework}framework/components/xe-input/xe-input.component";

@Component({
  selector: 'xe-${root.url}',
  styles: [],
  templateUrl: '${root.url}.component.html',
})
export class ${root.componentName} extends XeFormComponent {
  @ViewChildren(XeInputComponent) formControls: QueryList<XeInputComponent>;
  getFormControls = () => this.formControls;

  getObservable(model: any) {

  }

  onSubmitSuccess(response: any) {

  }
}
