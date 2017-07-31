package com.yolo.integration.ezugi.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import com.poseitech.exceptions.ErrorCodeResovler;
import com.poseitech.integration.message.ResponsePayload;
import com.poseitech.lang.Assert;
import com.yolo.integration.ezugi.model.*;
import com.yolo.integration.ezugi.model.backend.*;
import com.yolo.integration.ezugi.service.ApiName;
import com.yolo.integration.ezugi.util.JsonStringParser;
import com.yolo.integration.ezugi.util.SHA2Hash;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yolo.integration.ezugi.exception.EzugiError;
import com.yolo.integration.ezugi.service.EzugiException;
import com.yolo.integration.ezugi.service.EzugiService;

/**
 * @author peter.hsu
 *
 */
@Service
public class EzugiServiceImp implements EzugiService {
   private static Logger log = LoggerFactory.getLogger(EzugiServiceImp.class);

   @Inject
   private RestTemplate restTemplate;

   /* transfer Api default setting */
   @Value("${toAPIServerUrl.login}")
   private String loginURL;
   @Value("${toAPIServerUrl.register}")
   private String registerURL;
   @Value("${toAPIServerUrl.credit}")
   private String creditURL;
   @Value("${toAPIServerUrl.debit}")
   private String debitURL;
   @Value("${toAPIServerUrl.gameToken}")
   private String gameTokenURL;
   @Value("${toAPIServerUrl.balance}")
   private String balanceURL;
   @Value("${toAPIServerUrl.history}")
   private String historyURL;
   @Value("${toAPIServerUrl.backend}")
   private String backendURL;

   @Value("${apiSalt}")
   private String apiSalt;
   @Value("${apiCallerIp}")
   private String apiCallerIp;
   @Value("${agentId}")
   private Long agentId;
   @Value("${agentUserName}")
   private String agentUserName;
   @Value("${paymentMethod}")
   private String paymentMethod;
   @Value("${findGameNameKey}")
   private String findGameNameKey;
   @Value("${replaceGameNameKey}")
   private String replaceGameNameKey;

   /* backend Api default setting */
   @Value("${backendApiSalt}")
   private String backendApiSalt;
   @Value("${dataSetGameRounds}")
   private String dataSetGameRounds;
   @Value("${dataSetAllPlayers}")
   private String dataSetAllPlayers;
   @Value("${dataSetActivePlayers}")
   private String dataSetActivePlayers;
   @Value("${dataSetRevenueByGameType}")
   private String dataSetRevenueByGameType;
   @Value("${backendApiID}")
   private String backendApiID;
   @Value("${backendApiUser}")
   private String backendApiUser;

   @Override
   public ResponsePayload<ResponseRegisterData> register(EzugiUser pUser, ResponsePayload payload) {
      ResponseEntity<ResponseRegister> responseEntity = null;
      // call Ezugi register api
      String getUrl = null;

      try {
         // 必填欄位驗證
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerPassword()), "playerPassword");

         pUser.setAgentId(agentId);
         pUser.setUserName(agentUserName);
         pUser.setNickName(pUser.getPlayerUsername());
         pUser.setSessionIp(apiCallerIp);

         String requestToken = getRequestToken(apiSalt, pUser, null);
         pUser.setRequestToken(requestToken);

         getUrl = getRequestUrl(ApiName.REGISTER.name(), registerURL, pUser, null);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>(headers);

         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseRegister.class);
         log.debug("responseEntity=" + responseEntity.toString() + ",reponse code=" + responseEntity.getStatusCode());

         payload.setData(responseEntity.getBody().getData());
         payload.setErrorCode(responseEntity.getBody().getErrorCode());
         payload.setErrorDesc(responseEntity.getBody().getErrorDesc());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
      }

      return payload;

   }

   @Override
   public ResponsePayload<ResponseLoginData> login(EzugiLoginForm ezugiLoginForm, ResponsePayload payload) {
      ResponseEntity<String> responseEntity = null;
      ResponseLogin responseLogin = null;
      String getUrl = null;

      try {
         // 必填欄位驗證
         Assert.isTrue(StringUtils.isNotBlank(ezugiLoginForm.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(ezugiLoginForm.getPlayerPassword()), "playerPassword");

         ezugiLoginForm.setLogin("new");
         ezugiLoginForm.setAgentId(agentId);
         ezugiLoginForm.setUserName(agentUserName);
         ezugiLoginForm.setSessionIp(apiCallerIp);

         String requestToken = getRequestToken(apiSalt, null, ezugiLoginForm);
         ezugiLoginForm.setRequestToken(requestToken);

         getUrl = getRequestUrl(ApiName.LOGIN.name(), loginURL, null, ezugiLoginForm);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>(headers);
         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class);
         responseLogin = parseLoginJsonToBean(responseEntity.getBody());
         log.debug("responseEntity=" + responseLogin.toString() + ",reponse code=" + responseEntity.getStatusCode());

         payload.setData(responseLogin.getData());
         payload.setErrorCode(responseLogin.getErrorCode());
         payload.setErrorDesc(responseLogin.getErrorDesc());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
      }

      return payload;

   }

   @Override
   public ResponsePayload<ResponseTransferFundData> credit(EzugiUser pUser, ResponsePayload payload) {
      ResponseEntity<ResponseTransferFund> responseEntity = null;
      String getUrl = null;
      try {
         // 必填欄位驗證
         EzugiBalance inputBalance = pUser.getEzugiBalance();
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerPassword()), "playerPassword");
         Assert.notNull(inputBalance, "inputBalance");
         Assert.notNull(inputBalance.getTransferAmount(), "transferAmount");
         Assert.isTrue(inputBalance.getTransferAmount() > 0, "transferAmount");

         // 重新登入
         if (pUser.getSessionToken() == null) {
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         } else {// 延長時間,文件未提供目前先用重新登入實作
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         }

         pUser.setAgentId(agentId);
         pUser.setUserName(agentUserName);
         pUser.setPaymentMethod(paymentMethod);

         String requestToken = getRequestToken(apiSalt, pUser, null);
         pUser.setRequestToken(requestToken);

         // call Ezugi credit api
         getUrl = getRequestUrl(ApiName.CREDIT.name(), creditURL, pUser, null);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>("credit", headers);

         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseTransferFund.class);
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());
         payload.setData(responseEntity.getBody().getData());
         payload.setErrorCode(responseEntity.getBody().getErrorCode());
         payload.setErrorDesc(responseEntity.getBody().getErrorDesc());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      }

      return payload;
   }

   @Override
   public ResponsePayload<ResponseTransferFundData> debit(EzugiUser pUser, ResponsePayload payload) {
      ResponseEntity<ResponseTransferFund> responseEntity = null;
      // call Ezugi debit api
      String getUrl = null;

      try {
         // 必填欄位驗證
         EzugiBalance inputBalance = pUser.getEzugiBalance();
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerPassword()), "playerPassword");
         Assert.notNull(inputBalance, "inputBalance");
         Assert.notNull(inputBalance.getTransferAmount(), "transferAmount");
         Assert.isTrue(inputBalance.getTransferAmount() > 0, "input data[transferAmount] must be greater than zero");

         // 重新登入
         if (pUser.getSessionToken() == null) {
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         } else {// 延長時間,文件未提供目前先用重新登入實作
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         }

         pUser.setAgentId(agentId);
         pUser.setUserName(agentUserName);
         pUser.setPaymentMethod(paymentMethod);
         String requestToken = getRequestToken(apiSalt, pUser, null);
         pUser.setRequestToken(requestToken);

         getUrl = getRequestUrl(ApiName.DEBIT.name(), debitURL, pUser, null);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>("debit", headers);

         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseTransferFund.class);
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());
         payload.setData(responseEntity.getBody().getData());
         payload.setErrorCode(responseEntity.getBody().getErrorCode());
         payload.setErrorDesc(responseEntity.getBody().getErrorDesc());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      }

      return payload;
   }

   @Override
   public ResponsePayload<ResponseGameTokenData> getGameToken(EzugiUser pUser, ResponsePayload payload) {
      ResponseEntity<ResponseGameToken> responseEntity = null;
      // call Ezugi game_token api
      String getUrl = null;

      try {
         // 必填欄位驗證
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerPassword()), "playerPassword");

         // 重新登入
         if (pUser.getSessionToken() == null) {
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
            pUser.setProviderId(map.get("providerId"));
         } else {// 延長時間,文件未提供目前先用重新登入實作
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
            pUser.setProviderId(map.get("providerId"));
         }

         pUser.setAgentId(agentId);
         pUser.setUserName(agentUserName);
         pUser.setSessionIp(apiCallerIp);

         String requestToken = getRequestToken(apiSalt, pUser, null);
         pUser.setRequestToken(requestToken);

         getUrl = getRequestUrl(ApiName.GAME_TOKEN.name(), gameTokenURL, pUser, null);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>("gameToken", headers);

         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseGameToken.class);
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());
         // 將原本game_url的openGame replace selectGame,否則點此url會發生autorization
         // failed
         if(responseEntity.getBody().getData() != null) {
            ArrayList<ProviderGame> providerGameList = new ArrayList<ProviderGame>();
            for (ProviderGame lProviderGame : responseEntity.getBody().getData().getProviderGames()) {
               ProviderGame providerGame = lProviderGame;
               providerGame.setId(lProviderGame.getId());
               providerGame.setProviderId(lProviderGame.getProviderId());
               providerGame.setProviderGameId(lProviderGame.getProviderGameId());
               providerGame.setName(lProviderGame.getName());
               if (StringUtils.isNotBlank(findGameNameKey) && StringUtils.isNotBlank(replaceGameNameKey) && lProviderGame.getGameUrl().contains(findGameNameKey)) {
                  providerGame.setGameUrl(lProviderGame.getGameUrl().replace(findGameNameKey,replaceGameNameKey));
               } else {
                  providerGame.setGameUrl(lProviderGame.getGameUrl());
               }
               providerGame.setLogoUrl(lProviderGame.getLogoUrl());
               providerGame.setActive(lProviderGame.getActive());
               providerGame.setPlayForFun(lProviderGame.getPlayForFun());
               providerGame.setMobileSupport(lProviderGame.getMobileSupport());
               providerGame.setValidBet(lProviderGame.getValidBet());
               providerGameList.add(providerGame);
            }
            responseEntity.getBody().getData().setProviderGames(providerGameList.toArray(new ProviderGame[0]));
         }
         
         payload.setData(responseEntity.getBody().getData());
         payload.setErrorCode(responseEntity.getBody().getErrorCode());
         payload.setErrorDesc(responseEntity.getBody().getErrorDesc());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      }

      return payload;
   }

   @Override
   public ResponsePayload<ProviderGame> getGameDetailByName(EzugiUser pUser, ResponsePayload payload) {
      ResponseEntity<ResponseGameToken> responseEntity = null;
      ProviderGame providerGame = new ProviderGame();
      // call Ezugi game_token api and filter by game name
      String getUrl = null;

      try {
         // 必填欄位驗證
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerPassword()), "playerPassword");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getGameName()), "gameName");

         // 重新登入
         if (pUser.getSessionToken() == null) {
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
            pUser.setProviderId(map.get("providerId"));
         } else {// 延長時間,文件未提供目前先用重新登入實作
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
            pUser.setProviderId(map.get("providerId"));
         }
         pUser.setAgentId(agentId);
         pUser.setUserName(agentUserName);
         pUser.setSessionIp(apiCallerIp);

         String requestToken = getRequestToken(apiSalt, pUser, null);
         pUser.setRequestToken(requestToken);

         getUrl = getRequestUrl(ApiName.GAME_TOKEN.name(), gameTokenURL, pUser, null);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>(headers);

         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseGameToken.class);
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());
         if (responseEntity.getBody() != null && responseEntity.getBody().getData() != null
               && responseEntity.getBody().getData().getProviderGames() != null) {
            for (ProviderGame lProviderGame : responseEntity.getBody().getData().getProviderGames()) {
               if (pUser.getGameName() != null && lProviderGame.getGameUrl() != null && findGameByGameName(lProviderGame.getGameUrl(),pUser.getGameName())) {
                  providerGame.setId(lProviderGame.getId());
                  providerGame.setProviderId(lProviderGame.getProviderId());
                  providerGame.setProviderGameId(lProviderGame.getProviderGameId());
                  providerGame.setName(lProviderGame.getName());
                  // 將原本game_url的openGame replace
                  // selectGame,否則點此url會發生autorization failed
                  if (StringUtils.isNotBlank(findGameNameKey) && StringUtils.isNotBlank(replaceGameNameKey) && lProviderGame.getGameUrl().contains(findGameNameKey)) {
                     providerGame.setGameUrl(lProviderGame.getGameUrl().replace(findGameNameKey,replaceGameNameKey));
                  } else {
                     providerGame.setGameUrl(lProviderGame.getGameUrl());
                  }
                  providerGame.setLogoUrl(lProviderGame.getLogoUrl());
                  providerGame.setActive(lProviderGame.getActive());
                  providerGame.setPlayForFun(lProviderGame.getPlayForFun());
                  providerGame.setMobileSupport(lProviderGame.getMobileSupport());
                  providerGame.setValidBet(lProviderGame.getValidBet());
                  break;
               }
            }
            log.debug("providerGame=" + providerGame);
            if (StringUtils.isNotBlank(providerGame.getId())) {
               payload.setData(providerGame);
               payload.setErrorCode(responseEntity.getBody().getErrorCode());
               payload.setErrorDesc(responseEntity.getBody().getErrorDesc());
            } else {
               log.error("providerGame not found ,error code: " + EzugiError.NOT_FOUND_GAME.getErrorCode());
               payload.setData(null);
               payload.setErrorCode(EzugiError.NOT_FOUND_GAME.getErrorCode());
               payload.setErrorDesc(EzugiError.NOT_FOUND_GAME.getErrorDesc());
            }
         } else {
            log.error("providerGame not found ,error code: " + EzugiError.NOT_FOUND_GAME.getErrorCode());
            // 若上面重新登入或延長時間有成功會有data資料,這裡要把login的data資料清空
            if (payload.getData() != null) {
               payload.setData(null);
            }
            payload.setErrorCode(EzugiError.NOT_FOUND_GAME.getErrorCode());
            payload.setErrorDesc(EzugiError.NOT_FOUND_GAME.getErrorDesc());
         }
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      }

      return payload;
   }

   @Override
   public ResponsePayload<ResponseBalanceData> getBalance(EzugiUser pUser, ResponsePayload payload) {
      ResponseEntity<ResponseBalance> responseEntity = null;
      // call Ezugi balance api
      String getUrl = null;

      try {
         // 必填欄位驗證
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerPassword()), "playerPassword");

         // 重新登入
         if (pUser.getSessionToken() == null) {
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         } else {// 延長時間,文件未提供目前先用重新登入實作
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         }

         pUser.setAgentId(agentId);
         pUser.setUserName(agentUserName);
         pUser.setSessionIp(apiCallerIp);

         String requestToken = getRequestToken(apiSalt, pUser, null);
         pUser.setRequestToken(requestToken);

         getUrl = getRequestUrl(ApiName.BALANCE.name(), balanceURL, pUser, null);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>("balance", headers);

         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, ResponseBalance.class);
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());
         payload.setData(responseEntity.getBody().getData());
         payload.setErrorCode(responseEntity.getBody().getErrorCode());
         payload.setErrorDesc(responseEntity.getBody().getErrorDesc());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      }

      return payload;
   }

   @Override
   public ResponsePayload<ResponseHistoryData> getHistory(EzugiUser pUser, ResponsePayload payload) {
      ResponseEntity<String> responseEntity = null;
      ResponseHistory responseHistory = null;
      // call Ezugi history api
      String getUrl = null;

      try {
         // 必填欄位驗證
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerUsername()), "playerUsername");
         Assert.isTrue(StringUtils.isNotBlank(pUser.getPlayerPassword()), "playerPassword");

         // 重新登入
         if (pUser.getSessionToken() == null) {
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         } else {// 延長時間,文件未提供目前先用重新登入實作
            EzugiLoginForm loginForm = new EzugiLoginForm();
            loginForm.setPlayerUsername(pUser.getPlayerUsername());
            loginForm.setPlayerPassword(pUser.getPlayerPassword());
            HashMap<String, String> map = getPropertyMapAfterLogin(loginForm, payload);
            pUser.setSessionToken(map.get("sessionToken"));
         }

         pUser.setAgentId(agentId);
         pUser.setUserName(agentUserName);
         pUser.setSessionIp(apiCallerIp);

         String requestToken = getRequestToken(apiSalt, pUser, null);
         pUser.setRequestToken(requestToken);

         getUrl = getRequestUrl(ApiName.HISTORY.name(), historyURL, pUser, null);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
         headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
         final HttpEntity<String> entity = new HttpEntity<String>("balance", headers);

         responseEntity = restTemplate.exchange(getUrl, HttpMethod.GET, entity, String.class);
         // 自轉json string到java bean
         responseHistory = parseHistoryJsonToBean(responseEntity.getBody());
         log.debug("responseEntity=" + responseHistory.toString() + ",reponse code=" + responseEntity.getStatusCode());
         payload.setData(responseHistory.getData());
         payload.setErrorCode(responseHistory.getErrorCode());
         payload.setErrorDesc(responseHistory.getErrorDesc());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (IllegalArgumentException e) {
         log.error("IllegalArgumentException message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_ILLEGAL_INPUT_ARGUMENTS);
         payload.setErrorDesc(String.format("%s: illegal input argument of method", e.getMessage()));
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
         // 若上面重新登入或延長時間有成功會有data資料,其他地方產生exception後應該要把data資料清空
         if (payload.getData() != null) {
            payload.setData(null);
         }
      }
      return payload;
   }

   @Override
   public ResponsePayload<ResponseGameRounds> getGameRounds(RequestGameRounds requestGameRounds,
         ResponsePayload payload) {
      ResponseEntity<ResponseGameRounds> responseEntity = null;
      // call Ezugi gameRounds api
      try {
         requestGameRounds.setDataSet(dataSetGameRounds);
         requestGameRounds.setApiID(backendApiID);
         requestGameRounds.setApiUser(backendApiUser);
         String requestToken = getRequestToken(backendApiSalt, requestGameRounds, null, null, null);
         requestGameRounds.setRequestToken(requestToken);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

         MultiValueMap<String, Object> map = transferRequestJson(requestGameRounds, null, null, null);
         final HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,
               headers);

         responseEntity = restTemplate.postForEntity(backendURL, entity, ResponseGameRounds.class);
         if (responseEntity != null && responseEntity.getBody() != null && responseEntity.getBody().getData() != null) {
            Arrays.stream(responseEntity.getBody().getData()).forEach(gameRoundsData -> {
               if (StringUtils.isNotBlank(gameRoundsData.getRoundString())) {
                  try {
                     gameRoundsData.setRound(parseRoundStringJsonToBean(gameRoundsData.getRoundString()));
                  } catch (Exception e) {
                     log.error(EzugiError.JSON_STRING_PARSE_ERROR.getErrorDesc(), e);
                     payload.setErrorCode(EzugiError.JSON_STRING_PARSE_ERROR.getErrorCode());
                     payload.setErrorDesc(EzugiError.JSON_STRING_PARSE_ERROR.getErrorDesc());
                  }
               }
            });
         }
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());

         // Ezugi的response有data的沒有回傳error desc
         if (responseEntity.getBody().getError() == null) {
            payload.setData(responseEntity.getBody());
         } else {
            payload.setData(null);
         }
         payload.setErrorCode(responseEntity.getBody().getError() == null ? EzugiError.OK.getErrorCode()
               // 有些error並沒有回傳error code,因此拋出自訂義的error code
               : (responseEntity.getBody().getErrorCode() == null ? ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION
                     : responseEntity.getBody().getErrorCode()));
         payload.setErrorDesc(responseEntity.getBody().getError() == null ? EzugiError.OK.getErrorDesc()
               : responseEntity.getBody().getError());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
      }

      return payload;
   }

   @Override
   public ResponsePayload<ResponseAllPlayers> getAllPlayers(RequestAllPlayers requestAllPlayers,
         ResponsePayload payload) {
      ResponseEntity<ResponseAllPlayers> responseEntity = null;
      // call Ezugi allPlayers api
      try {
         requestAllPlayers.setDataSet(dataSetAllPlayers);
         requestAllPlayers.setApiID(backendApiID);
         requestAllPlayers.setApiUser(backendApiUser);
         String requestToken = getRequestToken(backendApiSalt, null, requestAllPlayers, null, null);
         requestAllPlayers.setRequestToken(requestToken);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

         MultiValueMap<String, Object> map = transferRequestJson(null, requestAllPlayers, null, null);
         final HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,
               headers);

         responseEntity = restTemplate.postForEntity(backendURL, entity, ResponseAllPlayers.class);
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());

         // Ezugi的response有data的沒有回傳error desc
         if (responseEntity.getBody().getError() == null) {
            payload.setData(responseEntity.getBody());
         } else {
            payload.setData(null);
         }
         payload.setErrorCode(responseEntity.getBody().getError() == null ? EzugiError.OK.getErrorCode()
               // 有些error並沒有回傳error code,因此拋出自訂義的error code
               : (responseEntity.getBody().getErrorCode() == null ? ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION
                     : responseEntity.getBody().getErrorCode()));
         payload.setErrorDesc(responseEntity.getBody().getError() == null ? EzugiError.OK.getErrorDesc()
               : responseEntity.getBody().getError());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
      }

      return payload;
   }

   @Override
   public ResponsePayload<ResponseActivePlayers> getActivePlayres(RequestActivePlayers requestActivePlayers,
         ResponsePayload payload) {
      ResponseEntity<ResponseActivePlayers> responseEntity = null;
      // call Ezugi activePlayres api
      try {
         requestActivePlayers.setDataSet(dataSetActivePlayers);
         requestActivePlayers.setApiID(backendApiID);
         requestActivePlayers.setApiUser(backendApiUser);
         String requestToken = getRequestToken(backendApiSalt, null, null, requestActivePlayers, null);
         requestActivePlayers.setRequestToken(requestToken);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

         MultiValueMap<String, Object> map = transferRequestJson(null, null, requestActivePlayers, null);
         final HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,
               headers);

         responseEntity = restTemplate.postForEntity(backendURL, entity, ResponseActivePlayers.class);
         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());

         // Ezugi的response有data的沒有回傳error desc
         if (responseEntity.getBody().getError() == null) {
            payload.setData(responseEntity.getBody());
         } else {
            payload.setData(null);
         }
         payload.setErrorCode(responseEntity.getBody().getError() == null ? EzugiError.OK.getErrorCode()
               // 有些error並沒有回傳error code,因此拋出自訂義的error code
               : (responseEntity.getBody().getErrorCode() == null ? ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION
                     : responseEntity.getBody().getErrorCode()));
         payload.setErrorDesc(responseEntity.getBody().getError() == null ? EzugiError.OK.getErrorDesc()
               : responseEntity.getBody().getError());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
      }

      return payload;
   }

   @Override
   public ResponsePayload<ResponseRevenueByGameType> getRevenueByGameType(
         RequestRevenueByGameType requestRevenueByGameType, ResponsePayload payload) {
      ResponseEntity<String> responseEntity = null;
      ResponseRevenueByGameType responseRevenueByGameType = null;
      // call Ezugi revenueByGameType api
      try {
         requestRevenueByGameType.setDataSet(dataSetRevenueByGameType);
         requestRevenueByGameType.setApiID(backendApiID);
         requestRevenueByGameType.setApiUser(backendApiUser);
         String requestToken = getRequestToken(backendApiSalt, null, null, null, requestRevenueByGameType);
         requestRevenueByGameType.setRequestToken(requestToken);

         // sync
         HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

         MultiValueMap<String, Object> map = transferRequestJson(null, null, null, requestRevenueByGameType);
         final HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,
               headers);

         responseEntity = restTemplate.postForEntity(backendURL, entity, String.class);
         responseRevenueByGameType = parseResponseRevenueByGameTypeJsonToBean(responseEntity.getBody());

         log.debug("responseEntity=" + responseEntity.getBody().toString() + ",reponse code="
               + responseEntity.getStatusCode());

         // Ezugi的response有data的沒有回傳error desc
         if (responseRevenueByGameType.getError() == null) {
            payload.setData(responseRevenueByGameType);
         } else {
            payload.setData(null);
         }
         payload.setErrorCode(responseRevenueByGameType.getError() == null ? EzugiError.OK.getErrorCode()
               // 有些error並沒有回傳error code,因此拋出自訂義的error code
               : (responseRevenueByGameType.getErrorCode() == null ? ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION
                     : responseRevenueByGameType.getErrorCode()));
         payload.setErrorDesc(responseRevenueByGameType.getError() == null ? EzugiError.OK.getErrorDesc()
               : responseRevenueByGameType.getError());
      } catch (final HttpClientErrorException | HttpServerErrorException e) {
         log.error("HttpClientErrorException | HttpServerErrorException message:", e.getResponseBodyAsString());
         payload.setErrorCode(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorCode());
         payload.setErrorDesc(EzugiError.HTTP_CLIENT_SERVER_ERROR.getErrorDesc());
      } catch (EzugiException e) {
         log.error("EzugiException:", e);
         payload.setErrorCode(e.getErrorCode());
         payload.setErrorDesc(e.getMessage());
      } catch (Exception e) {
         log.error("Exception message:", e);
         payload.setErrorCode(ErrorCodeResovler.ERROR_UNEXCEPTED_EXCEPTION);
         payload.setErrorDesc("Error unexcepted exception!");
      }

      return payload;
   }

   private ResponseHistory parseHistoryJsonToBean(String responseBody) {
      ResponseHistory responseHistory = new ResponseHistory();
      ResponseMessageDetails responseMessageDetails = new ResponseMessageDetails();
      try {
         JsonElement json = new JsonParser().parse(responseBody);
         ArrayList<HistoryDetail> historys = new ArrayList<HistoryDetail>();
         json.getAsJsonObject().entrySet().forEach(entry -> {
            if (StringUtils.isNumeric(entry.getKey())) {
               HistoryDetail historyDetail = new HistoryDetail();
               historyDetail.setId(JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("id")));
               historyDetail
                     .setPlayerId(JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("player_id")));
               historyDetail
                     .setSessionId(JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("session_id")));
               historyDetail.setGameId(JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("game_id")));
               historyDetail
                     .setGameName(JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("game_name")));
               historyDetail.setProviderId(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("provider_id")));
               historyDetail
                     .setRoundId(JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("round_id")));
               historyDetail.setCurrencyCode(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("currency_code")));
               historyDetail.setTransactionType(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("transaction_type")));
               historyDetail.setAmountCredit(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("amount_credit")));
               historyDetail.setAmountDebit(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("amount_debit")));
               historyDetail.setDateCreated(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("date_created")));
               historyDetail.setBalanceBefore(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("balance_before")));
               historyDetail.setBalanceAfter(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("balance_after")));
               JsonElement eProviderGame = entry.getValue().getAsJsonObject().get("provider_game");

               if (eProviderGame != null && eProviderGame.isJsonObject()) {
                  JsonObject providerGameObject = eProviderGame.getAsJsonObject();
                  ProviderGame providerGame = new ProviderGame(
                        JsonStringParser.getAsString(providerGameObject.get("id")),
                        JsonStringParser.getAsString(providerGameObject.get("provider_id")),
                        JsonStringParser.getAsString(providerGameObject.get("provider_game_id")),
                        JsonStringParser.getAsString(providerGameObject.get("name")),
                        JsonStringParser.getAsString(providerGameObject.get("game_url")),
                        JsonStringParser.getAsString(providerGameObject.get("logo_url")),
                        JsonStringParser.getAsString(providerGameObject.get("active")),
                        JsonStringParser.getAsString(providerGameObject.get("play_for_fun")),
                        JsonStringParser.getAsString(providerGameObject.get("mobile_support")),
                        JsonStringParser.getAsString(providerGameObject.get("valid_bet")));
                  historyDetail.setProviderGame(providerGame);
               } else {
                  historyDetail.setProviderGame(null);
               }

               historyDetail.setProviderTransactionId(
                     JsonStringParser.getAsString(entry.getValue().getAsJsonObject().get("provider_transaction_id")));
               JsonElement providerDetails = entry.getValue().getAsJsonObject().get("provider_game");
               if (providerDetails != null && providerDetails.isJsonObject()) {
                  JsonObject providerDetailsObject = entry.getValue().getAsJsonObject().get("provider_details")
                        .getAsJsonObject();
                  List<String> games = new ArrayList<String>();
                  JsonElement providerGames = providerDetailsObject.get("games");
                  if (providerGames.isJsonArray()) {
                     JsonArray jArray = providerGames.getAsJsonArray();
                     jArray.forEach(provider -> {
                        games.add(JsonStringParser.getAsString(provider));
                     });
                  }

                  Provider provider = new Provider(JsonStringParser.getAsString(providerDetailsObject.get("id")),
                        JsonStringParser.getAsString(providerDetailsObject.get("name")),
                        JsonStringParser.getAsString(providerDetailsObject.get("description")),
                        JsonStringParser.getAsString(providerDetailsObject.get("main_url")),
                        JsonStringParser.getAsString(providerDetailsObject.get("logo_url")),
                        JsonStringParser.getAsString(providerDetailsObject.get("provider_id")),
                        games.toArray(new String[0]),
                        JsonStringParser.getAsString(providerDetailsObject.get("context_root_name")),
                        JsonStringParser.getAsString(providerDetailsObject.get("ezugi")));
                  historyDetail.setProviderDetails(provider);
               } else {
                  historyDetail.setProviderDetails(null);
               }
               historys.add(historyDetail);
            } else if ("maintenance".equals(entry.getKey())) {
               responseHistory.setMaintenance(JsonStringParser.getAsString(entry.getValue()));
            } else if ("timestamp".equals(entry.getKey())) {
               responseHistory.setTimestamp(JsonStringParser.getAsLong(entry.getValue()));
            } else if ("error_code".equals(entry.getKey())) {
               responseHistory.setErrorCode(JsonStringParser.getAsInt(entry.getValue()));
            } else if ("error".equals(entry.getKey())) {
               responseMessageDetails.setError(JsonStringParser.getAsString(entry.getValue()));
               responseHistory.setDetails(responseMessageDetails);
            } else if ("details".equals(entry.getKey())) {
               responseMessageDetails.setDetails(JsonStringParser.getAsString(entry.getValue()));
               responseHistory.setDetails(responseMessageDetails);
            } else if ("title".equals(entry.getKey())) {
               responseMessageDetails.setDetails(JsonStringParser.getAsString(entry.getValue()));
               responseHistory.setDetails(responseMessageDetails);
            } else if ("message".equals(entry.getKey())) {
               responseMessageDetails.setDetails(JsonStringParser.getAsString(entry.getValue()));
               responseHistory.setDetails(responseMessageDetails);
            }
         });
         if (CollectionUtils.isNotEmpty(historys)) {
            responseHistory.setHistorys(historys);
         }
      } catch (Exception e) {
         log.error("parseHistoryJsonToBean exception:", e);
         throw new EzugiException(EzugiError.JSON_STRING_PARSE_ERROR.getErrorCode(),
               EzugiError.JSON_STRING_PARSE_ERROR.getErrorDesc(), "");
      }

      return responseHistory;
   }

   private ResponseLogin parseLoginJsonToBean(String responseBody) {
      ResponseLogin responseLogin = new ResponseLogin();
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode rootNode = objectMapper.readTree(responseBody);
         if (rootNode != null) {
            // player entity
            JsonNode playerNode = rootNode.get("player");
            if (playerNode != null && playerNode.isObject()) {
               Player player = new Player();
               player.setId(JsonStringParser.asString(playerNode.get("id")));
               player.setAgentId(JsonStringParser.asString(playerNode.get("agent_id")));
               player.setOperatorId(JsonStringParser.asString(playerNode.get("operator_id")));
               player.setUserId(JsonStringParser.asString(playerNode.get("user_id")));
               player.setPlayerType(JsonStringParser.asString(playerNode.get("player_type")));
               player.setUsername(JsonStringParser.asString(playerNode.get("username")));
               player.setPassword(JsonStringParser.asString(playerNode.get("password")));
               player.setFirstName(JsonStringParser.asString(playerNode.get("first_name")));
               player.setMiddleName(JsonStringParser.asString(playerNode.get("middle_name")));
               player.setLastName(JsonStringParser.asString(playerNode.get("last_name")));
               player.setNickname(JsonStringParser.asString(playerNode.get("nickname")));
               player.setPhone1(JsonStringParser.asString(playerNode.get("phone1")));
               player.setPhone2(JsonStringParser.asString(playerNode.get("phone2")));
               player.setDateOfBirth(JsonStringParser.asString(playerNode.get("date_of_birth")));
               player.setCountryCode(JsonStringParser.asString(playerNode.get("country_code")));
               player.setAddress1(JsonStringParser.asString(playerNode.get("address1")));
               player.setAddress2(JsonStringParser.asString(playerNode.get("address2")));
               player.setCity(JsonStringParser.asString(playerNode.get("city")));
               player.setState(JsonStringParser.asString(playerNode.get("state")));
               player.setZipcode(JsonStringParser.asString(playerNode.get("zipcode")));
               player.setCurrencyCode(JsonStringParser.asString(playerNode.get("currency_code")));
               player.setLanguage(JsonStringParser.asString(playerNode.get("language")));
               player.setBalance(JsonStringParser.asString(playerNode.get("balance")));
               player.setRegistrationDate(JsonStringParser.asString(playerNode.get("registration_date")));
               player.setRegistrationIp(JsonStringParser.asString(playerNode.get("registration_ip")));
               player.setDailyBettingLimit(JsonStringParser.asString(playerNode.get("daily_betting_limit")));
               player.setMonthlyBettingLimit(JsonStringParser.asString(playerNode.get("monthly_betting_limit")));
               player.setPersonalIdType(JsonStringParser.asString(playerNode.get("personal_id_type")));
               player.setPersonalIdNumber(JsonStringParser.asString(playerNode.get("personal_id_number")));
               player.setPersonalIdNumber(JsonStringParser.asString(playerNode.get("personal_id_country")));
               player.setPersonalIdIssueDate(JsonStringParser.asString(playerNode.get("personal_id_issue_date")));
               player.setPersonalIdExipres(JsonStringParser.asString(playerNode.get("personal_id_exipres")));
               player.setPersonalIdScan(JsonStringParser.asString(playerNode.get("personal_id_scan")));
               player.setPersonalIdScanExtra(JsonStringParser.asString(playerNode.get("personal_id_scan_extra")));
               player.setTerminal(JsonStringParser.asString(playerNode.get("terminal")));
               player.setTerminalIp(JsonStringParser.asString(playerNode.get("terminal_ip")));
               player.setTestUser(JsonStringParser.asString(playerNode.get("test_user")));
               player.setVipLevel(JsonStringParser.asString(playerNode.get("vip_level")));
               player.setAdvertiserId(JsonStringParser.asString(playerNode.get("advertiser_id")));
               player.setCommission(JsonStringParser.asString(playerNode.get("commission")));
               player.setBetCommission(JsonStringParser.asString(playerNode.get("bet_commission")));
               player.setNgrLimitMin(JsonStringParser.asString(playerNode.get("ngr_limit_min")));
               player.setNgrLimitMax(JsonStringParser.asString(playerNode.get("ngr_limit_max")));
               player.setLastLoginDate(JsonStringParser.asString(playerNode.get("last_login_date")));
               player.setLastTransactionDate(JsonStringParser.asString(playerNode.get("last_transaction_date")));
               player.setLastTransactionId(JsonStringParser.asString(playerNode.get("last_transaction_id")));
               player.setVipByDeposit(JsonStringParser.asString(playerNode.get("vip_by_deposit")));
               player.setDateChangePassword(JsonStringParser.asString(playerNode.get("date_change_password")));
               player.setActive(JsonStringParser.asString(playerNode.get("active")));
               player.setAgentsIsActive(JsonStringParser.asString(playerNode.get("agents_is_active")));
               player.setAgentUsername(JsonStringParser.asString(playerNode.get("agent_username")));
               player.setMustChangePassword(JsonStringParser.asBoolean(playerNode.get("must_change_password")));
               responseLogin.setPlayer(player);
            }

            // session entity
            JsonNode sessionNode = rootNode.get("session");
            if (sessionNode != null && sessionNode.isObject()) {
               Session session = new Session();
               session.setAgentId(JsonStringParser.asString(sessionNode.get("agent_id")));
               session.setPlayerId(JsonStringParser.asString(sessionNode.get("player_id")));
               session.setSessionToken(JsonStringParser.asString(sessionNode.get("session_token")));
               session.setDateCreated(JsonStringParser.asString(sessionNode.get("date_created")));
               session.setDateAccessed(JsonStringParser.asString(sessionNode.get("date_accessed")));
               session.setSessionIp(JsonStringParser.asString(sessionNode.get("session_ip")));
               session.setCountryCode(JsonStringParser.asString(sessionNode.get("country_code")));
               session.setActive(JsonStringParser.asInt(sessionNode.get("active")));
               session.setId(JsonStringParser.asLong(sessionNode.get("id")));
               session.setNotification(JsonStringParser.asString(sessionNode.get("notification")));
               session.setSocketToken(JsonStringParser.asString(sessionNode.get("socket_token")));
               responseLogin.setSession(session);
            }

            // lobby entity,will update if got format
            ArrayNode lobbyNode = (ArrayNode) rootNode.get("lobby");
            if (lobbyNode != null && lobbyNode.isObject()) {
               List<String> lobbyList = new ArrayList<String>();
               lobbyNode.forEach(lobby -> {
                  lobbyList.add(JsonStringParser.asString(lobby));
               });

               responseLogin.setLobby(lobbyList.toArray(new String[0]));
            }

            if (rootNode.get("games") != null && rootNode.get("games").isObject()) {
               // games entity
               List<ProviderGame> gamesList = new ArrayList<ProviderGame>();
               rootNode.get("games").forEach(game -> {
                  ProviderGame providerGame = new ProviderGame(JsonStringParser.asString(game.get("id")),
                        JsonStringParser.asString(game.get("provider_id")),
                        JsonStringParser.asString(game.get("provider_game_id")),
                        JsonStringParser.asString(game.get("name")), JsonStringParser.asString(game.get("game_url")),
                        JsonStringParser.asString(game.get("logo_url")), JsonStringParser.asString(game.get("active")),
                        JsonStringParser.asString(game.get("play_for_fun")),
                        JsonStringParser.asString(game.get("mobile_support")),
                        JsonStringParser.asString(game.get("valid_bet")));
                  gamesList.add(providerGame);
               });
               responseLogin.setGames(gamesList.toArray(new ProviderGame[0]));
            }

            if (rootNode.get("providers") != null && rootNode.get("providers").isObject()) {
               // providers entity
               List<Provider> providerList = new ArrayList<Provider>();
               rootNode.get("providers").forEach(pProvider -> {
                  List<String> gameList = new ArrayList<String>();
                  if (pProvider != null && pProvider.isObject()) {
                     pProvider.get("games").forEach(game -> {
                        gameList.add(JsonStringParser.asString(game));
                     });
                  }

                  Provider provider = new Provider(JsonStringParser.asString(pProvider.get("id")),
                        JsonStringParser.asString(pProvider.get("name")),
                        JsonStringParser.asString(pProvider.get("description")),
                        JsonStringParser.asString(pProvider.get("main_url")),
                        JsonStringParser.asString(pProvider.get("logo_url")),
                        JsonStringParser.asString(pProvider.get("provider_id")), gameList.toArray(new String[0]),
                        JsonStringParser.asString(pProvider.get("context_root_name")),
                        JsonStringParser.asString(pProvider.get("ezugi")));
                  providerList.add(provider);
               });
               responseLogin.setProviders(providerList.toArray(new Provider[0]));
            }

            ResponseMessageDetails responseMessageDetails = new ResponseMessageDetails();
            responseMessageDetails.setError(JsonStringParser.asString(rootNode.get("error")));
            responseMessageDetails.setDetails(JsonStringParser.asString(rootNode.get("details")));
            responseMessageDetails.setTitle(JsonStringParser.asString(rootNode.get("title")));
            responseMessageDetails.setMessage(JsonStringParser.asString(rootNode.get("message")));
            responseLogin.setDetails(responseMessageDetails);

            responseLogin.setMaintenance(JsonStringParser.asString(rootNode.get("maintenance")));
            responseLogin.setTimestamp(JsonStringParser.asLong(rootNode.get("timestamp")));
            responseLogin.setErrorCode(JsonStringParser.asInt(rootNode.get("error_code")));
         }
      } catch (Exception e) {
         log.error("parseLoginJsonToBean exception:", e);
         throw new EzugiException(EzugiError.JSON_STRING_PARSE_ERROR.getErrorCode(),
               EzugiError.JSON_STRING_PARSE_ERROR.getErrorDesc(), "");
      }

      return responseLogin;
   }

   private Round parseRoundStringJsonToBean(String responseBody) {
      Round round = new Round();

      try {
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode rootNode = objectMapper.readTree(responseBody);
         if (rootNode != null) {
            round.setTableId(JsonStringParser.asString(rootNode.get("TableId")));
            round.setPlayerId(JsonStringParser.asString(rootNode.get("PlayerId")));
            round.setBetAmount(JsonStringParser.asDouble(rootNode.get("BetAmount")));
            round.setSessionCurrency(JsonStringParser.asString(rootNode.get("SessionCurrency")));
            round.setTotalWin(JsonStringParser.asDouble(rootNode.get("TotalWin")));
            round.setOperatorID(JsonStringParser.asString(rootNode.get("OperatorID")));
            round.setBetsList(JsonStringParser.asString(rootNode.get("BetsList")));
            round.setWinningNumber(JsonStringParser.asInt(rootNode.get("WinningNumber")));
            round.setServerId(JsonStringParser.asInt(rootNode.get("ServerId")));
            round.setDealerId(JsonStringParser.asString(rootNode.get("DealerId")));

            ArrayNode dealerCardsNode = (ArrayNode) rootNode.get("DealerCards");
            if (dealerCardsNode != null && dealerCardsNode.isArray()) {
               List<Card> dealerCardsList = new ArrayList<Card>();
               dealerCardsNode.forEach(dealerCard -> {
                  Card card = new Card();
                  if (dealerCard != null) {
                     card.setCardName(JsonStringParser.asString(dealerCard.get("CardName")));
                     card.setCardValue(JsonStringParser.asInt(dealerCard.get("CardValue")));
                  }

                  dealerCardsList.add(card);
               });
               round.setDealerCards(dealerCardsList.toArray(new Card[0]));
            }

            round.setDealerName(JsonStringParser.asString(rootNode.get("DealerName")));

            round.setRoundId(JsonStringParser.asInt(rootNode.get("roundId")));

            ArrayNode seatCardsNode = (ArrayNode) rootNode.get("SeatCards");
            if (seatCardsNode != null && seatCardsNode.isArray()) {
               List<Card> seatCardsList = new ArrayList<Card>();
               seatCardsNode.forEach(seatCard -> {
                  // 此段response的array又包一個array,故再重新parse一次
                  if (seatCard != null && seatCard.isArray()) {
                     seatCard.forEach(uSeatCard -> {
                        Card card = new Card();
                        if (uSeatCard != null) {
                           card.setCardName(JsonStringParser.asString(uSeatCard.get("CardName")));
                           card.setCardValue(JsonStringParser.asInt(uSeatCard.get("CardValue")));
                        }

                        seatCardsList.add(card);
                     });
                  } else if (seatCard != null) {
                     Card card = new Card();
                     card.setCardName(JsonStringParser.asString(seatCard.get("CardName")));
                     card.setCardValue(JsonStringParser.asInt(seatCard.get("CardValue")));

                     seatCardsList.add(card);
                  }
               });
               round.setSeatCards(seatCardsList.toArray(new Card[0]));
            }

            round.setWinningBets(JsonStringParser.asString(rootNode.get("WinningBets")));
            round.setGameID(JsonStringParser.asInt(rootNode.get("GameID")));
         }
      } catch (Exception e) {
         log.error("parseRoundStringJsonToBean exception:" + e);
         throw new EzugiException(EzugiError.JSON_STRING_PARSE_ERROR.getErrorCode(),
               EzugiError.JSON_STRING_PARSE_ERROR.getErrorDesc(), "");
      }

      return round;
   }

   private ResponseRevenueByGameType parseResponseRevenueByGameTypeJsonToBean(String responseBody) {
      ResponseRevenueByGameType responseRevenueByGameType = new ResponseRevenueByGameType();

      try {
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode rootNode = objectMapper.readTree(responseBody);
         if (rootNode != null) {
            responseRevenueByGameType.setTotalRows(JsonStringParser.asString(rootNode.get("total_rows")));
            responseRevenueByGameType.setPageNumber(JsonStringParser.asInt(rootNode.get("page_number")));
            responseRevenueByGameType.setRowsPerPage(JsonStringParser.asInt(rootNode.get("rows_per_page")));
            responseRevenueByGameType.setDuration(JsonStringParser.asString(rootNode.get("duration")));
            responseRevenueByGameType.setRequestTimestamp(JsonStringParser.asInt(rootNode.get("request_timestamp")));
            responseRevenueByGameType.setRequestDate(JsonStringParser.asString(rootNode.get("request_date")));
            responseRevenueByGameType.setFilters(JsonStringParser.asString(rootNode.get("filters")));
            responseRevenueByGameType.setAccessList(JsonStringParser.asString(rootNode.get("access_list")));
            responseRevenueByGameType.setControl(JsonStringParser.asString(rootNode.get("control")));
            responseRevenueByGameType.setDataset(JsonStringParser.asString(rootNode.get("dataset")));
            responseRevenueByGameType.setFormat(JsonStringParser.asString(rootNode.get("format")));
            responseRevenueByGameType.setCompression(JsonStringParser.asBoolean(rootNode.get("compression")));
            if (JsonStringParser.asInt(rootNode.get("ErrorCode")) != null) {
               responseRevenueByGameType.setErrorCode(rootNode.get("ErrorCode").asInt());
            }
            if (JsonStringParser.asInt(rootNode.get("error")) != null) {
               responseRevenueByGameType.setError(rootNode.get("error").asText());
            }

            if (rootNode.get("data") != null && rootNode.get("data").isArray()) {
               ArrayNode revenueByGameTypeDataArray = (ArrayNode) rootNode.get("data");
               List<RevenueByGameTypeData> revenueByGameTypeDataList = new ArrayList<RevenueByGameTypeData>();
               revenueByGameTypeDataArray.forEach(oRevenueByGameTypeData -> {
                  RevenueByGameTypeData revenueByGameTypeData = new RevenueByGameTypeData();
                  if (oRevenueByGameTypeData != null) {
                     revenueByGameTypeData
                           .setOperatorID(JsonStringParser.asString(oRevenueByGameTypeData.get("OperatorID")));
                     revenueByGameTypeData
                           .setNumRounds(JsonStringParser.asString(oRevenueByGameTypeData.get("NumRounds")));
                     revenueByGameTypeData
                           .setNumPlayers(JsonStringParser.asString(oRevenueByGameTypeData.get("NumPlayers")));
                     revenueByGameTypeData.setBet(JsonStringParser.asString(oRevenueByGameTypeData.get("Bet")));
                     revenueByGameTypeData.setPayout(JsonStringParser.asString(oRevenueByGameTypeData.get("Payout")));
                     revenueByGameTypeData.setSessionCurrency(
                           JsonStringParser.asString(oRevenueByGameTypeData.get("SessionCurrency")));
                     revenueByGameTypeData.setConvertionRateToDollar(
                           JsonStringParser.asString(oRevenueByGameTypeData.get("ConvertionRateToDollar")));
                     revenueByGameTypeData.setConBet(JsonStringParser.asString(oRevenueByGameTypeData.get("ConBet")));
                     revenueByGameTypeData
                           .setConPayout(JsonStringParser.asString(oRevenueByGameTypeData.get("ConPayout")));
                     revenueByGameTypeData
                           .setBonusWinUSD(JsonStringParser.asString(oRevenueByGameTypeData.get("BonusWinUSD")));
                     revenueByGameTypeData
                           .setBonusBetUSD(JsonStringParser.asString(oRevenueByGameTypeData.get("BonusBetUSD")));
                     revenueByGameTypeData.setEdge(JsonStringParser.asString(oRevenueByGameTypeData.get("Edge")));
                     revenueByGameTypeData.setNgrUSD(JsonStringParser.asString(oRevenueByGameTypeData.get("NGRUSD")));
                     revenueByGameTypeData.setBonusConsumption(
                           JsonStringParser.asString(oRevenueByGameTypeData.get("BonusConsumption")));
                     revenueByGameTypeData.setGgrUSD(JsonStringParser.asString(oRevenueByGameTypeData.get("GGRUSD")));
                     revenueByGameTypeData
                           .setGameTypeID(JsonStringParser.asString(oRevenueByGameTypeData.get("GameTypeID")));
                  }

                  revenueByGameTypeDataList.add(revenueByGameTypeData);
               });
               responseRevenueByGameType.setData(revenueByGameTypeDataList.toArray(new RevenueByGameTypeData[0]));
            } else if (rootNode.get("data") != null && rootNode.get("data").isBoolean()) {
               List<RevenueByGameTypeData> revenueByGameTypeDataList = new ArrayList<RevenueByGameTypeData>();
               responseRevenueByGameType.setData(revenueByGameTypeDataList.toArray(new RevenueByGameTypeData[0]));
            }
         }
      } catch (Exception e) {
         log.error("parseResponseRevenueByGameTypeJsonToBean exception:" + e);
         throw new EzugiException(EzugiError.JSON_STRING_PARSE_ERROR.getErrorCode(),
               EzugiError.JSON_STRING_PARSE_ERROR.getErrorDesc(), "");
      }

      return responseRevenueByGameType;
   }

   private String parseDateByDateFormat(String pDateStr) {
      Date date = null;
      String dateString = null;
      try {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         date = sdf.parse(pDateStr);
         dateString = DateFormatUtils.format(date, "MM.dd.yyyy");
      } catch (Exception e) {
         log.error("parseDateByDateFormat exception:" + e);
         throw new EzugiException(EzugiError.PARSE_DATE_FORMAT_ERROR.getErrorCode(),
               EzugiError.PARSE_DATE_FORMAT_ERROR.getErrorDesc(), "");
      }

      return dateString;
   }

   private String getRequestUrl(String apiName, String baseUrl, EzugiUser pUser, EzugiLoginForm ezugiLoginForm)
         throws Exception {
      StringBuffer stringBuffer = new StringBuffer(baseUrl).append("?");

      if (pUser != null && pUser.getAgentId() != null && StringUtils.isNotBlank(pUser.getAgentId().toString())) {
         stringBuffer.append("agent_id=").append(pUser.getAgentId().toString());
      } else if (ezugiLoginForm != null && ezugiLoginForm.getAgentId() != null
            && StringUtils.isNotBlank(ezugiLoginForm.getAgentId().toString())) {
         stringBuffer.append("agent_id=").append(ezugiLoginForm.getAgentId().toString());
      } else {
         throw new IllegalArgumentException("agent_id");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPlayerUsername())) {
         stringBuffer.append("&player_username=").append(pUser.getPlayerUsername());
      } else if (ezugiLoginForm != null && StringUtils.isNotBlank(ezugiLoginForm.getPlayerUsername())) {
         stringBuffer.append("&player_username=").append(ezugiLoginForm.getPlayerUsername());
      } else if (ApiName.REGISTER.name().equals(apiName) || ApiName.LOGIN.name().equals(apiName)) {
         throw new IllegalArgumentException("player_username");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPlayerPassword())) {
         stringBuffer.append("&player_password=").append(pUser.getPlayerPassword());
      } else if (ezugiLoginForm != null && StringUtils.isNotBlank(ezugiLoginForm.getPlayerPassword())) {
         stringBuffer.append("&player_password=").append(ezugiLoginForm.getPlayerPassword());
      } else if (ApiName.REGISTER.name().equals(apiName) || ApiName.LOGIN.name().equals(apiName)) {
         throw new IllegalArgumentException("player_password");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getUserName())) {
         stringBuffer.append("&username=").append(pUser.getUserName());
      } else if (ezugiLoginForm != null && StringUtils.isNotBlank(ezugiLoginForm.getUserName())) {
         stringBuffer.append("&username=").append(ezugiLoginForm.getUserName());
      } else {
         throw new IllegalArgumentException("username");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPaymentMethod())) {
         stringBuffer.append("&payment_method=").append(pUser.getPaymentMethod());
      } else if (ApiName.CREDIT.name().equals(apiName) || ApiName.DEBIT.name().equals(apiName)) {
         throw new IllegalArgumentException("payment_method");
      }

      if (pUser != null && pUser.getEzugiBalance() != null
            && StringUtils.isNotBlank(pUser.getEzugiBalance().getTransferAmount().toString())) {
         stringBuffer.append("&amount=").append(pUser.getEzugiBalance().getTransferAmount());
      } else if (ApiName.CREDIT.name().equals(apiName) || ApiName.DEBIT.name().equals(apiName)) {
         throw new IllegalArgumentException("amount");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getNickName())) {
         stringBuffer.append("&nickname=").append(pUser.getNickName());
      } else if (ApiName.REGISTER.name().equals(apiName)) {
         throw new IllegalArgumentException("nickname");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getSessionIp())) {
         stringBuffer.append("&session_ip=").append(pUser.getSessionIp());
      } else if (ezugiLoginForm != null && StringUtils.isNotBlank(ezugiLoginForm.getSessionIp())) {
         stringBuffer.append("&session_ip=").append(ezugiLoginForm.getSessionIp());
      } else if (ApiName.REGISTER.name().equals(apiName) || ApiName.LOGIN.name().equals(apiName)
            || ApiName.GAME_TOKEN.name().equals(apiName) || ApiName.BALANCE.name().equals(apiName)
            || ApiName.HISTORY.name().equals(apiName)) {
         throw new IllegalArgumentException("session_ip");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getSessionToken())) {
         stringBuffer.append("&session_token=").append(pUser.getSessionToken());
      } else if (ApiName.CREDIT.name().equals(apiName) || ApiName.DEBIT.name().equals(apiName)
            || ApiName.GAME_TOKEN.name().equals(apiName) || ApiName.BALANCE.name().equals(apiName)
            || ApiName.HISTORY.name().equals(apiName)) {
         throw new IllegalArgumentException("session_token");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getProviderId())) {
         stringBuffer.append("&provider_id=").append(pUser.getProviderId());
      } else if (ApiName.GAME_TOKEN.name().equals(apiName)) {
         throw new IllegalArgumentException("provider_id");
      }

      if (pUser != null && pUser.getAdvertiserId() != null
            && StringUtils.isNotBlank(pUser.getAdvertiserId().toString())) {
         stringBuffer.append("&advertiser_id=").append(pUser.getAdvertiserId());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getFirstName())) {
         stringBuffer.append("&first_name=").append(pUser.getFirstName());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getMiddleName())) {
         stringBuffer.append("&middle_name=").append(pUser.getMiddleName());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getLastName())) {
         stringBuffer.append("&last_name=").append(pUser.getLastName());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPhone1())) {
         stringBuffer.append("&phone1=").append(pUser.getPhone1());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPhone2())) {
         stringBuffer.append("&phone2=").append(pUser.getPhone2());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getDateOfBirth())) {
         stringBuffer.append("&date_of_birth=").append(pUser.getDateOfBirth());
      }

      if (pUser != null && pUser.getDailyBettingLimit() != null
            && StringUtils.isNotBlank(pUser.getDailyBettingLimit().toString())) {
         stringBuffer.append("&daily_betting_limit=").append(pUser.getDailyBettingLimit());
      }

      if (pUser != null && pUser.getMonthlyBettingLimit() != null
            && StringUtils.isNotBlank(pUser.getMonthlyBettingLimit().toString())) {
         stringBuffer.append("&monthly_betting_limit=").append(pUser.getMonthlyBettingLimit());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getLanguage())) {
         stringBuffer.append("&language=").append(pUser.getLanguage());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPersonalIdType())) {
         stringBuffer.append("&personal_id_type=").append(pUser.getPersonalIdType());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPersonalIdCountry())) {
         stringBuffer.append("&personal_id_country=").append(pUser.getPersonalIdCountry());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPersonalIdIssueDate())) {
         stringBuffer.append("&personal_id_issue_date=").append(pUser.getPersonalIdIssueDate());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getPersonalIdExipres())) {
         stringBuffer.append("&personal_id_exipres=").append(pUser.getPersonalIdExipres());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getAddress1())) {
         stringBuffer.append("&address1=").append(pUser.getAddress1());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getAddress2())) {
         stringBuffer.append("&address2=").append(pUser.getAddress2());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getCity())) {
         stringBuffer.append("&city=").append(pUser.getCity());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getState())) {
         stringBuffer.append("&state=").append(pUser.getState());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getZipcode())) {
         stringBuffer.append("&zipcode=").append(pUser.getZipcode());
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getCountryCode())) {
         stringBuffer.append("&country_code=").append(pUser.getCountryCode());
      }

      if (ezugiLoginForm != null && StringUtils.isNotBlank(ezugiLoginForm.getLogin())) {
         stringBuffer.append("&login=").append(ezugiLoginForm.getLogin());
      } else if (ApiName.LOGIN.name().equals(apiName)) {
         throw new IllegalArgumentException("login");
      }

      if (pUser != null && StringUtils.isNotBlank(pUser.getRequestToken())) {
         stringBuffer.append("&request_token=").append(pUser.getRequestToken());
      } else if (StringUtils.isNotBlank(ezugiLoginForm.getRequestToken())) {
         stringBuffer.append("&request_token=").append(ezugiLoginForm.getRequestToken());
      } else {
         throw new IllegalArgumentException("request_token");
      }

      return stringBuffer.toString();
   }

   private MultiValueMap<String, Object> transferRequestJson(RequestGameRounds requestGameRounds,
         RequestAllPlayers requestAllPlayers, RequestActivePlayers requestActivePlayers,
         RequestRevenueByGameType requestRevenueByGameType) throws Exception {
      MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getDataSet())) {
         map.add("DataSet", requestGameRounds.getDataSet());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getDataSet())) {
         map.add("DataSet", requestAllPlayers.getDataSet());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getDataSet())) {
         map.add("DataSet", requestActivePlayers.getDataSet());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getDataSet())) {
         map.add("DataSet", requestRevenueByGameType.getDataSet());
      } else {
         throw new IllegalArgumentException("DataSet");
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getApiID())) {
         map.add("APIID", requestGameRounds.getApiID());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getApiID())) {
         map.add("APIID", requestAllPlayers.getApiID());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getApiID())) {
         map.add("APIID", requestActivePlayers.getApiID());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getApiID())) {
         map.add("APIID", requestRevenueByGameType.getApiID());
      } else {
         throw new IllegalArgumentException("APIID");
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getApiUser())) {
         map.add("APIUser", requestGameRounds.getApiUser());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getApiUser())) {
         map.add("APIUser", requestAllPlayers.getApiUser());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getApiUser())) {
         map.add("APIUser", requestActivePlayers.getApiUser());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getApiUser())) {
         map.add("APIUser", requestRevenueByGameType.getApiUser());
      } else {
         throw new IllegalArgumentException("APIUser");
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getTimePeriod())) {
         map.add("TimePeriod", requestGameRounds.getTimePeriod());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getTimePeriod())) {
         map.add("TimePeriod", requestAllPlayers.getTimePeriod());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getTimePeriod())) {
         map.add("TimePeriod", requestActivePlayers.getTimePeriod());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getTimePeriod())) {
         map.add("TimePeriod", requestRevenueByGameType.getTimePeriod());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getStartTime())) {
         map.add("StartTime", requestGameRounds.getStartTime());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getStartTime())) {
         map.add("StartTime", requestAllPlayers.getStartTime());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getStartTime())) {
         map.add("StartTime", requestActivePlayers.getStartTime());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getStartTime())) {
         map.add("StartTime", requestRevenueByGameType.getStartTime());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getEndTime())) {
         map.add("EndTime", requestGameRounds.getEndTime());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getEndTime())) {
         map.add("EndTime", requestAllPlayers.getEndTime());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getEndTime())) {
         map.add("EndTime", requestActivePlayers.getEndTime());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getEndTime())) {
         map.add("EndTime", requestRevenueByGameType.getEndTime());
      }
      if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getCurrency())) {
         map.add("Currency", requestRevenueByGameType.getCurrency());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getRoundID())) {
         map.add("RoundID", requestGameRounds.getRoundID());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getTableID())) {
         map.add("TableID", requestGameRounds.getTableID());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getTableID())) {
         map.add("TableID", requestRevenueByGameType.getTableID());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getDealerID())) {
         map.add("DealerID", requestGameRounds.getDealerID());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getuID())) {
         map.add("UID", requestGameRounds.getuID());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getuID())) {
         map.add("UID", requestAllPlayers.getuID());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getuID())) {
         map.add("UID", requestActivePlayers.getuID());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getGameType())) {
         map.add("GameType", requestGameRounds.getGameType());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getGameType())) {
         map.add("GameType", requestAllPlayers.getGameType());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getGameType())) {
         map.add("GameType", requestActivePlayers.getGameType());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getLimit())) {
         map.add("Limit", requestGameRounds.getLimit());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getLimit())) {
         map.add("Limit", requestAllPlayers.getLimit());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getLimit())) {
         map.add("Limit", requestActivePlayers.getLimit());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getLimit())) {
         map.add("Limit", requestRevenueByGameType.getLimit());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getPage())) {
         map.add("Page", requestGameRounds.getPage());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getPage())) {
         map.add("Page", requestAllPlayers.getPage());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getPage())) {
         map.add("Page", requestActivePlayers.getPage());
      } else if (requestRevenueByGameType != null && StringUtils.isNotBlank(requestRevenueByGameType.getPage())) {
         map.add("Page", requestRevenueByGameType.getPage());
      }

      if (requestGameRounds != null && StringUtils.isNotBlank(requestGameRounds.getRequestToken())) {
         map.add("RequestToken", requestGameRounds.getRequestToken());
      } else if (requestAllPlayers != null && StringUtils.isNotBlank(requestAllPlayers.getRequestToken())) {
         map.add("RequestToken", requestAllPlayers.getRequestToken());
      } else if (requestActivePlayers != null && StringUtils.isNotBlank(requestActivePlayers.getRequestToken())) {
         map.add("RequestToken", requestActivePlayers.getRequestToken());
      } else if (requestRevenueByGameType != null
            && StringUtils.isNotBlank(requestRevenueByGameType.getRequestToken())) {
         map.add("RequestToken", requestRevenueByGameType.getRequestToken());
      } else {
         throw new IllegalArgumentException("RequestToken");
      }
      return map;
   }

   /* transfer api combind request token */
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
      } else {
         throw new IllegalArgumentException("agent_id");
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
      } else {
         throw new IllegalArgumentException("username");
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

      try {
         accessToken = sha2.toHash(sb.toString());
      } catch (Exception e) {
         throw new EzugiException(EzugiError.SHA256_ERROR.getErrorCode(), EzugiError.SHA256_ERROR.getErrorDesc(), "");
      }
      return accessToken;
   }

   /* 取得login的response資訊,目前抓取session token及provider_id */
   private HashMap<String, String> getPropertyMapAfterLogin(EzugiLoginForm ezugiLoginForm, ResponsePayload payload)
         throws Exception {
      String sessionToken = null;
      HashMap<String, String> map = new HashMap<String, String>();
      ResponsePayload<ResponseLoginData> responseLoginData = login(ezugiLoginForm, payload);

      if (responseLoginData != null && responseLoginData.getData() != null
            && responseLoginData.getData().getSession() != null) {
         sessionToken = responseLoginData.getData().getSession().getSessionToken();
         map.put("sessionToken", sessionToken);
      }

      if (responseLoginData != null && responseLoginData.getData() != null
            && responseLoginData.getData().getProviders() != null) {
         for (Provider provider : responseLoginData.getData().getProviders()) {
            if (StringUtils.isNotBlank(provider.getProviderId())) {
               map.put("providerId", provider.getProviderId());
               break;
            }
         }
      }

      return map;
   }

   /* backend api combind request token */
   private String getRequestToken(String apiSalt, RequestGameRounds requestGameRounds,
         RequestAllPlayers requestAllPlayers, RequestActivePlayers requestActivePlayers,
         RequestRevenueByGameType requestRevenueByGameType) throws Exception {
      // init data
      SHA2Hash sha2 = new SHA2Hash();
      StringBuffer sb = new StringBuffer();
      String accessToken = null;

      sb.append(apiSalt);
      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getDataSet())) {
         sb.append("DataSet=").append(requestGameRounds.getDataSet());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getDataSet())) {
         sb.append("DataSet=").append(requestAllPlayers.getDataSet());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getDataSet())) {
         sb.append("DataSet=").append(requestActivePlayers.getDataSet());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getDataSet())) {
         sb.append("DataSet=").append(requestRevenueByGameType.getDataSet());
      } else {
         throw new IllegalArgumentException("DataSet");
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getApiID())) {
         sb.append("&APIID=").append(requestGameRounds.getApiID());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getApiID())) {
         sb.append("&APIID=").append(requestAllPlayers.getApiID());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getApiID())) {
         sb.append("&APIID=").append(requestActivePlayers.getApiID());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getApiID())) {
         sb.append("&APIID=").append(requestRevenueByGameType.getApiID());
      } else {
         throw new IllegalArgumentException("APIID");
      }

      if (requestGameRounds != null && StringUtils.isNotEmpty(requestGameRounds.getApiUser())) {
         sb.append("&APIUser=").append(requestGameRounds.getApiUser());
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getApiUser())) {
         sb.append("&APIUser=").append(requestAllPlayers.getApiUser());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getApiUser())) {
         sb.append("&APIUser=").append(requestActivePlayers.getApiUser());
      } else if (requestRevenueByGameType != null && StringUtils.isNotEmpty(requestRevenueByGameType.getApiUser())) {
         sb.append("&APIUser=").append(requestRevenueByGameType.getApiUser());
      } else {
         throw new IllegalArgumentException("APIUser");
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
      } else if (requestAllPlayers != null && StringUtils.isNotEmpty(requestAllPlayers.getGameType())) {
         sb.append("&GameType=").append(requestAllPlayers.getGameType());
      } else if (requestActivePlayers != null && StringUtils.isNotEmpty(requestActivePlayers.getGameType())) {
         sb.append("&GameType=").append(requestActivePlayers.getGameType());
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

      try {
         accessToken = sha2.toHash(sb.toString());
      } catch (Exception e) {
         throw new EzugiException(EzugiError.SHA256_ERROR.getErrorCode(), EzugiError.SHA256_ERROR.getErrorDesc(), "");
      }
      return accessToken;
   }

   private boolean findGameByGameName(String originString, String matchString) throws Exception {
      if(StringUtils.isNotBlank(originString) && StringUtils.isNotBlank(matchString)) {
         if(originString.indexOf("?") > 0) {
            String splitCharacters = originString.substring(originString.indexOf("?"));
            String[] splitArary = splitCharacters.split("&");
            if(StringUtils.isNotBlank(findGameNameKey)) {
               String matchKeyAndValue = new StringBuffer().append(findGameNameKey).append("=").append(matchString).toString();
               for(String keyAndValue : splitArary) {
                  if(keyAndValue.equals(matchKeyAndValue)) {
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }

}