package entities;

import gamestate.Playing;
import main.Game;
import objects.Bullet;
import objects.Laser;
import objects.ProjectileBoss;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.*;
import static utilz.constants.Direction.*;
import static utilz.constants.FinalBossConstants.*;
import static utilz.constants.Laser.LASER_WIDTH;

public class FinalBoss extends Entity {
    private Playing playing;

    private BufferedImage[][] animations;
    private int maxHeath = 1500;
    private int currentHealth;
    private int aniIndex, enemyState;
    private int aniTick;
    private int airDir = UP;
    private float airSpeed = 0.5f * Game.SCALE;
    private boolean active = true;
    private boolean canMove = false;
    private boolean canUpdate = false;
    private ArrayList<ProjectileBoss> projectiles;
    private Laser laser;

    private int xOffset = (int) (75 * Game.SCALE);
    private int yOffset = (int) (72 * Game.SCALE);

    private boolean isDead = false;
    private boolean canShoot = true;
    private boolean canLaser = true;
    private boolean hurted = true;

    private float damage = 1;
    private float armor = 0;

    // Time
    private int timerMax = 20 * 180;
    private int timer = 0;


    public FinalBoss(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadsAnimation();
        initHitbox(x, y, (int) (48 * 3 * Game.SCALE), (int) (46 * 3 * Game.SCALE));
        this.currentHealth = maxHeath;
        this.enemyState = IDLE;
        this.projectiles = new ArrayList<>();
        this.laser = new Laser((int) (hitbox.x), (int) (hitbox.y));
    }

    private void loadsAnimation() {
        this.animations = new BufferedImage[8][26];
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/demon/Final Boss/sprite_sheet.png"));
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 10; j++) {
                    animations[i][j] = image.getSubimage(j * 100, i * 100, 100, 100);
                }
            }
            for (int i = 9; i < 14; i++) {
                animations[SHOOT][i] = animations[SHOOT][8];
            }
            for (int i = 7; i < 26; i++) {
                animations[LASER_CASTING][i] = animations[LASER_CASTING][6];
            }
            for (int j = 8; j < 16; j++) {
                animations[HURT][j] = animations[HURT][7];
            }
            for (int j = 7; j < 15; j++) {
                animations[PUNCH][j] = animations[PUNCH][6];
            }
            for (int j = 0; j < 10; j++) {
                animations[DEAD][j] = image.getSubimage(j * 100, 7 * 100, 100, 100);
            }
            for (int j = 10; j < 14; j++) {
                animations[DEAD][j] = image.getSubimage((j - 10) * 100, 8 * 100, 100, 100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void update(int[][] lvlData) {
        if (canMove) {
            if (!(enemyState == LASER_CASTING && aniIndex >= 7) && enemyState != HURT && enemyState != PUNCH) {
                updatePos(lvlData);
            }
        }
        if (canUpdate) {
            updateTimer();
            updateAnimationTicks();

            updateProjectile(lvlData);
            updateLaser();
        }
    }

    private void updateLaser() {
        laser.update();
        if (laser.isActive()) {
            if (laser.getAniIndex() == 9)
                playing.checkPlayerLaser(laser);
        }
    }

    private void updatePos(int[][] lvlData) {
        float ySpeed = 0;
        float xSpeed = -0.25f * Game.SCALE;

        if (airDir == UP) {
            ySpeed = -airSpeed;
        } else {
            ySpeed = airSpeed;
        }

        hitbox.x += xSpeed;
        if (hitbox.x <= playing.getLevelManager().getCurrentLevel().getWidthLevel() - 1 - FINAL_BOSS_WIDTH / 2) {
            hitbox.x = playing.getLevelManager().getCurrentLevel().getWidthLevel() - 1 - FINAL_BOSS_WIDTH / 2;
            canUpdate = true;

        }

        if (CanMoveHere(hitbox.x, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += ySpeed;
            return;
        }

        changeAirDir();
    }

    private void changeAirDir() {
        if (airDir == UP) airDir = DOWN;
        else airDir = UP;
    }


    private void updateTimer() {
        if (enemyState != DEAD) {
            timer++;
        }
        if (timer >= timerMax) {
            timer -= timerMax;
        }
        switch (timer) {
            case 0 * 180:
                setState(BUFF_ARMOR);
                return;
            case 2 * 180:
                setState(PUNCH);
                return;
            case 6 * 180:
                setState(BUFF_DAMAGE);
                return;
            case 7 * 180:
                canLaser = true;
                setState(LASER_CASTING);
                return;
            case 12 * 180:
                canShoot = true;
                setState(SHOOT);
                return;
            case 17 * 180:
                hurted = false;
                setState(HURT);
        }
    }

    private void updateAnimationTicks() {
        aniTick++;
        if (aniTick >= GetAniSpeedAction(enemyState)) {
            aniTick = 0;
            aniIndex++;
            if (enemyState == SHOOT) {
                if (aniIndex >= 7) {
                    if (canShoot) shoot();
                }
            }
            if (enemyState == LASER_CASTING) {
                if (aniIndex >= 7) {
                    if (canLaser) laser();
                }
            }
            if (aniIndex >= GetSpriteAmount(enemyState)) {
                aniIndex = 0;
                switch (enemyState) {
                    case DEAD:
                        active = false;
                        canMove = false;
                        return;
                    case BUFF_ARMOR:
                        armor += 0.1;
                        if (armor >= 5) {
                            armor = 5;
                        }
                        setState(IDLE);
                        return;
                    case BUFF_DAMAGE:
                        damage += 0.1;
                        setState(IDLE);
                        return;
                    case HURT:
                        hurted = true;
                        setState(IDLE);
                        return;
                    default:
                        setState(IDLE);
                }
            }
        }
    }

    private void shoot() {
        this.projectiles.add(new ProjectileBoss((int) (hitbox.x - 15 * Game.SCALE), (int) (hitbox.y + 32 - 120 * 2)));
        this.projectiles.add(new ProjectileBoss((int) (hitbox.x - 15 * Game.SCALE), (int) (hitbox.y + 32 - 120 * 1)));
        this.projectiles.add(new ProjectileBoss((int) (hitbox.x - 15 * Game.SCALE), (int) (hitbox.y + 32 - 120 * 0)));
        this.projectiles.add(new ProjectileBoss((int) (hitbox.x - 15 * Game.SCALE), (int) (hitbox.y + 32 - 120 * -1)));
        this.projectiles.add(new ProjectileBoss((int) (hitbox.x - 15 * Game.SCALE), (int) (hitbox.y + 32 - 120 * -2)));
        canShoot = false;
    }

    private void laser() {
        laser.changeHitbox((int) (hitbox.x + 92 * Game.SCALE - LASER_WIDTH), (int) hitbox.y + 10);
        laser.setActive(true);
        canLaser = false;
    }

    private void updateProjectile(int[][] lvlData) {
        for (ProjectileBoss p : projectiles) {
            if (p.isActive()) {
                p.update();
                playing.checkPlayerHit(p);
                if (IsProjectilesHittingLevel(p, lvlData)) {
                    p.setActive(false);
                }
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        if (active) {
            g.drawImage(animations[enemyState][aniIndex], (int) (hitbox.x - xOffset) - xLvlOffset + width, (int) (hitbox.y - yOffset), width * -1, height, null);
            drawProjectiles(g, xLvlOffset);
            drawLaser(g, xLvlOffset);
        }
    }

    private void drawLaser(Graphics g, int xLvlOffset) {
        if (laser.isActive()) {
            laser.draw(g, xLvlOffset);
        }
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.get(i).draw(g, xLvlOffset);
        }
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            setState(DEAD);
            isDead = true;

        }
    }

    public void resetAll() {
        active = true;
        canMove = false;
        isDead = false;
        canLaser = true;
        canShoot = true;
        hitbox.x = x;
        hitbox.y = y;
        currentHealth = maxHeath;
        setState(IDLE);
        projectiles.clear();
        laser.setActive(false);
        timer = 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getState() {
        return this.enemyState;
    }

    public float getArmor() {
        return armor;
    }

    public float getDamage() {
        return damage;
    }

    public boolean isHurt() {
        return hurted;
    }
}