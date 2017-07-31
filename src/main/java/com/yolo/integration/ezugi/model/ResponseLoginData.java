package com.yolo.integration.ezugi.model;

import java.io.Serializable;

public class ResponseLoginData implements Serializable {

   private static final long serialVersionUID = 3650197040830209538L;

   private Player player;
   private Session session;
   private String[] lobby;
   private ProviderGame[] games;
   private Provider[] providers;

   public Player getPlayer() {
      return player;
   }

   public void setPlayer(Player player) {
      this.player = player;
   }

   public Session getSession() {
      return session;
   }

   public void setSession(Session session) {
      this.session = session;
   }

   public String[] getLobby() {
      return lobby;
   }

   public void setLobby(String[] lobby) {
      this.lobby = lobby;
   }

   public ProviderGame[] getGames() {
      return games;
   }

   public void setGames(ProviderGame[] games) {
      this.games = games;
   }

   public Provider[] getProviders() {
      return providers;
   }

   public void setProviders(Provider[] providers) {
      this.providers = providers;
   }

}
