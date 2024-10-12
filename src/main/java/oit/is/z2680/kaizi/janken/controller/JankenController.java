package oit.is.z2680.kaizi.janken.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2680.kaizi.janken.model.Entry;
import oit.is.z2680.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String janken(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName(); // セッションからユーザー名を取得

    Entry entry = new Entry();
    entry.addUser(username); // ユーザー名をリストに追加

    model.addAttribute("entry", entry); // Entryオブジェクトをモデルに追加
    model.addAttribute("users", entry.getUsers()); // ユーザーリストをモデルに追加
    return "janken";
  }

  @GetMapping("/janken/result")
  public String jankenResult(@RequestParam String hand, Model model) {
    Janken janken = new Janken();
    janken.setYourHand(hand);

    model.addAttribute("yourHand", janken.getYourHand());
    model.addAttribute("cpuHand", janken.getCpuHand());
    model.addAttribute("result", janken.getResult());

    return "janken";
  }
}
