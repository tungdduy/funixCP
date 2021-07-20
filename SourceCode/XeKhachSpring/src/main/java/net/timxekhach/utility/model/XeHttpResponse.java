package net.timxekhach.utility.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class XeHttpResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private final Date timeStamp;
    private final String reason;
    private List<Message> messages;
    private final HttpStatus status;
    private final int statusCode;

    public XeHttpResponse(HttpStatus status, String reason, List<Message> messages){
        this.timeStamp = new Date();
        this.reason = reason;
        this.status = status;
        this.statusCode = status.value();
        this.messages = messages;
    }
}
