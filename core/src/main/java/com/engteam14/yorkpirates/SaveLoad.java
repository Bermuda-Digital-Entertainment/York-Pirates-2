package com.engteam14.yorkpirates;

import org.json.simple.JSONObject;

public class SaveLoad {
  protected String path;
  protected JSONObject savedGame;

  public SaveLoad(String directory){
    path = directory;
    savedGame = new JSONObject();
  }
}
