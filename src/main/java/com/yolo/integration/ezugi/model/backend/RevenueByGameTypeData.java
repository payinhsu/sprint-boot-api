package com.yolo.integration.ezugi.model.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
public class RevenueByGameTypeData implements Serializable {

   private static final long serialVersionUID = -3145470301121319257L;
   @JsonProperty("OperatorID")
   private String operatorID;
   @JsonProperty("NumRounds")
   private String numRounds;
   @JsonProperty("NumPlayers")
   private String numPlayers;
   @JsonProperty("Bet")
   private String bet;
   @JsonProperty("Payout")
   private String payout;
   @JsonProperty("SessionCurrency")
   private String sessionCurrency;
   @JsonProperty("ConvertionRateToDollar")
   private String convertionRateToDollar;
   @JsonProperty("ConBet")
   private String conBet;
   @JsonProperty("ConPayout")
   private String conPayout;
   @JsonProperty("BonusWinUSD")
   private String bonusWinUSD;
   @JsonProperty("BonusBetUSD")
   private String bonusBetUSD;
   @JsonProperty("Edge")
   private String edge;
   @JsonProperty("NGRUSD")
   private String ngrUSD;
   @JsonProperty("BonusConsumption")
   private String bonusConsumption;
   @JsonProperty("GGRUSD")
   private String ggrUSD;
   @JsonProperty("GameTypeID")
   private String gameTypeID;

   public String getOperatorID() {
      return operatorID;
   }

   public void setOperatorID(String operatorID) {
      this.operatorID = operatorID;
   }

   public String getNumRounds() {
      return numRounds;
   }

   public void setNumRounds(String numRounds) {
      this.numRounds = numRounds;
   }

   public String getNumPlayers() {
      return numPlayers;
   }

   public void setNumPlayers(String numPlayers) {
      this.numPlayers = numPlayers;
   }

   public String getBet() {
      return bet;
   }

   public void setBet(String bet) {
      this.bet = bet;
   }

   public String getPayout() {
      return payout;
   }

   public void setPayout(String payout) {
      this.payout = payout;
   }

   public String getSessionCurrency() {
      return sessionCurrency;
   }

   public void setSessionCurrency(String sessionCurrency) {
      this.sessionCurrency = sessionCurrency;
   }

   public String getConvertionRateToDollar() {
      return convertionRateToDollar;
   }

   public void setConvertionRateToDollar(String convertionRateToDollar) {
      this.convertionRateToDollar = convertionRateToDollar;
   }

   public String getConBet() {
      return conBet;
   }

   public void setConBet(String conBet) {
      this.conBet = conBet;
   }

   public String getConPayout() {
      return conPayout;
   }

   public void setConPayout(String conPayout) {
      this.conPayout = conPayout;
   }

   public String getBonusWinUSD() {
      return bonusWinUSD;
   }

   public void setBonusWinUSD(String bonusWinUSD) {
      this.bonusWinUSD = bonusWinUSD;
   }

   public String getBonusBetUSD() {
      return bonusBetUSD;
   }

   public void setBonusBetUSD(String bonusBetUSD) {
      this.bonusBetUSD = bonusBetUSD;
   }

   public String getEdge() {
      return edge;
   }

   public void setEdge(String edge) {
      this.edge = edge;
   }

   public String getNgrUSD() {
      return ngrUSD;
   }

   public void setNgrUSD(String ngrUSD) {
      this.ngrUSD = ngrUSD;
   }

   public String getBonusConsumption() {
      return bonusConsumption;
   }

   public void setBonusConsumption(String bonusConsumption) {
      this.bonusConsumption = bonusConsumption;
   }

   public String getGgrUSD() {
      return ggrUSD;
   }

   public void setGgrUSD(String ggrUSD) {
      this.ggrUSD = ggrUSD;
   }

   public String getGameTypeID() {
      return gameTypeID;
   }

   public void setGameTypeID(String gameTypeID) {
      this.gameTypeID = gameTypeID;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("RevenueByGameTypeData [operatorID=");
      builder.append(getOperatorID());
      builder.append(", numRounds=");
      builder.append(getNumRounds());
      builder.append(", numPlayers=");
      builder.append(getNumPlayers());
      builder.append(", bet=");
      builder.append(getBet());
      builder.append(", payout=");
      builder.append(getPayout());
      builder.append(", sessionCurrency=");
      builder.append(getSessionCurrency());
      builder.append(", convertionRateToDollar=");
      builder.append(getConvertionRateToDollar());
      builder.append(", conBet=");
      builder.append(getConBet());
      builder.append(", conPayout=");
      builder.append(getConPayout());
      builder.append(", bonusWinUSD=");
      builder.append(getBonusWinUSD());
      builder.append(", bonusBetUSD=");
      builder.append(getBonusBetUSD());
      builder.append(", edge=");
      builder.append(getEdge());
      builder.append(", ngrUSD=");
      builder.append(getNgrUSD());
      builder.append(", bonusConsumption=");
      builder.append(getBonusConsumption());
      builder.append(", ggrUSD=");
      builder.append(getGgrUSD());
      builder.append(", gameTypeID=");
      builder.append(getGameTypeID());
      builder.append("]");
      return builder.toString();
   }
}
