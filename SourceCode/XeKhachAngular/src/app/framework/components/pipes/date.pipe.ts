import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {DatePipe} from "@angular/common";

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
  singleToInline(value: any, options?: any) {
    if (typeof value === 'string') return this._datePipe.transform(value, "dd-MM-yyyy");
    if (value !== null && value !== undefined) return this._datePipe.transform(value, "dd-MM-yyyy");
    return '';
  }
  singleToAppValue = (value: Date, options?: any) => value;
  singleToSubmitFormat = (value: Date, options?: any) => this._datePipe.transform(value, "dd-MM-yyyy");
  private static _instance: XeDatePipe;
  static get instance(): XeDatePipe {
    if (!this._instance) {
      this._instance = new XeDatePipe();
    }
    return this._instance;
  }
  private _datePipe: DatePipe = new DatePipe("en-US");

  areEquals = (date1: Date, date2: Date): boolean => {
    if ((!date1 && date2) || (date1 && !date2)) return false;
    if (!date1 && !date2) return true;
    if (typeof date1 === 'string') date1 = new Date(date1);
    if (typeof date2 === 'string') date2 = new Date(date1);
    return date1.getDate() === date2.getDate()
    && date1.getMonth() === date2.getMonth()
    && date1.getFullYear() === date2.getFullYear();
  }

  validate = (time) => time !== undefined && time !== null;

}

