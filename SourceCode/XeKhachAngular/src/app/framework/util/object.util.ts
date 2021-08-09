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
  static deepCopyForEntityBackUpOnly(source: {}, result: {}) {
    if (!source) return undefined;
    Object.keys(source).forEach(key => {
      if (ObjectUtil.isObject(source[key])) {
        result[key] = {};
        this.deepCopyForEntityBackUpOnly(source[key], result[key]);
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

  static assignEntityTable(option: {}, table: XeTableData) {
    if (!option) return table;
    Object.keys(option).forEach(key => {
      if (key === 'idFields') {
        const oldIdsFunction = table[key];
        const optFunc = option[key];
        table[key] = () => {
          const oldIds = oldIdsFunction();
          const optIds = optFunc();
          optIds.forEach(optId => {
            for (let i = 0; i < oldIds.length; i++) {
              if (oldIds[i]['name'] === optId['name']) {
                oldIds[i] = optId;
              }
            }
          });
          return oldIds;
        };
      } else if (ObjectUtil.isObject(option[key])) {
        if (table[key] === undefined) {
          table[key] = option[key];
        } else {
          ObjectUtil.assignEntityTable(option[key], table[key]);
        }
      } else {
        table[key] = option[key];
      }
    });
    return table;
  }


}
