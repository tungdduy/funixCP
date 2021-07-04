export const ApiMessages_vi = {
  // ********************API_MESSAGE******************** //
  ACCESS_DENIED: "Truy cập bị từ chối",
  USER_NAME_NOT_FOUND: "Tên đăng nhập không tồn tại!",
  VALIDATOR_SIZE_INVALID: (param) => `${param.fieldName} cần có độ dài từ ${param.min} đến ${param.max} ký tự`,
  DO_NOT_HAVE_PERMISSION: `Bạn không có quyền truy cập trang này!`,
  EMAIL_EXISTED: "Địa chỉ email đã tồn tại!",
  USERNAME_EXISTED: "Tên đăng nhập đã tồn tại!",
  UNDEFINED_ERROR: "Có lỗi xảy ra, vui lòng thử lại!",
  VALIDATOR_EMAIL_INVALID: "Địa chỉ email không hợp lệ!",
  VALIDATOR_NOT_BLANK: (param) => `${param.fieldName} không được để trống.`,
  NO_USER_FOUND_BY_USERNAME: (param) => `Tài khoản ${param.username} không tồn tại`
  // ********************API_MESSAGE******************** //
};
