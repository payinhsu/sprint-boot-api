package com.yolo.integration.ezugi.model;

import java.io.Serializable;

/**
 * @author peter.hsu
 *
 */
public final class EzugiLoginForm extends EzugiUser implements Serializable {

   private static final long serialVersionUID = 6425043746985359191L;
   private String login;

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

}
