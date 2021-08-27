import {XePipe} from "./XePipe";
import {Trip} from "../../../business/entities/Trip";
import {IconWrapComponent} from "../details/icon-wrap/icon-wrap.component";

export class AvailableSeat  extends XePipe {

  singleToHtml = (trip: Trip, options?) => {
    const wrapper = {icon: 'couch', content: trip.totalPreparedAvailableSeats + "/" + trip.totalSeats};
    return `
      <icon-wrap [wrapper]="${wrapper}"></icon-wrap>
    `;
  }

  singleToXeComponent = (trip: Trip) => {
    return new IconWrapComponent();
  }

}
