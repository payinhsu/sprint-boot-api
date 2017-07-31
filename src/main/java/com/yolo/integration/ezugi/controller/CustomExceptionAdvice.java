package com.yolo.integration.ezugi.controller;

import com.poseitech.integration.message.ResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
@ControllerAdvice
@RestController
public class CustomExceptionAdvice {
   private static Logger log = LoggerFactory.getLogger(CustomExceptionAdvice.class);

   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ResponsePayload handleBadRequest() throws Exception {
      ResponsePayload payload = new ResponsePayload();
      payload.setErrorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
      payload.setErrorDesc("Request Parameter data type was wrong or was invalid");
      return payload;
   }

}