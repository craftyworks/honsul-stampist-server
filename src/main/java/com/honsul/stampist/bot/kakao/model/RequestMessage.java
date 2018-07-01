package com.honsul.stampist.bot.kakao.model;

import java.util.Objects;

import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class RequestMessage {
  @JsonProperty("user_key")
  private String userKey;
  private String type;
  private String content;
  
  /**
   * 카카오 유저키를 Stampist WebSocket 통신을 위한 Access Token 으로 변환.
   */
  public String getAccessToken() {
    return Base64Utils.encodeToString(String.valueOf(Objects.hash(userKey, "stampist")).getBytes()).toUpperCase();
  }
  
}
