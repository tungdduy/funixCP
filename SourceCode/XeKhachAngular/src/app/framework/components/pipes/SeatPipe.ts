import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {XeLabel} from "../../../business/i18n";

@Pipe({name: 'seatPipe'})
export class SeatPipe extends XePipe implements PipeTransform {
  private static _instance: SeatPipe;
  static get instance(): SeatPipe {
    if (!SeatPipe._instance) {
      SeatPipe._instance = new SeatPipe();
    }
    return SeatPipe._instance;
  }

  singleToHtml = (seatsString) => {
    return !seatsString ? `
      <div class="p-1 rounded d-inline-block alert-secondary border border-secondary d-">${XeLabel.NO_SELECTED_SEAT}</div>
    ` : seatsString.split(",").map(seatNo => parseInt(seatNo, 10)).sort((a, b) => a - b).map(seatNo => `
      <div class="p-1 mb-1 rounded alert-info border border-info text-large text-bold d-inline-block">${seatNo}</div>
    `).join("");
  }

}
