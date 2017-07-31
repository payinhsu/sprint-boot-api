package com.yolo.integration.ezugi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProviderParams implements Serializable {

   private static final long serialVersionUID = 2331142010699510106L;
   @JsonProperty("OperatorID")
   private Integer operatorID;

   public Integer getOperatorID() {
      return operatorID;
   }

   @JsonProperty("OperatorID")
   public void setOperatorID(Integer operatorID) {
      this.operatorID = operatorID;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ProviderParams [operatorID=");
      if (getOperatorID() != null) {
         builder.append(getOperatorID().toString());
      }
      builder.append("]");
      return builder.toString();
   }
}
