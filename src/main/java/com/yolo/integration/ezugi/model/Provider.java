package com.yolo.integration.ezugi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Arrays;

public class Provider implements Serializable {
   private static final long serialVersionUID = -6124773037897786131L;
   private String id;
   private String name;
   private String description;
   @JsonProperty("main_url")
   private String mainUrl;
   @JsonProperty("logo_url")
   private String logoUrl;
   @JsonProperty("provider_id")
   private String providerId;
   private String[] games;
   @JsonProperty("context_root_name")
   private String contextRootName;
   private String ezugi;

   public Provider() {
   }

   public Provider(String nothing) {
      this.setId(nothing);
      this.setName(nothing);
      this.setDescription(nothing);
      this.setMainUrl(nothing);
      this.setLogoUrl(nothing);
      this.setProviderId(nothing);
      this.setGames(null);
      this.setContextRootName(nothing);
      this.setEzugi(nothing);
   }

   public Provider(String id, String name, String description, String mainUrl, String logoUrl, String providerId,
         String[] games, String contextRootName, String ezugi) {
      this.setId(id);
      this.setName(name);
      this.setDescription(description);
      this.setMainUrl(mainUrl);
      this.setLogoUrl(logoUrl);
      this.setProviderId(providerId);
      this.setGames(games);
      this.setContextRootName(contextRootName);
      this.setEzugi(ezugi);
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getMainUrl() {
      return mainUrl;
   }

   public void setMainUrl(String mainUrl) {
      this.mainUrl = mainUrl;
   }

   public String getLogoUrl() {
      return logoUrl;
   }

   public void setLogoUrl(String logoUrl) {
      this.logoUrl = logoUrl;
   }

   public String getProviderId() {
      return providerId;
   }

   public void setProviderId(String providerId) {
      this.providerId = providerId;
   }

   public String[] getGames() {
      return games;
   }

   public void setGames(String[] games) {
      this.games = games;
   }

   public String getContextRootName() {
      return contextRootName;
   }

   public void setContextRootName(String contextRootName) {
      this.contextRootName = contextRootName;
   }

   public String getEzugi() {
      return ezugi;
   }

   public void setEzugi(String ezugi) {
      this.ezugi = ezugi;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Provider [id=");
      builder.append(getId());
      builder.append(", name=");
      builder.append(getName());
      builder.append(", description=");
      builder.append(getDescription());
      builder.append(", main_url=");
      builder.append(getMainUrl());
      builder.append(", logo_url=");
      builder.append(getLogoUrl());
      builder.append(", provider_id=");
      builder.append(getProviderId());
      builder.append(", games=");
      if (getGames() != null) {
         builder.append(Arrays.toString(getGames()));
      }
      builder.append(", context_root_name=");
      builder.append(getContextRootName());
      builder.append(", ezugi=");
      builder.append(getEzugi());
      builder.append("]");
      return builder.toString();
   }

}