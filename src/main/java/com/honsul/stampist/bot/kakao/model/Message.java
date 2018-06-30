package com.honsul.stampist.bot.kakao.model;

import lombok.Data;

@Data
public class Message {
  String text;
  Photo photo;
  MessageButton message_button;
}