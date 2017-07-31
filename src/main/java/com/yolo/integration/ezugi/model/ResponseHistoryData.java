package com.yolo.integration.ezugi.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by peter on 2017/2/6.
 */
public class ResponseHistoryData implements Serializable {

   private static final long serialVersionUID = -7562974089507106219L;
   private ArrayList<HistoryDetail> historys;

   public ArrayList<HistoryDetail> getHistorys() {
      return historys;
   }

   public void setHistorys(ArrayList<HistoryDetail> historys) {
      this.historys = historys;
   }
}
