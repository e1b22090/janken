package oit.is.z2680.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2680.kaizi.janken.model.MatchMapper;
import oit.is.z2680.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2680.kaizi.janken.model.MatchInfo;

@Service
public class AsyncKekka {

  private static final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  private boolean dbUpdated = false;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Transactional
  public void syncUpdateMatchResults() {
    dbUpdated = true;
  }

  @Async
  public void asyncCheckMatchResults(SseEmitter emitter, int userId) {
    try {
      while (true) {
        if (dbUpdated) {
          ArrayList<MatchInfo> activeMatches = matchInfoMapper.findActiveMatchInfoByUserId(userId);
          if (!activeMatches.isEmpty()) {
            emitter.send(activeMatches); // ここで送信
            dbUpdated = false;
          }
        }
        TimeUnit.MILLISECONDS.sleep(1000);
      }
    } catch (Exception e) {
      logger.warn("Exception: " + e.getClass().getName() + ": " + e.getMessage());
    } finally {
      emitter.complete();
    }
  }

}
