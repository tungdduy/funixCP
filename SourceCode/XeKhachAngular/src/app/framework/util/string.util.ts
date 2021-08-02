const NONE_ALPHA_DIGIT_REGEX = /[^A-Za-z0-9]/g;
const CAPITAL_REGEX = /(?=[A-Z][a-z]+)/g;

export const StringUtil = {
  isBlank(value: string | null | undefined) {
    return value === null
      || value === undefined
      || value.toString().trim().length === 0
      || value.toString().trim() === "null";
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
  }
  , lowercaseFirstLetter: (str: string) => str.charAt(0).toLowerCase() + str.slice(1)

};

