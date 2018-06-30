package com.honsul.stampist.bot.kakao.event;

public enum TextCommand {
  출근, 퇴근, 토큰, UNKNOWN;
  
  public static TextCommand getCommand(String name) {
    for(TextCommand r : TextCommand.values()) {
      if(r.name().equals(name)) {
        return r;
      }
    }
    return TextCommand.UNKNOWN;
  }
}
