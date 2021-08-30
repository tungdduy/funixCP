package net.timxekhach.security.handler;

import lombok.extern.log4j.Log4j2;
import net.timxekhach.operation.response.XeExceptionHandler;
import net.timxekhach.utility.XeResponseUtils;
import net.timxekhach.utility.model.Message;
import net.timxekhach.utility.model.XeHttpResponse;
import net.timxekhach.utility.model.XeRuntimeException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Component
@Log4j2
public class XeExceptionListener {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final XeExceptionHandler xeExceptionHandler;
    private final List<Message> storageMessages = new ArrayList<>();

    public XeExceptionListener(ApplicationEventPublisher applicationEventPublisher, XeExceptionHandler xeExceptionHandler) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.xeExceptionHandler = xeExceptionHandler;
    }

    @SuppressWarnings("unused")
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleBeforeCommit(XeExceptionListener event) {
        if(!this.storageMessages.isEmpty()){
            throw new XeRuntimeException();
        }
    }

    public List<Message> mergeAndClearStorageMessages(List<Message> messages){
        if(messages != null) {
            storageMessages.addAll(messages);
        }
        List<Message> lastMessages = new ArrayList<>(storageMessages);
        storageMessages.clear();
        return lastMessages;
    }


    public void addErrorToEvent(Message message){
        this.storageMessages.add(message);
        applicationEventPublisher.publishEvent(this);
    }

    private ResponseEntity<XeHttpResponse> findAnyFeasibleResponse(Throwable ex) {
        List<Throwable> exceptions = new ArrayList<>();
        exceptions.add(ex);
        while(ex.getCause() != null) {
            exceptions.add(0, ex.getCause());
            ex = ex.getCause();
        }
        for(Throwable tracingException : exceptions) {
            ResponseEntity<XeHttpResponse> response = findAndInvokeHandleMethod(tracingException);
            if(response != null) {
                return response;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity<XeHttpResponse> findAndInvokeHandleMethod(Throwable exception) {
        String simpleClassName = exception.getClass().getSimpleName();
        for (Method method : XeExceptionHandler.class.getDeclaredMethods())
            if(method.getParameterCount() == 1
                    && method.getParameterTypes()[0] == exception.getClass()
                    && method.getName().equalsIgnoreCase(simpleClassName) ){
                try {
                    method.setAccessible(true);
                    return (ResponseEntity<XeHttpResponse>) method.invoke(xeExceptionHandler,  exception);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<XeHttpResponse> handleException(Exception exception) {
        exception.printStackTrace();
        log.error(exception.getMessage(), exception);
        ResponseEntity<XeHttpResponse> handled = findAnyFeasibleResponse(exception);
        if(handled != null) {
            return handled;
        }
        return XeResponseUtils.error(exception);
    }
}
