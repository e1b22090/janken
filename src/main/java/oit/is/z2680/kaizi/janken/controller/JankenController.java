package oit.is.z2680.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2680.kaizi.janken.model.Janken; // 追加

@Controller
public class JankenController {
  private Janken janken;

  public JankenController() {
    this.janken = new Janken();
  }

  @GetMapping("/janken")
  public String janken(@RequestParam String username, Model model) {
    model.addAttribute("username", username);
    return "janken";
  }

  @GetMapping("/janken/play")
  public String playJanken(@RequestParam(name = "hand") String yourHand, Model model) {
    janken.setPlayerHand(yourHand); // プレイヤーの手を設定
    String result = janken.judge(yourHand); // judgeメソッドを呼び出す
    model.addAttribute("yourHand", yourHand);
    model.addAttribute("result", result);
    return "janken";
  }

}
