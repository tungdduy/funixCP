import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {XeLbl} from "../../../business/i18n";

@Pipe({name: 'tripStatusPipe'})
export class TripStatusPipe extends XePipe implements PipeTransform {
  private static _instance: TripStatusPipe;
  static get instance(): TripStatusPipe {
    if (!TripStatusPipe._instance) {
      TripStatusPipe._instance = new TripStatusPipe();
    }
    return TripStatusPipe._instance;
  }

  static statusClasses = {
    CONFIRMED: 'success',
    DELETED: 'secondary',
    PENDING: 'warning'
  };
  singleToHtml = (tripStatus) => {
    const clazz = TripStatusPipe.statusClasses[tripStatus];
    return `
      <div class="p-2 cursor-pointer text-center border border-${clazz} rounded alert-${clazz}">
          ${XeLbl('TripStatus.' + tripStatus)}
      </div>
    `;
  }

}
