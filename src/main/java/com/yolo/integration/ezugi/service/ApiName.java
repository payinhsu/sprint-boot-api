package com.yolo.integration.ezugi.service;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$
 * \$Date\$
 * \$Revision\$
 */
public enum ApiName {
   REGISTER("register"),
   LOGIN("login"),
   CREDIT("credit"),
   DEBIT("debit"),
   GAME_TOKEN("gameToken"),
   BALANCE("balance"),
   HISTORY("history")
   ;

   ApiName(String name) {
   }
}
