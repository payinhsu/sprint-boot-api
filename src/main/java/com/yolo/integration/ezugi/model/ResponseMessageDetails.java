package com.yolo.integration.ezugi.model;

import java.io.Serializable;

public class ResponseMessageDetails implements Serializable {

   private static final long serialVersionUID = -7010361944676435879L;

   private String details;
   private String error;
   private String title;
   private String message;

   public ResponseMessageDetails() {
   }

   public ResponseMessageDetails(String details) {
      this.setDetails(details);
   }

   public ResponseMessageDetails(String error, String title, String message) {
      this.setError(error);
      this.setTitle(title);
      this.setMessage(message);
   }

   public String getDetails() {
      return details;
   }

   public void setDetails(String details) {
      this.details = details;
   }

   public String getError() {
      return error;
   }

   public void setError(String error) {
      this.error = error;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Details [details=");
      builder.append(getDetails());
      builder.append(", error=");
      builder.append(getError());
      builder.append(", title=");
      builder.append(getTitle());
      builder.append(", message=");
      builder.append(getMessage());
      builder.append("]");
      return builder.toString();
   }

}
