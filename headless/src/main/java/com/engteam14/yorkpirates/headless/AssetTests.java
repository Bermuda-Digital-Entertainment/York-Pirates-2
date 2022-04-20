package com.engteam14.yorkpirates.headless;

import com.badlogic.gdx.Gdx;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

@RunWith(HeadlessLauncher.class)
public class AssetTests {

    @Test
    public void testAlcuin2AssetExists() {
        assertTrue("This test will only pass when the ship.png asset exists.", Gdx.files
                .internal("alcuin_2.png").exists());
    }

    @Test
    public void testAlcuinBoatAssetExists() {
        assertTrue("This test will only pass when the ship.png asset exists.", Gdx.files
                .internal("alcuin_boat.png").exists());
    }
}
