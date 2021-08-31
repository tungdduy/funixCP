import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {RegexUtil} from "../../util/regex.util";
import {XeLabel} from "../../../business/i18n";

@Pipe({name: 'phonePipe'})
export class PhonePipe extends XePipe implements PipeTransform {
  private static _instance: PhonePipe;
  static get instance(): PhonePipe {
    if (!this._instance) {
      this._instance = new PhonePipe();
    }
    return this._instance;
  }

  singleToAppValue = (value, options?) => this.singleToSubmitFormat(value);

  singleToInline = (value: string, options?) => {
    const stringValue = String(value);
    return !value || stringValue.length < 9 ? value : `${stringValue.substring(0, 3)}.${stringValue.substring(3, 6)}.${stringValue.substring(6)}`;
  }
  singleToSubmitFormat = (value, options?) => !value ? '' : value.replace(/[^0-9]+/g, "");


  singleValidate = (phone: string) => {
    return RegexUtil.isValidPhone(phone) ? undefined : XeLabel.PHONE_INVALID;
  }
}
