package com.yolo.integration.ezugi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author peter.hsu
 *
 */
public class Session implements Serializable {

   private static final long serialVersionUID = -4867270482592280983L;

   @JsonProperty("agent_id")
   private String agentId;
   @JsonProperty("player_id")
   private String playerId;
   @JsonProperty("session_token")
   private String sessionToken;
   @JsonProperty("date_created")
   private String dateCreated;
   @JsonProperty("date_accessed")
   private String dateAccessed;
   @JsonProperty("session_ip")
   private String sessionIp;
   @JsonProperty("country_code")
   private String countryCode;
   private Integer active;
   private Long id;
   private String notification;
   @JsonProperty("socket_token")
   private String socketToken;

   public String getAgentId() {
      return agentId;
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
   }

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
   }

   public String getSessionToken() {
      return sessionToken;
   }

   public void setSessionToken(String sessionToken) {
      this.sessionToken = sessionToken;
   }

   public String getDateCreated() {
      return dateCreated;
   }

   public void setDateCreated(String dateCreated) {
      this.dateCreated = dateCreated;
   }

   public String getDateAccessed() {
      return dateAccessed;
   }

   public void setDateAccessed(String dateAccessed) {
      this.dateAccessed = dateAccessed;
   }

   public String getSessionIp() {
      return sessionIp;
   }

   public void setSessionIp(String sessionIp) {
      this.sessionIp = sessionIp;
   }

   public String getCountryCode() {
      return countryCode;
   }

   public void setCountryCode(String countryCode) {
      this.countryCode = countryCode;
   }

   public Integer getActive() {
      return active;
   }

   public void setActive(Integer active) {
      this.active = active;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNotification() {
      return notification;
   }

   public void setNotification(String notification) {
      this.notification = notification;
   }

   public String getSocketToken() {
      return socketToken;
   }

   public void setSocketToken(String socketToken) {
      this.socketToken = socketToken;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Session [agent_id=");
      builder.append(getAgentId());
      builder.append(", player_id=");
      builder.append(getPlayerId());
      builder.append(", session_token=");
      builder.append(getSessionToken());
      builder.append(", date_created=");
      builder.append(getDateCreated());
      builder.append(", date_accessed=");
      builder.append(getDateAccessed());
      builder.append(", session_ip=");
      builder.append(getSessionIp());
      builder.append(", country_code=");
      builder.append(getCountryCode());
      builder.append(", active=");
      builder.append(getActive());
      builder.append(", id=");
      builder.append(getId());
      builder.append(", notification=");
      builder.append(getNotification());
      builder.append(", socket_token=");
      builder.append(getSocketToken());
      builder.append("]");
      return builder.toString();
   }

}
