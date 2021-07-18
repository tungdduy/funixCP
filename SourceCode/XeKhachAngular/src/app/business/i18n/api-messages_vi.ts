export const ApiMessages_vi = {
  ACCESS_DENIED: "asdasd",
  ASSIGN_1_TIME_ONLY: "",
  DO_NOT_HAVE_PERMISSION: "asdasdas",
  EMAIL_EXISTED: "asdasdsadsads",
  NO_USER_FOUND_BY_USERNAME: (param) => `${param.username}`,
  UNDEFINED_ERROR: "asdasd",
  USERNAME_EXISTED: (param) => `${param.username}`,
  VALIDATOR_EMAIL_INVALID: "",
  VALIDATOR_NOT_BLANK: (param) => `${param.fieldName}`,
  VALIDATOR_PATTERN_INVALID: (param) => `${param.fieldName}`,
  VALIDATOR_SIZE_INVALID: (param) => `${param.fieldName} không hợp lệ, độ dài cần ${param.min} đến ${param.max} ký tự`,
};
