package net.timxekhach.operation.response;


import net.timxekhach.utility.XeReflectionUtils;
import net.timxekhach.utility.XeResponseUtils;
import net.timxekhach.utility.XeStringUtils;
import net.timxekhach.utility.model.Message;
import net.timxekhach.utility.model.XeHttpResponse;
import net.timxekhach.utility.model.XeRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Component
public class XeExceptionHandler {

    public ResponseEntity<XeHttpResponse> xeRuntimeException(XeRuntimeException exception) {
        return XeResponseUtils.error(exception);
    }

    public ResponseEntity<XeHttpResponse> constraintViolationException(ConstraintViolationException exception) {
        List<Message> messages = new ArrayList<>();
        exception.getConstraintViolations().forEach(violation -> {
            Message message = getConstraintMessage(violation);
            if(message != null) {
                messages.add(message);
            }
        });
        return XeResponseUtils.error(exception, messages);
    }

    public ResponseEntity<XeHttpResponse> dataIntegrityViolationException(DataIntegrityViolationException exception) {
        List<Message> messages = new ArrayList<>();
        return XeResponseUtils.error(exception, messages);
    }

    public ResponseEntity<XeHttpResponse> sQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
        List<Message> messages = new ArrayList<>();
        String exMsg = exception.getMessage();
        if (exMsg.contains("Duplicate")) {
            String [] splitter =  exception.getMessage().substring("Duplicate entry '".length()).split("' for key '");
            String fieldName = splitter[0];
            String tableName = splitter[1].split("\\.")[0];
            messages.add(ErrorCode.FIELD_EXISTED.createMessage(fieldName, tableName));
        }
        if (exception.getSQLState().equals("23000") && exception.getErrorCode() == 1452) {
            String fieldName = XeStringUtils.getStringBetween(exMsg, "FOREIGN KEY (`", "`) REFERENCES");
            messages.add(ErrorCode.VALIDATOR_NOT_BLANK.createMessage(fieldName));
        }

        return XeResponseUtils.error(exception, messages);
    }

    private Message getConstraintMessage(ConstraintViolation<?> constraintViolation){
        switch (constraintViolation.getMessageTemplate()) {
            case "{javax.validation.constraints.Size.message}":
                return processSizeConstraint(constraintViolation);
            case "{javax.validation.constraints.Pattern.message}":
                return ErrorCode.VALIDATOR_PATTERN_INVALID.createConstraintMessage(constraintViolation);
            case "{javax.validation.constraints.Email.message}":
                return ErrorCode.VALIDATOR_EMAIL_INVALID.createConstraintMessage(constraintViolation);
            case "{javax.validation.constraints.NotBlank.message}":
                return ErrorCode.VALIDATOR_NOT_BLANK.createConstraintMessage(constraintViolation);
        }
        return null;
    }

    private Message processSizeConstraint(ConstraintViolation<?> ex) {
        String fieldName = ex.getPropertyPath().toString();
        Map<String, Object> attributes = ex.getConstraintDescriptor().getAttributes();
        String min = String.valueOf(attributes.get("min"));
        String max = String.valueOf(attributes.get("max"));
        return ErrorCode.VALIDATOR_SIZE_INVALID.createMessage(fieldName, min, max);
    }

    private void printConstraintDetails(ConstraintViolation<?> ex) {
        System.out.println("================ START ===================");
        System.out.println("property Path: " + ex.getPropertyPath());
        System.out.println("Message Template: " +ex.getMessageTemplate());
        System.out.println("Message: " +ex.getMessage());
        ex.getConstraintDescriptor().getAttributes().forEach((key, value) -> {
            System.out.println("key: " + key + " -- value: " + value);
        });

        System.out.println("============= END ========================");
        System.out.println();
        System.out.println();
    }


    public ResponseEntity<XeHttpResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<Message> messages = new ArrayList<>();

        for(FieldError field : exception.getFieldErrors()) {
            if(field.getClass().getName().equals("org.springframework.validation.beanvalidation.SpringValidatorAdapter$ViolationFieldError")){
                ConstraintViolation<?> violation = XeReflectionUtils.getField(field, "violation");
                if(violation != null) {
                    Message message = getConstraintMessage(violation);
                    if(message != null) {
                        messages.add(message);
                    }
                }
            }
        }
        return XeResponseUtils.error(exception, messages);
    }

}


