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
}
