package com.honsul.stampist.bot.kakao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.honsul.stampist.bot.kakao.model.Keyboard;
import com.honsul.stampist.bot.kakao.model.Message;
import com.honsul.stampist.bot.kakao.model.MessageButton;
import com.honsul.stampist.bot.kakao.model.Photo;
import com.honsul.stampist.bot.kakao.model.RequestMessage;
import com.honsul.stampist.bot.kakao.model.ResponseMessage;

@RestController
@RequestMapping("/bot/kakao/")
public class KakaoBotController {
  
  //@GetMapping("keyboard") 
  public Keyboard keyboard() {
    Keyboard keyboard = new Keyboard();
    keyboard.setType("text");
    return keyboard;
  }
  
  @GetMapping("keyboard") 
  public Keyboard buttonKeyboard() {
    Keyboard keyboard = new Keyboard();
    keyboard.setType("button");
    keyboard.setButtons(new String[]{"출근", "퇴근", "토큰"});
    return keyboard;
  }  

  @PostMapping("message")
  public ResponseMessage message(@RequestBody RequestMessage request) {
    
    Message msg = new Message();
    msg.setText(request.getContent());
    msg.setPhoto(Photo.of("https://hubot.github.com/assets/images/layout/hubot-avatar@2x.png", 184, 184));
    msg.setMessage_button(MessageButton.of("쿠폰받기", "http://naver.com"));
    
    ResponseMessage response = new ResponseMessage();
    response.setMessage(msg);
    response.setKeyboard(Keyboard.of("text"));

    return response;
  }
}
