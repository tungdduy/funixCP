export const ApiMessages_vi = {
  ACCESS_DENIED: "Truy cập bị từ chối",
  DO_NOT_HAVE_PERMISSION: `Bạn không có quyền truy cập trang này!`,
  EMAIL_EXISTED: "Địa chỉ email đã tồn tại!",
  NO_USER_FOUND_BY_USERNAME: (param) => `Tài khoản ${param.username} không tồn tại`,
  UNDEFINED_ERROR: "Có lỗi xảy ra, vui lòng thử lại!",
  USERNAME_EXISTED: "Tên đăng nhập đã tồn tại!",
  USER_NAME_NOT_FOUND: "Tên đăng nhập không tồn tại!",
  VALIDATOR_EMAIL_INVALID: "Địa chỉ email không hợp lệ!",
  VALIDATOR_NOT_BLANK: (param) => `${param.fieldName} không được để trống.`,
  VALIDATOR_PATTERN_INVALID: (param) => `${param.fieldName}`,
  VALIDATOR_SIZE_INVALID: (param) => `${param.fieldName} cần có độ dài từ ${param.min} đến ${param.max} ký tự`,
};
