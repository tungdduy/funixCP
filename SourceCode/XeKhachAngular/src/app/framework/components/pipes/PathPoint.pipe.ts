import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {PathPoint} from "../../../business/entities/PathPoint";

@Pipe({name: 'PathPoint'})
export class PathPointPipe extends XePipe implements PipeTransform {
  private static _instance: PathPointPipe;
  static get instance(): PathPointPipe {
    if (!PathPointPipe._instance) {
      PathPointPipe._instance = new PathPointPipe();
    }
    return PathPointPipe._instance;
  }
  toSubmitFormat = (pathPoint: PathPoint) => !pathPoint ? 0 : pathPoint.pathPointId;
  toReadableString = (pathPoint: PathPoint) => {
    return pathPoint ? `${pathPoint.pointName}<br/>${pathPoint.pointDesc}` : '';
  }
  toHtmlString = (pathPoint: PathPoint) => {
    if (!pathPoint) return '';
    return `
    <h6 class="text-danger">${pathPoint.pointName}</h6>
    ${pathPoint.pointDesc ? '<div class="text-secondary">' + pathPoint.pointDesc + '</div>' : ''}
    <div class="text-primary">${pathPoint.location?.locationName || ''}</div>
    <div class="text-secondary">${pathPoint.location?.parent?.locationName || ''}</div>
    <div class="text-secondary">${pathPoint.location?.parent?.parent?.locationName || ''}</div>
    `;
  }
}
