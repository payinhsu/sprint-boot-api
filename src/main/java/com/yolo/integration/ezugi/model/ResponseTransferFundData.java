package com.yolo.integration.ezugi.model;

import java.io.Serializable;

/**
 * Created by peter on 2017/2/6.
 */
public class ResponseTransferFundData implements Serializable {

   private static final long serialVersionUID = -3252064555333701675L;

   private String userId;
   private String agentId;
   private String operatorId;
   private String playerId;
   private String fullName;
   private String phoneNumber;
   private String bankName;
   private String accountNumber;
   private String dateCreated;
   private String dateComplete;
   private String paymentMethod;
   private Integer transactionType;
   private Integer transactionSubType;
   private String currencyCode;
   private Double amountDeposit;
   private Double amountWithdraw;
   private Integer status;
   private Integer depositType;
   private Double balanceBefore;
   private Double balanceAfter;
   private String comment;
   private Integer id;

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getAgentId() {
      return agentId;
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
   }

   public String getOperatorId() {
      return operatorId;
   }

   public void setOperatorId(String operatorId) {
      this.operatorId = operatorId;
   }

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getBankName() {
      return bankName;
   }

   public void setBankName(String bankName) {
      this.bankName = bankName;
   }

   public String getAccountNumber() {
      return accountNumber;
   }

   public void setAccountNumber(String accountNumber) {
      this.accountNumber = accountNumber;
   }

   public String getDateCreated() {
      return dateCreated;
   }

   public void setDateCreated(String dateCreated) {
      this.dateCreated = dateCreated;
   }

   public String getDateComplete() {
      return dateComplete;
   }

   public void setDateComplete(String dateComplete) {
      this.dateComplete = dateComplete;
   }

   public String getPaymentMethod() {
      return paymentMethod;
   }

   public void setPaymentMethod(String paymentMethod) {
      this.paymentMethod = paymentMethod;
   }

   public Integer getTransactionType() {
      return transactionType;
   }

   public void setTransactionType(Integer transactionType) {
      this.transactionType = transactionType;
   }

   public Integer getTransactionSubType() {
      return transactionSubType;
   }

   public void setTransactionSubType(Integer transactionSubType) {
      this.transactionSubType = transactionSubType;
   }

   public String getCurrencyCode() {
      return currencyCode;
   }

   public void setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
   }

   public Double getAmountDeposit() {
      return amountDeposit;
   }

   public void setAmountDeposit(Double amountDeposit) {
      this.amountDeposit = amountDeposit;
   }

   public Double getAmountWithdraw() {
      return amountWithdraw;
   }

   public void setAmountWithdraw(Double amountWithdraw) {
      this.amountWithdraw = amountWithdraw;
   }

   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
      this.status = status;
   }

   public Integer getDepositType() {
      return depositType;
   }

   public void setDepositType(Integer depositType) {
      this.depositType = depositType;
   }

   public Double getBalanceBefore() {
      return balanceBefore;
   }

   public void setBalanceBefore(Double balanceBefore) {
      this.balanceBefore = balanceBefore;
   }

   public Double getBalanceAfter() {
      return balanceAfter;
   }

   public void setBalanceAfter(Double balanceAfter) {
      this.balanceAfter = balanceAfter;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
}
