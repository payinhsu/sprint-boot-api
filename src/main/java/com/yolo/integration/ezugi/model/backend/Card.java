package com.yolo.integration.ezugi.model.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
public class Card implements Serializable {

   private static final long serialVersionUID = 2604031788359969915L;

   @JsonProperty("CardName")
   private String cardName;
   @JsonProperty("CardValue")
   private Integer cardValue;

   public String getCardName() {
      return cardName;
   }

   public void setCardName(String cardName) {
      this.cardName = cardName;
   }

   public Integer getCardValue() {
      return cardValue;
   }

   public void setCardValue(Integer cardValue) {
      this.cardValue = cardValue;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Card [cardName=");
      builder.append(getCardName());
      builder.append(", cardValue=");
      builder.append(getCardValue());
      builder.append("]");
      return builder.toString();
   }

}
