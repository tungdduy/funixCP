import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {PathPoint} from "../../../business/entities/PathPoint";

@Pipe({name: 'pathPointPipe'})
export class PathPointPipe extends XePipe implements PipeTransform {
  private static _instance: PathPointPipe;
  static get instance(): PathPointPipe {
    if (!PathPointPipe._instance) {
      PathPointPipe._instance = new PathPointPipe();
    }
    return PathPointPipe._instance;
  }
  singleToSubmitFormat = (pathPoint: PathPoint) => !pathPoint ? 0 : pathPoint.pathPointId;
  singleToInline = (pathPoint: PathPoint) => {
    return pathPoint ? `${pathPoint.pointName}<br/>${pathPoint.pointDesc}` : '';
  }
  singleToHtml = (pathPoint: PathPoint) => {
    if (!pathPoint) return '';
    return `
    <h6 class="text-danger">${pathPoint.pointName}</h6>
    ${pathPoint.pointDesc ? '<div class="text-secondary">' + pathPoint.pointDesc + '</div>' : ''}
    `;
  }

}
