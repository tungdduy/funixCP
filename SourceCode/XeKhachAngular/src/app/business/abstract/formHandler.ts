import {XeFormComponent} from "../../framework/components/xe-form/xe-form.component";

export declare interface FormHandler {
  name: string;
  processor: any;
  success?: {call: (form: XeFormComponent) => void};
  callbackOnSuccess?: {call: (form: XeFormComponent) => void};
  reset?: {call: () => void};
}
