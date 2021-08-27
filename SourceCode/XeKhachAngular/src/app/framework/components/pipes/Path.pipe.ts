import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {Path} from "../../../business/entities/Path";
import {ObjectUtil} from "../../util/object.util";
import {EntityUtil} from "../../util/EntityUtil";

@Pipe({name: 'Path'})
export class PathPipe extends XePipe implements PipeTransform {
  private static _instance: PathPipe;
  static get instance(): PathPipe {
    if (!PathPipe._instance) {
      PathPipe._instance = new PathPipe();
    }
    return PathPipe._instance;
  }

  singleToHtml = (path: Path) => {
    if (ObjectUtil.isNumberGreaterThanZero(path)) path = EntityUtil.getFromCache("Path", path);
    if (!path) return '';
    return `
    <h6 class="text-danger">${path.pathName || ''}</h6>
    ${path.pathDesc ? '<div class="text-secondary">' + path.pathDesc || '' + '</div>' : ''}
    `;
  }

  singleToInline = (path, options?) => {
    console.log(path);
    return path ? `${path.pathName || ''}<br/>${path.pathDesc || ''}` : '';
  }

  singleToSubmitFormat = (path: Path, options?) => {
    console.log(path);
    return !path ? 0 : path.pathId;
  }
}
