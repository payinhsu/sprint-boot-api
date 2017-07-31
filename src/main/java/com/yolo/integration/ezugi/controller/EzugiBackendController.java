package com.yolo.integration.ezugi.controller;

import com.poseitech.integration.message.ResponsePayload;
import com.yolo.integration.ezugi.model.backend.*;
import com.yolo.integration.ezugi.service.EzugiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ${file_name} created ${date}
 * <p>
 * \$LastChangedBy\$ \$Date\$ \$Revision\$
 */
@RestController
public class EzugiBackendController {
   private static Logger log = LoggerFactory.getLogger(EzugiBackendController.class);

   @Autowired
   private EzugiService ezugiService;

   /**
    * 取得game rounds data <br>
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/getGameRounds
    *
    * @param RequestGameRounds
    *           必填欄位：dataSet、apiID、apiUser
    * @return true: return ResponseGameRounds
    */
   @RequestMapping(value = "/getGameRounds", produces = { "application/json" }, consumes = {
         "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseGameRounds> gameRounds(
         @RequestBody RequestGameRounds requestGameRounds) {
      log.info("gameRounds start...");
      ResponsePayload<ResponseGameRounds> payload = new ResponsePayload<ResponseGameRounds>();
      payload = ezugiService.getGameRounds(requestGameRounds, payload);

      return payload;
   }

   /**
    * 取得all players data <br>
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/getAllPlayers
    *
    * @param RequestAllPlayers
    *           必填欄位：dataSet、apiID、apiUser
    * @return true: return ResponseAllPlayers
    */
   @RequestMapping(value = "/getAllPlayers", produces = { "application/json" }, consumes = {
         "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseAllPlayers> getAllPlayers(
         @RequestBody RequestAllPlayers requestAllPlayers) {
      log.info("getAllPlayers start...");
      ResponsePayload<ResponseAllPlayers> payload = new ResponsePayload<ResponseAllPlayers>();
      payload = ezugiService.getAllPlayers(requestAllPlayers, payload);

      return payload;
   }

   /**
    * 取得active players data <br>
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/getActivePlayers
    *
    * @param RequestActivePlayers
    *           必填欄位：dataSet、apiID、apiUser
    * @return true: return ResponseActivePlayers
    */
   @RequestMapping(value = "/getActivePlayers", produces = { "application/json" }, consumes = {
         "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseActivePlayers> getActivePlayers(
         @RequestBody RequestActivePlayers requestActivePlayers) {
      log.info("getActivePlayers start...");
      ResponsePayload<ResponseActivePlayers> payload = new ResponsePayload<ResponseActivePlayers>();
      payload = ezugiService.getActivePlayres(requestActivePlayers, payload);

      return payload;
   }

   /**
    * 取得revenue by game type data <br>
    * link: http://localhost:38038/yoloIntChaofan2Ezgui/getRevenueByGameType
    *
    * @param RequestRevenueByGameType
    *           必填欄位：dataSet、apiID、apiUser
    * @return true: return ResponseRevenueByGameType
    */
   @RequestMapping(value = "/getRevenueByGameType", produces = { "application/json" }, consumes = {
         "application/json" }, method = { RequestMethod.POST })
   @ResponseStatus(HttpStatus.OK)
   public @ResponseBody ResponsePayload<ResponseRevenueByGameType> getRevenueByGameType(
         @RequestBody RequestRevenueByGameType requestRevenueByGameType) {
      log.info("getRevenueByGameType start...");
      ResponsePayload<ResponseRevenueByGameType> payload = new ResponsePayload<ResponseRevenueByGameType>();
      payload = ezugiService.getRevenueByGameType(requestRevenueByGameType, payload);

      return payload;
   }

}
