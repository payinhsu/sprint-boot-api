package com.yolo.integration.ezugi.model;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yolo.integration.ezugi.exception.EzugiError;

public final class ResponseGameToken implements Serializable {

   private static final long serialVersionUID = 3577162930492242903L;

   private ResponseGameTokenData data;
   @JsonProperty("agent_id")
   private String agentId;
   @JsonProperty("player_id")
   private String playerId;
   @JsonProperty("player_session_id")
   private String playerSessionId;
   @JsonProperty("provider_id")
   private String providerId;
   @JsonProperty("game_token")
   private String gameToken;
   @JsonProperty("date_created")
   private String dateCreated;
   @JsonProperty("date_accessed")
   private String dateAccessed;
   @JsonProperty("session_ip")
   private String sessionIp;
   private String active;
   private String id;
   private Provider provider;
   @JsonProperty("provider_params")
   private ProviderParams providerParams;
   @JsonProperty("provider_games")
   private ProviderGame[] providerGames;
   private String maintenance;
   private Long timestamp;
   @JsonProperty("error_code")
   private String errorCode;
   private String errorDesc;
   private ResponseMessageDetails details;

   public ResponseGameTokenData getData() {
      return data;
   }

   public String getAgentId() {
      return agentId;
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setAgentId(agentId);
   }

   public String getPlayerId() {
      return playerId;
   }

   public void setPlayerId(String playerId) {
      this.playerId = playerId;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setPlayerId(playerId);
   }

   public String getPlayerSessionId() {
      return playerSessionId;
   }

   public void setPlayerSessionId(String playerSessionId) {
      this.playerSessionId = playerSessionId;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setPlayerSessionId(playerSessionId);
   }

   public String getProviderId() {
      return providerId;
   }

   public void setProviderId(String providerId) {
      this.providerId = providerId;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setProviderId(providerId);
   }

   public String getGameToken() {
      return gameToken;
   }

   public void setGameToken(String gameToken) {
      this.gameToken = gameToken;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setGameToken(gameToken);
   }

   public String getDateCreated() {
      return dateCreated;
   }

   public void setDateCreated(String dateCreated) {
      this.dateCreated = dateCreated;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setDateCreated(dateCreated);
   }

   public String getDateAccessed() {
      return dateAccessed;
   }

   public void setDateAccessed(String dateAccessed) {
      this.dateAccessed = dateAccessed;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setDateAccessed(dateAccessed);
   }

   public String getSessionIp() {
      return sessionIp;
   }

   public void setSessionIp(String sessionIp) {
      this.sessionIp = sessionIp;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setSessionIp(sessionIp);
   }

   public String getActive() {
      return active;
   }

   public void setActive(String active) {
      this.active = active;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setActive(active);
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setId(id);
   }

   public Provider getProvider() {
      return provider;
   }

   public void setProvider(Provider provider) {
      this.provider = provider;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setProvider(provider);
   }

   public ProviderParams getProviderParams() {
      return providerParams;
   }

   public void setProviderParams(ProviderParams providerParams) {
      this.providerParams = providerParams;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setProviderParams(providerParams);
   }

   public ProviderGame[] getProviderGames() {
      return providerGames;
   }

   public void setProviderGames(ProviderGame[] providerGames) {
      this.providerGames = providerGames;
      if (this.data == null) {
         this.data = new ResponseGameTokenData();
      }
      this.data.setProviderGames(providerGames);
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
      builder.append("ResponseGameToken [agent_id=");
      builder.append(getAgentId());
      builder.append(", player_id=");
      builder.append(getPlayerId());
      builder.append(", player_session_id=");
      builder.append(getPlayerSessionId());
      builder.append(", provider_id=");
      builder.append(getProviderId());
      builder.append(", game_token=");
      builder.append(getGameToken());
      builder.append(", date_created=");
      builder.append(getDateCreated());
      builder.append(", date_accessed=");
      builder.append(getDateAccessed());
      builder.append(", session_ip=");
      builder.append(getSessionIp());
      builder.append(", active=");
      builder.append(getActive());
      builder.append(", id=");
      builder.append(getId());
      builder.append(", provider=");
      builder.append(getProvider());
      builder.append(", provider_params=");
      builder.append(getProviderParams());
      builder.append(", provider_games=");
      if (getProviderGames() != null) {
         builder.append(Arrays.toString(getProviderGames()));
      }
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
