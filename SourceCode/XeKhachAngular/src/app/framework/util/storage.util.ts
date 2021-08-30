import {ObjectUtil} from "./object.util";

export class StorageUtil {

  static getString(key: string): string | null {
    return localStorage.getItem(key);
  }

  static getNumber(key: string): number {
    return +localStorage.getItem((key));
  }

  static getFromJson(key: string): any {
    const jsonString = StorageUtil.getString(key);
    if (jsonString !== null && this.isJson(jsonString)) {
      return JSON.parse(jsonString);
    }
    return null;
  }

  static setItem(key: string, item: any) {
    if (ObjectUtil.isPrimitive(item)) {
      localStorage.setItem(key, String(item));
    } else {
      const obj = ObjectUtil.trimCircularObject(item);
      localStorage.setItem(key, JSON.stringify(obj));
    }
  }

  static isJson(item) {
    item = typeof item !== "string"
      ? JSON.stringify(item)
      : item;
    try {
      item = JSON.parse(item);
    } catch (e) {
      return false;
    }

    return typeof item === "object" && item !== null;
  }
}
