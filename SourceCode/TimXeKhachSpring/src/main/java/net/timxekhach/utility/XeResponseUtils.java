package net.timxekhach.utility;

import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.pojo.Message;
import net.timxekhach.utility.pojo.XeHttpResponse;
import net.timxekhach.utility.pojo.XeRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


public class XeResponseUtils {

    public static ResponseEntity<Void> success() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> success(T t) {
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> successWithHeaders(T t, HttpHeaders headers) {
        return new ResponseEntity<>(t, headers,HttpStatus.OK);
    }

    public static ResponseEntity<XeHttpResponse> of(HttpStatus status, String reason, List<Message> messages) {
        XeHttpResponse response = new XeHttpResponse(status, reason, messages);
        return new ResponseEntity<>(response, status);
    }

    public static <E extends Exception> ResponseEntity<XeHttpResponse> error(E e, List<Message> messages){
        List<Message> totalMessages = XeBeanUtils.exceptionListener.mergeAndClearStorageMessages(messages);
        return XeResponseUtils.of(HttpStatus.BAD_REQUEST, e.getClass().getSimpleName(), totalMessages);
    }

    public static ResponseEntity<XeHttpResponse> error(Exception exception) {
        List<Message> messages = new ArrayList<>();
        if(exception instanceof XeRuntimeException) {
            messages.add(((XeRuntimeException) exception).getXeMessage());
        }
        return error(exception, messages);
    }

    public static ResponseEntity<XeHttpResponse> of(HttpStatus status, ErrorCode errorCode) {
        return of(status, errorCode.name(), new ArrayList<>());
    }
}
