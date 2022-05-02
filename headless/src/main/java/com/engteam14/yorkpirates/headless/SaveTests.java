package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import com.engteam14.yorkpirates.SaveLoad;
import com.engteam14.yorkpirates.GameObject;

/** This is a class to test that ALL assets required by the game exist. */
@RunWith(HeadlessLauncher.class)
public class SaveTests {

  /** Tests if the SaveLoad class can be created */
  @Test
  public void testSaveLoadInit() throws Exception {
    SaveLoad saveGame = new SaveLoad("./game");
  }

    /** Tests if the JSON generator for GameObject works correctly */
    @Test
    public void testGameObjectSave1() {
      Array<Texture>testTexture = new Array<>();
      GameObject testObject;
      String testString;
      testTexture.add(new Texture("alcuin_2.png"));

      testObject = new GameObject(testTexture, 0, 12,14, 16, 18, "red");
      testObject.genSave();
      testString = testObject.returnSaveString();

      assertEquals(testString, "{\"currentHealth\":0.0,\"x\":12.0,\"y\":14.0,\"maxHealth\":0,\"team\":\"red\"}");
    }

    /** Tests if the JSON generator for GameObject and SaveLoad works correctly and feeds properly into SaveLoad */
    @Test
    public void testGameObjectSave2() {
      SaveLoad saveGame = new SaveLoad("./game");
      Array<Texture>testTexture = new Array<>();
      GameObject testObject;
      String testString;
      testTexture.add(new Texture("alcuin_2.png"));

      testObject = new GameObject(testTexture, 0, 12,14, 16, 18, "red");
      saveGame.saveObject(testObject);
      testString = saveGame.returnSaveString();
      assertEquals(testString, "{\"red\":{\"currentHealth\":0.0,\"x\":12.0,\"y\":14.0,\"maxHealth\":0,\"team\":\"red\"}}");
    }

    /** Tests if the SaveLoad class can save it's object to a file */
    @Test
    public void testGameObjectSave3() {
      SaveLoad saveGame = new SaveLoad("./game");
      Array<Texture>testTexture = new Array<>();
      GameObject testObject;
      String testString;
      testTexture.add(new Texture("alcuin_2.png"));

      testObject = new GameObject(testTexture, 0, 12,14, 16, 18, "red");
      saveGame.saveObject(testObject);
      saveGame.saveSave();
    }
}
