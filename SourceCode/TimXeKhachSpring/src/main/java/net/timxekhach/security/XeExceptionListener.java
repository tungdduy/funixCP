package net.timxekhach.security;

import net.timxekhach.operation.response.XeExceptionHandler;
import net.timxekhach.utility.XeResponseUtils;
import net.timxekhach.utility.pojo.Message;
import net.timxekhach.utility.pojo.XeHttpResponse;
import net.timxekhach.utility.pojo.XeRuntimeException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class XeExceptionListener {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final List<Message> storageMessages = new ArrayList<>();

    public XeExceptionListener(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

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

    private boolean pushedTransactionEvent = false;

    public void addErrorToEvent(Message message){
        this.storageMessages.add(message);
        if(!this.pushedTransactionEvent) {
            applicationEventPublisher.publishEvent(this);
            this.pushedTransactionEvent = true;
        }
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
    private ResponseEntity<XeHttpResponse> findAndInvokeHandleMethod(Throwable exception){
        for (Method method : XeExceptionHandler.class.getDeclaredMethods()) {
            if(method.getParameterCount() == 1) {
                Class<?> clazz = method.getParameterTypes()[0];
                if(clazz.isInstance(exception)
                        && method.getName().equalsIgnoreCase(clazz.getSimpleName())){
                    try {
                        method.setAccessible(true);
                        return (ResponseEntity<XeHttpResponse>) method.invoke(XeExceptionHandler.class, clazz.cast(exception));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<XeHttpResponse> handleException(Exception exception) {
        ResponseEntity<XeHttpResponse> handled = findAnyFeasibleResponse(exception);
        if(handled != null) {
            return handled;
        }
        return XeResponseUtils.error(exception);
    }
}
