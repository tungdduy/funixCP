import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {BussSchedulePoint} from "../../../business/entities/BussSchedulePoint";
import {EntityUtil} from "../../util/EntityUtil";
import {ObjectUtil} from "../../util/object.util";
import {PathPointPipe} from "./PathPoint.pipe";
import {LocationPipe} from "./location.pipe";

@Pipe({name: 'BussSchedulePoint'})
export class BussSchedulePointPipe extends XePipe implements PipeTransform {
  private static _instance: BussSchedulePointPipe;
  static get instance(): BussSchedulePointPipe {
    if (!this._instance) {
      this._instance = new BussSchedulePointPipe();
    }
    return this._instance;
  }

  singleToHtml = (point: BussSchedulePoint) => {
    if (ObjectUtil.isNumberGreaterThanZero(point.pathPoint)) {
      const pathPointCache = EntityUtil.getFromCache('PathPoint', point.pathPoint);
      if (pathPointCache) return PathPointPipe.instance.singleToHtml(pathPointCache) + LocationPipe.instance.singleToHtml(pathPointCache.location);
    }
    return point.searchText;
  }

  singleToInline = (point: BussSchedulePoint, options?) => {
    return point.searchText;
  }

  singleToSubmitFormat = (point: BussSchedulePoint, options?) => {
    return point ? point.bussSchedulePointId : 0;
  }
}
