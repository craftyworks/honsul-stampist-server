package com.honsul.stampist.bot.kakao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.stampist.bot.kakao.event.TextCommand;
import com.honsul.stampist.bot.kakao.model.Keyboard;
import com.honsul.stampist.bot.kakao.model.Message;
import com.honsul.stampist.bot.kakao.model.RequestMessage;
import com.honsul.stampist.bot.kakao.model.ResponseMessage;
import com.honsul.stampist.core.message.StartWorkingStamp;
import com.honsul.stampist.core.message.StopWorkingStamp;
import com.honsul.stampist.server.websocket.StampistMessagePublisher;

@RestController
@RequestMapping("/bot/kakao/")
public class KakaoBotController {
  private static final Logger logger = LoggerFactory.getLogger(KakaoBotController.class);
  
  @Autowired
  private StampistMessagePublisher messagePublisher;
  
  @GetMapping("keyboard") 
  public Keyboard keyboard() {
    return Keyboard.TEXT_KEYBOARD;
  }
  
  //@GetMapping("keyboard") 
  public Keyboard buttonKeyboard() {
    return Keyboard.BUTTON_KEYOARD;
  }  

  @PostMapping("message")
  public ResponseMessage message(@RequestBody RequestMessage request) {
    ResponseMessage response = null;
    
    logger.info("message({}, {}, {})", request.getType(), request.getUserKey(), request.getContent());
    TextCommand command = TextCommand.getCommand(request.getContent());
    switch(command) {
      case 출근:
        response = stampStartWorking(request);
        break;
      case 퇴근:
        response = stampStopWorking(request);
        break;
      case 토큰:
        response = tokenResponse(request);
        break;
      case 확인:
        break;
      case 버튼:
        response = keyboardResponse(Keyboard.BUTTON_KEYOARD);
        break;
      case 키보드:
        response = keyboardResponse(Keyboard.TEXT_KEYBOARD);
        break;
      default:
        response = defaultResponse(request);
        break;
    }
    return response;

  }

  private ResponseMessage keyboardResponse(Keyboard keyboard) {
    return getTextResponseMessage(keyboard.getType().name(), keyboard);
  }
  
  private ResponseMessage stampStartWorking(RequestMessage request) {
    messagePublisher.publishStampMessage(new StartWorkingStamp(request.getUserKey()));
    return getTextResponseMessage("출근도장 찍었습니다.", Keyboard.TEXT_KEYBOARD);
  }

  private ResponseMessage stampStopWorking(RequestMessage request) {
    messagePublisher.publishStampMessage(new StopWorkingStamp(request.getUserKey()));
    return getTextResponseMessage("퇴근도장 찍었습니다.", Keyboard.TEXT_KEYBOARD);
  }

  private ResponseMessage tokenResponse(RequestMessage request) {
    return getTextResponseMessage("인증토큰 : " + request.getUserKey(), Keyboard.TEXT_KEYBOARD);
  }
  
  private ResponseMessage defaultResponse(RequestMessage request) {
    return getTextResponseMessage(request.getContent() + " 는 올바르지 않은 명령입니다.", Keyboard.BUTTON_KEYOARD);
  }
  
  private ResponseMessage getTextResponseMessage(String text, Keyboard keyboard) {
    Message msg = new Message();
    msg.setText(text);
    
    ResponseMessage response = new ResponseMessage();
    response.setMessage(msg);
    response.setKeyboard(keyboard);
    
    return response;
  }
  
  @ExceptionHandler(value = RuntimeException.class)  
  public ResponseMessage handleError(RuntimeException e){  
      return getTextResponseMessage(String.format("시스템에 문제가 발생하여 처리할 수 없습니다. [%s]", e.getClass().getSimpleName()), Keyboard.TEXT_KEYBOARD);  
  } 

}
