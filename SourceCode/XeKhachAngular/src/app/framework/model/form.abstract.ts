import {AfterViewInit, Directive, QueryList, ViewChildren} from "@angular/core";
import {XeFormComponent} from "../components/xe-form/xe-form.component";
import {FormHandler} from "./FormHandler";
import {XeSubscriber} from "./XeSubscriber";

@Directive()
export abstract class FormAbstract extends XeSubscriber implements AfterViewInit {

  @ViewChildren(XeFormComponent) private ___forms: QueryList<XeFormComponent>;

  handlers: FormHandler[];

  ngAfterViewInit(): void {
    this.assignCtrlForForms();
  }

  assignCtrlForForms() {
    this.___forms.forEach(form => {
      form.ctrl = this;
    });
  }

  private getGroupCode = (formCode: string) => {
    if (!formCode) return "";
    const groups = formCode.split(".");
    if (groups.length > 1) {
      return groups.join(".");
    }
    return "";
  }

  showForm(formCode: string) {
    const groupCode = this.getGroupCode(formCode);
    setTimeout(() => {
      this.___forms.forEach(form => {
        if (form.name.startsWith(groupCode)) {
          form.show = form.name === formCode;
        }
      });
    }, 0);
  }

}
