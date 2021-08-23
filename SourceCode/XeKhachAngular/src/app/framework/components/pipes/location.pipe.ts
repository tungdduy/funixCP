import {Pipe, PipeTransform} from '@angular/core';
import {XePipe} from "./XePipe";
import {Location} from "../../../business/entities/Location";

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

  toReadableString = (location: Location): string => {
    return `
    ${location?.locationName || ''}, ${location?.parent?.locationName || ''}, ${location?.parent?.parent?.locationName || ''}
    `;
  }
  toHtmlString = (location: Location) => {
    return `
    <div class="text-primary">${location?.locationName || ''}</div>
    <div class="text-secondary">${location?.parent?.locationName || ''}</div>
    <div class="text-secondary">${location?.parent?.parent?.locationName || ''}</div>
    `;
  }

  toSubmitFormat = (location: Location) => {
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
}
