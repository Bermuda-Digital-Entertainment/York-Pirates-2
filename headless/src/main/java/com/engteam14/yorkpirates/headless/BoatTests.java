package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import com.engteam14.yorkpirates.Player;
import com.engteam14.yorkpirates.Boat;

/** This is a class to test that ALL assets required by the game exist. */
@RunWith(HeadlessLauncher.class)
public class BoatTests {

  /** Creates a basic Boat for tests to be run on
  *   @return Returns a functional Boat
  */
  protected Boat createBoat(){
    Array<Texture>playerTexture = new Array<>();
    Player testPlayer;
    Array<Texture>testTexture = new Array<>();
    Boat testBoat;
    playerTexture.add(new Texture("ship1.png"));
    testPlayer = new Player(playerTexture, 0, 12, 10, 25, 40, "red");
    testTexture.add(new Texture("ship3.png"));
    testBoat = new Boat(testTexture, 15, 20, 0.25f, "red", testPlayer);
    return testBoat;
  }

  /**Tests Boat class can be instantiated without causing the game to crash
  */
  @Test
  public void testBoatInit() throws Exception {
    Boat testBoat = createBoat();
  }

  /**Tests Boat class can be instantiated without causing the game to crash
  */
  @Test
  public void testNearPlayer() {
    Boat testBoat = createBoat();
    assertTrue("testBoat is near the player",testBoat.nearPlayer(50f,50f));
    testBoat.x=1000;
    assertFalse("testBoat is not near the player",testBoat.nearPlayer(50f,50f));
  }
}
