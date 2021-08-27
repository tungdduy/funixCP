import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {PathPoint} from "../../../business/entities/PathPoint";
import {ObjectUtil} from "../../util/object.util";
import {EntityUtil} from "../../util/EntityUtil";

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
    console.log(pathPoint);
    return pathPoint ? `${pathPoint.pointName}<br/>${pathPoint.pointDesc}` : '';
  }
  singleToHtml = (pathPoint: PathPoint) => {
    if (ObjectUtil.isNumberGreaterThanZero(pathPoint)) pathPoint = EntityUtil.getFromCache("PathPoint", pathPoint);
    if (!pathPoint) return '';
    return `
    <div class="text-danger">${pathPoint.pointName}</div>
    ${pathPoint.pointDesc ? '<div class="text-secondary">' + pathPoint.pointDesc + '</div>' : ''}
    `;
  }

}
