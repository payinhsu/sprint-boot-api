package com.yolo.integration.ezugi.model;

import java.io.Serializable;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$
 * \$Date\$
 * \$Revision\$
 */
public class ResponseRegisterData implements Serializable {

   private static final long serialVersionUID = -3708205683058571447L;

   private Player player;
   private Session session;

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
}
