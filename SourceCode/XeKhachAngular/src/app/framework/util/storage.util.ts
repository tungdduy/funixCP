import {XeRole} from "../../business/constant/xe.role";

export class StorageUtil {

  static getString(key: string): string | null {
    return localStorage.getItem(key);
  }

  static getNumber(key: string): number {
    return +localStorage.getItem((key));
  }

  static getFromJson(key: string): object {
    const jsonString = StorageUtil.getString(key);
    if (jsonString !== null) {
      return JSON.parse(localStorage.getItem(key));
    }
    return null;
  }

  static setItem(key: string, item: any) {
    if (StorageUtil.isPrimitive(item)) {
      localStorage.setItem(key, String(item));
    } else {
      localStorage.setItem(key, JSON.stringify(item));
    }
  }

  private static isPrimitive(value) {
    return Object(value) !== value;
  }

}
