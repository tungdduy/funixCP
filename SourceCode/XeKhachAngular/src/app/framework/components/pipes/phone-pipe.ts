import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'phonePipe'})
export class PhonePipe implements PipeTransform {
  private static _instance: PhonePipe;
  static get instance(): PhonePipe {
    if (!PhonePipe._instance) {
      PhonePipe._instance = new PhonePipe();
    }
    return PhonePipe._instance;
  }

  transform(value: string): string {
    return `${value.substring(0, 3)}.${value.substring(3, 6)}.${value.substring(6)}`;
  }
}
