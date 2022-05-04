package com.engteam14.yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Objects;

import org.json.simple.JSONObject;

import static java.lang.Math.abs;

public class College extends GameObject {

    public static int capturedCount = 0;

    private HealthBar collegeBar;
    private Indicator direction;

    private float splashTime;
    private long lastShotFired;
    private final String collegeName;
    private final Array<Texture> collegeImages;
    private int pointsGained = 50;
    private int lootGained = 150;
    private boolean doBloodSplash = false;

    /**
     * Generates a college object within the game with animated frame(s) and a hit-box.
     * @param sprites   The Array of Textures to be used for rendering the college
     * @param x         The x coordinate within the map to initialise the object at.
     * @param y         The y coordinate within the map to initialise the object at.
     * @param scale     Float to be used to scale the college Texture on screen
     * @param name      The name of the college.
     * @param team      The team the college is on.
     * @param player    The Player the college will shoot at/interact with
     */
    public College(Array<Texture> sprites, float x, float y, float scale, String name, String team, Player player){
        super(sprites, 0, x, y, sprites.get(0).getWidth()*scale, sprites.get(0).getHeight()*scale, team);

        collegeImages = new Array<>();
        for(int i = 0; i < sprites.size; i++) {
            collegeImages.add(sprites.get(i));
        }

        splashTime = 0;
        setMaxHealth(2000);
        lastShotFired = 0;
        collegeName = name;

        Array<Texture> healthBarSprite = new Array<>();
        Array<Texture> indicatorSprite = new Array<>();
        if(Objects.equals(team, GameScreen.playerTeam)){
            if(Objects.equals(name, "Home")){
                indicatorSprite.add(new Texture("homeArrow.png"));
            }else{
                indicatorSprite.add(new Texture("allyArrow.png"));
            }
            healthBarSprite.add(new Texture("allyHealthBar.png"));

        }else{
            healthBarSprite.add(new Texture("enemyHealthBar.png"));
            indicatorSprite.add(new Texture("questArrow.png"));
        }
        collegeBar = new HealthBar(this,healthBarSprite);
        direction = new Indicator(this,player,indicatorSprite);
    }

    /**
     * Called once per frame. Used to perform calculations such as collision.
     * @param screen        The main game screen.
     * @param enterPressed  Whether the key 'Enter/Return' has been pressed to end the game
     */
    public void update(GameScreen screen, boolean enterPressed){
        direction.move();
        float playerX = screen.getPlayer().x;
        float playerY = screen.getPlayer().y;
        boolean nearPlayer = abs(this.x - playerX) < (Gdx.graphics.getWidth()/15f) && abs(this.y - playerY) < (Gdx.graphics.getHeight()/10f);

        if(nearPlayer || screen.isPaused()){
            direction.setVisible(false);

            if(!Objects.equals(team, GameScreen.playerTeam)) { // Checks if the college is an enemy of the player
                // How often the college can shoot.
                int shootFrequency = 1000;
                if (TimeUtils.timeSinceMillis(lastShotFired) > shootFrequency){
                    lastShotFired = TimeUtils.millis();
                    Array<Texture> sprites = new Array<>();
                    sprites.add(new Texture("fire_ball.png"));
                    screen.projectiles.add(new Projectile(sprites, 0, this, playerX, playerY, team));
                }
            }else if(Objects.equals(collegeName, "Home")){
                boolean victory = true;
                for(int i = 0; i < screen.colleges.size; i++) {
                    if(!Objects.equals(screen.colleges.get(i).team, GameScreen.playerTeam)){
                        victory = false;
                    }
                }
                if(victory){
                    screen.getHUD().setGameEndable();
                    if(enterPressed) screen.gameEnd(true);
                }
            }
        }else{
            direction.setVisible(true);
        }

        if(doBloodSplash){
            if(splashTime > 1){
                doBloodSplash = false;
                splashTime = 0;
            }else{
                splashTime += 1;
            }
        }
    }

    /**
     * Called when a projectile hits the college.
     * @param screen            The main game screen.
     * @param damage            The damage dealt by the projectile.
     * @param projectileTeam    The team of the projectile.
     */
    public void takeDamage(GameScreen screen, float damage, String projectileTeam){
        currentHealth -= damage;
        doBloodSplash = false;

        if(currentHealth > 0){
            collegeBar.resize(currentHealth);
        }else{
            if(!Objects.equals(team, GameScreen.playerTeam)){ // Checks if the college is an enemy of the player
                // College taken over
                if(screen.getDifficulty()==1){
                    pointsGained = 50;
                    lootGained = 150;
                }
                else if(screen.getDifficulty()==2){
                    pointsGained = 100;
                    lootGained = 100;
                }
                else{
                    pointsGained = 200;
                    lootGained = 50;
                }
                screen.points.Add(pointsGained);
                screen.loot.Add(lootGained);

                Array<Texture> healthBarSprite = new Array<>();
                Array<Texture> indicatorSprite = new Array<>();
                healthBarSprite.add(new Texture("allyHealthBar.png"));
                indicatorSprite.add(new Texture("allyArrow.png"));

                Array<Texture> sprites = new Array<>();
                sprites.add(collegeImages.get(1));
                changeImage(sprites,0);

                collegeBar.changeImage(healthBarSprite,0);
                currentHealth = maxHealth;
                collegeBar.resize(currentHealth);
                College.capturedCount++;
                direction.changeImage(indicatorSprite,0);
                team = GameScreen.playerTeam;
            }else{
                // Destroy college
                collegeBar = null;
                direction = null;
                destroy(screen);
            }
        }
    }

    /**
     * Called when the college needs to be destroyed.
     * @param screen    The main game screen.
     */
    private void destroy(GameScreen screen){
        screen.colleges.removeValue(this,true);
    }

    /**
     * Called when drawing the object.
     * @param batch         The batch to draw the object within.
     * @param elapsedTime   The current time the game has been running for.
     */
    @Override
    public void draw(SpriteBatch batch, float elapsedTime){
        batch.setShader(null);
        // Draw college
        batch.draw(anim.getKeyFrame(elapsedTime, true), x - width/2, y - height/2, width, height);

        collegeBar.draw(batch, 0);
        direction.draw(batch,0);
    }

    /** Returns the name of the college*/
    public String getName(){
      return collegeName;
    }

    /** Generates the JSON for the college save*/
    @Override
    public void genSave(){
      super.genSave();
      objectJSON.put("splashTime",splashTime);
      objectJSON.put("lastShotFired",lastShotFired);
    }

    /** Loads the JSON from a college save*/
    //@Override
    public void loadSave(JSONObject objectJSON, GameScreen screen){
      this.loadSave(objectJSON);
      splashTime = ((Double) objectJSON.get("splashTime")).floatValue();
      lastShotFired = (long) objectJSON.get("lastShotFired");

      if (this.team=="PLAYER"){
        Array<Texture> healthBarSprite = new Array<>();
        Array<Texture> indicatorSprite = new Array<>();
        healthBarSprite.add(new Texture("allyHealthBar.png"));
        indicatorSprite.add(new Texture("allyArrow.png"));

        Array<Texture> sprites = new Array<>();
        sprites.add(collegeImages.get(1));
        changeImage(sprites,0);

        collegeBar.changeImage(healthBarSprite,0);
        currentHealth = maxHealth;
        collegeBar.resize(currentHealth);
        College.capturedCount++;
        direction.changeImage(indicatorSprite,0);
      }
    }
}
