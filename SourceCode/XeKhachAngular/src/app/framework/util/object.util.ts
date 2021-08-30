export class ObjectUtil {
  static isString(obj: any) {
    return typeof obj === 'string';
  }

  static isObject(obj: any) {
    return obj !== null && typeof obj === 'object';
  }

  static isFunction(obj: any) {
    return typeof obj === 'function';
  }

  static isNumberGreaterThanZero(obj: any) {
    return typeof obj === 'number' && obj > 0;
  }

  static eraserAndDeepCopyForRestore(source: {}, result: {}, lvl = 0) {
    if (lvl > 4) return result;
    lvl++;
    if (!source) return undefined;
    Object.keys(result).forEach(key => {
      delete result[key];
    });

    Object.keys(source).forEach(key => {
      if (Array.isArray(source[key])) {
        result[key] = [];
        source[key].forEach((element, idx) => {
          if (Object.keys(element).length === 0) {
            result[key][idx] = element;
          } else if (Array.isArray(element)) {
            result[key][idx] = [];
            this.eraserAndDeepCopyForRestore(element, result[key][idx], lvl);
          } else if (ObjectUtil.isObject(element)) {
            result[key][idx] = {};
            this.eraserAndDeepCopyForRestore(element, result[key][idx], lvl);
          } else {
            result[key][idx] = element;
          }
        });
      } else if (ObjectUtil.isObject(source[key])) {
        result[key] = {};
        this.eraserAndDeepCopyForRestore(source[key], result[key], lvl);
      } else {
        result[key] = source[key];
      }
    });
    return result;
  }

  static recursiveAssignValueOnly(source: {}, result: {}): any {
    if (!source) return result;
    Object.keys(source).forEach(key => {
      if (ObjectUtil.isObject(source[key])) {
        if (result[key] === undefined) {
          result[key] = source[key];
        } else {
          this.recursiveAssignValueOnly(source[key], result[key]);
        }
      } else {
        result[key] = source[key];
      }
    });
    return result;
  }

  public static isPrimitive(value) {
    return Object(value) !== value;
  }

  static trimCircularObject(obj, result = {}, deepLvl = 0) {
    if (!obj) return obj;
    if (deepLvl > 3) return obj;
    deepLvl++;
    Object.keys(obj).forEach(key => {
      if (key === 'user') return;
      if (ObjectUtil.isPrimitive(obj[key])) {
        result[key] = obj[key];
      } else {
        result[key] = this.trimCircularObject(obj[key], result[key], deepLvl);
      }
    });
    return result;
  }

}
