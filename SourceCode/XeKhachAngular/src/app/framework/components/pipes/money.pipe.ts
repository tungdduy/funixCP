import { Pipe, PipeTransform } from '@angular/core';
import {XePipe} from "./XePipe";

@Pipe({name: 'moneyPipe'})
export class MoneyPipe extends XePipe implements PipeTransform {
  private static _instance: MoneyPipe;
  static get instance(): MoneyPipe {
    if (!MoneyPipe._instance) {
      MoneyPipe._instance = new MoneyPipe();
    }
    return MoneyPipe._instance;
  }

  singleToAppValue = (value, options?) => {
    return !value ? '' : parseInt(String(value).replace(/[^0-9]+/g, ""), 10);
  }

  singleToInline(value, options?) {
    return !value ? "" : String(value).replace(/[^0-9]+/, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",") + "đ";
  }

  singleToSubmitFormat = (value, options?) => this.singleToAppValue(value);

}
