package com.yolo.integration.ezugi.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yolo.integration.ezugi.exception.EzugiError;

public class ResponseHistory implements Serializable {

   private static final long serialVersionUID = 2919643301609529472L;

   private ResponseHistoryData data;
   private ArrayList<HistoryDetail> historys;
   private String maintenance;
   private Long timestamp;
   @JsonProperty("error_code")
   private String errorCode;
   private String errorDesc;
   private ResponseMessageDetails details;

   public ResponseHistoryData getData() {
      return data;
   }

   public ArrayList<HistoryDetail> getHistorys() {
      return historys;
   }

   public void setHistorys(ArrayList<HistoryDetail> historys) {
      this.historys = historys;
      if (this.data == null) {
         this.data = new ResponseHistoryData();
      }
      this.data.setHistorys(historys);
   }

   public String getMaintenance() {
      return maintenance;
   }

   public void setMaintenance(String maintenance) {
      this.maintenance = maintenance;
   }

   public Long getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(Long timestamp) {
      this.timestamp = timestamp;
   }

   public String getErrorCode() {
      return errorCode;
   }

   // 轉換server端回傳的int error code為自定義的error code
   public void setErrorCode(int errorCode) {
      if (errorCode == EzugiError.OK.getReturnCode()) {
         this.errorCode = EzugiError.OK.getErrorCode();
         setErrorDesc(EzugiError.OK.getErrorDesc());
      } else if (errorCode == EzugiError.AUTH_ERROR.getReturnCode()) {
         this.errorCode = EzugiError.AUTH_ERROR.getErrorCode();
         setErrorDesc(EzugiError.AUTH_ERROR.getErrorDesc());
      } else if (errorCode == EzugiError.API_ERROR.getReturnCode()) {
         this.errorCode = EzugiError.API_ERROR.getErrorCode();
         setErrorDesc(EzugiError.API_ERROR.getErrorDesc());
      } else if (errorCode == EzugiError.INVALID_REQ.getReturnCode()) {
         this.errorCode = EzugiError.INVALID_REQ.getErrorCode();
         setErrorDesc(EzugiError.INVALID_REQ.getErrorDesc());
      } else if (errorCode == EzugiError.LOGIN_FAIL.getReturnCode()) {
         this.errorCode = EzugiError.LOGIN_FAIL.getErrorCode();
         setErrorDesc(EzugiError.LOGIN_FAIL.getErrorDesc());
      } else if (errorCode == EzugiError.MAINTENANCE.getReturnCode()) {
         this.errorCode = EzugiError.MAINTENANCE.getErrorCode();
         setErrorDesc(EzugiError.MAINTENANCE.getErrorDesc());
      } else if (errorCode == EzugiError.NOT_ENOUGH_FUNDS.getReturnCode()) {
         this.errorCode = EzugiError.NOT_ENOUGH_FUNDS.getErrorCode();
         setErrorDesc(EzugiError.NOT_ENOUGH_FUNDS.getErrorDesc());
      } else if (errorCode == EzugiError.PLAYER_AUTH_ERROR.getReturnCode()) {
         this.errorCode = EzugiError.PLAYER_AUTH_ERROR.getErrorCode();
         setErrorDesc(EzugiError.PLAYER_AUTH_ERROR.getErrorDesc());
      } else if (errorCode == EzugiError.PLAYER_REGISTER_ERROR.getReturnCode()) {
         this.errorCode = EzugiError.PLAYER_REGISTER_ERROR.getErrorCode();
         setErrorDesc(EzugiError.PLAYER_REGISTER_ERROR.getErrorDesc());
      } else if (errorCode == EzugiError.TRANSFER_FAIL.getReturnCode()) {
         this.errorCode = EzugiError.TRANSFER_FAIL.getErrorCode();
         setErrorDesc(EzugiError.TRANSFER_FAIL.getErrorDesc());
      }
   }

   public String getErrorDesc() {
      return errorDesc;
   }

   public void setErrorDesc(String errorDesc) {
      this.errorDesc = errorDesc;
   }

   public ResponseMessageDetails getDetails() {
      return details;
   }

   public void setDetails(ResponseMessageDetails details) {
      this.details = details;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ResponseHistory [historys=");
      builder.append(getHistorys());
      builder.append(", maintenance=");
      builder.append(getMaintenance());
      builder.append(", timestamp=");
      builder.append(getTimestamp());
      builder.append(", error_code=");
      builder.append(getErrorCode());
      builder.append(", errorDesc=");
      builder.append(getErrorDesc());
      builder.append(", details=");
      builder.append(getDetails());
      builder.append("]");
      return builder.toString();
   }

}
