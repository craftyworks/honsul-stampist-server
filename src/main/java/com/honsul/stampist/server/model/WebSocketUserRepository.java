package com.honsul.stampist.server.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.honsul.stampist.server.model.WebSocketUser;

@Component
public class WebSocketUserRepository {
  private final Map<String, WebSocketUser> sessions = new ConcurrentHashMap<>();
  
  public void save(WebSocketUser user) {
    this.sessions.put(user.getSessionId(), user);
  }
  
  public void delete(String sessionId) {
    this.sessions.remove(sessionId);
  }
  
  public WebSocketUser findByUserToken(String token) {
    for(Entry<String, WebSocketUser> entry : sessions.entrySet()) {
      if(token.equals(entry.getValue().getUserToken())) {
        return entry.getValue();
      }
    }
    return null;
  }
  
  public WebSocketUser findBySessionId(String sessionId) {
    return this.sessions.get(sessionId);
  }

  public String[] allTokens() {
    return sessions.values().stream().map(el -> el.getUserToken()).toArray(String[]::new);
  }
}
