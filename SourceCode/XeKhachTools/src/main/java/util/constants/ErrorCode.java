package util.constants;

@SuppressWarnings("all")
public enum ErrorCode {
    ACCESS_DENIED,
    DO_NOT_HAVE_PERMISSION,
    EMAIL_EXISTED,
    NO_USER_FOUND_BY_USERNAME("username"),
    USERNAME_EXISTED("username"),
    UNDEFINED_ERROR,
    VALIDATOR_EMAIL_INVALID,
    VALIDATOR_NOT_BLANK("fieldName"),
    VALIDATOR_PATTERN_INVALID("fieldName"),
    VALIDATOR_SIZE_INVALID("fieldName", "min", "max"),
    ASSIGN_1_TIME_ONLY,
    SEND_EMAIL_FAILED,
    EMAIL_NOT_EXIST("email"),
    SECRET_KEY_NOT_MATCH,
    INVALID_TIME_FORMAT("fieldName"),
    CANNOT_FIND_USER("userId", "username", "email"),
    DATA_NOT_FOUND,
    CURRENT_PASSWORD_WRONG,
    PASSWORD_NOT_MATCH,
    NOTHING_CHANGED,
    TRIP_NOT_FOUND,
    PASSWORD_MUST_MORE_THAN_3_CHARS,
    FIELD_EXISTED("fieldName", "tableName"),
    DATA_EXISTED
    ;

    private String[] paramNames;

    ErrorCode(String... paramNames) {
        this.paramNames = paramNames;
    }

    ErrorCode() {
    }

    public String[] getParamNames() {
        return this.paramNames;
    }
}
