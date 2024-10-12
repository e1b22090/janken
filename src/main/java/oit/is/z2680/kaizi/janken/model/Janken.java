package oit.is.z2680.kaizi.janken.model;

public class Janken {
  private String yourHand;
  private String cpuHand;
  private String result;

  public Janken() {
    this.cpuHand = "グー";
  }

  public void setYourHand(String yourHand) {
    this.yourHand = yourHand;
    this.result = this.judge();
  }

  public String getYourHand() {
    return this.yourHand;
  }

  public String getCpuHand() {
    return this.cpuHand;
  }

  public String getResult() {
    return this.result;
  }

  private String judge() {
    if (this.yourHand.equals(this.cpuHand)) {
      return "引き分け";
    } else if (this.yourHand.equals("チョキ")) {
      return "あなたの負け";
    } else if (this.yourHand.equals("パー")) {
      return "あなたの勝ち";
    } else {
      return "無効な手です"; // 無効な手が選ばれた場合のメッセージ
    }
  }
}
