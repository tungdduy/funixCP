import {XeLocation} from "./xe-location";

export class BussPoint {
  locationId: number;
  location: XeLocation;
  busPointDesc: string;

  constructor() {
    this.location = new XeLocation();
  }
}
