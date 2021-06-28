export const StringUtil = {
  isBlank(value: string | null | undefined) {
    return value === null || value === undefined || value.toString().trim().length === 0;
  },

  isNotBlank(value: string | null | undefined) {
    return !this.isBlank(value);
  },

  equalAndNotBlank(value1: any, value2: any) {
    return this.isNotBlank(value1) && value1 === value2;
  },

  blankOrNotEqual(value1: any, value2: any) {
    return !this.equalAndNotBlank(value1, value2);
  }
};

