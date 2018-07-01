package com.honsul.stampist.server.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Component;

import com.honsul.stampist.core.exception.WebSocketUserNotFoundException;
import com.honsul.stampist.core.message.StampMessage;
import com.honsul.stampist.server.model.WebSocketUser;
import com.honsul.stampist.server.model.WebSocketUserRepository;

@Component
public class StampistMessagePublisher {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private SimpMessageSendingOperations messagingTemplate;
  
  @Autowired
  private WebSocketUserRepository repository; 
  
  public void publishStampMessage(StampMessage stamp) {
    WebSocketUser user = repository.findByUserToken(stamp.getToken());
    if(user == null) {
      throw new WebSocketUserNotFoundException(String.format("Token ID %s 에 해당하는 사용자 발견하지 못함.", stamp.getToken()));
    }

    logger.info("publishing stamp[{}] order to user[{}]", stamp, user);
    messagingTemplate.convertAndSendToUser(user.getSessionId(), stamp.getDestination(), stamp, createMessageHeaders(user.getSessionId()));
  }
  
  private MessageHeaders createMessageHeaders(String sessionId) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);
    headerAccessor.setLeaveMutable(true);
    return headerAccessor.getMessageHeaders();
  }
}
