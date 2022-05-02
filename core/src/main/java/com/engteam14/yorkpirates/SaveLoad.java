
package com.engteam14.yorkpirates;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.util.Objects;

public class SaveLoad {
  protected String path;
  protected JSONObject savedGame;
  protected JSONObject loadedGame;

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

  // public void saveObject(GameScreen screen){
  //   object.genSave();
  //   savedGame.put(object.getName(), object.returnSave());
  // }

  public void resumeSave(GameScreen screen){
    resumeObject(screen.getPlayer());
  }

  public void resumeObject(Player player){
    JSONObject playerObj = (JSONObject) loadedGame.get("player");

    player.loadSave(playerObj);
  }

  public void loadSave(){
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(path)) {
        Object loadedGameObj = parser.parse(reader);

        loadedGame = (JSONObject) loadedGameObj;

        System.out.println(loadedGame);
    }catch (FileNotFoundException exc) {
      ;
    }catch (IOException exc) {
      ;
    }catch (ParseException exc) {
      ;
    }

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
