package oit.is.z2680.kaizi.janken.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class Entry {
  private ArrayList<String> users = new ArrayList<>();
  private int entryNo = 1;

  public void addUser(String name) {
    // 同名のユーザーが居たら何もせずにreturn
    for (String user : this.users) {
      if (user.equals(name)) {
        return;
      }
    }
    // 同名のユーザーが居なかった場合はusersにnameを追加する
    this.users.add(name);
  }

  // 以降はフィールドのgetter/setter
  public int getEntryNo() {
    return entryNo;
  }

  public void setEntryNo(int entryNo) {
    this.entryNo = entryNo;
  }

  public ArrayList<String> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<String> users) {
    this.users = users;
  }
}
