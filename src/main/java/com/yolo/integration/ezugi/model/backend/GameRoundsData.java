package com.yolo.integration.ezugi.model.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
public class GameRoundsData implements Serializable {

   private static final long serialVersionUID = -9172725741447696573L;
   @JsonProperty("RoundID")
   private String roundID;
   @JsonProperty("TableID")
   private String tableID;
   @JsonProperty("ServerID")
   private String serverID;
   @JsonProperty("DealerID")
   private String dealerID;
   @JsonProperty("GameTypeID")
   private String gameTypeID;
   @JsonProperty("RoundString")
   private String roundString;

   private Round round;

   @JsonProperty("RoundStatusID")
   private String roundStatusID;
   @JsonProperty("PlayersNum")
   private String playersNum;
   @JsonProperty("RoundDateTime")
   private String roundDateTime;

   public String getRoundID() {
      return roundID;
   }

   public void setRoundID(String roundID) {
      this.roundID = roundID;
   }

   public String getTableID() {
      return tableID;
   }

   public void setTableID(String tableID) {
      this.tableID = tableID;
   }

   public String getServerID() {
      return serverID;
   }

   public void setServerID(String serverID) {
      this.serverID = serverID;
   }

   public String getDealerID() {
      return dealerID;
   }

   public void setDealerID(String dealerID) {
      this.dealerID = dealerID;
   }

   public String getGameTypeID() {
      return gameTypeID;
   }

   public void setGameTypeID(String gameTypeID) {
      this.gameTypeID = gameTypeID;
   }

   public String getRoundString() {
      return roundString;
   }

   public Round getRound() {
      return round;
   }

   public void setRound(Round round) {
      this.round = round;
   }

   public void setRoundString(String roundString) {
      this.roundString = roundString;
   }

   public String getRoundStatusID() {
      return roundStatusID;
   }

   public void setRoundStatusID(String roundStatusID) {
      this.roundStatusID = roundStatusID;
   }

   public String getPlayersNum() {
      return playersNum;
   }

   public void setPlayersNum(String playersNum) {
      this.playersNum = playersNum;
   }

   public String getRoundDateTime() {
      return roundDateTime;
   }

   public void setRoundDateTime(String roundDateTime) {
      this.roundDateTime = roundDateTime;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("GameRoundsData [roundID=");
      builder.append(getRoundID());
      builder.append(", tableID=");
      builder.append(getTableID());
      builder.append(", serverID=");
      builder.append(getServerID());
      builder.append(", dealerID=");
      builder.append(getDealerID());
      builder.append(", gameTypeID=");
      builder.append(getGameTypeID());
      builder.append(", round=");
      builder.append(getRound());
      builder.append(", roundString=");
      builder.append(getRoundString());
      builder.append(", roundStatusID=");
      builder.append(getRoundStatusID());
      builder.append(", playersNum=");
      builder.append(getPlayersNum());
      builder.append(", roundDateTime=");
      builder.append(getRoundDateTime());
      builder.append("]");
      return builder.toString();
   }
}
