package com.honsul.stampist.server.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.stampist.core.message.StartWorkingStamp;
import com.honsul.stampist.server.websocket.StampistMessagePublisher;


@RestController
@RequestMapping("/console")
public class StampistController {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private StampistMessagePublisher messagePublisher;
  
  @RequestMapping("/start/{token}")
  public void startWorkingStamp(@PathVariable String token) {
    logger.info("start working : {}", token);
    
    StartWorkingStamp stamp = new StartWorkingStamp(token);
    messagePublisher.publishStampMessage(stamp);
  }

  @RequestMapping("/stop/{token}")
  public void stopWorkingStamp(@PathVariable String token) {
    logger.info("stop working : {}", token);
    //sendToUser(sessionId, new Greeting("Hello Hello"));
  }

}
