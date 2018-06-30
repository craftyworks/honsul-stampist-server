package com.honsul.stampist.bot.kakao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
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
