/**
 * PlaynGoException.java created 2016年11月5日
 *
 * \$LastChangedBy\$
 * \$Date\$
 * \$Revision\$
 */
package com.yolo.integration.ezugi.service;

import com.poseitech.exceptions.RootException;
import com.poseitech.lang.Assert;

/**
 * @author bj
 *
 */
public class EzugiException extends RootException {

   /**
    * 
    */
   private static final long serialVersionUID = -5897968742534289059L;

   final private String apiUrl;

   final private String message;

   /**
    * @param pErrorCode
    * @param pMessage
    */
   public EzugiException(String pErrorCode, String pMessage, String aApiUrl) {
      super(pErrorCode, pMessage);

      Assert.notNull(aApiUrl, "Ezugi reqeust api url must not be null.");

      this.apiUrl = aApiUrl;
      this.message = pMessage;
      appendMessage("Ezugi api url: " + this.apiUrl);
   }

   /**
    * @param pErrorCode
    * @param pMessage
    * @param pCause
    */
   public EzugiException(String pErrorCode, String pMessage, String aApiUrl, Throwable pCause) {
      super(pErrorCode, pMessage, pCause);

      Assert.notNull(aApiUrl, "Ezugi reqeust api url must not be null.");

      this.apiUrl = aApiUrl;
      this.message = pMessage;
      appendMessage("Ezugi api url: " + this.apiUrl);
   }

   @Override
   public String getMessage() {
      return message;
   }
}
