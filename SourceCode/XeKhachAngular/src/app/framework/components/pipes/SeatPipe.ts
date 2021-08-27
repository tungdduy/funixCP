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
      <div class="alert alert-info">${XeLabel['NO_SELECTED_SEAT']}</div>
    ` : seatsString.split(",").map(seatNo => parseInt(seatNo, 10)).sort((a, b) => a - b).map(seatNo => `
      <div class="alert alert-warning border border-warning text-large text-bold d-inline-block">${seatNo}</div>
    `).join("");
  }

}
