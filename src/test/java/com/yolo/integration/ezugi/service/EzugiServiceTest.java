package com.yolo.integration.ezugi.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.poseitech.exceptions.ErrorCodeResovler;
import com.poseitech.integration.message.ResponsePayload;
import com.yolo.integration.ezugi.model.*;
import com.yolo.integration.ezugi.model.backend.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yolo.integration.ezugi.config.MicroserviceApplication;
import com.yolo.integration.ezugi.exception.EzugiError;
import com.yolo.integration.ezugi.util.SHA2Hash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Sample junit test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MicroserviceApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EzugiServiceTest {

   private static final Logger log = LoggerFactory.getLogger(EzugiServiceTest.class);

   @Autowired
   private EzugiService ezugiService;

   @Value("${apiSalt}")
   private String apiSalt;
   @Value("${backendApiSalt}")
   private String backendApiSalt;
   @Value("${apiCallerIp}")
   private String apiCallerIp;

   private static String sessionToken;

   /**
    * 正向測試register已註冊過的使用者
    */
   @Ignore
   public void stage1_1_testRegisterSuccess() {
      try {
         ResponsePayload<ResponseRegisterData> payload = new ResponsePayload<ResponseRegisterData>();
         EzugiUser ezugiUser = new EzugiUser();

         String playerUsername = "test990123";
         String playerPassword = "test123";

         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         ezugiUser.setSessionIp(apiCallerIp);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.register(ezugiUser,payload);
         assertTrue(payload.getErrorCode().equals(EzugiError.OK.getErrorCode()));
         assertEquals(EzugiError.OK.getErrorDesc(), payload.getErrorDesc());
      } catch (Exception e) {
         log.error("UnitTest[testRegisterSuccess] error:\n", e);
      }
   }

   /**
    * 反向測試register已註冊過的使用者
    */
   @Test
   public void stage1_2_testRegisterDuplicateUser() {
      try {
         ResponsePayload<ResponseRegisterData> payload = new ResponsePayload<ResponseRegisterData>();
         EzugiUser ezugiUser = new EzugiUser();

         String playerUsername = "test10123";
         String playerPassword = "test123";

         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         ezugiUser.setSessionIp(apiCallerIp);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.register(ezugiUser,payload);
         assertTrue(payload.getErrorCode().equals(EzugiError.PLAYER_REGISTER_ERROR.getErrorCode()));
         assertEquals(EzugiError.PLAYER_REGISTER_ERROR.getErrorDesc(), payload.getErrorDesc());

      } catch (Exception e) {
         log.error("UnitTest[testRegisterDuplicateUser] error:\n", e);
      }
   }

   /**
    * 正向測試登入成功
    */
   @Test
   public void stage1_3_testLoginSuccess() {
      try {
         ResponsePayload<ResponseLoginData> payload = new ResponsePayload<ResponseLoginData>();
         EzugiLoginForm ezugiLoginForm = new EzugiLoginForm();

         String playerUsername = "test10123";
         String playerPassword = "test123";

         ezugiLoginForm.setPlayerUsername(playerUsername);
         ezugiLoginForm.setPlayerPassword(playerPassword);
         ezugiLoginForm.setSessionIp(apiCallerIp);
         String requestToken = getRequestToken(apiSalt, null, ezugiLoginForm);
         ezugiLoginForm.setRequestToken(requestToken);
         payload = ezugiService.login(ezugiLoginForm,payload);

         sessionToken = payload.getData().getSession().getSessionToken();
         assertTrue(payload.getErrorCode().equals(EzugiError.OK.getErrorCode()));

      } catch (Exception e) {
         log.error("UnitTest[testLoginSuccess] error:\n", e);
      }
   }

   /**
    * 反向測試登入失敗-player not found
    */
   @Test
   public void stage1_4_testLoginFail() {
      try {
         ResponsePayload<ResponseLoginData> payload = new ResponsePayload<ResponseLoginData>();
         EzugiLoginForm ezugiLoginForm = new EzugiLoginForm();
         String playerUsername = "test0123";
         String playerPassword = "test123";

         ezugiLoginForm.setPlayerUsername(playerUsername);
         ezugiLoginForm.setPlayerPassword(playerPassword);
         ezugiLoginForm.setSessionIp(apiCallerIp);
         String requestToken = getRequestToken(apiSalt, null, ezugiLoginForm);
         ezugiLoginForm.setRequestToken(requestToken);
         payload = ezugiService.login(ezugiLoginForm,payload);

         assertTrue(payload.getErrorCode().equals(EzugiError.LOGIN_FAIL.getErrorCode()));

      } catch (Exception e) {
         log.error("UnitTest[testLoginFail] error:\n", e);
      }
   }

   /**
    * 正向測試credit扣款成功
    */
   @Test
   public void stage1_5_testCreditSuccess() {
      try {
         ResponsePayload<ResponseTransferFundData> payload = new ResponsePayload<ResponseTransferFundData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "test123";
         Double transferAmount = 2.5;
         EzugiBalance ezugiBalance = new EzugiBalance();
         ezugiBalance.setTransferAmount(transferAmount);
         ezugiUser.setEzugiBalance(ezugiBalance);
         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.credit(ezugiUser,payload);

         assertTrue(payload.getErrorCode().equals(EzugiError.OK.getErrorCode()));
         assertEquals(EzugiError.OK.getErrorDesc(), payload.getErrorDesc());
      } catch (Exception e) {
         log.error("UnitTest[testCreditSuccess] error:\n", e);
      }
   }

   /**
    * 反向測試credit扣款失敗
    */
   @Test
   public void stage1_6_testCreditFail() {
      try {
         ResponsePayload<ResponseTransferFundData> payload = new ResponsePayload<ResponseTransferFundData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "test123";
         Double transferAmount = 900000000.0;
         String sToken = sessionToken == null ? "" : sessionToken;
         EzugiBalance ezugiBalance = new EzugiBalance();
         ezugiBalance.setTransferAmount(transferAmount);
         ezugiUser.setEzugiBalance(ezugiBalance);
         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.credit(ezugiUser,payload);

         assertTrue(payload.getErrorCode().equals(EzugiError.TRANSFER_FAIL.getErrorCode()));
         assertEquals(EzugiError.TRANSFER_FAIL.getErrorDesc(), payload.getErrorDesc());
      } catch (Exception e) {
         log.error("UnitTest[testCreditFail] error:\n", e);
      }
   }

   /**
    * 正向測試debit存款成功
    */
   @Test
   public void stage1_7_testDebitSuccess() {
      try {
         ResponsePayload<ResponseTransferFundData> payload = new ResponsePayload<ResponseTransferFundData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "test123";
         Double transferAmount = 3.5;
         EzugiBalance ezugiBalance = new EzugiBalance();
         ezugiBalance.setTransferAmount(transferAmount);
         ezugiUser.setEzugiBalance(ezugiBalance);
         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.debit(ezugiUser,payload);

         assertTrue(payload.getErrorCode().equals(EzugiError.OK.getErrorCode()));
         assertEquals(EzugiError.OK.getErrorDesc(), payload.getErrorDesc());
      } catch (Exception e) {
         log.error("UnitTest[testDebitSuccess] error:\n", e);
      }
   }

   /**
    * 反向測試debit存款失敗
    */
   @Test
   public void stage1_8_testDebitFail() {
      try {
         ResponsePayload<ResponseTransferFundData> payload = new ResponsePayload<ResponseTransferFundData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "test123";
         Double transferAmount = -1.0;
         EzugiBalance ezugiBalance = new EzugiBalance();
         ezugiBalance.setTransferAmount(transferAmount);
         ezugiUser.setEzugiBalance(ezugiBalance);
         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.debit(ezugiUser,payload);
         assertTrue(payload.getErrorCode().equals(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS));
      } catch (Exception e) {
         log.error("UnitTest[testDebitFail] error:\n", e);
      }
   }

   /**
    * 正向測試game token api
    */
   @Test
   public void stage1_9_testGameTokenSuccess() {
      try {
         ResponsePayload<ResponseGameTokenData> payload = new ResponsePayload<ResponseGameTokenData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "test123";
         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.getGameToken(ezugiUser,payload);

         assertTrue(payload.getErrorCode().equals(EzugiError.OK.getErrorCode()));
         assertEquals(EzugiError.OK.getErrorDesc(), payload.getErrorDesc());
      } catch (Exception e) {
         log.error("UnitTest[testGameTokenSuccess] error:\n", e);
      }
   }

   /**
    * 正向測試get balance api
    */
   @Test
   public void stage2_2_testGetBalanceSuccess() {
      try {
         ResponsePayload<ResponseBalanceData> payload = new ResponsePayload<ResponseBalanceData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "test123";
         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.getBalance(ezugiUser,payload);
         assertTrue(payload.getErrorCode().equals(EzugiError.OK.getErrorCode()));
         assertEquals(EzugiError.OK.getErrorDesc(), payload.getErrorDesc());
      } catch (Exception e) {
         log.error("UnitTest[testGetBalanceSuccess] error:\n", e);
      }
   }

   /**
    * 反向測試get balance api
    */
   @Test
   public void stage2_3_testGetBalanceFail() {
      try {
         ResponsePayload<ResponseBalanceData> payload = new ResponsePayload<ResponseBalanceData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "";
         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.getBalance(ezugiUser,payload);
         assertTrue(payload.getErrorCode().equals(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS));
      } catch (Exception e) {
         log.error("UnitTest[stage12_testGetBalanceFail] error:\n", e);
      }
   }

   /**
    * 正向測試get history api
    */
   @Test
   public void stage2_4_testGetHistorySuccess() {
      try {
         ResponsePayload<ResponseHistoryData> payload = new ResponsePayload<ResponseHistoryData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "test123";

         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.getHistory(ezugiUser,payload);
         assertTrue(payload.getErrorCode().equals(EzugiError.OK.getErrorCode()));
         assertEquals(EzugiError.OK.getErrorDesc(), payload.getErrorDesc());
      } catch (Exception e) {
         log.error("UnitTest[testGetHistorySuccess] error:\n", e);
      }
   }

   /**
    * 反向測試get history api
    */
   @Test
   public void stage2_5_testGetHistoryFail() {
      try {
         ResponsePayload<ResponseHistoryData> payload = new ResponsePayload<ResponseHistoryData>();
         EzugiUser ezugiUser = new EzugiUser();
         String playerUsername = "test10123";
         String playerPassword = "";

         ezugiUser.setPlayerUsername(playerUsername);
         ezugiUser.setPlayerPassword(playerPassword);
         String requestToken = getRequestToken(apiSalt, ezugiUser, null);
         ezugiUser.setRequestToken(requestToken);
         payload = ezugiService.getHistory(ezugiUser,payload);
         assertTrue(payload.getErrorCode().equals(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS));
      } catch (Exception e) {
         log.error("UnitTest[testGetHistoryFail] error:\n", e);
      }
   }

   /**
    * 正向測試get gameRounds api
    */
   @Test
   public void stage2_6_testGetGameRoundsSuccess() {
      try {
         ResponsePayload<ResponseGameRounds> payload = new ResponsePayload<ResponseGameRounds>();
         RequestGameRounds requestGameRounds = new RequestGameRounds();
         String requestToken = getRequestToken(backendApiSalt, requestGameRounds, null, null, null);
         requestGameRounds.setRequestToken(requestToken);
         payload = ezugiService.getGameRounds(requestGameRounds,payload);
         assertTrue("game_rounds".equals(payload.getData().getDataset()));
      } catch (Exception e) {
         log.error("UnitTest[testGetGameRoundsSuccess] error:\n", e);
      }
   }

   /**
    * 正向測試get allPlayers api
    */
   @Test
   public void stage2_7_testGetAllPlayersSuccess() {
      try {
         ResponsePayload<ResponseAllPlayers> payload = new ResponsePayload<ResponseAllPlayers>();
         RequestAllPlayers requestAllPlayers = new RequestAllPlayers();
         String requestToken = getRequestToken(backendApiSalt, null, requestAllPlayers, null, null);
         requestAllPlayers.setRequestToken(requestToken);
         payload = ezugiService.getAllPlayers(requestAllPlayers,payload);
         assertTrue("all_players".equals(payload.getData().getDataset()));
      } catch (Exception e) {
         log.error("UnitTest[testGetAllPlayersSuccess] error:\n", e);
      }
   }

   /**
    * 正向測試get activePlayers api
    */
   @Test
   public void stage2_8_testGetActivePlayersSuccess() {
      try {
         ResponsePayload<ResponseActivePlayers> payload = new ResponsePayload<ResponseActivePlayers>();
         RequestActivePlayers requestActivePlayers = new RequestActivePlayers();
         String requestToken = getRequestToken(backendApiSalt, null, null, requestActivePlayers, null);
         requestActivePlayers.setRequestToken(requestToken);
         payload = ezugiService.getActivePlayres(requestActivePlayers,payload);
         assertTrue("active_players".equals(payload.getData().getDataset()));
      } catch (Exception e) {
         log.error("UnitTest[testGetActivePlayersSuccess] error:\n", e);
      }
   }

   /**
    * 正向測試get revenueByGameType api
    */
   @Test
   public void stage2_9_testGetRevenueByGameTypeSuccess() {
      try {
         ResponsePayload<ResponseRevenueByGameType> payload = new ResponsePayload<ResponseRevenueByGameType>();
         RequestRevenueByGameType requestRevenueByGameType = new RequestRevenueByGameType();
         String requestToken = getRequestToken(backendApiSalt, null, null, null, requestRevenueByGameType);
         requestRevenueByGameType.setRequestToken(requestToken);
         payload = ezugiService.getRevenueByGameType(requestRevenueByGameType,payload);
         assertTrue("revenue_by_game_type".equals(payload.getData().getDataset()));
      } catch (Exception e) {
         log.error("UnitTest[testGetRevenueByGameTypeSuccess] error:\n", e);
      }
   }

   private String getRequestToken(String apiSalt, EzugiUser ezugiUser, EzugiLoginForm ezugiLoginForm) throws Exception {
      // init data
      SHA2Hash sha2 = new SHA2Hash();
      StringBuffer sb = new StringBuffer();
      String accessToken = null;

      sb.append(apiSalt);
      if (ezugiUser != null && ezugiUser.getAgentId() != null
            && StringUtils.isNotEmpty(ezugiUser.getAgentId().toString())) {
         sb.append("agent_id=").append(ezugiUser.getAgentId().toString());
      } else if (ezugiLoginForm != null && ezugiLoginForm.getAgentId() != null
            && StringUtils.isNotEmpty(ezugiLoginForm.getAgentId().toString())) {
         sb.append("agent_id=").append(ezugiLoginForm.getAgentId().toString());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPlayerUsername())) {
         sb.append("&player_username=").append(ezugiUser.getPlayerUsername());
      } else if (ezugiLoginForm != null && StringUtils.isNotEmpty(ezugiLoginForm.getPlayerUsername())) {
         sb.append("&player_username=").append(ezugiLoginForm.getPlayerUsername());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPlayerPassword())) {
         sb.append("&player_password=").append(ezugiUser.getPlayerPassword());
      } else if (ezugiLoginForm != null && StringUtils.isNotEmpty(ezugiLoginForm.getPlayerPassword())) {
         sb.append("&player_password=").append(ezugiLoginForm.getPlayerPassword());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getUserName())) {
         sb.append("&username=").append(ezugiUser.getUserName());
      } else if (ezugiLoginForm != null && StringUtils.isNotEmpty(ezugiLoginForm.getUserName())) {
         sb.append("&username=").append(ezugiLoginForm.getUserName());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getNickName())) {
         sb.append("&nickname=").append(ezugiUser.getNickName());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getSessionIp())) {
         sb.append("&session_ip=").append(ezugiUser.getSessionIp());
      } else if (ezugiLoginForm != null && StringUtils.isNotEmpty(ezugiLoginForm.getSessionIp())) {
         sb.append("&session_ip=").append(ezugiLoginForm.getSessionIp());
      }

      if (ezugiLoginForm != null && StringUtils.isNotEmpty(ezugiLoginForm.getLogin())) {
         sb.append("&login=").append(ezugiLoginForm.getLogin());
      }

      if (ezugiUser != null && ezugiUser.getAdvertiserId() != null
            && StringUtils.isNotEmpty(ezugiUser.getAdvertiserId().toString())) {
         sb.append("&advertiser_id=").append(ezugiUser.getAdvertiserId());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getFirstName())) {
         sb.append("&first_name=").append(ezugiUser.getFirstName());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getMiddleName())) {
         sb.append("&middle_name=").append(ezugiUser.getMiddleName());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getLastName())) {
         sb.append("&last_name=").append(ezugiUser.getLastName());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPhone1())) {
         sb.append("&phone1=").append(ezugiUser.getPhone1());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPhone2())) {
         sb.append("&phone2=").append(ezugiUser.getPhone2());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getDateOfBirth())) {
         ezugiUser.setDateOfBirth(parseDateByDateFormat(ezugiUser.getDateOfBirth()));
         sb.append("&date_of_birth=").append(ezugiUser.getDateOfBirth());
      }

      if (ezugiUser != null && ezugiUser.getDailyBettingLimit() != null
            && StringUtils.isNotEmpty(ezugiUser.getDailyBettingLimit().toString())) {
         sb.append("&daily_betting_limit=").append(ezugiUser.getDailyBettingLimit());
      }

      if (ezugiUser != null && ezugiUser.getMonthlyBettingLimit() != null
            && StringUtils.isNotEmpty(ezugiUser.getMonthlyBettingLimit().toString())) {
         sb.append("&monthly_betting_limit=").append(ezugiUser.getMonthlyBettingLimit());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getLanguage())) {
         sb.append("&language=").append(ezugiUser.getLanguage());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPersonalIdType())) {
         sb.append("&personal_id_type=").append(ezugiUser.getPersonalIdType());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPersonalIdCountry())) {
         sb.append("&personal_id_country=").append(ezugiUser.getPersonalIdCountry());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPersonalIdIssueDate())) {
         ezugiUser.setPersonalIdIssueDate(parseDateByDateFormat(ezugiUser.getPersonalIdIssueDate()));
         sb.append("&personal_id_issue_date=").append(ezugiUser.getPersonalIdIssueDate());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPersonalIdExipres())) {
         ezugiUser.setPersonalIdExipres(parseDateByDateFormat(ezugiUser.getPersonalIdExipres()));
         sb.append("&personal_id_exipres=").append(ezugiUser.getPersonalIdExipres());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getAddress1())) {
         sb.append("&address1=").append(ezugiUser.getAddress1());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getAddress2())) {
         sb.append("&address2=").append(ezugiUser.getAddress2());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getCity())) {
         sb.append("&city=").append(ezugiUser.getCity());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getState())) {
         sb.append("&state=").append(ezugiUser.getState());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getZipcode())) {
         sb.append("&zipcode=").append(ezugiUser.getZipcode());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getCountryCode())) {
         sb.append("&country_code=").append(ezugiUser.getCountryCode());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getPaymentMethod())) {
         sb.append("&payment_method=").append(ezugiUser.getPaymentMethod());
      }

      if (ezugiUser != null && ezugiUser.getEzugiBalance() != null
            && StringUtils.isNotEmpty(ezugiUser.getEzugiBalance().getTransferAmount().toString())) {
         sb.append("&amount=").append(ezugiUser.getEzugiBalance().getTransferAmount());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getSessionToken())) {
         sb.append("&session_token=").append(ezugiUser.getSessionToken());
      }

      if (ezugiUser != null && StringUtils.isNotEmpty(ezugiUser.getProviderId())) {
         sb.append("&provider_id=").append(ezugiUser.getProviderId());
      }

      accessToken = sha2.toHash(sb.toString());

      return accessToken;
   }

   private String getRequestToken(String apiSalt, RequestGameRounds requestGameRounds, RequestAllPlayers requestAllPlayers, RequestActivePlayers requestActivePlayers, RequestRevenueByGameType requestRevenueByGameType) throws Exception {
      // init data
      SHA2Hash sha2 = new SHA2Hash();
      StringBuffer sb = new StringBuffer();

      sb.append(apiSalt);
      if(requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getDataSet())){
         sb.append("DataSet=").append(requestGameRounds.getDataSet());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getDataSet())) {
         sb.append("DataSet=").append(requestAllPlayers.getDataSet());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getDataSet())) {
         sb.append("DataSet=").append(requestActivePlayers.getDataSet());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getDataSet())) {
         sb.append("DataSet=").append(requestRevenueByGameType.getDataSet());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getApiID())) {
         sb.append("&APIID=").append(requestGameRounds.getApiID());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getApiID())) {
         sb.append("&APIID=").append(requestAllPlayers.getApiID());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getApiID())) {
         sb.append("&APIID=").append(requestActivePlayers.getApiID());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getApiID())) {
         sb.append("&APIID=").append(requestRevenueByGameType.getApiID());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getApiUser())) {
         sb.append("&APIUser=").append(requestGameRounds.getApiUser());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getApiUser())) {
         sb.append("&APIUser=").append(requestAllPlayers.getApiUser());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getApiUser())) {
         sb.append("&APIUser=").append(requestActivePlayers.getApiUser());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getApiUser())) {
         sb.append("&APIUser=").append(requestRevenueByGameType.getApiUser());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getTimePeriod())) {
         sb.append("&TimePeriod=").append(requestGameRounds.getTimePeriod());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getTimePeriod())) {
         sb.append("&TimePeriod=").append(requestAllPlayers.getTimePeriod());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getTimePeriod())) {
         sb.append("&TimePeriod=").append(requestActivePlayers.getTimePeriod());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getTimePeriod())) {
         sb.append("&TimePeriod=").append(requestRevenueByGameType.getTimePeriod());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getStartTime())) {
         sb.append("&StartTime=").append(requestGameRounds.getStartTime());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getStartTime())) {
         sb.append("&StartTime=").append(requestAllPlayers.getStartTime());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getStartTime())) {
         sb.append("&StartTime=").append(requestActivePlayers.getStartTime());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getStartTime())) {
         sb.append("&StartTime=").append(requestRevenueByGameType.getStartTime());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getEndTime())) {
         sb.append("&EndTime=").append(requestGameRounds.getEndTime());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getEndTime())) {
         sb.append("&EndTime=").append(requestAllPlayers.getEndTime());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getEndTime())) {
         sb.append("&EndTime=").append(requestActivePlayers.getEndTime());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getEndTime())) {
         sb.append("&EndTime=").append(requestRevenueByGameType.getEndTime());
      }

      if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getCurrency())) {
         sb.append("&Currency=").append(requestRevenueByGameType.getCurrency());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getRoundID())) {
         sb.append("&RoundID=").append(requestGameRounds.getRoundID());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getTableID())) {
         sb.append("&TableID=").append(requestGameRounds.getTableID());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getTableID())) {
         sb.append("&TableID=").append(requestRevenueByGameType.getTableID());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getDealerID())) {
         sb.append("&DealerID=").append(requestGameRounds.getDealerID());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getuID())) {
         sb.append("&UID=").append(requestGameRounds.getuID());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getuID())) {
         sb.append("&UID=").append(requestAllPlayers.getuID());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getuID())) {
         sb.append("&UID=").append(requestActivePlayers.getuID());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getGameType())) {
         sb.append("&GameType=").append(requestGameRounds.getGameType());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getLimit())) {
         sb.append("&Limit=").append(requestGameRounds.getLimit());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getLimit())) {
         sb.append("&Limit=").append(requestAllPlayers.getLimit());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getLimit())) {
         sb.append("&Limit=").append(requestActivePlayers.getLimit());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getLimit())) {
         sb.append("&Limit=").append(requestRevenueByGameType.getLimit());
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getPage())) {
         sb.append("&Page=").append(requestGameRounds.getPage());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getPage())) {
         sb.append("&Page=").append(requestAllPlayers.getPage());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getPage())) {
         sb.append("&Page=").append(requestActivePlayers.getPage());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getPage())) {
         sb.append("&Page=").append(requestRevenueByGameType.getPage());
      }
      String accessToken = sha2.toHash(sb.toString());
      return accessToken;
   }

   private String parseDateByDateFormat(String pDateStr) throws Exception {
      Date date = null;
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      date = sdf.parse(pDateStr);

      return DateFormatUtils.format(date, "MM.dd.yyyy");
   }

}
