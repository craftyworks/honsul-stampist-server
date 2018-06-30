package com.honsul.stampist.core.exception;

public class WebSocketUserNotFoundException extends StampistException {

  private static final long serialVersionUID = 4304900424094142336L;

  public WebSocketUserNotFoundException() {
    super();
  }

  public WebSocketUserNotFoundException(String message) {
    super(message);
  }

  public WebSocketUserNotFoundException(Throwable cause) {
    super(cause);
  }
  
  public WebSocketUserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
