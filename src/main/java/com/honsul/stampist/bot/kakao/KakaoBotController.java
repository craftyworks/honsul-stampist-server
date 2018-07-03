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
import com.honsul.stampist.server.model.StampStatusRepository;
import com.honsul.stampist.server.websocket.StampistMessagePublisher;

@RestController
@RequestMapping("/bot/kakao/")
public class KakaoBotController {
  private static final Logger logger = LoggerFactory.getLogger(KakaoBotController.class);
  
  @Autowired
  private StampistMessagePublisher messagePublisher;
  
  @Autowired
  private StampStatusRepository stampStatusRepository;
  
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
        response = stampStatus(request);
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
    logger.info("response : {}", response);
    return response;

  }

  private ResponseMessage keyboardResponse(Keyboard keyboard) {
    return getTextResponseMessage(keyboard.getType().name(), keyboard);
  }
  
  private ResponseMessage stampStartWorking(RequestMessage request) {
    //messagePublisher.publishStampMessage(new StartWorkingStamp(request.getAccessToken()));
    return getTextResponseMessage("출근도장 찍었어 (굿)", Keyboard.TEXT_KEYBOARD);
  }

  private ResponseMessage stampStopWorking(RequestMessage request) {
    //messagePublisher.publishStampMessage(new StopWorkingStamp(request.getAccessToken()));
    return getTextResponseMessage("퇴근도장 찍었어 (꺄아)", Keyboard.TEXT_KEYBOARD);
  }

  private ResponseMessage stampStatus(RequestMessage request) {
    //StampStatusMessage message = stampStatusRepository.findByUserToken(request.getAccessToken());
    /*
    String text = message == null 
        ? "잘 모르겠어 (짜증)" 
        : String.format("Date %s\n출근체크 %s\n퇴근체크 %s", message.getDate(), message.getStartWorking(), message.getStopWorking());
    */
    String text = "정보보호 규정이 겁나서 일 접었다. (부르르)";
    return getTextResponseMessage(text, Keyboard.TEXT_KEYBOARD);
  }
  
  private ResponseMessage tokenResponse(RequestMessage request) {
    return getTextResponseMessage("인증토큰 : " + request.getAccessToken(), Keyboard.TEXT_KEYBOARD);
  }
  
  private ResponseMessage defaultResponse(RequestMessage request) {
    return getTextResponseMessage("올바르지 않아! (열받아)", Keyboard.TEXT_KEYBOARD);
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
      return getTextResponseMessage(String.format("내 실수인가? [%s]", e.getClass().getSimpleName() + " (푸하하)"), Keyboard.TEXT_KEYBOARD);  
  } 

}
