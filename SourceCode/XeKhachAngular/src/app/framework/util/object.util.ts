import {XeTableData} from "../model/XeTableData";

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
  static eraserAndDeepCopyForRestore(source: {}, result: {}) {
    if (!source) return undefined;
    Object.keys(result).forEach(key => {
      if (ObjectUtil.isObject(result[key])) {
        delete result[key];
      }
    });

    Object.keys(source).forEach(key => {
      if (Array.isArray(source[key])) {
        result[key] = [];
        source[key].forEach((element, idx) => {
          if (Object.keys(element).length === 0) {
            result[key][idx] = element;
          } else if (Array.isArray(element)) {
            result[key][idx] = [];
            this.eraserAndDeepCopyForRestore(element, result[key][idx]);
          } else if (ObjectUtil.isObject(element))  {
            result[key][idx] = {};
            this.eraserAndDeepCopyForRestore(element, result[key][idx]);
          } else {
            result[key][idx] = element;
          }
        });
      } else if (ObjectUtil.isObject(source[key])) {
        result[key] = {};
        this.eraserAndDeepCopyForRestore(source[key], result[key]);
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

  static assignEntity(option: {}, entity) {
    if (!option) return entity;
    Object.keys(option).forEach(key => {
      if (['function', 'string', 'boolean'].includes(typeof option[key])
      || ['xeScreen', 'parent', 'template'].includes(key)) {
        entity[key] = option[key];
      } else if (option[key] === undefined) {
        delete entity[key];
      } else if (ObjectUtil.isObject(option[key])) {
        if (entity === undefined) entity = {};
        if (entity[key] === undefined) Array.isArray(option[key]) ? entity[key] = [] : entity[key] = {};
        ObjectUtil.assignEntity(option[key], entity[key]);
      } else {
        entity[key] = option[key];
      }
    });
    if (ObjectUtil.isObject(entity)) {
      Object.keys(entity).forEach(key => {
        if (Array.isArray(entity[key])) {
          entity[key] = entity[key].filter(e => e !== undefined);
        }
      });
    }
    return entity;
  }


}
