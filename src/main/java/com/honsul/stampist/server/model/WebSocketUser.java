package com.honsul.stampist.server.model;

import lombok.Data;

@Data
public class WebSocketUser {
  private String sessionId;
  private String userToken;
  public WebSocketUser(String sessionId, String userToken) {
    this.sessionId = sessionId;
    this.userToken = userToken;
  }
}
