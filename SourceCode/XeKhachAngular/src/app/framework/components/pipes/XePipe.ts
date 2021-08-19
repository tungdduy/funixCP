import {Directive} from "@angular/core";

@Directive()
export abstract class XePipe {
  abstract transform(...value);
  toReadableString = (value) => value;
  toAppFormat = (value) => value;
  toSubmitFormat = (value) => value;
  toRawInputString = (fromReadable) => fromReadable;

  areEquals = (e1, e2): boolean => e1 === e2;
}
