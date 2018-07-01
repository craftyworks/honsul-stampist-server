package com.honsul.stampist.server.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.honsul.stampist.core.message.StampStatusMessage;

@Component
public class StampStatusRepository {
  private final Map<String, StampStatusMessage> repository = new ConcurrentHashMap<>();
  
  public void save(StampStatusMessage message) {
    this.repository.put(message.getToken(), message);
  }
  
  public void delete(String token) {
    this.repository.remove(token);
  }
  
  public StampStatusMessage findByUserToken(String token) {
    return this.repository.get(token);
  }
  
}
