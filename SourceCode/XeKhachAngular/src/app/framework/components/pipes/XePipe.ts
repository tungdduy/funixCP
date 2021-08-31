import {Directive} from "@angular/core";
import {AutoInputModel} from "../../model/AutoInputModel";
import {StringUtil} from "../../util/string.util";
import {XeLabel} from "../../../business/i18n";

export interface PipeOption {
  inline?: boolean;
  html?: boolean;
  appValue?: boolean;
  submitFormat?: boolean;
  autoInputModel?: boolean;
  multiPart?: boolean;
  instance?: any;
}

@Directive()
export abstract class XePipe {
  singleToShortString;

  transform(values, option: PipeOption = {html: true}, option2?: any) {
    if (!values && !option2) return [];
    const instance = option?.instance || this;
    if (!Array.isArray(values)) {
      if (option.inline) return instance.singleToInline(values, option2);
      if (option.html) return instance.singleToHtml(values, option2);
      if (option.appValue) return instance.singleToAppValue(values, option2);
      if (option.submitFormat) return instance.singleToSubmitFormat(values, option2);
      if (option.autoInputModel) return instance.singleToAutoInputModel(values, option2);
      if (option.multiPart) return instance.singleToMultiPartArray(values, option2);
      return instance.singleToInline(values, option2);
    }

    if (option.inline) return instance.arrayToInline(values, option2);
    if (option.html) return instance.arrayToHtml(values, option2);
    if (option.appValue) return instance.arrayToAppValue(values, option2);
    if (option.submitFormat) return instance.arrayToSubmitFormat(values, option2);
    if (option.autoInputModel) return instance.arrayToAutoInputModel(values, option2);
    if (option.multiPart) return instance.arrayToMultiPartArray(values, option2);
    return instance.arrayToInline(values, option2);
  }

  arrayToInline = (values: [], options2?) => values.map(value => this.singleToInline(value, options2));

  arrayToHtml = (values: [], options2?) => values.map(value => this.singleToHtml(value, options2));

  arrayToAppValue = (values: [], options2?) => values.map(value => this.singleToAppValue(value, options2));

  arrayToSubmitFormat = (values: [], options2?) => values.map(value => this.singleToSubmitFormat(value, options2));

  arrayToAutoInputModel = (values: [], options2?): AutoInputModel[] => values.map(value => this.singleToAutoInputModel(value, options2));

  arrayToMultiPartArray = (values: [], options2?) => values.map(value => this.singleToMultiPart(value, options2));

  singleToHtml = (value, options?) => this.singleToInline(value, options);

  singleToInline = (value, options?) => value;

  singleToAppValue = (value, options?) => value;

  singleToSubmitFormat = (value, options?) => value;

  singleToManualShortInput = (value, options?) => {
    if (StringUtil.isBlank(value)) return "";
    return this.singleToSubmitFormat(value, options);
  }

  singleToAutoInputModel(value, options?): AutoInputModel {
    return null;
  }

  singleToMultiPart = (value, options) => [];

  areEquals = (e1, e2): boolean => e1 === e2;
  singleValidate = (value) => {
    if (value !== null && value !== undefined) {
      return undefined;
    }
    return XeLabel.INVALID_INPUT;
  }

}
