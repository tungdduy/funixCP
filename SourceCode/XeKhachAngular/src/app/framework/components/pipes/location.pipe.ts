import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {Location} from "../../../business/entities/Location";
import {AutoInputModel} from "../../model/AutoInputModel";
import {EntityUtil} from "../../util/EntityUtil";
import {ObjectUtil} from "../../util/object.util";

@Pipe({name: 'locationPipe'})
export class LocationPipe extends XePipe implements PipeTransform {
  private static _instance: LocationPipe;
  static get instance(): LocationPipe {
    if (!this._instance) {
      this._instance = new LocationPipe();
    }
    return this._instance;
  }

  singleToAutoInputModel(location: Location): AutoInputModel {
    if (!location) return {appValue: undefined, inlineString: ''};
    return location
      ? {appValue: location, inlineString: location.displayName}
      : {appValue: undefined, inlineString: ''};
  }

  singleToInline = (location) => 'inline' + location?.displayName || '';

  singleToHtml = (location: Location) => {
    if (ObjectUtil.isNumberGreaterThanZero(location)) {
      location = EntityUtil.getFromCache("Location", location);
    }
    if (!location || !location.displayName) return "";
    let htmlText = "";
    location.displayName.split(",").forEach((name, index) => {
      htmlText += `
      <div class="text-${index === 0 ? 'primary' : 'secondary'}">${name}</div>
      `;
    });
    return htmlText;
  }

  singleToSubmitFormat = (location: Location) => {
    if (typeof location === 'number') return location;
    if (typeof location === 'object') {
      if ('locationId' in location) {
        return location.locationId;
      }
    }
    const id = parseInt(location, 10);
    if (id) return id;
    return 0;
  }

  singleToAppValue = (location, options?) => location;
  singleToMultiPart = (location: Location, options) => !location ? [] : location.displayName.split(",");
  singleToShortString = (location: Location) => location?.locationName ? location.locationName : "";
}
