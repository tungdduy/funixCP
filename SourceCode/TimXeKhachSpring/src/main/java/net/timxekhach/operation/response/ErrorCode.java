package net.timxekhach.operation.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.timxekhach.utility.XeBeanUtils;
import net.timxekhach.utility.XeResponseUtils;
import net.timxekhach.utility.pojo.Message;
import net.timxekhach.utility.pojo.XeRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public enum ErrorCode {
    ACCESS_DENIED,
    DO_NOT_HAVE_PERMISSION,
    EMAIL_EXISTED,
    NO_USER_FOUND_BY_USERNAME,
    USERNAME_EXISTED,
    USER_NOT_FOUND,
    VALIDATOR_EMAIL_INVALID,
    VALIDATOR_NOT_BLANK,
    VALIDATOR_PATTERN_INVALID,
    VALIDATOR_SIZE_INVALID("fieldName", "min", "max");


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
