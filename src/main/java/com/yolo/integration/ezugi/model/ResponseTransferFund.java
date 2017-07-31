package com.yolo.integration.ezugi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yolo.integration.ezugi.exception.EzugiError;

public class ResponseTransferFund implements Serializable {

   private static final long serialVersionUID = -2360758235571261764L;

   private ResponseTransferFundData data;
   @JsonProperty("user_id")
   private String userId;
   @JsonProperty("agent_id")
   private String agentId;
   @JsonProperty("operator_id")
   private String operatorId;
   @JsonProperty("player_id")
   private String playerId;
   @JsonProperty("full_name")
   private String fullName;
   @JsonProperty("phone_number")
   private String phoneNumber;
   @JsonProperty("bank_name")
   private String bankName;
   @JsonProperty("account_number")
   private String accountNumber;
   @JsonProperty("date_created")
   private String dateCreated;
   @JsonProperty("date_complete")
   private String dateComplete;
   @JsonProperty("payment_method")
   private String paymentMethod;
   @JsonProperty("transaction_type")
   private Integer transactionType;
   @JsonProperty("transaction_sub_type")
   private Integer transactionSubType;
   @JsonProperty("currency_code")
   private String currencyCode;
   @JsonProperty("amount_deposit")
   private Double amountDeposit;
   @JsonProperty("amount_withdraw")
   private Double amountWithdraw;
   private Integer status;
   @JsonProperty("deposit_type")
   private Integer depositType;
   @JsonProperty("balance_before")
   private Double balanceBefore;
   @JsonProperty("balance_after")
   private Double balanceAfter;
   private String comment;
   private Integer id;
   private String maintenance;
   private Long timestamp;
   @JsonProperty("error_code")
   private String errorCode;
   private String errorDesc;
   private ResponseMessageDetails details;

   public ResponseTransferFundData getData() {
      return data;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setUserId(userId);
   }

   public String getAgentId() {
      return agentId;
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setAgentId(agentId);
   }

   public String getOperatorId() {
      return operatorId;
   }

   public void setOperatorId(String operatorId) {
      this.operatorId = operatorId;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setOperatorId(operatorId);
   }

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setPlayerId(playerId);
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setFullName(fullName);
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setPhoneNumber(phoneNumber);
   }

   public String getBankName() {
      return bankName;
   }

   public void setBankName(String bankName) {
      this.bankName = bankName;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setBankName(bankName);
   }

   public String getAccountNumber() {
      return accountNumber;
   }

   public void setAccountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setAccountNumber(accountNumber);
   }

   public String getDateCreated() {
      return dateCreated;
   }

   public void setDateCreated(String dateCreated) {
      this.dateCreated = dateCreated;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setDateCreated(dateCreated);
   }

   public String getDateComplete() {
      return dateComplete;
   }

   public void setDateComplete(String dateComplete) {
      this.dateComplete = dateComplete;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setDateComplete(dateComplete);
   }

   public String getPaymentMethod() {
      return paymentMethod;
   }

   public void setPaymentMethod(String paymentMethod) {
      this.paymentMethod = paymentMethod;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setPaymentMethod(paymentMethod);
   }

   public Integer getTransactionType() {
      return transactionType;
   }

   public void setTransactionType(Integer transactionType) {
      this.transactionType = transactionType;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setTransactionType(transactionType);
   }

   public Integer getTransactionSubType() {
      return transactionSubType;
   }

   public void setTransactionSubType(Integer transactionSubType) {
      this.transactionSubType = transactionSubType;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setTransactionSubType(transactionSubType);
   }

   public String getCurrencyCode() {
      return currencyCode;
   }

   public void setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setCurrencyCode(currencyCode);
   }

   public Double getAmountDeposit() {
      return amountDeposit;
   }

   public void setAmountDeposit(Double amountDeposit) {
      this.amountDeposit = amountDeposit;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setAmountDeposit(amountDeposit);
   }

   public Double getAmountWithdraw() {
      return amountWithdraw;
   }

   public void setAmountWithdraw(Double amountWithdraw) {
      this.amountWithdraw = amountWithdraw;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setAmountWithdraw(amountWithdraw);
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setStatus(status);
   }

   public Integer getDepositType() {
      return depositType;
   }

   public void setDepositType(Integer depositType) {
      this.depositType = depositType;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setDepositType(depositType);
   }

   public Double getBalanceBefore() {
      return balanceBefore;
   }

   public void setBalanceBefore(Double balanceBefore) {
      this.balanceBefore = balanceBefore;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setBalanceBefore(balanceBefore);
   }

   public Double getBalanceAfter() {
      return balanceAfter;
   }

   public void setBalanceAfter(Double balanceAfter) {
      this.balanceAfter = balanceAfter;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setBalanceAfter(balanceAfter);
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setComment(comment);
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
      if (this.data == null) {
         this.data = new ResponseTransferFundData();
      }
      this.data.setId(id);
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
      builder.append("ResponseTransferFund [user_id=");
      builder.append(getUserId());
      builder.append(", agent_id=");
      builder.append(getAgentId());
      builder.append(", operator_id=");
      builder.append(getOperatorId());
      builder.append(", player_id=");
      builder.append(getPlayerId());
      builder.append(", full_name=");
      builder.append(getFullName());
      builder.append(", bank_name=");
      builder.append(getBankName());
      builder.append(", account_number=");
      builder.append(getAccountNumber());
      builder.append(", date_created=");
      builder.append(getDateCreated());
      builder.append(", date_complete=");
      builder.append(getDateComplete());
      builder.append(", payment_method=");
      builder.append(getPaymentMethod());
      builder.append(", transaction_type=");
      builder.append(getTransactionType());
      builder.append(", transaction_sub_type=");
      builder.append(getTransactionSubType());
      builder.append(", currency_code=");
      builder.append(getCurrencyCode());
      builder.append(", amount_deposit=");
      builder.append(getAmountDeposit());
      builder.append(", amount_withdraw=");
      builder.append(getAmountWithdraw());
      builder.append(", status=");
      builder.append(getStatus());
      builder.append(", deposit_type=");
      builder.append(getDepositType());
      builder.append(", balance_before=");
      builder.append(getBalanceBefore());
      builder.append(", balance_after=");
      builder.append(getBalanceAfter());
      builder.append(", comment=");
      builder.append(getComment());
      builder.append(", id=");
      builder.append(getId());
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
