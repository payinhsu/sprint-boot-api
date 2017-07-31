package com.yolo.integration.ezugi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author peter.hsu
 *
 */
public class Player implements Serializable {

   private static final long serialVersionUID = 5802650945152723403L;

   private String id;
   @JsonProperty("agent_id")
   private String agentId;
   @JsonProperty("operator_id")
   private String operatorId;
   @JsonProperty("user_id")
   private String userId;
   @JsonProperty("player_type")
   private String playerType;
   private String username;
   private String password;
   @JsonProperty("first_name")
   private String firstName;
   @JsonProperty("middle_name")
   private String middleName;
   @JsonProperty("last_name")
   private String lastName;
   private String nickname;
   private String phone1;
   private String phone2;
   @JsonProperty("date_of_birth")
   private String dateOfBirth;
   @JsonProperty("country_code")
   private String countryCode;
   private String address1;
   private String address2;
   private String city;
   private String state;
   private String zipcode;
   @JsonProperty("currency_code")
   private String currencyCode;
   private String language;
   private String balance;
   @JsonProperty("registration_date")
   private String registrationDate;
   @JsonProperty("registration_ip")
   private String registrationIp;
   @JsonProperty("daily_betting_limit")
   private String dailyBettingLimit;
   @JsonProperty("monthly_betting_limit")
   private String monthlyBettingLimit;
   @JsonProperty("personalIdType")
   private String personalIdType;
   @JsonProperty("personal_id_number")
   private String personalIdNumber;
   @JsonProperty("personal_id_country")
   private String personalIdCountry;
   @JsonProperty("personalIdIssue_date")
   private String personalIdIssueDate;
   @JsonProperty("personal_id_exipres")
   private String personalIdExipres;
   @JsonProperty("personal_id_scan")
   private String personalIdScan;
   @JsonProperty("personal_id_scan_extra")
   private String personalIdScanExtra;
   private String terminal;
   @JsonProperty("terminal_ip")
   private String terminalIp;
   @JsonProperty("test_user")
   private String testUser;
   @JsonProperty("vip_level")
   private String vipLevel;
   @JsonProperty("advertiser_id")
   private String advertiserId;
   private String commission;
   @JsonProperty("bet_commission")
   private String betCommission;
   @JsonProperty("ngr_limit_min")
   private String ngrLimitMin;
   @JsonProperty("ngr_limit_max")
   private String ngrLimitMax;
   @JsonProperty("last_login_date")
   private String lastLoginDate;
   @JsonProperty("last_transaction_date")
   private String lastTransactionDate;
   @JsonProperty("last_transaction_id")
   private String lastTransactionId;
   @JsonProperty("vip_by_deposit")
   private String vipByDeposit;
   @JsonProperty("date_change_password")
   private String dateChangePassword;
   private String active;
   @JsonProperty("agents_is_active")
   private String agentsIsActive;
   @JsonProperty("agent_username")
   private String agentUsername;
   @JsonProperty("must_change_password")
   private Boolean mustChangePassword;

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

   public String getOperatorId() {
      return operatorId;
   }

   public void setOperatorId(String operatorId) {
      this.operatorId = operatorId;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getPlayerType() {
      return playerType;
   }

   public void setPlayerType(String playerType) {
      this.playerType = playerType;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
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

   public String getNickname() {
      return nickname;
   }

   public void setNickname(String nickname) {
      this.nickname = nickname;
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

   public String getCountryCode() {
      return countryCode;
   }

   public void setCountryCode(String countryCode) {
      this.countryCode = countryCode;
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

   public String getCurrencyCode() {
      return currencyCode;
   }

   public void setCurrencyCode(String currencyCode) {
      this.currencyCode = currencyCode;
   }

   public String getLanguage() {
      return language;
   }

   public void setLanguage(String language) {
      this.language = language;
   }

   public String getBalance() {
      return balance;
   }

   public void setBalance(String balance) {
      this.balance = balance;
   }

   public String getRegistrationDate() {
      return registrationDate;
   }

   public void setRegistrationDate(String registrationDate) {
      this.registrationDate = registrationDate;
   }

   public String getRegistrationIp() {
      return registrationIp;
   }

   public void setRegistrationIp(String registrationIp) {
      this.registrationIp = registrationIp;
   }

   public String getDailyBettingLimit() {
      return dailyBettingLimit;
   }

   public void setDailyBettingLimit(String dailyBettingLimit) {
      this.dailyBettingLimit = dailyBettingLimit;
   }

   public String getMonthlyBettingLimit() {
      return monthlyBettingLimit;
   }

   public void setMonthlyBettingLimit(String monthlyBettingLimit) {
      this.monthlyBettingLimit = monthlyBettingLimit;
   }

   public String getPersonalIdType() {
      return personalIdType;
   }

   public void setPersonalIdType(String personalIdType) {
      this.personalIdType = personalIdType;
   }

   public String getPersonalIdNumber() {
      return personalIdNumber;
   }

   public void setPersonalIdNumber(String personalIdNumber) {
      this.personalIdNumber = personalIdNumber;
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

   public String getPersonalIdScan() {
      return personalIdScan;
   }

   public void setPersonalIdScan(String personalIdScan) {
      this.personalIdScan = personalIdScan;
   }

   public String getPersonalIdScanExtra() {
      return personalIdScanExtra;
   }

   public void setPersonalIdScanExtra(String personalIdScanExtra) {
      this.personalIdScanExtra = personalIdScanExtra;
   }

   public String getTerminal() {
      return terminal;
   }

   public void setTerminal(String terminal) {
      this.terminal = terminal;
   }

   public String getTerminalIp() {
      return terminalIp;
   }

   public void setTerminalIp(String terminalIp) {
      this.terminalIp = terminalIp;
   }

   public String getTestUser() {
      return testUser;
   }

   public void setTestUser(String testUser) {
      this.testUser = testUser;
   }

   public String getVipLevel() {
      return vipLevel;
   }

   public void setVipLevel(String vipLevel) {
      this.vipLevel = vipLevel;
   }

   public String getAdvertiserId() {
      return advertiserId;
   }

   public void setAdvertiserId(String advertiserId) {
      this.advertiserId = advertiserId;
   }

   public String getCommission() {
      return commission;
   }

   public void setCommission(String commission) {
      this.commission = commission;
   }

   public String getBetCommission() {
      return betCommission;
   }

   public void setBetCommission(String betCommission) {
      this.betCommission = betCommission;
   }

   public String getNgrLimitMin() {
      return ngrLimitMin;
   }

   public void setNgrLimitMin(String ngrLimitMin) {
      this.ngrLimitMin = ngrLimitMin;
   }

   public String getNgrLimitMax() {
      return ngrLimitMax;
   }

   public void setNgrLimitMax(String ngrLimitMax) {
      this.ngrLimitMax = ngrLimitMax;
   }

   public String getLastLoginDate() {
      return lastLoginDate;
   }

   public void setLastLoginDate(String lastLoginDate) {
      this.lastLoginDate = lastLoginDate;
   }

   public String getLastTransactionDate() {
      return lastTransactionDate;
   }

   public void setLastTransactionDate(String lastTransactionDate) {
      this.lastTransactionDate = lastTransactionDate;
   }

   public String getLastTransactionId() {
      return lastTransactionId;
   }

   public void setLastTransactionId(String lastTransactionId) {
      this.lastTransactionId = lastTransactionId;
   }

   public String getVipByDeposit() {
      return vipByDeposit;
   }

   public void setVipByDeposit(String vipByDeposit) {
      this.vipByDeposit = vipByDeposit;
   }

   public String getDateChangePassword() {
      return dateChangePassword;
   }

   public void setDateChangePassword(String dateChangePassword) {
      this.dateChangePassword = dateChangePassword;
   }

   public String getActive() {
      return active;
   }

   public void setActive(String active) {
      this.active = active;
   }

   public String getAgentsIsActive() {
      return agentsIsActive;
   }

   public void setAgentsIsActive(String agentsIsActive) {
      this.agentsIsActive = agentsIsActive;
   }

   public String getAgentUsername() {
      return agentUsername;
   }

   public void setAgentUsername(String agentUsername) {
      this.agentUsername = agentUsername;
   }

   public Boolean getMustChangePassword() {
      return mustChangePassword;
   }

   public void setMustChangePassword(Boolean mustChangePassword) {
      this.mustChangePassword = mustChangePassword;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("Player [id=");
      builder.append(getId());
      builder.append(", agent_id=");
      builder.append(getAgentId());
      builder.append(", operator_id=");
      builder.append(getOperatorId());
      builder.append(", user_id=");
      builder.append(getUserId());
      builder.append(", player_type=");
      builder.append(getPlayerType());
      builder.append(", username=");
      builder.append(getUsername());
      builder.append(", password=");
      builder.append(getPassword());
      builder.append(", first_name=");
      builder.append(getFirstName());
      builder.append(", middle_name=");
      builder.append(getMiddleName());
      builder.append(", last_name=");
      builder.append(getLastName());
      builder.append(", nickname=");
      builder.append(getNickname());
      builder.append(", phone1=");
      builder.append(getPhone1());
      builder.append(", phone2=");
      builder.append(getPhone2());
      builder.append(", date_of_birth=");
      builder.append(getDateOfBirth());
      builder.append(", country_code=");
      builder.append(getCountryCode());
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
      builder.append(", currency_code=");
      builder.append(getCurrencyCode());
      builder.append(", language=");
      builder.append(getLanguage());
      builder.append(", balance=");
      builder.append(getBalance());
      builder.append(", registration_date=");
      builder.append(getRegistrationDate());
      builder.append(", registration_ip=");
      builder.append(getRegistrationIp());
      builder.append(", daily_betting_limit=");
      builder.append(getDailyBettingLimit());
      builder.append(", monthly_betting_limit=");
      builder.append(getMonthlyBettingLimit());
      builder.append(", personal_id_type=");
      builder.append(getPersonalIdType());
      builder.append(", personal_id_number=");
      builder.append(getPersonalIdNumber());
      builder.append(", personal_id_country=");
      builder.append(getPersonalIdCountry());
      builder.append(", personal_id_issue_date=");
      builder.append(getPersonalIdIssueDate());
      builder.append(", personal_id_exipres=");
      builder.append(getPersonalIdExipres());
      builder.append(", personal_id_scan=");
      builder.append(getPersonalIdScan());
      builder.append(", personal_id_scan_extra=");
      builder.append(getPersonalIdScanExtra());
      builder.append(", terminal=");
      builder.append(getTerminal());
      builder.append(", terminal_ip=");
      builder.append(getTerminalIp());
      builder.append(", test_user=");
      builder.append(getTestUser());
      builder.append(", vip_level=");
      builder.append(getVipLevel());
      builder.append(", advertiser_id=");
      builder.append(getAdvertiserId());
      builder.append(", commission=");
      builder.append(getCommission());
      builder.append(", bet_commission=");
      builder.append(getBetCommission());
      builder.append(", ngr_limit_min=");
      builder.append(getNgrLimitMin());
      builder.append(", ngr_limit_max=");
      builder.append(getNgrLimitMax());
      builder.append(", last_login_date=");
      builder.append(getLastLoginDate());
      builder.append(", last_transaction_date=");
      builder.append(getLastTransactionDate());
      builder.append(", last_transaction_id=");
      builder.append(getLastTransactionId());
      builder.append(", vip_by_deposit=");
      builder.append(getVipByDeposit());
      builder.append(", date_change_password=");
      builder.append(getDateChangePassword());
      builder.append(", active=");
      builder.append(getActive());
      builder.append(", agents_is_active=");
      builder.append(getAgentsIsActive());
      builder.append(", agent_username=");
      builder.append(getAgentUsername());
      builder.append(", must_change_password=");
      builder.append(getMustChangePassword());
      builder.append("]");
      return builder.toString();
   }
}
