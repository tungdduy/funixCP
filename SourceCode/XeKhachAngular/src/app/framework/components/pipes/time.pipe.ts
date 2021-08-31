import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {DatePipe} from "@angular/common";
import {of} from "rxjs";
import {AutoInputModel} from "../../model/AutoInputModel";
import {XeLabel} from "../../../business/i18n";

@Pipe({name: 'xeTimePipe'})
export class XeTimePipe extends XePipe implements PipeTransform {
  private _timePipe: DatePipe = new DatePipe("en-US");

  private static _instance: XeTimePipe;

  static get instance(): XeTimePipe {
    if (!XeTimePipe._instance) {
      XeTimePipe._instance = new XeTimePipe();
    }
    return XeTimePipe._instance;
  }

  static getHourMinuteBlocks(hours: AutoInputModel[], hFormatted: string | number) {
    hours.push({appValue: this.instance.inputStringToAppValue(`${hFormatted}:00`), inlineString: `${hFormatted}:00`});
    hours.push({appValue: this.instance.inputStringToAppValue(`${hFormatted}:15`), inlineString: `${hFormatted}:15`});
    hours.push({appValue: this.instance.inputStringToAppValue(`${hFormatted}:30`), inlineString: `${hFormatted}:30`});
    hours.push({appValue: this.instance.inputStringToAppValue(`${hFormatted}:45`), inlineString: `${hFormatted}:45`});
  }

  static autoInputObservable = (time: string) => {
    const hours: AutoInputModel[] = [];
    for (let i = 0; i < 24; i++) {
      const h = i < 16 ? (i + 7) : i - 16;
      const hFormatted = h < 10 ? "0" + h : h;
      XeTimePipe.getHourMinuteBlocks(hours, hFormatted);
    }
    return of(hours);
  }

  areEquals = (time1: Date, time2: Date): boolean => {
    if ((!time1 && time2) || (time1 && !time2)) return false;
    if (!time1 && !time2) return true;
    const convertedTime1 = (typeof time1 === 'string') ? new Date(time1) : time1;
    const convertedTime2 = (typeof time2 === 'string') ? new Date(time2) : time2;
    return convertedTime1.getHours() === convertedTime2.getHours() && convertedTime1.getMinutes() === convertedTime2.getMinutes();
  }

  singleValidate = (time) => time !== undefined && time !== null ? undefined : XeLabel.INVALID_INPUT;

  inputStringToAppValue = (time: any) => {
    const timer = String(time).trim().split(":");
    const hour = parseInt(timer[0], 10);
    const minute = parseInt(timer[1], 10);
    return new Date(0, 0, 0, hour, minute);
  }

  singleToAppValue = (inputTime: any): Date => typeof inputTime === 'string' ? new Date(inputTime) : inputTime;

  singleToInline = (time) => {
    return !time ? '' : this._timePipe.transform(time, "HH:mm");
  }

  singleToSubmitFormat = (time: Date): string => this._timePipe.transform(time, "HH:mm");

  singleToAutoInputModel(value, options?): AutoInputModel {
    return value;
  }
}
