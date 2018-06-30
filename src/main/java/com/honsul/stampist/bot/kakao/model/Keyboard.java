package com.honsul.stampist.bot.kakao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Keyboard {
  private KeyboardType type;
  private String[] buttons;
  
  public final static Keyboard TEXT_KEYBOARD;
  
  public final static Keyboard BUTTON_KEYOARD;
  
  static {
    TEXT_KEYBOARD = new Keyboard();
    TEXT_KEYBOARD.setType(KeyboardType.text);
    
    BUTTON_KEYOARD = new Keyboard();
    BUTTON_KEYOARD.setType(KeyboardType.buttons);
    BUTTON_KEYOARD.setButtons(new String[]{"출근", "퇴근", "토큰"});
  }
  
  public static enum KeyboardType {
    text, buttons;
  }
  
}
