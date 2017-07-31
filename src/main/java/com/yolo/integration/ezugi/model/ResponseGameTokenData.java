package com.yolo.integration.ezugi.model;

import java.io.Serializable;

/**
 * Created by peter on 2017/2/6.
 */
public class ResponseGameTokenData implements Serializable {

   private static final long serialVersionUID = -5556625590656901754L;

   private String agentId;
   private String playerId;
   private String playerSessionId;
   private String providerId;
   private String gameToken;
   private String dateCreated;
   private String dateAccessed;
   private String sessionIp;
   private String active;
   private String id;
   private Provider provider;
   private ProviderParams providerParams;
   private ProviderGame[] providerGames;

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

   public String getPlayerSessionId() {
      return playerSessionId;
   }

   public void setPlayerSessionId(String playerSessionId) {
      this.playerSessionId = playerSessionId;
   }

   public String getProviderId() {
      return providerId;
   }

   public void setProviderId(String providerId) {
      this.providerId = providerId;
   }

   public String getGameToken() {
      return gameToken;
   }

   public void setGameToken(String gameToken) {
      this.gameToken = gameToken;
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

   public String getActive() {
      return active;
   }

   public void setActive(String active) {
      this.active = active;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public Provider getProvider() {
      return provider;
   }

   public void setProvider(Provider provider) {
      this.provider = provider;
   }

   public ProviderParams getProviderParams() {
      return providerParams;
   }

   public void setProviderParams(ProviderParams providerParams) {
      this.providerParams = providerParams;
   }

   public ProviderGame[] getProviderGames() {
      return providerGames;
   }

   public void setProviderGames(ProviderGame[] providerGames) {
      this.providerGames = providerGames;
   }
}
