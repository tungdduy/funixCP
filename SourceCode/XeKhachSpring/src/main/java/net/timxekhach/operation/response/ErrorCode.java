package net.timxekhach.operation.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.timxekhach.utility.XeBeanUtils;
import net.timxekhach.utility.XeResponseUtils;
import net.timxekhach.utility.XeStringUtils;
import net.timxekhach.utility.model.Message;
import net.timxekhach.utility.model.XeRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    DATA_EXISTED,
    SEAT_RANGE_OVERLAP,
    SEAT_RANGE_MUST_CONTINUOUS_FROM_1,
    INVALID_DIRECTION
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

    public void throwNow(String... params) {
        throw new XeRuntimeException(this.createMessage(params));
    }

    public void generateResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, XeResponseUtils.of(status, this));
        outputStream.flush();
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity getErrorResponse() {
        return XeResponseUtils.of(HttpStatus.BAD_REQUEST, this);
    }

    public void throwIf(boolean isThrow, String... paramValues) {
        if (isThrow) {
            throwNow(paramValues);
        }
    }

    public void throwIfFalse(boolean isFalse, String... paramValues) {
        if(!isFalse) {
            throwNow(paramValues);
        }
    }

    public <T> T throwIfNull(T object, String... paramValues) {
        if (object == null) {
            throwNow(paramValues);
        }
        return object;
    }
    public Object[] throwIfAnyNull(Object... objects) {
        if(objects == null || Arrays.stream(objects).anyMatch(o -> o == null)) {
            throwNow();
        }
        return objects;
    }

    public <T> List<T> throwIfNotEmpty(List<T> list, String... paramValues) {
        if (list == null || !list.isEmpty()) {
            throwNow(paramValues);
        }
        return list;
    }

    public <T> T throwIfNotPresent(Optional<T> optional, String... paramValues) {
        if (!optional.isPresent()) {
            throwNow(paramValues);
        }
        return optional.get();
    }

    public void throwIfBlank(String value, String... paramValues) {
        if (XeStringUtils.isBlank(value)) {
            throwNow(paramValues);
        }
    }

    public void cumulativeIf(boolean isStore, String... paramValues) {
        if (isStore) {
            cumulative(paramValues);
        }
    }

    public void cumulative(String... paramValues) {
        XeBeanUtils.exceptionListener.addErrorToEvent(new Message(this, paramValues));
    }

    public Message createMessage(String... paramValues) {
        return new Message(this, paramValues);
    }

    public Message createConstraintMessage(ConstraintViolation<?> ex) {
        return this.createMessage(ex.getPropertyPath().toString());
    }
}
