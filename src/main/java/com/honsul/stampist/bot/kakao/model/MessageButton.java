package com.honsul.stampist.bot.kakao.model;

import lombok.Data;

@Data
public class MessageButton {
  private String label;
  private String url;
  
  public static MessageButton of(String label, String url) {
    MessageButton btn = new MessageButton();
    btn.setLabel(label);
    btn.setUrl(url);
    return btn;
  }
}
