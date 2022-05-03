package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import com.engteam14.yorkpirates.College;
import com.engteam14.yorkpirates.Player;

/** This is a class to test that the College class is working correctly. */
@RunWith(HeadlessLauncher.class)
public class CollegeTests {

  protected College createCollege(String name, String team){
    Array<Texture>testTexture = new Array<>();
    Array<Texture>testPlayerTexture = new Array<>();
    Player player;
    College testObject;
    testPlayerTexture.add(new Texture("ship1.png"));
    player = new Player(testPlayerTexture, 0, 30f, 35f, 32f, 16f, "PLAYER");
    testTexture.add(new Texture("alcuin_2.png"));
    testObject = new College(testTexture, 12, 10, 1, name, team, player);
    return testObject;
  }

  @Test
  public void testCollegeInit() throws Exception {
    College testCollege = createCollege("TestCollegePleaseIgnore", "Red");
  }

  @Test
  public void testGetName() {
    College testCollege = createCollege("TestCollegePleaseIgnore", "Red");
    assertTrue("The college name is TestCollegePleaseIgnore", "TestCollegePleaseIgnore"==testCollege.getName());
  }

}
