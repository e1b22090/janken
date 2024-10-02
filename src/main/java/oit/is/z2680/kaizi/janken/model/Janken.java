package oit.is.z2680.kaizi.janken.model;

import org.springframework.stereotype.Component;

@Component
public class Janken {
  private String playerHand;
  private String cpuHand;
  private String result;

  public String getPlayerHand() {
    return playerHand;
  }

  public void setPlayerHand(String playerHand) {
    this.playerHand = playerHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public String getResult() {
    return result;
  }

  // CPUが常にグーを出す例
  public void judge() {
    this.cpuHand = "グー"; // CPUは常にグーを出す
    if (playerHand == null) {
      this.result = "エラー: 手が選択されていません";
      return;
    }

    if (playerHand.equals("グー")) {
      this.result = "引き分け";
    } else if (playerHand.equals("チョキ")) {
      this.result = "あなたの負け";
    } else if (playerHand.equals("パー")) {
      this.result = "あなたの勝ち";
    }
  }

  // 引数を受け取るように定義
  public String judge(String yourHand) {
    String cpuHand = "グー"; // CPUの手は常にグー
    if (yourHand.equals(cpuHand)) {
      return "引き分け";
    } else if (yourHand.equals("チョキ")) {
      return "負け";
    } else if (yourHand.equals("パー")) {
      return "勝ち";
    }
    return "不正な手";
  }
}
