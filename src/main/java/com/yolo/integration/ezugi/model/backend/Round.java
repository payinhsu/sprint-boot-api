package com.yolo.integration.ezugi.model.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
public class Round implements Serializable {

   private static final long serialVersionUID = -4769821596115773010L;
   @JsonProperty("TableID")
   private String tableId;
   @JsonProperty("PlayerId")
   private String playerId;
   @JsonProperty("BetAmount")
   private Double betAmount;
   @JsonProperty("SessionCurrency")
   private String sessionCurrency;
   @JsonProperty("TotalWin")
   private Double totalWin;
   @JsonProperty("OperatorID")
   private String operatorID;
   @JsonProperty("BetsList")
   private String betsList;
   @JsonProperty("WinningNumber")
   private Integer winningNumber;
   @JsonProperty("ServerId")
   private Integer serverId;
   @JsonProperty("DealerId")
   private String dealerId;
   @JsonProperty("DealerCards")
   private Card[] dealerCards;
   @JsonProperty("DealerName")
   private String dealerName;

   private Integer roundId;
   @JsonProperty("SeatCards")
   private Card[] seatCards;
   @JsonProperty("WinningBets")
   private String winningBets;
   @JsonProperty("GameID")
   private Integer gameID;

   public String getTableId() {
      return tableId;
   }

   public void setTableId(String tableId) {
      this.tableId = tableId;
   }

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   public Double getBetAmount() {
      return betAmount;
   }

   public void setBetAmount(Double betAmount) {
      this.betAmount = betAmount;
   }

   public String getSessionCurrency() {
      return sessionCurrency;
   }

   public void setSessionCurrency(String sessionCurrency) {
      this.sessionCurrency = sessionCurrency;
   }

   public Double getTotalWin() {
      return totalWin;
   }

   public void setTotalWin(Double totalWin) {
      this.totalWin = totalWin;
   }

   public String getOperatorID() {
      return operatorID;
   }

   public void setOperatorID(String operatorID) {
      this.operatorID = operatorID;
   }

   public String getBetsList() {
      return betsList;
   }

   public void setBetsList(String betsList) {
      this.betsList = betsList;
   }

   public Integer getWinningNumber() {
      return winningNumber;
   }

   public void setWinningNumber(Integer winningNumber) {
      this.winningNumber = winningNumber;
   }

   public Integer getServerId() {
      return serverId;
   }

   public void setServerId(Integer serverId) {
      this.serverId = serverId;
   }

   public String getDealerId() {
      return dealerId;
   }

   public void setDealerId(String dealerId) {
      this.dealerId = dealerId;
   }

   public Card[] getDealerCards() {
      return dealerCards;
   }

   public void setDealerCards(Card[] dealerCards) {
      this.dealerCards = dealerCards;
   }

   public String getDealerName() {
      return dealerName;
   }

   public void setDealerName(String dealerName) {
      this.dealerName = dealerName;
   }

   public Integer getRoundId() {
      return roundId;
   }

   public void setRoundId(Integer roundId) {
      this.roundId = roundId;
   }

   public Card[] getSeatCards() {
      return seatCards;
   }

   public void setSeatCards(Card[] seatCards) {
      this.seatCards = seatCards;
   }

   public String getWinningBets() {
      return winningBets;
   }

   public void setWinningBets(String winningBets) {
      this.winningBets = winningBets;
   }

   public Integer getGameID() {
      return gameID;
   }

   public void setGameID(Integer gameID) {
      this.gameID = gameID;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Round [tableId=");
      builder.append(getTableId());
      builder.append(", playerId=");
      builder.append(getPlayerId());
      builder.append(", betAmount=");
      builder.append(getBetAmount());
      builder.append(", sessionCurrency=");
      builder.append(getSessionCurrency());
      builder.append(", totalWin=");
      builder.append(getTotalWin());
      builder.append(", operatorID=");
      builder.append(getOperatorID());
      builder.append(", betsList=");
      builder.append(getBetsList());
      builder.append(", winningNumber=");
      builder.append(getWinningNumber());
      builder.append(", serverId=");
      builder.append(getServerId());
      builder.append(", dealerId=");
      builder.append(getDealerId());
      builder.append(", dealerCards=");
      if(getDealerCards() != null){
         builder.append(Arrays.toString(getDealerCards()));
      }
      builder.append(", dealerName=");
      builder.append(getDealerName());
      builder.append(", roundId=");
      builder.append(getRoundId());
      builder.append(", seatCards=");
      if(getSeatCards() != null){
         builder.append(Arrays.toString(getSeatCards()));
      }
      builder.append(", winningBets=");
      builder.append(getWinningBets());
      builder.append(", gameID=");
      builder.append(getGameID());
      builder.append("]");
      return builder.toString();
   }
}
