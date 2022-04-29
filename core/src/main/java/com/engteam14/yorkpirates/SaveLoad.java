package com.engteam14.yorkpirates;

import org.json.simple.JSONObject;

public class SaveLoad {
  protected String path;
  protected JSONObject savedGame;

  public SaveLoad(String directory){
    path = directory;
    savedGame = new JSONObject();
  }

  public void saveObject(GameObject object){
    object.genSave();
    savedGame.put(object.team, object.returnSave());
  }

  public void saveObject(College object){
    object.genSave();
    savedGame.put(object.getName(), object.returnSave());
  }

  public void saveObject(Player object){
    object.genSave();
    savedGame.put("player", object.returnSave());
  }
}
