package com.honsul.stampist.bot.kakao.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Photo {
  private String url;
  private int width;
  private int height;
  
  public static Photo of(String url, int width, int height) {
    Photo photo = new Photo();
    photo.setUrl(url);
    photo.setWidth(width);
    photo.setHeight(height);
    return photo;
  }
}
