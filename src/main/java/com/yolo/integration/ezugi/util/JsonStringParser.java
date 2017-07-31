package com.yolo.integration.ezugi.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonElement;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$
 * \$Date\$
 * \$Revision\$
 */
public class JsonStringParser {

   public static String getAsString(JsonElement jsonElement) {
      if(jsonElement == null) {
         return null;
      } else if("null".equals(jsonElement.getAsString())) {
         return null;
      }

      return jsonElement.getAsString();
   }

   public static Boolean getAsBoolean(JsonElement jsonElement) {
      if(jsonElement == null) {
         return null;
      }
      return jsonElement.getAsBoolean();
   }

   public static Integer getAsInt(JsonElement jsonElement) {
      if(jsonElement == null) {
         return null;
      }
      return jsonElement.getAsInt();
   }

   public static Long getAsLong(JsonElement jsonElement) {
      if(jsonElement == null) {
         return null;
      }
      return jsonElement.getAsLong();
   }

   public static Double getAsDouble(JsonElement jsonElement) {
      if(jsonElement == null) {
         return null;
      }
      return jsonElement.getAsDouble();
   }

   public static String asString(JsonNode jsonNode) {
      if(jsonNode == null) {
         return null;
      } else if("null".equals(jsonNode.asText())) {
         return null;
      }
      return jsonNode.asText();
   }

   public static Boolean asBoolean(JsonNode jsonNode) {
      if(jsonNode == null) {
         return null;
      }
      return jsonNode.asBoolean();
   }

   public static Integer asInt(JsonNode jsonNode) {
      if(jsonNode == null) {
         return null;
      }
      return jsonNode.asInt();
   }

   public static Long asLong(JsonNode jsonNode) {
      if(jsonNode == null) {
         return null;
      }
      return jsonNode.asLong();
   }

   public static Double asDouble(JsonNode jsonNode) {
      if(jsonNode == null) {
         return null;
      }
      return jsonNode.asDouble();
   }
}
