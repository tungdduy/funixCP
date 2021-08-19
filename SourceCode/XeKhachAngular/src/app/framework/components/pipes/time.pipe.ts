import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {DatePipe} from "@angular/common";

@Pipe({name: 'XeTime'})
export class XeTimePipe extends XePipe implements PipeTransform {
  private static _instance: XeTimePipe;
  static get instance(): XeTimePipe {
    if (!XeTimePipe._instance) {
      XeTimePipe._instance = new XeTimePipe();
    }
    return XeTimePipe._instance;
  }
  private _timePipe: DatePipe = new DatePipe("en-US");

  transform = (value) => value;

  toReadableString = (time: Date): string => {
    return this._timePipe.transform(time, "HH:mm");
  }
  toSubmitFormat = (time: Date): string => this._timePipe.transform(time, "HH:mm");
  toAppFormat = (inputTime: any): Date => typeof inputTime === 'string' ? new Date(inputTime) : inputTime;



  static getHourMinuteBlocks(hours: any[], hFormatted: string | number) {
    hours.push(`${hFormatted}:00`);
    hours.push(`${hFormatted}:15`);
    hours.push(`${hFormatted}:30`);
    hours.push(`${hFormatted}:45`);
  }

  static allHourOptions() {
    const hours = [];
    for (let i = 0; i < 24; i++) {
      const h = i < 16 ? (i + 7) : i - 16;
      const hFormatted = h < 10 ? "0" + h : h;
      XeTimePipe.getHourMinuteBlocks(hours, hFormatted);
    }
    return hours;
  }

  static filterTime(time: string): string[] {
    if (!time) return [];
    const value = time.trim().toLowerCase();
    const hour = value.length > 2 ? parseInt(value.substring(0, 1), 10) : parseInt(value, 10);
    const hours = [];
    if (hour >= 0 && hour <= 23) {
      const hourFormatted = hour < 10 ? "0" + hour : hour;
      XeTimePipe.getHourMinuteBlocks(hours, hourFormatted);
      return hours;
    }
    return XeTimePipe.allHourOptions();
  }

  areEquals = (time1: Date, time2: Date): boolean => {
    const convertedTime1 = (typeof time1 === 'string') ? new Date(time1) : time1;
    const convertedTime2 = (typeof time2 === 'string') ? new Date(time2) : time2;
    return convertedTime1.getHours() === convertedTime2.getHours() && convertedTime1.getMinutes() === convertedTime2.getMinutes();
  }
}
