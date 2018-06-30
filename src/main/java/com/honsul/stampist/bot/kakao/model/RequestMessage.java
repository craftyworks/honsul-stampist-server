package com.honsul.stampist.bot.kakao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class RequestMessage {

  private String userKey;
  private String type;
  private String content;
  
}
