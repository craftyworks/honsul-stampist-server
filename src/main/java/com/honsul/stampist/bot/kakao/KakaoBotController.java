package com.honsul.stampist.bot.kakao;

import org.springframework.beans.factory.annotation.Autowired;
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
  
  private final static Keyboard TEXT_KEYBOARD;
  private final static Keyboard BUTTON_KEYOARD;
  
  static {
    TEXT_KEYBOARD = Keyboard.of("text");
    
    BUTTON_KEYOARD = new Keyboard();
    BUTTON_KEYOARD.setType("button");
    BUTTON_KEYOARD.setButtons(new String[]{"출근", "퇴근", "토큰"});
  }
  
  @Autowired
  private StampistMessagePublisher messagePublisher;
  
  @GetMapping("keyboard") 
  public Keyboard keyboard() {
    return TEXT_KEYBOARD;
  }
  
  //@GetMapping("keyboard") 
  public Keyboard buttonKeyboard() {
    return BUTTON_KEYOARD;
  }  

  @PostMapping("message")
  public ResponseMessage message(@RequestBody RequestMessage request) {
    ResponseMessage response = null;
    
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
      default:
        response = defaultResponse(request);
        break;
    }
    return response;

  }

  private ResponseMessage stampStartWorking(RequestMessage request) {
    messagePublisher.publishStampMessage(new StartWorkingStamp(request.getUserKey()));
    return getTextResponseMessage("출근도장 찍었습니다.", TEXT_KEYBOARD);
  }

  private ResponseMessage stampStopWorking(RequestMessage request) {
    messagePublisher.publishStampMessage(new StopWorkingStamp(request.getUserKey()));
    return getTextResponseMessage("퇴근도장 찍었습니다.", TEXT_KEYBOARD);
  }

  private ResponseMessage tokenResponse(RequestMessage request) {
    return getTextResponseMessage("인증토큰 : " + request.getUserKey(), TEXT_KEYBOARD);
  }
  
  private ResponseMessage defaultResponse(RequestMessage request) {
    return getTextResponseMessage(request.getContent() + " 는 올바르지 않은 명령입니다.", BUTTON_KEYOARD);
  }
  
  private ResponseMessage getTextResponseMessage(String text, Keyboard keyboard) {
    Message msg = new Message();
    msg.setText(text);
    
    ResponseMessage response = new ResponseMessage();
    response.setMessage(msg);
    response.setKeyboard(keyboard);
    
    return response;
  }

}
