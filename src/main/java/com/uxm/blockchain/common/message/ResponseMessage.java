package com.uxm.blockchain.common.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@Builder
public class ResponseMessage {

  private HttpStatus httpStatus;
  private String message;

  @JsonInclude(Include.NON_NULL)
  private Object data;

  public static ResponseMessage of(HttpStatus httpStatus, String resultMessage){
    return ResponseMessage.builder()
        .httpStatus(httpStatus)
        .message(resultMessage)
        .build();
  }
  public static ResponseMessage of(HttpStatus httpStatus, String resultMessage,Object data){
    return ResponseMessage.builder()
        .httpStatus(httpStatus)
        .message(resultMessage)
        .data(data)
        .build();
  }

}
