package net.timxekhach.utility.config;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@AllArgsConstructor
@Log4j2
@RequestMapping(path={"/webSocket"})
public class WebSocketController {
  private final SimpMessagingTemplate template;

  @MessageMapping("/send")
  public void sendMessage(String message){
    log.info(message);
    this.template.convertAndSend("/message",  message);
  }

  @GetMapping("/send")
  @ResponseStatus(value = HttpStatus.OK)
  public void sendHttpMessage(@RequestParam("message") String message){
    log.info(message);
    this.template.convertAndSend("/topic/"+message,  "Hello from "+message);
  }
}
