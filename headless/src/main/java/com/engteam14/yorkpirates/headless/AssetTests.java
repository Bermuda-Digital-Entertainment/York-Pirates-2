package main.java.com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

/** This is a class to test that ALL assets required by the game exist. */
@RunWith(HeadlessLauncher.class)
public class AssetTests {

    /** Tests if the alcuin_2.png file exists */
    @Test
    public void testAlcuin2AssetExists() {
        assertTrue("This test will only pass when the alcuin_2.png asset exists.", Gdx.files
                .internal("alcuin_2.png").exists());
    }

    /** Tests if the alcuin_boat.png file exists */
    @Test
    public void testAlcuinBoatAssetExists() {
        assertTrue("This test will only pass when the alcuin_boat asset exists.", Gdx.files
                .internal("alcuin_boat.png").exists());
    }
    
    /** Tests if the alcuin.png file exists */
    @Test
    public void testAlcuinAssetExists() {
        assertTrue("This test will only pass when the alcuin asset exists.", Gdx.files
                .internal("alcuin.png").exists());
    }
    
    /** Tests if the allyArrow.png file exists */
    @Test
    public void testAllyArrowAssetExists() {
        assertTrue("This test will only pass when the allyArrow asset exists.", Gdx.files
                .internal("allyArrow.png").exists());
    }
    
    /** Tests if the allyHealthBar.png file exists */
    @Test
    public void testAllyHealthBarAssetExists() {
        assertTrue("This test will only pass when the allyHealthBar asset exists.", Gdx.files
                .internal("allyHealthBar.png").exists());
    }
    
    /** Tests if the default.fnt file exists */
    @Test
    public void testDefaultAssetExists() {
        assertTrue("This test will only pass when the default.fnt asset exists.", Gdx.files
                .internal("default.fnt").exists());
    }
    
    /** Tests if the derwent.png file exists */
    @Test
    public void testDerwentAssetExists() {
        assertTrue("This test will only pass when the derwent asset exists.", Gdx.files
                .internal("derwent.png").exists());
    }
    
    /** Tests if the derwent_2.png file exists */
    @Test
    public void testDerwent2AssetExists() {
        assertTrue("This test will only pass when the derwent_2 asset exists.", Gdx.files
                .internal("derwent_2.png").exists());
    }
    
    /** Tests if the derwent_boat.png file exists */
    @Test
    public void testDerwentBoatAssetExists() {
        assertTrue("This test will only pass when the derwent_boat asset exists.", Gdx.files
                .internal("derwent_boat.png").exists());
    }
    
    /** Tests if the edges.csv file exists */
    @Test
    public void testEdgesAssetExists() {
        assertTrue("This test will only pass when the edges.csv asset exists.", Gdx.files
                .internal("edges.csv").exists());
    }
    
    /** Tests if the enemyHealthBar.png file exists */
    @Test
    public void testEnemyHealthBarAssetExists() {
        assertTrue("This test will only pass when the enemyHealthBar asset exists.", Gdx.files
                .internal("enemyHealthBar.png").exists());
    }
    
    /** Tests if the enter.png file exists */
    @Test
    public void testEnterAssetExists() {
        assertTrue("This test will only pass when the enter asset exists.", Gdx.files
                .internal("enter.png").exists());
    }
    
    /** Tests if the game_over.png file exists */
    @Test
    public void testGameOverAssetExists() {
        assertTrue("This test will only pass when the game_over asset exists.", Gdx.files
                .internal("game_over.png").exists());
    }
    
    /** Tests if the game_win.png file exists */
    @Test
    public void testGameWinAssetExists() {
        assertTrue("This test will only pass when the game_win asset exists.", Gdx.files
                .internal("game_win.png").exists());
    }
    
    /** Tests if the goodricke.png file exists */
    @Test
    public void testGoodrickeAssetExists() {
        assertTrue("This test will only pass when the goodricke asset exists.", Gdx.files
                .internal("goodricke.png").exists());
    }
    
    /** Tests if the homeArrow.png file exists */
    @Test
    public void testHomeArrowAssetExists() {
        assertTrue("This test will only pass when the homeArrow asset exists.", Gdx.files
                .internal("homeArrow.png").exists());
    }
    
    
   /** Tests if the keyboard.png file exists */
    @Test
    public void testKeyboardAssetExists() {
        assertTrue("This test will only pass when the keyboard asset exists.", Gdx.files
                .internal("keyboard.png").exists());
    }
    
    /** Tests if the langwith.png file exists */
    @Test
    public void testLangwithAssetExists() {
        assertTrue("This test will only pass when the langwith asset exists.", Gdx.files
                .internal("langwith.png").exists());
    }
    
    /** Tests if the langwith_2.png file exists */
    @Test
    public void testLangwith2AssetExists() {
        assertTrue("This test will only pass when the langwith_2 asset exists.", Gdx.files
                .internal("langwith_2.png").exists());
    }
    
    /** Tests if the langwith_boat.png file exists */
    @Test
    public void testLangwithBoatAssetExists() {
        assertTrue("This test will only pass when the langwith_boat asset exists.", Gdx.files
                .internal("langwith_boat.png").exists());
    }
    
    /** Tests if the logo.png file exists */
    @Test
    public void testLogohAssetExists() {
        assertTrue("This test will only pass when the logo asset exists.", Gdx.files
                .internal("logo.png").exists());
    }
    
    /** Tests if the loot.png file exists */
    @Test
    public void testLootAssetExists() {
        assertTrue("This test will only pass when the loot asset exists.", Gdx.files
                .internal("loot.png").exists());
    }
    
    /** Tests if the mouse.png file exists */
    @Test
    public void testMouseAssetExists() {
        assertTrue("This test will only pass when the mouse asset exists.", Gdx.files
                .internal("mouse.png").exists());
    }
    
    /** Tests if the paused.png file exists */
    @Test
    public void testPausedAssetExists() {
        assertTrue("This test will only pass when the paused asset exists.", Gdx.files
                .internal("paused.png").exists());
    }
    
    /** Tests if the pirate12.tmx file exists */
    @Test
    public void testPirate12AssetExists() {
        assertTrue("This test will only pass when the pirate12 asset exists.", Gdx.files
                .internal("pirate12.tmx").exists());
    }
    
    /** Tests if the pirate16x16.tsx file exists */
    @Test
    public void testPirate16x16AssetExists() {
        assertTrue("This test will only pass when the pirate16x16 asset exists.", Gdx.files
                .internal("pirate16x16.tsx").exists());
    }
    
    /** Tests if the points.png file exists */
    @Test
    public void testPointsAssetExists() {
        assertTrue("This test will only pass when the points asset exists.", Gdx.files
                .internal("points.png").exists());
    }
    
    /** Tests if the questArrow.png file exists */
    @Test
    public void testQuestArrowAssetExists() {
        assertTrue("This test will only pass when the questArrow asset exists.", Gdx.files
                .internal("questArrow.png").exists());
    }
    
    /** Tests if the red.fsh file exists */
    @Test
    public void testRedFshAssetExists() {
        assertTrue("This test will only pass when the red.fsh asset exists.", Gdx.files
                .internal("red.fsh").exists());
    }
    
    /** Tests if the red.vsh file exists */
    @Test
    public void testRedVshAssetExists() {
        assertTrue("This test will only pass when the red.vsh asset exists.", Gdx.files
                .internal("red.vsh").exists());
    }
    
    /** Tests if the ship1.png file exists */
    @Test
    public void testShip1AssetExists() {
        assertTrue("This test will only pass when the ship1 asset exists.", Gdx.files
                .internal("ship1.png").exists());
    }
    
    /** Tests if the ship2.png file exists */
    @Test
    public void testShip2AssetExists() {
        assertTrue("This test will only pass when the ship2 asset exists.", Gdx.files
                .internal("ship2.png").exists());
    }
    
    /** Tests if the ship3.png file exists */
    @Test
    public void testShip3AssetExists() {
        assertTrue("This test will only pass when the ship3 asset exists.", Gdx.files
                .internal("ship3.png").exists());
    }
    
    /** Tests if the tempProjectile.png file exists */
    @Test
    public void testTempProjectileAssetExists() {
        assertTrue("This test will only pass when the tempProjectile asset exists.", Gdx.files
                .internal("tempProjectile.png").exists());
    }
    
    /** Tests if the tiles_sheet2.png file exists */
    @Test
    public void testTilesSheet2AssetExists() {
        assertTrue("This test will only pass when the tiles_sheet2 asset exists.", Gdx.files
                .internal("tiles_sheet2.png").exists());
    }
    
    /** Tests if the transparent.png file exists */
    @Test
    public void testTransparentAssetExists() {
        assertTrue("This test will only pass when the transparent asset exists.", Gdx.files
                .internal("transparent.png").exists());
    }
        
   
    
}
