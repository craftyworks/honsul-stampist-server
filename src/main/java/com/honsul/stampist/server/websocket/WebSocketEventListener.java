package com.honsul.stampist.server.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.honsul.stampist.server.model.WebSocketUser;

@Component
public class WebSocketEventListener {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private WebSocketUserRepository repository;

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    MessageHeaders headers = event.getMessage().getHeaders();
    MessageHeaders connectHeaders = ((GenericMessage)headers.get(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER)).getHeaders();
    
    String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
    String token = SimpMessageHeaderAccessor.getFirstNativeHeader("token", connectHeaders);
    
    repository.save(new WebSocketUser(sessionId, token));
    
    logger.info("connected : {}, {}, {}", sessionId, token, headers);
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    
    String sessionId = headerAccessor.getSessionId();
    
    WebSocketUser user = repository.findBySessionId(sessionId);
    
    repository.delete(sessionId);
    
    logger.info("disconnected : {}, {}", sessionId, user);
  }
}
