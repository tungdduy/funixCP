export const ApiMessages_vi = {
  ACCESS_DENIED: "Truy cập bị từ chối",
  ASSIGN_1_TIME_ONLY: "Giá trị field chỉ được gán 1 lần thôi!",
  BAD_CREDENTIALS_EXCEPTION: `Thông tin đăng nhập không hợp lệ!`,
  CANNOT_FIND_USER: (param) => `${param.userId}${param.username}${param.email}`,
  CANNOT_REMOVE_BUSS_HAS_EMPLOYEES: (param) => `Không thể xóa Xe vẫn còn nhân viên xe`,
  CANNOT_REMOVE_BUSS_HAS_SCHEDULE: (param) => `Không thể xóa Xe đang có Lịch trình`,
  CANNOT_REMOVE_BUSS_SCHEDULE_HAS_BUSS_SCHEDULE_POINTS: (param) => `Không thể xóa Lịch trình đang có các điểm dừng`,
  CANNOT_REMOVE_BUSS_SCHEDULE_HAS_TRIP: (param) => `Không thể xóa lịch trình đã có chuyến đi`,
  CANNOT_REMOVE_EMPLOYEE_HAS_CONFIRMED_TICKET: (param) => `Không thể xóa nhân viên đã từng xác nhận cho vé`,
  CANNOT_REMOVE_PATH_HAS_BUSS_SCHEDULES: (param) => `Không thể xóa Tuyến đường đang có lịch trình`,
  CANNOT_REMOVE_PATH_HAS_PATH_POINT: (param) => `Không thể xóa Tuyến đường vẫn còn điểm dừng`,
  CANNOT_REMOVE_PATH_POINT_HAS_BUSS_SCHEDULE_POINT: (param) => `Không thể xóa Điểm dừng đang còn trên Lịch trình`,
  CANNOT_REMOVE_TRIP_HAS_TICKET: (param) => `Không thể xóa Chuyến đi đã có vé`,
  CANNOT_REMOVE_USER_HAS_TICKET: (param) => `Không thể xóa người dùng đã từng đặt vé`,
  CANNOT_REMOVE_USER_IS_EMPLOYEE: (param) => `Không thể xóa người dùng của nhà xe`,
  CURRENT_PASSWORD_WRONG: "Mật khẩu hiện tại không đúng",
  DATA_EXISTED: "Dữ liệu đã tồn tại!",
  DATA_NOT_FOUND: "Không tìm thấy dữ liệu",
  DONT_KNOW_WHY_THIS_ERROR: (param) => `${param.name}${param.cause}`,
  DO_NOT_HAVE_PERMISSION: "Bạn chưa được cấp quyền truy cập trang này.",
  EMAIL_EXISTED: "Email đã tồn tại!",
  EMAIL_NOT_EXIST: (param) => `${param.email} không tồn tại!`,
  FIELD_EXISTED: (param) => `${param.tableName} ${param.fieldName} đã tồn tại! `,
  INVALID_BUSS_SCHEDULE_WORKING_DAY: (param) => `Lịch trình không áp dụng cho ngày này`,
  INVALID_DIRECTION: (param) => `Điểm ko hợp lệ`,
  INVALID_EFFECTIVE_DATE: (param) => `Ngày khởi hành không được trước ngày hiệu lực`,
  INVALID_TIME_FORMAT: (param) => `Định dạng thời gian ko hợp lệ: ${param.fieldName}`,
  LOCKED_EXCEPTION: `Tài khoản tạm khóa do nhập sai quá nhiều lần!`,
  NOTHING_CHANGED: "Không có gì được thay đổi!",
  NO_HANDLER_FOUND_EXCEPTION: `Chưa khai báo API xử lý!`,
  NO_USER_FOUND_BY_USERNAME: (param) => `tài khoản ${param.username} không tồn tại!`,
  PASSWORD_MUST_MORE_THAN_3_CHARS: "Mật khẩu phải dài hơn 3 ký tự",
  PASSWORD_NOT_MATCH: "Mật khẩu mới không khớp",
  PLEASE_INPUT: (param) => `Vui lòng nhập ${param.fieldName}}`,
  REMOVE_THIS_EMPLOYEE_FROM_BUSS_FIRST: (param) => `Nhân viên này vẫn đang quản lý xe ${param.bussLicense}`,
  SEAT_RANGE_MUST_CONTINUOUS_FROM_1: (param) => `Giữa các dãy ghế không được gián đoạn!`,
  SEAT_RANGE_OVERLAP: (param) => `Trùng với dãy ghế đã tồn tại`,
  SECRET_KEY_NOT_MATCH: "Mã code không khớp!",
  SEND_EMAIL_FAILED: "Gửi email thất bại",
  TRIP_NOT_FOUND: "Không tìm thấy chuyến đi",
  UNDEFINED_ERROR: "Lỗi không xác định!",
  USERNAME_EXISTED: (param) => `tên đăng nhập ${param.username} đã tồn tại `,
  VALIDATOR_EMAIL_INVALID: "Email không hợp lệ",
  VALIDATOR_NOT_BLANK: (param) => `${param.fieldName} không được để trống!`,
  VALIDATOR_PATTERN_INVALID: (param) => `${param.fieldName} không hợp lệ`,
  VALIDATOR_SIZE_INVALID: (param) => `${param.fieldName} không hợp lệ, độ dài cần ${param.min} đến ${param.max} ký tự`,
};
