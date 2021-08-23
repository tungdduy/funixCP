import {Pipe, PipeTransform} from "@angular/core";
import {XePipe} from "./XePipe";
import {Path} from "../../../business/entities/Path";

@Pipe({name: 'Path'})
export class PathPipe extends XePipe implements PipeTransform {
  private static _instance: PathPipe;
  static get instance(): PathPipe {
    if (!PathPipe._instance) {
      PathPipe._instance = new PathPipe();
    }
    return PathPipe._instance;
  }

  toSubmitFormat = (path: Path) => !path ? 0 : path.pathId;
  toReadableString = (path: Path) => {
    return path ? `${path.pathName || ''}<br/>${path.pathDesc}` : '';
  }
  toHtmlString = (path: Path) => {
    if (!path) return '';
    return `
    <h6 class="text-danger">${path.pathName || ''}</h6>
    ${path.pathDesc ? '<div class="text-secondary">' + path.pathDesc || '' + '</div>' : ''}
    `;
  }
}
