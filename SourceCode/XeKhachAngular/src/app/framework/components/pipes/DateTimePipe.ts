import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {DatePipe} from "@angular/common";
import {XeLabel} from "../../../business/i18n";

export const DATE_TIME_FORMATS = {
  parse: {
    dateInput: 'LL',
  },
  display: {
    dateInput: 'DD-MM-YYYY HH:mm',
    monthYearLabel: 'YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'YYYY',
  },
};

@Pipe({name: 'xeDateTimePipe'})
export class XeDateTimePipe extends XePipe implements PipeTransform {
  private _datePipe: DatePipe = new DatePipe("en-US");

  private static _instance: XeDateTimePipe;

  static get instance(): XeDateTimePipe {
    if (!this._instance) {
      this._instance = new XeDateTimePipe();
    }
    return this._instance;
  }

  singleToInline = (value: any, options?: any) => {
    if (!value) return '';
    const dateFormat = "dd-MM-YYYY HH:mm";
    if (typeof value === 'string'
      && (value.length === "dd-MM-yyyy".length
        || value.length === "MM-dd-yyyy HH:mm".length)) {
      value = value.substring(3, 5) + "-" + value.substring(0, 2) + value.substring(5);
    }
    if (typeof value === 'string') return this._datePipe.transform(value, dateFormat);
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

