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

@Pipe({name: 'XeDate'})
export class XeDatePipe extends XePipe implements PipeTransform {
  private static _instance: XeDatePipe;
  static get instance(): XeDatePipe {
    if (!XeDatePipe._instance) {
      XeDatePipe._instance = new XeDatePipe();
    }
    return XeDatePipe._instance;
  }
  private _datePipe: DatePipe = new DatePipe("en-US");

  transform = (value: any): any => value;
  toReadableString = (value: Date): string => this._datePipe.transform(value, "dd/MM/yyyy");
  toSubmitFormat = (date: any): string => {
    return  this._datePipe.transform(date, "dd/MM/yyyy");
  }
  toAppFormat = (inputDate: any): Date => typeof inputDate === 'string' ? new Date(inputDate) : inputDate;

  areEquals = (date1: Date, date2: Date): boolean => {
    return date1.getDate() === date2.getDate()
    && date1.getMonth() === date2.getMonth()
    && date1.getFullYear() === date2.getFullYear();
  }

}
