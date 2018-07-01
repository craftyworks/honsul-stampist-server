package com.honsul.stampist.server.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.honsul.stampist.core.message.StampStatusMessage;
import com.honsul.stampist.server.model.StampStatusRepository;


@Controller
public class StampistWebSocketController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private StampStatusRepository stampStatusRepository;
  
  @MessageMapping("/stamp.status")
  public void stampStatus(@Payload StampStatusMessage message, SimpMessageHeaderAccessor headerAccessor) {
    logger.info("stamp status : {}", message);
    
    stampStatusRepository.save(message);
  }

}
