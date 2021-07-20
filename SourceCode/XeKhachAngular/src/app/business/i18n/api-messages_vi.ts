export const ApiMessages_vi = {
  ACCESS_DENIED: "Truy cập bị từ chối",
  ASSIGN_1_TIME_ONLY: "Giá trị field chỉ được gán 1 lần thôi!",
  DO_NOT_HAVE_PERMISSION: "Bạn chưa được cấp quyền truy cập trang này.",
  EMAIL_EXISTED: "Email đã tồn tại!",
  NO_USER_FOUND_BY_USERNAME: (param) => `tài khoản ${param.username} không tồn tại!`,
  SEND_EMAIL_FAILED: "",
  UNDEFINED_ERROR: "Lỗi không xác định!",
  USERNAME_EXISTED: (param) => `tên đăng nhập ${param.username} đã tồn tại `,
  VALIDATOR_EMAIL_INVALID: "Email không hợp lệ",
  VALIDATOR_NOT_BLANK: (param) => `${param.fieldName} không được để trống!`,
  VALIDATOR_PATTERN_INVALID: (param) => `${param.fieldName} không hợp lệ`,
  VALIDATOR_SIZE_INVALID: (param) => `${param.fieldName} không hợp lệ, độ dài cần ${param.min} đến ${param.max} ký tự`,
};
