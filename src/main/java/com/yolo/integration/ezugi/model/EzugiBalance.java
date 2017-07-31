package com.yolo.integration.ezugi.model;

import java.io.Serializable;

/**
 * @author peter.hsu
 *
 */
public final class EzugiBalance implements Serializable {

   private static final long serialVersionUID = 5128714634310757803L;
   private String sn; // ExternalTransactionId
   private Double total;
   private Double transferAmount;
   private Long exTransId; // web service transactionId

   /**
    * Ezugi 帳戶金額
    */
   public Double getTotal() {
      return total;
   }

   public void setTotal(Double pTotal) {
      total = pTotal;
   }

   /**
    * 此次交易轉帳金額
    */
   public Double getTransferAmount() {
      return transferAmount;
   }

   public void setTransferAmount(Double pTransferAmount) {
      transferAmount = pTransferAmount;
   }

   public String getSn() {
      return sn;
   }

   public void setSn(String sn) {
      this.sn = sn;
   }

   public Long getExTransId() {
      return exTransId;
   }

   public void setExTransId(Long exTransId) {
      this.exTransId = exTransId;
   }

   @Override
   public String toString() {
      return "EzugiBalance [total=" + getTotal() + ", transferAmount=" + getTransferAmount() + "]";
   }
}
