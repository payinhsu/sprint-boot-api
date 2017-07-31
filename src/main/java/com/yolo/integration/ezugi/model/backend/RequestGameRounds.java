package com.yolo.integration.ezugi.model.backend;

import java.io.Serializable;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
public class RequestGameRounds implements Serializable {

   private static final long serialVersionUID = 7886154757318379856L;

   private String dataSet;

   private String apiID;

   private String apiUser;
   /* option 1,7,30 default 1 */
   private String timePeriod;
   /* MySQL format yyyy-mm-dd hh:mm:ss */
   private String startTime;
   /* MySQL format yyyy-mm-dd hh:mm:ss */
   private String endTime;

   private String roundID;

   private String tableID;

   private String dealerID;

   private String uID;

   private String gameType;
   /* 25,50,100,200,500 */
   private String limit;

   private String page;

   private String requestToken;

   public String getDataSet() {
      return dataSet;
   }

   public void setDataSet(String dataSet) {
      this.dataSet = dataSet;
   }

   public String getApiID() {
      return apiID;
   }

   public void setApiID(String apiID) {
      this.apiID = apiID;
   }

   public String getApiUser() {
      return apiUser;
   }

   public void setApiUser(String apiUser) {
      this.apiUser = apiUser;
   }

   public String getTimePeriod() {
      return timePeriod;
   }

   public void setTimePeriod(String timePeriod) {
      this.timePeriod = timePeriod;
   }

   public String getStartTime() {
      return startTime;
   }

   public void setStartTime(String startTime) {
      this.startTime = startTime;
   }

   public String getEndTime() {
      return endTime;
   }

   public void setEndTime(String endTime) {
      this.endTime = endTime;
   }

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

   public String getDealerID() {
      return dealerID;
   }

   public void setDealerID(String dealerID) {
      this.dealerID = dealerID;
   }

   public String getuID() {
      return uID;
   }

   public void setuID(String uID) {
      this.uID = uID;
   }

   public String getGameType() {
      return gameType;
   }

   public void setGameType(String gameType) {
      this.gameType = gameType;
   }

   public String getLimit() {
      return limit;
   }

   public void setLimit(String limit) {
      this.limit = limit;
   }

   public String getPage() {
      return page;
   }

   public void setPage(String page) {
      this.page = page;
   }

   public String getRequestToken() {
      return requestToken;
   }

   public void setRequestToken(String requestToken) {
      this.requestToken = requestToken;
   }
}
