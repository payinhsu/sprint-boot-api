package com.yolo.integration.ezugi.exception;

import com.poseitech.exceptions.ErrorCodeResovler;

public enum EzugiError {
   OK(ErrorCodeResovler.OK, 0, "Completed successfully"),
   INVALID_REQ("62SYS00001", 1, "Invalid request"), 
   AUTH_ERROR("62SYS00002", 2, "Authentication error"), 
   PLAYER_AUTH_ERROR("62SYS00003", 3, "Player authentication error"), 
   PLAYER_REGISTER_ERROR("62SYS00004", 4, "Player registration error"), 
   TRANSFER_FAIL("62SYS00005", 5, "Transaction failed"), 
   API_ERROR("62SYS00006", 6, "API error"), 
   MAINTENANCE("62SYS00007", 7, "Maintenance"), 
   LOGIN_FAIL("62SYS00008", 8, "Login Failed"), 
   NOT_ENOUGH_FUNDS("62SYS00009", 9, "Insufficient funds"),
   JSON_STRING_PARSE_ERROR("62SYS00010", 10, "Parse json string error"),
   PARSE_DATE_FORMAT_ERROR("62SYS00011", 11, "Parse date format error"),
   HTTP_CLIENT_SERVER_ERROR("62SYS00012", 12, "Http client/server error exception"),
   SHA256_ERROR("62SYS00013", 13, "Hash string error"),
   NOT_FOUND_GAME("62SYS00014", 14, "Search game detail by name not found");

   private final String errorCode;
   private final int returnCode;
   private final String errorDesc;

   private EzugiError(String code, int returnCode, String description) {
      this.errorCode = code;
      this.returnCode = returnCode;
      this.errorDesc = description;
   }

   public String getErrorCode() {
      return errorCode;
   }

   public String getErrorDesc() {
      return errorDesc;
   }

   public int getReturnCode() {
      return returnCode;
   }

   @Override
   public String toString() {

      return errorCode + ":" + errorDesc;
   }
}
