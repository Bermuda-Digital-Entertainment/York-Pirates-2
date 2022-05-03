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

/** This is a class to test that ALL assets required by the game exist. */
@RunWith(HeadlessLauncher.class)
public class PlayerTests {

  /** Creates a basic Player objects for tests to be run on
  *   @return Returns a functional Player
  */
  protected Player createPlayer(){
    Array<Texture>testTexture = new Array<>();
    Player testPlayer;
    testTexture.add(new Texture("ship1.png"));
    testPlayer = new Player(testTexture, 0, 12, 10, 25, 40, "red");
    return testPlayer;
  }

  /**Tests Player class can be instantiated without causing the game to crash
  */
  @Test
  public void testPlayerInit() throws Exception {
    Player testPlayer = createPlayer();
  }

  /**Tests that the safeMove method works correctly
  */
  @Test
  public void testSafeMove() {
    Player testPlayer = createPlayer();
    Array<Array<Boolean>> edges;
    //Taken from YorkPirates.java
    edges = new Array<>();
		String data = Gdx.files.internal("FINAL_MAP_Terrain.csv").readString();
		for(String row: data.split("\n")){
			Array<Boolean> newRow = new Array<>();
			for(String num: row.split(",")){
				if(num.equals("-1")) newRow.add(true);
				else newRow.add(false);
			}
			edges.insert(0, newRow);
		}
    //Sets the player coordinates to the normal start position
    testPlayer.x=821;
    testPlayer.y=489;
    assertTrue("This is a safe position", testPlayer.safeMove(edges));
    testPlayer.x=testPlayer.x-50;
    assertFalse("This is not a safe position", testPlayer.safeMove(edges));
  }

  /**Tests the addHealth method works correctly
  */
  @Test
  public void testAddHealth() {
    Player testPlayer = createPlayer();
    int oldMaxHealth = testPlayer.maxHealth;
    float oldHealth = testPlayer.currentHealth;
    testPlayer.addHealth();
    assertTrue("Player Health increased", testPlayer.currentHealth==(oldHealth+50f));
    assertTrue("Player Max Health increased", testPlayer.maxHealth==(oldMaxHealth+50));
    oldMaxHealth = testPlayer.maxHealth;
    oldHealth = testPlayer.currentHealth;
    testPlayer.addHealth();
    assertFalse("Player Health increased", testPlayer.currentHealth==(oldHealth+50f));
    assertFalse("Player Max Health increased", testPlayer.maxHealth==(oldMaxHealth+50));
  }

  /**Tests the heal method works correctly
  */
  @Test
  public void testHeal() {
    Player testPlayer = createPlayer();
    testPlayer.currentHealth-=50;
    testPlayer.heal(100);
    assertTrue("Player Health increased", testPlayer.currentHealth==testPlayer.maxHealth);
  }

  /**Tests the addDamage method works correctly
  */
  @Test
  public void testAddDamage() {
    Player testPlayer = createPlayer();
    float oldDamage = testPlayer.getPlayerDamage();
    testPlayer.addDamage();
    assertTrue("Player Damage", testPlayer.getPlayerDamage()==(oldDamage+50f));
  }

  /**Tests the setHealth method works correctly
  */
  @Test
  public void testSetHealth() {
    Player testPlayer = createPlayer();
    testPlayer.setHealth(100);
    assertTrue("Player Health changed", testPlayer.currentHealth==100);
    assertTrue("Player Max Health changed", testPlayer.maxHealth==100);
  }

  /**Tests the takeInstantDamage method works correctly
  */
  @Test
  public void testTakeInstantDamage() {
    Player testPlayer = createPlayer();
    float oldHealth = testPlayer.currentHealth;
    testPlayer.takeInstantDamage(10);
    assertTrue("Player Health increased", testPlayer.currentHealth==(oldHealth-10f));
  }

  /**Tests the setDamage method works correctly
  */
  @Test
  public void testSetDamage() {
    Player testPlayer = createPlayer();
    testPlayer.setDamage(100);
    assertTrue("Player Health increased", testPlayer.getPlayerDamage()==100);
  }
}
