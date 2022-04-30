
package com.engteam14.yorkpirates;


import org.json.simple.JSONObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

  public void loadSave(){

  }

  public void saveSave(){
    try {
      FileWriter gameWriter = new FileWriter(path);
      gameWriter.write(this.returnSaveString());
      gameWriter.close();
    }
    catch (IOException exception) {
      System.out.println("Cannot write to file");
      exception.printStackTrace();
    }
  }

  public JSONObject returnSave(){
    return savedGame;
  }

  public String returnSaveString(){
    return savedGame.toString();
  }
}
