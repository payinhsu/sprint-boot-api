package com.yolo.integration.ezugi.model.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
public class AllPlayersData implements Serializable {

   private static final long serialVersionUID = 3848373578823457140L;
   @JsonProperty("UID")
   private String uID;
   @JsonProperty("OperatorID")
   private String operatorID;
   @JsonProperty("NickName")
   private String nickName;
   @JsonProperty("IsBlocked")
   private String isBlocked;
   @JsonProperty("ServerID")
   private String serverID;
   @JsonProperty("TableID")
   private String tableID;
   @JsonProperty("IDS")
   private String iDS;
   @JsonProperty("SessionCurrency")
   private String sessionCurrency;
   @JsonProperty("BetSum")
   private String betSum;
   @JsonProperty("BetUSD")
   private String betUSD;
   @JsonProperty("WinUSD")
   private String winUSD;
   @JsonProperty("WinSum")
   private String winSum;
   @JsonProperty("PlayerLastLogin")
   private String playerLastLogin;
   @JsonProperty("PlayerFirstLogin")
   private String playerFirstLogin;

   public String getuID() {
      return uID;
   }

   public void setuID(String uID) {
      this.uID = uID;
   }

   public String getOperatorID() {
      return operatorID;
   }

   public void setOperatorID(String operatorID) {
      this.operatorID = operatorID;
   }

   public String getNickName() {
      return nickName;
   }

   public void setNickName(String nickName) {
      this.nickName = nickName;
   }

   public String getIsBlocked() {
      return isBlocked;
   }

   public void setIsBlocked(String isBlocked) {
      this.isBlocked = isBlocked;
   }

   public String getServerID() {
      return serverID;
   }

   public void setServerID(String serverID) {
      this.serverID = serverID;
   }

   public String getTableID() {
      return tableID;
   }

   public void setTableID(String tableID) {
      this.tableID = tableID;
   }

   public String getiDS() {
      return iDS;
   }

   public void setiDS(String iDS) {
      this.iDS = iDS;
   }

   public String getSessionCurrency() {
      return sessionCurrency;
   }

   public void setSessionCurrency(String sessionCurrency) {
      this.sessionCurrency = sessionCurrency;
   }

   public String getBetSum() {
      return betSum;
   }

   public void setBetSum(String betSum) {
      this.betSum = betSum;
   }

   public String getBetUSD() {
      return betUSD;
   }

   public void setBetUSD(String betUSD) {
      this.betUSD = betUSD;
   }

   public String getWinUSD() {
      return winUSD;
   }

   public void setWinUSD(String winUSD) {
      this.winUSD = winUSD;
   }

   public String getWinSum() {
      return winSum;
   }

   public void setWinSum(String winSum) {
      this.winSum = winSum;
   }

   public String getPlayerLastLogin() {
      return playerLastLogin;
   }

   public void setPlayerLastLogin(String playerLastLogin) {
      this.playerLastLogin = playerLastLogin;
   }

   public String getPlayerFirstLogin() {
      return playerFirstLogin;
   }

   public void setPlayerFirstLogin(String playerFirstLogin) {
      this.playerFirstLogin = playerFirstLogin;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("AllPlayersData [uID=");
      builder.append(getuID());
      builder.append(", operatorID=");
      builder.append(getOperatorID());
      builder.append(", nickName=");
      builder.append(getNickName());
      builder.append(", isBlocked=");
      builder.append(getIsBlocked());
      builder.append(", serverID=");
      builder.append(getServerID());
      builder.append(", tableID=");
      builder.append(getTableID());
      builder.append(", iDS=");
      builder.append(getiDS());
      builder.append(", sessionCurrency=");
      builder.append(getSessionCurrency());
      builder.append(", betSum=");
      builder.append(getBetSum());
      builder.append(", betUSD=");
      builder.append(getBetUSD());
      builder.append(", winUSD=");
      builder.append(getWinUSD());
      builder.append(", winSum=");
      builder.append(getWinSum());
      builder.append(", playerLastLogin=");
      builder.append(getPlayerLastLogin());
      builder.append(", playerFirstLogin=");
      builder.append(getPlayerFirstLogin());
      builder.append("]");
      return builder.toString();
   }
}
