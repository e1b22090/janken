package oit.is.z2680.kaizi.janken.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.ui.Model;

import oit.is.z2680.kaizi.janken.model.User;
import oit.is.z2680.kaizi.janken.model.UserMapper;
import oit.is.z2680.kaizi.janken.service.AsyncKekka;
import oit.is.z2680.kaizi.janken.model.Match;
import oit.is.z2680.kaizi.janken.model.MatchInfo;
import oit.is.z2680.kaizi.janken.model.MatchMapper;
import oit.is.z2680.kaizi.janken.model.MatchInfoMapper;

@Controller
public class JankenController {

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka asyncKekka;

  @GetMapping("/janken")
  public String getAllUsers(Model model) {
    ArrayList<User> users = userMapper.findAllUsers();
    ArrayList<Match> matches = matchMapper.findAllMatches();
    ArrayList<MatchInfo> activeMatches = matchInfoMapper.findActiveMatches(); // アクティブな試合を取得

    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("activeMatches", activeMatches); // モデルにアクティブな試合を追加
    return "janken";
  }

  @GetMapping("/match")
  public String getMatch(@RequestParam("id") int id, Model model) {
    String loggedInUserName = "ほんだ";
    User opponent = userMapper.findUserById(id);
    // 追加
    User loggedInUser = userMapper.findUserByName(loggedInUserName);

    model.addAttribute("loggedInUser", loggedInUserName);
    model.addAttribute("opponent", opponent.getName());

    // 追加
    model.addAttribute("loggedInUserId", loggedInUser.getId());
    model.addAttribute("opponentId", opponent.getId());

    return "match";
  }

  // 追加７回目
  @GetMapping("/match/fight")
  public String fight(@RequestParam("userHand") String userHand,
      @RequestParam("opponentId") int opponentId,
      Model model) {

    String loggedInUserName = "ほんだ";
    User loggedInUser = userMapper.findUserByName(loggedInUserName);
    User opponent = userMapper.findUserById(opponentId);

    String opponentHand = "Gu";
    String result = determineWinner(userHand, opponentHand);

    ArrayList<MatchInfo> activeMatches = matchInfoMapper.findActiveMatchInfoByUserId(loggedInUser.getId());

    if (activeMatches.isEmpty()) {
      matchInfoMapper.insertMatchInfo(loggedInUser.getId(), opponent.getId(), userHand, true);
    } else {
      matchMapper.insertMatch(loggedInUser.getId(), opponent.getId(), userHand, opponentHand, true);
    }

    asyncKekka.syncUpdateMatchResults();

    model.addAttribute("loggedInUser", loggedInUserName);
    model.addAttribute("opponent", opponent.getName());
    model.addAttribute("userHand", userHand);
    model.addAttribute("opponentHand", opponentHand);
    model.addAttribute("result", result);

    return "wait";
  }

  private String determineWinner(String userHand, String opponentHand) {
    if (userHand.equals(opponentHand)) {
      return "Draw";
    } else if ((userHand.equals("Gu") && opponentHand.equals("Choki")) ||
        (userHand.equals("Choki") && opponentHand.equals("Pa")) ||
        (userHand.equals("Pa") && opponentHand.equals("Gu"))) {
      return "Win";
    } else {
      return "LoLose";
    }
  }

  @GetMapping("/your-endpoint-for-sse")
  public SseEmitter handleSse(@RequestParam int id) {
    SseEmitter emitter = new SseEmitter();
    asyncKekka.asyncCheckMatchResults(emitter, id);
    return emitter;
  }

}
