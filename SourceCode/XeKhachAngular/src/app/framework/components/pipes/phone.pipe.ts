import { Pipe, PipeTransform } from '@angular/core';
import {XePipe} from "./XePipe";

@Pipe({name: 'phonePipe'})
export class PhonePipe extends XePipe implements PipeTransform {
  private static _instance: PhonePipe;
  static get instance(): PhonePipe {
    if (!PhonePipe._instance) {
      PhonePipe._instance = new PhonePipe();
    }
    return PhonePipe._instance;
  }

  transform(value: string): string {
    return !value ? '' : `${value.substring(0, 3)}.${value.substring(3, 6)}.${value.substring(6)}`;
  }
  toSubmitFormat = (value: string): string => {
    return this.transform(value);
  }
  toAppFormat = (value) => {
    return value;
  }

  toReadableString = (value): string => this.transform(value);

}