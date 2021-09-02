import {CharacterUtil} from "./character.util";

const NONE_ALPHA_DIGIT_REGEX = /[^A-Za-z0-9]/g;
const CAPITAL_REGEX = /(?=[A-Z][a-z]+)/g;

export const StringUtil = {
  isBlank(value: string | null | undefined) {
    return value === null
      || value === undefined
      || value === 'undefined'
      || value.toString().trim().length === 0
      || value.toString().trim() === "null";
  },
  equalsIgnoreCase(string1, string2) {
    return string1 && string2 && string1.toLowerCase() === string2.toLowerCase();
  },

  isNotBlank(value: string | null | undefined) {
    return !this.isBlank(value);
  },

  equalAndNotBlank(value1: any, value2: any) {
    return this.isNotBlank(value1) && value1 === value2;
  },

  blankOrNotEqual(value1: any, value2: any) {
    return !this.equalAndNotBlank(value1, value2);
  },
  urlToCapitalizeEachWord: (str: string) =>
    str.split("-")
      .map(s => `${s.slice(0, 1).toUpperCase()}${s.substring(1)}`)
      .join("")
  , toUPPER_UNDERLINE: (str: string) => {
    if (typeof str !== 'string') return "";
    return str.split(NONE_ALPHA_DIGIT_REGEX)
      .join("_")
      .split(CAPITAL_REGEX)
      .join("_").toUpperCase();
  },
  lowercaseFirstLetter: (str: string) => str.charAt(0).toLowerCase() + str.slice(1),
  upperFirstLetter: (str: string) => str.charAt(0).toUpperCase() + str.slice(1),
  classNameToIdName(className) {
    return this.lowercaseFirstLetter(className) + "Id";
  },
  toSearchString(value: string) {
    return CharacterUtil.removeAccents(String(value).toLowerCase()).trim().replaceAll(/[^a-z0-9]+/g, "");
  }
};

