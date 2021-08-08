
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
  static deepCopy(source: {}, result: {}) {
    if (!source) return undefined;
    Object.keys(source).forEach(key => {
      if (ObjectUtil.isObject(source[key])) {
        result[key] = {};
        this.deepCopy(source[key], result[key]);
      } else {
        result[key] = source[key];
      }
    });
    return result;
  }
}
