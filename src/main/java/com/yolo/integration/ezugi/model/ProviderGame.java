package com.yolo.integration.ezugi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ProviderGame implements Serializable {

   private static final long serialVersionUID = -3369384818506035619L;

   private String id;
   @JsonProperty("provider_id")
   private String providerId;
   @JsonProperty("provider_game_id")
   private String providerGameId;
   private String name;
   @JsonProperty("game_url")
   private String gameUrl;
   @JsonProperty("logo_url")
   private String logoUrl;
   private String active;
   @JsonProperty("play_for_fun")
   private String playForFun;
   @JsonProperty("mobile_support")
   private String mobileSupport;
   @JsonProperty("valid_bet")
   private String validBet;

   public ProviderGame() {
   }

   public ProviderGame(String nothing) {
      this.setId(nothing);
      this.setProviderId(nothing);
      this.setProviderGameId(nothing);
      this.setName(nothing);
      this.setGameUrl(nothing);
      this.setLogoUrl(nothing);
      this.setActive(nothing);
      this.setPlayForFun(nothing);
      this.setMobileSupport(nothing);
      this.setValidBet(nothing);
   }

   public ProviderGame(String id, String providerId, String providerGameId, String name, String gameUrl,
         String logoUrl, String active, String playForFun, String mobileSupport, String validBet) {
      this.setId(id);
      this.setProviderId(providerId);
      this.setProviderGameId(providerGameId);
      this.setName(name);
      this.setGameUrl(gameUrl);
      this.setLogoUrl(logoUrl);
      this.setActive(active);
      this.setPlayForFun(playForFun);
      this.setMobileSupport(mobileSupport);
      this.setValidBet(validBet);
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getProviderId() {
      return providerId;
   }

   public void setProviderId(String providerId) {
      this.providerId = providerId;
   }

   public String getProviderGameId() {
      return providerGameId;
   }

   public void setProviderGameId(String providerGameId) {
      this.providerGameId = providerGameId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getGameUrl() {
      return gameUrl;
   }

   public void setGameUrl(String gameUrl) {
      this.gameUrl = gameUrl;
   }

   public String getLogoUrl() {
      return logoUrl;
   }

   public void setLogoUrl(String logoUrl) {
      this.logoUrl = logoUrl;
   }

   public String getActive() {
      return active;
   }

   public void setActive(String active) {
      this.active = active;
   }

   public String getPlayForFun() {
      return playForFun;
   }

   public void setPlayForFun(String playForFun) {
      this.playForFun = playForFun;
   }

   public String getMobileSupport() {
      return mobileSupport;
   }

   public void setMobileSupport(String mobileSupport) {
      this.mobileSupport = mobileSupport;
   }

   public String getValidBet() {
      return validBet;
   }

   public void setValidBet(String validBet) {
      this.validBet = validBet;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ProviderGame [id=");
      builder.append(getId());
      builder.append(", provider_id=");
      builder.append(getProviderId());
      builder.append(", provider_game_id=");
      builder.append(getProviderGameId());
      builder.append(", name=");
      builder.append(getName());
      builder.append(", game_url=");
      builder.append(getGameUrl());
      builder.append(", logo_url=");
      builder.append(getLogoUrl());
      builder.append(", active=");
      builder.append(getActive());
      builder.append(", play_for_fun=");
      builder.append(getPlayForFun());
      builder.append(", mobile_support=");
      builder.append(getMobileSupport());
      builder.append(", valid_bet=");
      builder.append(getValidBet());
      builder.append("]");
      return builder.toString();
   }

}
