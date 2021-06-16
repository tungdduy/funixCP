package net.timxekhach.operation.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.timxekhach.utility.XeBeanUtils;
import net.timxekhach.utility.XeResponseUtils;
import net.timxekhach.utility.pojo.Message;
import net.timxekhach.utility.pojo.XeRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public enum ErrorCode {
    EMAIL_EXISTED,
    USERNAME_EXISTED,
    NO_USER_FOUND_BY_USERNAME,
    ACCESS_DENIED,
    DO_NOT_HAVE_PERMISSION,
    USER_NOT_FOUND;


    public void throwRuntimeException(String... params)  {
        throw new XeRuntimeException(new Message(this, params));
    }
    public void processResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, XeResponseUtils.of(status, this));
        outputStream.flush();
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity errorResponseContent() {
        return XeResponseUtils.of(HttpStatus.BAD_REQUEST, this);
    }

    public void throwIf(boolean isThrow, String... params) {
        if(isThrow) {
            throwRuntimeException(params);
        }
    }

    public void cumulativeIf(boolean isStore, String... params) {
        if(isStore) {
            cumulative(params);
        }
    }

    public void cumulative(String... params) {
        XeBeanUtils.exceptionListener.addErrorToEvent(new Message(this, params));
    }
}
