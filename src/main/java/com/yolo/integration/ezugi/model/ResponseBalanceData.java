package com.yolo.integration.ezugi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by peter on 2017/2/6.
 */
public class ResponseBalanceData implements Serializable {

   private static final long serialVersionUID = 655984243424280281L;

   private String id;
   private String agentId;
   private String username;
   private String balance;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getAgentId() {
      return agentId;
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getBalance() {
      return balance;
   }

   public void setBalance(String balance) {
      this.balance = balance;
   }
}
