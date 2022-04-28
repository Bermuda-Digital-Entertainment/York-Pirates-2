package com.engteam14.yorkpirates.headless;

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
    
}
