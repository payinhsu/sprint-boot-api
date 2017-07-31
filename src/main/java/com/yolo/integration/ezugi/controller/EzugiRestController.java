package com.yolo.integration.ezugi.controller;

import com.yolo.integration.ezugi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poseitech.integration.message.ResponsePayload;
import com.yolo.integration.ezugi.service.EzugiService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EzugiRestController {

   private static Logger log = LoggerFactory.getLogger(EzugiRestController.class);

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

   @Autowired
   private EzugiService ezugiService;

   /**
    * 註冊會員 <br>
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/register
    * 
    * @param EzugiUser
    *           必填欄位：playerUsername、playerPassword
    * @return true: return http status 200, false: else
    */
   @RequestMapping(value = "/register", produces = { "application/json" }, consumes = { "application/json" }, method = {
         RequestMethod.POST })
   public @ResponseBody ResponsePayload<ResponseRegisterData> register(HttpServletRequest request,
         @RequestBody EzugiUser ezugiUser) {
      log.info("register start...");
      ResponsePayload<ResponseRegisterData> payload = new ResponsePayload<ResponseRegisterData>();
      payload = ezugiService.register(ezugiUser, payload);

      return payload;
   }

   /**
    * 會員登入 link: http://localhost:38038/yoloIntChaofan2Ezgui/login
    * 
    * @param EzugiLoginForm
    *           必填欄位：playerUsername、playerPassword
    * @return true: return http status 200, false: else
    */
   @RequestMapping(value = "/login", produces = { "application/json" }, consumes = { "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseLoginData> login(HttpServletRequest request,
         @RequestBody EzugiLoginForm ezugiLoginForm) {
      log.info("login start...");
      ResponsePayload<ResponseLoginData> payload = new ResponsePayload<ResponseLoginData>();
      payload = ezugiService.login(ezugiLoginForm, payload);

      return payload;
   }

   /**
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/debit
    * 
    * @param EzugiUser
    *           必填欄位：EzugiBalance.transferAmount、playerUsername、playerPassword
    * @return
    */
   @RequestMapping(value = "/credit", produces = { "application/json" }, consumes = { "application/json" }, method = {
         RequestMethod.POST })
   public @ResponseBody ResponsePayload<ResponseTransferFundData> credit(@RequestBody EzugiUser ezugiUser) {
      log.info("credit start...");
      ResponsePayload<ResponseTransferFundData> payload = new ResponsePayload<ResponseTransferFundData>();
      payload = ezugiService.credit(ezugiUser, payload);

      return payload;
   }

   /**
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/debit
    * 
    * @param EzugiUser
    *           必填欄位：EzugiBalance.transferAmount、playerUsername、playerPassword
    * @return
    */
   @RequestMapping(value = "/debit", produces = { "application/json" }, consumes = { "application/json" }, method = {
         RequestMethod.POST })
   public @ResponseBody ResponsePayload<ResponseTransferFundData> debit(@RequestBody EzugiUser ezugiUser) {
      log.info("debit start...");
      ResponsePayload<ResponseTransferFundData> payload = new ResponsePayload<ResponseTransferFundData>();
      payload = ezugiService.debit(ezugiUser, payload);

      return payload;
   }

   /**
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/gameToken
    * 
    * @param EzugiUser
    *           必填欄位：playerUsername、playerPassword
    * @return
    */
   @RequestMapping(value = "/gameToken", produces = { "application/json" }, consumes = { "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseGameTokenData> gameToken(HttpServletRequest request,
         @RequestBody EzugiUser ezugiUser) {
      log.info("gameToken start...");
      ResponsePayload<ResponseGameTokenData> payload = new ResponsePayload<ResponseGameTokenData>();
      payload = ezugiService.getGameToken(ezugiUser, payload);

      return payload;
   }

   /**
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/gameDetailByName
    *
    * @param EzugiUser
    *           必填欄位：playerUsername、playerPassword、gameName
    * @return
    */
   @RequestMapping(value = "/gameDetailByName", produces = { "application/json" }, consumes = { "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ProviderGame> gameDetailByName(HttpServletRequest request,
         @RequestBody EzugiUser ezugiUser) {
      log.info("gameDetailByName start...");
      ResponsePayload<ProviderGame> payload = new ResponsePayload<ProviderGame>();
      payload = ezugiService.getGameDetailByName(ezugiUser,payload);

      return payload;
   }

   /**
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/balance
    * 
    * @param EzugiUser
    *           必填欄位：playerUsername、playerPassword
    * @return
    */
   @RequestMapping(value = "/balance", produces = { "application/json" }, consumes = { "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseBalanceData> balance(HttpServletRequest request,
         @RequestBody EzugiUser ezugiUser) {
      log.info("balance start...");
      ResponsePayload<ResponseBalanceData> payload = new ResponsePayload<ResponseBalanceData>();
      payload = ezugiService.getBalance(ezugiUser, payload);

      return payload;
   }

   /**
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/history
    * 
    * @param EzugiUser
    *           必填欄位：playerUsername、playerPassword
    * @return
    */
   @RequestMapping(value = "/history", produces = { "application/json" }, consumes = { "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseHistoryData> history(HttpServletRequest request,
         @RequestBody EzugiUser ezugiUser) {
      log.info("history start...");
      ResponsePayload<ResponseHistoryData> payload = new ResponsePayload<ResponseHistoryData>();
      payload = ezugiService.getHistory(ezugiUser, payload);

      return payload;
   }

}
