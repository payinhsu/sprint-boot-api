/**
 * PlaynGoUser.java created 2016年11月5日
 *
 */
package com.yolo.integration.ezugi.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author peter.hsu
 *
 */
public class EzugiUser implements Serializable {
   //
   private static final long serialVersionUID = -4841392240495609528L;

   private Long agentId;
   private String userName;
   private String playerUsername;
   // between 6..20 characters, must include at least one digit and one
   // character
   private String playerPassword;
   // Nickname of the player. (2-12 letters. a-z A-Z 0-9 - . _ & space
   private String nickName;
   private String sessionIp;
   private String requestToken;
   private String sessionToken;
   private String providerId;
   // If the player has registered via advitiser, provide its id
   private Long advertiserId;
   private String firstName;
   private String middleName;
   private String lastName;
   private String phone1;
   private String phone2;
   private String dateOfBirth;
   private Long dailyBettingLimit;
   private Long monthlyBettingLimit;
   private String language;
   private String personalIdType;
   private String personalIdCountry;
   private String personalIdIssueDate;
   private String personalIdExipres;
   private String address1;
   private String address2;
   private String city;
   private String state;
   private String zipcode;
   private String countryCode;
   private EzugiBalance ezugiBalance;
   // 1 for Cash, 2 for Voucher and 3 for Manual
   private String paymentMethod;
   // 查詢某個game detail的過濾條件
   private String gameName;

   public Long getAgentId() {
      return agentId;
   }

   public void setAgentId(Long agentId) {
      this.agentId = agentId;
   }

   /**
    * Login of the player.
    */
   public String getUserName() {
      return userName;
   }

   public void setUserName(String pUserName) {
      userName = pUserName;
   }

   /**
    * Nickname of the player. (2-12 letters. a-z A-Z 0-9 - . _ & space)
    */
   public String getNickName() {
      return nickName;
   }

   public void setNickName(String pNickName) {
      nickName = pNickName;
   }

   public String getCountryCode() {
      return countryCode;
   }

   public void setCountryCode(String pCountryCode) {
      countryCode = pCountryCode;
   }

   public String getPlayerUsername() {
      return playerUsername;
   }

   public void setPlayerUsername(String playerUsername) {
      this.playerUsername = playerUsername;
   }

   public EzugiBalance getEzugiBalance() {
      return ezugiBalance;
   }

   public void setEzugiBalance(EzugiBalance ezugiBalance) {
      this.ezugiBalance = ezugiBalance;
   }

   public String getPlayerPassword() {
      return playerPassword;
   }

   public void setPlayerPassword(String playerPassword) {
      this.playerPassword = playerPassword;
   }

   public String getSessionIp() {
      return sessionIp;
   }

   public void setSessionIp(String sessionIp) {
      this.sessionIp = sessionIp;
   }

   public Long getAdvertiserId() {
      return advertiserId;
   }

   public void setAdvertiserId(Long advertiserId) {
      this.advertiserId = advertiserId;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getMiddleName() {
      return middleName;
   }

   public void setMiddleName(String middleName) {
      this.middleName = middleName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getPhone1() {
      return phone1;
   }

   public void setPhone1(String phone1) {
      this.phone1 = phone1;
   }

   public String getPhone2() {
      return phone2;
   }

   public void setPhone2(String phone2) {
      this.phone2 = phone2;
   }

   public String getDateOfBirth() {
      return dateOfBirth;
   }

   public void setDateOfBirth(String dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
   }

   public Long getDailyBettingLimit() {
      return dailyBettingLimit;
   }

   public void setDailyBettingLimit(Long dailyBettingLimit) {
      this.dailyBettingLimit = dailyBettingLimit;
   }

   public Long getMonthlyBettingLimit() {
      return monthlyBettingLimit;
   }

   public void setMonthlyBettingLimit(Long monthlyBettingLimit) {
      this.monthlyBettingLimit = monthlyBettingLimit;
   }

   public String getLanguage() {
      return language;
   }

   public void setLanguage(String language) {
      this.language = language;
   }

   public String getPersonalIdType() {
      return personalIdType;
   }

   public void setPersonalIdType(String personalIdType) {
      this.personalIdType = personalIdType;
   }

   public String getRequestToken() {
      return requestToken;
   }

   public void setRequestToken(String requestToken) {
      this.requestToken = requestToken;
   }

   public String getSessionToken() {
      return sessionToken;
   }

   public void setSessionToken(String sessionToken) {
      this.sessionToken = sessionToken;
   }

   public String getProviderId() {
      return providerId;
   }

   public void setProviderId(String providerId) {
      this.providerId = providerId;
   }

   public String getPersonalIdCountry() {
      return personalIdCountry;
   }

   public void setPersonalIdCountry(String personalIdCountry) {
      this.personalIdCountry = personalIdCountry;
   }

   public String getPersonalIdIssueDate() {
      return personalIdIssueDate;
   }

   public void setPersonalIdIssueDate(String personalIdIssueDate) {
      this.personalIdIssueDate = personalIdIssueDate;
   }

   public String getPersonalIdExipres() {
      return personalIdExipres;
   }

   public void setPersonalIdExipres(String personalIdExipres) {
      this.personalIdExipres = personalIdExipres;
   }

   public String getAddress1() {
      return address1;
   }

   public void setAddress1(String address1) {
      this.address1 = address1;
   }

   public String getAddress2() {
      return address2;
   }

   public void setAddress2(String address2) {
      this.address2 = address2;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public String getZipcode() {
      return zipcode;
   }

   public void setZipcode(String zipcode) {
      this.zipcode = zipcode;
   }

   public String getPaymentMethod() {
      return paymentMethod;
   }

   public void setPaymentMethod(String paymentMethod) {
      this.paymentMethod = paymentMethod;
   }

   public String getGameName() {
      return gameName;
   }

   public void setGameName(String gameName) {
      this.gameName = gameName;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("EzugiUser [agentId=");
      builder.append(getAgentId());
      builder.append(", userName=");
      builder.append(getUserName());
      builder.append(", playerUsername=");
      builder.append(getPlayerUsername());
      builder.append(", playerPassword=");
      builder.append(getPlayerPassword());
      builder.append(", nickName=");
      builder.append(getNickName());
      builder.append(", sessionIp=");
      builder.append(getSessionIp());
      builder.append(", requestToken=");
      builder.append(getRequestToken());
      builder.append(", sessionToken=");
      builder.append(getSessionToken());
      builder.append(", providerId=");
      builder.append(getProviderId());
      builder.append(", advertiserId=");
      builder.append(getAdvertiserId());
      builder.append(", firstName=");
      builder.append(getFirstName());
      builder.append(", middleName=");
      builder.append(getMiddleName());
      builder.append(", lastName=");
      builder.append(getLastName());
      builder.append(", phone1=");
      builder.append(getPhone1());
      builder.append(", phone2=");
      builder.append(getPhone2());
      builder.append(", dateOfBirth=");
      builder.append(getDateOfBirth());
      builder.append(", dailyBettingLimit=");
      builder.append(getDailyBettingLimit());
      builder.append(", monthlyBettingLimit=");
      builder.append(getMonthlyBettingLimit());
      builder.append(", language=");
      builder.append(getLanguage());
      builder.append(", personalIdType=");
      builder.append(getPersonalIdType());
      builder.append(", personalIdCountry=");
      builder.append(getPersonalIdCountry());
      builder.append(", personalIdIssueDate=");
      builder.append(getPersonalIdIssueDate());
      builder.append(", personalIdExipres=");
      builder.append(getPersonalIdExipres());
      builder.append(", address1=");
      builder.append(getAddress1());
      builder.append(", address2=");
      builder.append(getAddress2());
      builder.append(", city=");
      builder.append(getCity());
      builder.append(", state=");
      builder.append(getState());
      builder.append(", zipcode=");
      builder.append(getZipcode());
      builder.append(", countryCode=");
      builder.append(getCountryCode());
      builder.append(", paymentMethod=");
      builder.append(getPaymentMethod());
      builder.append(", ezugiBalance=");
      builder.append(getEzugiBalance());
      builder.append(", gameName=");
      builder.append(getGameName());
      builder.append("]");
      return builder.toString();
   }

}
