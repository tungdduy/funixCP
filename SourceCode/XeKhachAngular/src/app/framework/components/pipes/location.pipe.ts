import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";

@Pipe({name: 'filterLocation'})
export class LocationPipe extends XePipe implements PipeTransform {
  private static _instance: LocationPipe;
  static get instance(): LocationPipe {
    if (!LocationPipe._instance) {
      LocationPipe._instance = new LocationPipe();
    }
    return LocationPipe._instance;
  }

  transform(locations: any) {
    if (!locations) return locations;
    const parentId = [];
    return locations.filter(loc => {
        parentId.push(loc.parent?.locationId);
        parentId.push(loc.parent?.parent?.locationId);
        return !parentId.includes(loc.locationId);
      }
    );
  }

  toReadableString = (value): string => this.transform(value);
  toAppFormat;
  toSubmitFormat;
}
