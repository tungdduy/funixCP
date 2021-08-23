import {Directive} from "@angular/core";

@Directive()
export abstract class XePipe {
  transform(value) {
    return value ? value : '';
  }
  toReadableString = (value) => value;
  toAppFormat = (value) => value;
  toSubmitFormat = (value) => value;
  toRawInputString = (fromReadable) => fromReadable;

  areEquals = (e1, e2): boolean => e1 === e2;

  toHtmlString = (value) => value;

  validate = (value) => {
    if (value !== null && value !== undefined) {
      if (typeof value === 'object' && Object.keys(value).length !== 0) {
        return true;
      }
    }
    return false;
  }
}
