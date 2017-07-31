package com.yolo.integration.ezugi.model.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yolo.integration.ezugi.exception.EzugiError;

import java.io.Serializable;
import java.util.Arrays;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
public class ResponseAllPlayers implements Serializable {

   private static final long serialVersionUID = 1206374606459371890L;

   @JsonProperty("total_rows")
   private String totalRows;
   @JsonProperty("page_number")
   private Integer pageNumber;
   @JsonProperty("rows_per_page")
   private Integer rowsPerPage;

   private String duration;
   @JsonProperty("request_timestamp")
   private Integer requestTimestamp;
   @JsonProperty("request_date")
   private String requestDate;

   private String filters;
   @JsonProperty("access_list")
   private String accessList;

   private String control;

   private AllPlayersData[] data;

   private String dataset;

   private String format;

   private Boolean compression;

   private String errorCode;

   private String error;

   public String getTotalRows() {
      return totalRows;
   }

   public void setTotalRows(String totalRows) {
      this.totalRows = totalRows;
   }

   public Integer getPageNumber() {
      return pageNumber;
   }

   public void setPageNumber(Integer pageNumber) {
      this.pageNumber = pageNumber;
   }

   public Integer getRowsPerPage() {
      return rowsPerPage;
   }

   public void setRowsPerPage(Integer rowsPerPage) {
      this.rowsPerPage = rowsPerPage;
   }

   public String getDuration() {
      return duration;
   }

   public void setDuration(String duration) {
      this.duration = duration;
   }

   public Integer getRequestTimestamp() {
      return requestTimestamp;
   }

   public void setRequestTimestamp(Integer requestTimestamp) {
      this.requestTimestamp = requestTimestamp;
   }

   public String getRequestDate() {
      return requestDate;
   }

   public void setRequestDate(String requestDate) {
      this.requestDate = requestDate;
   }

   public String getFilters() {
      return filters;
   }

   public void setFilters(String filters) {
      this.filters = filters;
   }

   public String getAccessList() {
      return accessList;
   }

   public void setAccessList(String accessList) {
      this.accessList = accessList;
   }

   public String getControl() {
      return control;
   }

   public void setControl(String control) {
      this.control = control;
   }

   public AllPlayersData[] getData() {
      return data;
   }

   public void setData(AllPlayersData[] data) {
      this.data = data;
   }

   public String getDataset() {
      return dataset;
   }

   public void setDataset(String dataset) {
      this.dataset = dataset;
   }

   public String getFormat() {
      return format;
   }

   public void setFormat(String format) {
      this.format = format;
   }

   public Boolean getCompression() {
      return compression;
   }

   public void setCompression(Boolean compression) {
      this.compression = compression;
   }

   public String getErrorCode() {
      return errorCode;
   }

   @JsonProperty("ErrorCode")
   public void setErrorCode(int errorCode) {
      if (errorCode == EzugiError.OK.getReturnCode()) {
         this.errorCode = EzugiError.OK.getErrorCode();
         setError(EzugiError.OK.getErrorDesc());
      } else if (errorCode == EzugiError.AUTH_ERROR.getReturnCode()) {
         this.errorCode = EzugiError.AUTH_ERROR.getErrorCode();
         setError(EzugiError.AUTH_ERROR.getErrorDesc());
      } else if (errorCode == EzugiError.INVALID_REQ.getReturnCode()) {
         this.errorCode = EzugiError.INVALID_REQ.getErrorCode();
         setError(EzugiError.INVALID_REQ.getErrorDesc());
      }
   }

   public String getError() {
      return error;
   }

   public void setError(String error) {
      this.error = error;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("ResponseAllPlayers [totalRows=");
      builder.append(getTotalRows());
      builder.append(", pageNumber=");
      builder.append(getPageNumber());
      builder.append(", rowsPerPage=");
      builder.append(getRowsPerPage());
      builder.append(", duration=");
      builder.append(getDuration());
      builder.append(", requestTimestamp=");
      builder.append(getRequestTimestamp());
      builder.append(", requestDate=");
      builder.append(getRequestDate());
      builder.append(", filters=");
      builder.append(getFilters());
      builder.append(", access_list=");
      builder.append(getAccessList());
      builder.append(", control=");
      builder.append(getControl());
      builder.append(", data=");
      if (getData() != null) {
         builder.append(Arrays.toString(getData()));
      }
      builder.append(", dataset=");
      builder.append(getDataset());
      builder.append(", format=");
      builder.append(getFormat());
      builder.append(", compression=");
      builder.append(getCompression());
      builder.append(", error_code=");
      builder.append(getErrorCode());
      builder.append(", errorDesc=");
      builder.append(getError());
      builder.append("]");
      return builder.toString();
   }
}
