package com.honsul.stampist.core.message;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampStatusMessage {
  private String token;
  private LocalDate date;
  @JsonProperty("start_working")
  private String startWorking;
  @JsonProperty("stop_working")
  private String stopWorking;
}
