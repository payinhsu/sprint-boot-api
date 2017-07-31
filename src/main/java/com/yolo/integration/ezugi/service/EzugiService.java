/**
 * PlaynGoService.java created 2016年11月5日
 *
 */
package com.yolo.integration.ezugi.service;

import com.poseitech.integration.message.ResponsePayload;
import com.yolo.integration.ezugi.model.*;
import com.yolo.integration.ezugi.model.backend.*;

/**
 * @author peter.hsu
 */
public interface EzugiService {

   public ResponsePayload<ResponseRegisterData> register(EzugiUser pUser, ResponsePayload payload) throws EzugiException;

   public ResponsePayload<ResponseLoginData> login(EzugiLoginForm ezugiLoginForm, ResponsePayload payload) throws EzugiException;

   public ResponsePayload<ResponseTransferFundData> credit(EzugiUser pUser, ResponsePayload payload) throws EzugiException;

   public ResponsePayload<ResponseTransferFundData> debit(EzugiUser pUser, ResponsePayload payload) throws EzugiException;

   public ResponsePayload<ResponseGameTokenData> getGameToken(EzugiUser pUser, ResponsePayload payload) throws EzugiException;

   public ResponsePayload<ProviderGame> getGameDetailByName(EzugiUser pUser, ResponsePayload payload) throws EzugiException;

   public ResponsePayload<ResponseBalanceData> getBalance(EzugiUser pUser, ResponsePayload payload) throws EzugiException;

   public ResponsePayload<ResponseHistoryData> getHistory(EzugiUser pUser, ResponsePayload payload) throws EzugiException;

   /* backend api service */
   public ResponsePayload<ResponseGameRounds> getGameRounds(RequestGameRounds requestGameRounds, ResponsePayload payload) throws EzugiException;

   /* backend api service */
   public ResponsePayload<ResponseAllPlayers> getAllPlayers(RequestAllPlayers requestAllPlayers, ResponsePayload payload) throws EzugiException;

   /* backend api service */
   public ResponsePayload<ResponseActivePlayers> getActivePlayres(RequestActivePlayers requestActivePlayers, ResponsePayload payload) throws EzugiException;

   /* backend api service */
   public ResponsePayload<ResponseRevenueByGameType> getRevenueByGameType(RequestRevenueByGameType requestRevenueByGameType, ResponsePayload payload)
         throws EzugiException;

}
