package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import com.engteam14.yorkpirates.GameObject;

/** This is a class to test that ALL assets required by the game exist. */
@RunWith(HeadlessLauncher.class)
public class GameObjectTests {

  /** Creates a basic GameObject for tests to be run on
  *   @return Returns a functional GameObject
  */
  protected GameObject createGameObject(){
    Array<Texture>testTexture = new Array<>();
    GameObject testObject;
    testTexture.add(new Texture("alcuin_2.png"));
    testObject = new GameObject(testTexture, 0, 12, 10, 180, 158, "red");
    return testObject;
  }

  /**Tests GameObject class can be instantiated without causing the game to crash
  */
  @Test
  public void testGameObjectInit() throws Exception {
    GameObject testObject = createGameObject();
  }

  /**Tests that the setMaxHealth method works
  */
  @Test
  public void testSetMaxHealth(){
    GameObject testObject = createGameObject();
    testObject.setMaxHealth(25);
    assertTrue("Tests that the setMaxHealth method works", 25 == testObject.maxHealth);
    assertTrue("Tests that the setMaxHealth method works", 25f == testObject.currentHealth);
  }

  /**Tests that the takeDamage method works and only subtracts from currentHealth (not maxHealth)
  */
  @Test
  public void testTakeDamage(){
    GameObject testObject = createGameObject();
    testObject.setMaxHealth(25);
    testObject.takeDamage(5f);
    assertTrue("Tests that the takeDamage method only subtracts from currentHealth (not maxHealth)", 25 == testObject.maxHealth);
    assertTrue("Tests that the takeDamage method works and only subtracts from currentHealth", 20f == testObject.currentHealth);
  }

  /**Tests move method works correctly
  */
  @Test
  public void testMove() throws Exception {
    GameObject testObject = createGameObject();
    assertTrue("Starting X position is correct", testObject.x == 12f);
    assertTrue("Starting Y position is correct", testObject.y == 10f);
    testObject.move(15f,20f);
    assertTrue("End X position is correct", testObject.x > 12f);
    assertTrue("End Y position is correct", testObject.y > 10f);
  }

  /**Tests that the overlaps method works correctly (between two GameObjects)
  */
  @Test
  public void testOverlaps() throws Exception {
    GameObject testObject1 = createGameObject();
    GameObject testObject2 = createGameObject();
    assertTrue("testObject1 overlaps testObject2",testObject1.overlaps(testObject2.getHitBox()));
    assertTrue("testObject2 overlaps testObject1",testObject2.overlaps(testObject1.getHitBox()));
    testObject1.x=200f;//Makes sure the two definitely are not overlapping
    assertFalse("testObject1 no longer overlaps testObject2",testObject1.overlaps(testObject2.getHitBox()));
    assertFalse("testObject2 no longer overlaps testObject1",testObject2.overlaps(testObject1.getHitBox()));
  }
}
