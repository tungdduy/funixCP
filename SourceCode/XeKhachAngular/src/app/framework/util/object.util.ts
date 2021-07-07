
export class ObjectUtil {
  static isString(obj: any) {
    return typeof obj === 'string';
  }
  static isObject(obj: any) {
    return typeof obj === 'object';
  }
  static isFunction(obj: any) {
    return typeof obj === 'function';
  }
}
