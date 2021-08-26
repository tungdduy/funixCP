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

  singleToHtml = (path: Path) => {
    if (!path) return '';
    return `
    <h6 class="text-danger">${path.pathName || ''}</h6>
    ${path.pathDesc ? '<div class="text-secondary">' + path.pathDesc || '' + '</div>' : ''}
    `;
  }

  singleToInline = (path, options?) => path ? `${path.pathName || ''}<br/>${path.pathDesc || ''}` : '';

  singleToSubmitFormat(value: Path, options?) {
    return !value ? 0 : value.pathId;
  }
}
