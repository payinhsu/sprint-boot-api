package com.yolo.integration.ezugi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class HistoryDetail implements Serializable {

   private static final long serialVersionUID = -4710089878038436633L;

   private String id;
   @JsonProperty("player_id")
   private String playerId;
   @JsonProperty("session_id")
   private String sessionId;
   @JsonProperty("game_id")
   private String gameId;
   @JsonProperty("game_name")
   private String gameName;
   @JsonProperty("provider_id")
   private String providerId;
   @JsonProperty("round_id")
   private String roundId;
   @JsonProperty("currency_code")
   private String currencyCode;
   @JsonProperty("transaction_type")
   private String transactionType;
   @JsonProperty("amount_credit")
   private String amountCredit;
   @JsonProperty("amount_debit")
   private String amountDebit;
   @JsonProperty("date_created")
   private String dateCreated;
   @JsonProperty("balance_before")
   private String balanceBefore;
   @JsonProperty("balance_after")
   private String balanceAfter;
   @JsonProperty("provider_game")
   private ProviderGame providerGame;
   @JsonProperty("provider_transaction_id")
   private String providerTransactionId;
   @JsonProperty("provider_details")
   private Provider providerDetails;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   public String getSessionId() {
      return sessionId;
   }

   public void setSessionId(String sessionId) {
      this.sessionId = sessionId;
   }

   public String getGameId() {
      return gameId;
   }

   public void setGameId(String gameId) {
      this.gameId = gameId;
   }

   public String getGameName() {
      return gameName;
   }

   public void setGameName(String gameName) {
      this.gameName = gameName;
   }

   public String getProviderId() {
      return providerId;
   }

   public void setProviderId(String providerId) {
      this.providerId = providerId;
   }

   public String getRoundId() {
      return roundId;
   }

   public void setRoundId(String roundId) {
      this.roundId = roundId;
   }

   public String getCurrencyCode() {
      return currencyCode;
   }

   public void setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
   }

   public String getTransactionType() {
      return transactionType;
   }

   public void setTransactionType(String transactionType) {
      this.transactionType = transactionType;
   }

   public String getAmountCredit() {
      return amountCredit;
   }

   public void setAmountCredit(String amountCredit) {
      this.amountCredit = amountCredit;
   }

   public String getAmountDebit() {
      return amountDebit;
   }

   public void setAmountDebit(String amountDebit) {
      this.amountDebit = amountDebit;
   }

   public String getDateCreated() {
      return dateCreated;
   }

   public void setDateCreated(String dateCreated) {
      this.dateCreated = dateCreated;
   }

   public String getBalanceBefore() {
      return balanceBefore;
   }

   public void setBalanceBefore(String balanceBefore) {
      this.balanceBefore = balanceBefore;
   }

   public String getBalanceAfter() {
      return balanceAfter;
   }

   public void setBalanceAfter(String balanceAfter) {
      this.balanceAfter = balanceAfter;
   }

   public ProviderGame getProviderGame() {
      return providerGame;
   }

   public void setProviderGame(ProviderGame providerGame) {
      this.providerGame = providerGame;
   }

   public String getProviderTransactionId() {
      return providerTransactionId;
   }

   public void setProviderTransactionId(String providerTransactionId) {
      this.providerTransactionId = providerTransactionId;
   }

   public Provider getProviderDetails() {
      return providerDetails;
   }

   public void setProviderDetails(Provider providerDetails) {
      this.providerDetails = providerDetails;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("HistoryDetail [id=");
      builder.append(getId());
      builder.append(", player_id=");
      builder.append(getPlayerId());
      builder.append(", session_id=");
      builder.append(getSessionId());
      builder.append(", game_id=");
      builder.append(getGameId());
      builder.append(", game_name=");
      builder.append(getGameName());
      builder.append(", provider_id=");
      builder.append(getProviderId());
      builder.append(", round_id=");
      builder.append(getRoundId());
      builder.append(", currency_code=");
      builder.append(getCurrencyCode());
      builder.append(", transaction_type=");
      builder.append(getTransactionType());
      builder.append(", amount_credit=");
      builder.append(getAmountCredit());
      builder.append(", amount_debit=");
      builder.append(getAmountDebit());
      builder.append(", date_created");
      builder.append(getDateCreated());
      builder.append(", balance_before=");
      builder.append(getBalanceBefore());
      builder.append(", balance_after=");
      builder.append(getBalanceAfter());
      builder.append(", provider_game=");
      if (getProviderGame() == null) {
         builder.append("---");
      } else {
         builder.append(getProviderGame().toString());
      }
      builder.append(", provider_transaction_id=");
      builder.append(getProviderTransactionId());
      builder.append(", provider_details=");
      if (getProviderDetails() == null) {
         builder.append("---");
      } else {
         builder.append(getProviderDetails().toString());
      }
      builder.append("]");
      return builder.toString();
   }
}
