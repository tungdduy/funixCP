package net.timxekhach.operation.response;


import net.timxekhach.utility.XeResponseUtils;
import net.timxekhach.utility.XeStringUtils;
import net.timxekhach.utility.pojo.Message;
import net.timxekhach.utility.pojo.XeHttpResponse;
import net.timxekhach.utility.pojo.XeRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.timxekhach.utility.XeStringUtils.*;

@SuppressWarnings("unused")
public class XeExceptionHandler {

    public ResponseEntity<XeHttpResponse> xeRuntimeException(XeRuntimeException exception) {
        return XeResponseUtils.error(exception);
    }

    public ResponseEntity<XeHttpResponse> constraintViolationException(ConstraintViolationException exception) {
        List<Message> messages = new ArrayList<>();
        exception.getConstraintViolations().forEach(ex -> {
            String fieldName = ex.getPropertyPath().toString();
            String errorMessage = ex.getMessage();
            List<String> attributes = new ArrayList<>();
            attributes.add(fieldName);
            ex.getConstraintDescriptor().getAttributes().forEach((key, value) -> {
                String val = expressToString(value);
                if(!val.isEmpty()) {
                    attributes.add(joinByColon(Arrays.asList(key, val)));
                }

            });
            messages.add(new Message(errorMessage, attributes));
        });
        return XeResponseUtils.error(exception, messages);
    }


    public ResponseEntity<XeHttpResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<Message> messages = new ArrayList<>();

        for(FieldError field : exception.getFieldErrors()) {
            List<String> attrs = new ArrayList<>();
            attrs.add(field.getObjectName());
            attrs.add(field.getField());

            if(field.getArguments() != null) {
                List<String> attributes = Arrays.stream(field.getArguments())
                        .map(XeStringUtils::expressToString)
                        .filter(XeStringUtils::isNotBlank)
                        .collect(Collectors.toList());

                if(!attributes.isEmpty()) attrs.add(joinByComma(attributes));
            }

            messages.add(new Message(field.getDefaultMessage(),attrs));
        }
        return XeResponseUtils.error(exception, messages);
    }

}


