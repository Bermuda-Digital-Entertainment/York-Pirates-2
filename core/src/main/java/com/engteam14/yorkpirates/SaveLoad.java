package com.engteam14.yorkpirates;

import org.json.simple.JSONObject;

public class SaveLoad {
  protected String path;
  protected JSONObject savedGame;

  SaveLoad(String directory){
    path = directory;
    savedGame = new JSONObject();
  }
}
