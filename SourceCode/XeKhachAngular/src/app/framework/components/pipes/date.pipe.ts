import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {DatePipe} from "@angular/common";
import {XeLabel} from "../../../business/i18n";

export const DATE_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'DD-MM-YYYY',
    monthYearLabel: 'YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'YYYY',
  },
};

@Pipe({name: 'xeDatePipe'})
export class XeDatePipe extends XePipe implements PipeTransform {
  private _datePipe: DatePipe = new DatePipe("en-US");

  private static _instance: XeDatePipe;

  static get instance(): XeDatePipe {
    if (!this._instance) {
      this._instance = new XeDatePipe();
    }
    return this._instance;
  }

  singleToInline = (value: any, options?: any) => {
    if (!value) return '';
    let dateFormat = "dd-MM-yyyy";
    if (typeof value === 'string'
      && (value.length === "dd-MM-yyyy".length
        || value.length === "MM-dd-yyyy HH:mm".length)) {
      dateFormat = "MM-dd-yyyy";
      if (options && options.fullDateTime) {
        dateFormat = "MM-dd-yyyy HH:mm";
      }
      value = value.substring(3, 5) + "-" + value.substring(0, 2) + value.substring(5);
      return this._datePipe.transform(value, dateFormat);
    }
    return this._datePipe.transform(value, dateFormat);
  }

  singleToAppValue = (value: Date, options?: any) => value;

  singleToSubmitFormat = (value: Date, options?: any) => this._datePipe.transform(value, "dd-MM-yyyy");

  singleToFullDateTime = (value: Date) => this._datePipe.transform(value, "dd-MM-yyyy HH:mm");

  areEquals = (date1: Date, date2: Date): boolean => {
    if ((!date1 && date2) || (date1 && !date2)) return false;
    if (!date1 && !date2) return true;
    if (typeof date1 === 'string') date1 = new Date(date1);
    if (typeof date2 === 'string') date2 = new Date(date1);
    return date1.getDate() === date2.getDate()
      && date1.getMonth() === date2.getMonth()
      && date1.getFullYear() === date2.getFullYear();
  }

  singleValidate = (time) => time !== undefined && time !== null ? undefined : XeLabel.INVALID_INPUT;

}

