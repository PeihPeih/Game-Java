package entities;

import javax.imageio.ImageIO;

import audio.AudioPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


import static utilz.HelpMethods.IsEntityOntheFloor;
import static utilz.constants.Direction.*;
import static utilz.constants.EnemyConstants.DEAD;
import static utilz.constants.ObjectConstants.BULLET;
import static utilz.constants.PlayerConstants.*;

import static utilz.HelpMethods.*;

import gamestate.Gamestate;
import gamestate.Playing;
import main.Game;
import objects.Bullet;
import objects.Laser;
import objects.ProjectileBoss;
import objects.Trap;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;

    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xdrawOffset = 18 * Game.SCALE;
    private float ydrawOffset = 12 * Game.SCALE;
    private ArrayList<BufferedImage> hearts;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private int timerAttack;
    private int timerAttackMax = 40;
    private boolean canAttack;
    private int damage = 150;


    // Flip animation when turn left or right
    private int flipX = 0;
    private int flipW = 1;


    //JUMPING // GRAVITY
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadsAnimation();
        initHeart();
        // Lay hitbox cua player
        initHitbox(x, y + 10, 20 * Game.SCALE, 38 * Game.SCALE);

        this.timerAttack = this.timerAttackMax;
    }

    private void initHeart() {
        hearts = new ArrayList<>();
        BufferedImage heart = LoadSave.GetSpriteAtlas(LoadSave.HEART);
        for (int i = 0; i < 3; i++) hearts.add(heart);
    }
    

    public void update() {
        updateTimer();
        updatePos();
        
        updateAnimationonTick();
        updateBullet();
        setAnimation();
        
    }

    private void updateTimer() {
        if (this.timerAttack >= this.timerAttackMax) {
            canAttack = true;
            this.timerAttack = 0;
        }
        this.timerAttack++;
    }

    private void updateBullet() {
        for (int i =0;i<bullets.size();i++) {
            Bullet b = bullets.get(i);
            if (b.isActive()) {
                b.update();
                playing.checkEnemyHit(b);
                if (IsBulletsHittingLevel(b, lvlData))
                    b.setActive(false);
            }
        }
    }

    // Lay du lieu tu lvl de chuan bi cho collision
    public void loadLvlData(int[][] lvlData) {

        this.lvlData = lvlData;
        // check in the air at start (nhân vật rơi xuống lúc start game)
        if (!IsEntityOntheFloor(hitbox, lvlData)) {
            inAir = true;
        }

    }

    // In player ra screen
    public void render(Graphics g, int xLvlOffset) {

        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xdrawOffset - xLvlOffset + flipX), (int) (hitbox.y - ydrawOffset), width * flipW, height, null);
        // Ve hitbox cho nhan vat (xoa di khi game hoan thanh)
//        drawHitbox(g, xLvlOffset);

        drawBullet(g, xLvlOffset);
        drawHeart(g);
    }

    private void drawBullet(Graphics g, int xLvlOffset) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g, xLvlOffset);

        }
    }

    // Draw heart at left-top corner
    private void drawHeart(Graphics g) {
        for (int i = 0; i < hearts.size(); i++) {
            g.drawImage(hearts.get(i), 50*(i+1), 30, 45, 45, null);
        }
    }

    // Chuyển frame của mỗi animation
    private void updateAnimationonTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    // Animation về hành động
    private void setAnimation() {
        int startAni = playerAction;
        // Run
        if (moving) {
            playerAction = RUN;
        } else {
            playerAction = IDLE;
        }

        // Jump
        if (jump || inAir) {
            playerAction = JUMP;
        }

        // Attack
        if (attacking) {
            if (playerAction == RUN) {
                playerAction = RUN_SHOOT;
            } else if (playerAction == IDLE) {
                playerAction = SHOOT;
            }
        }

        // Nếu chuyeren hành động thì chạy animation mới
        if (startAni != playerAction) {
//            resetAninTick();
            if (!(startAni == RUN && playerAction == RUN_SHOOT)) {
                resetAninTick();
            }
        }

    }

    private void resetAninTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void setRectPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Nhân vật di chuyển
    private void updatePos() {
        moving = false;

        if (jump)
        	jump();
         
        float xSpeed = 0;

        // Left
        if (left && !right) {
            xSpeed -= playerSpeed;
            flipW = -1;
            flipX = width;
            moving = true;
        }
        // Right
        if (right && !left) {
            xSpeed += playerSpeed;
            flipW = 1;
            flipX = 0;
            moving = true;
        }

        if (moving) {
            checkHeartTouched();
        }

        // nghia là đang left hoặc right
        if (!inAir) {
            if (!IsEntityOntheFloor(hitbox, lvlData)) {
                inAir = true;
            }
            // Chạm đất thì có thể bắn
            if (attacking && canAttack) {
                shoot();
                
            }
        }

        // Jump | Gravity
        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXpos(xSpeed);
            } else {
                hitbox.y = getEntityYPosUnderRootOrAboveFloor(hitbox, airSpeed);
                // check wheather hit floor or not
                if (airSpeed > 0) { // airSpeed > 0 là đang đi xuống hà chạm thứ gì đó (floor)
                    resetInAir();
                } else {
                    // k chạm sàn thì chạm trần nhà và sau đó rơi xuống
                    airSpeed = fallSpeedAfterCollision;
                    updateXpos(xSpeed);
                }
            }

        } else {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
            }
            updateXpos(xSpeed);
        }

    }

    private void checkHeartTouched() {
        playing.checkHeartTouch(hitbox);
    }

    // Nhân vật bắn súng
    private void shoot() {
        if (aniTick == 0) {
            shootBullet();
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.SHOOT);
            canAttack = false;
        }
    }

    private void shootBullet() throws ConcurrentModificationException {
        int x = (int) (5 * Game.SCALE);
        int y = (int) (1 * Game.SCALE);
        bullets.add(new Bullet((int) this.hitbox.x + x, (int) this.hitbox.y + y, flipW, BULLET));
    }

    private void jump() {
        if (inAir)
            return; // nếu đang jump thì đang in air rồi
        airSpeed = jumSpeed; // bất cứ khi nào jump có airSpeed bằng jumpSpeed
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0; // k rơi nữa thì k có speed
    }

    private void updateXpos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            // đang va chạm
            // hitbox next to wall
            hitbox.x = GetEntityXposNextToWAll(hitbox, xSpeed);

        }
    }

    // Load ảnh animation
    private void loadsAnimation() {
        animations = new BufferedImage[7][8];

        // IDLE
        for (int i = 0; i < GetSpriteAmount(IDLE); i++) {
            try {
                animations[IDLE][i] = ImageIO.read(getClass().getResourceAsStream("/player/idle/idle-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // BACK-JUMP
        for (int i = 0; i < GetSpriteAmount(BACK_JUMP); i++) {
            try {
                animations[BACK_JUMP][i] = ImageIO.read(getClass().getResourceAsStream("/player/back-jump/back-jump-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // JUMP
        for (int i = 0; i < GetSpriteAmount(JUMP); i++) {
            try {
                animations[JUMP][i] = ImageIO.read(getClass().getResourceAsStream("/player/jump/jump-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // HURT
        for (int i = 0; i < GetSpriteAmount(HURT); i++) {
            try {
                animations[HURT][i] = ImageIO.read(getClass().getResourceAsStream("/player/hurt/hurt.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // RUN
        for (int i = 0; i < GetSpriteAmount(RUN); i++) {
            try {
                animations[RUN][i] = ImageIO.read(getClass().getResourceAsStream("/player/run/run-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // RUN-SHOOT
        for (int i = 0; i < GetSpriteAmount(RUN_SHOOT); i++) {
            try {
                animations[RUN_SHOOT][i] = ImageIO.read(getClass().getResourceAsStream("/player/run-shoot/run-shoot-" + (i + 1) + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // SHOOT
        for (int i = 0; i < GetSpriteAmount(SHOOT); i++) {
            try {
                animations[SHOOT][i] = ImageIO.read(getClass().getResourceAsStream("/player/shoot/shoot.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }


    public void setAttack(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    // Set đứng yên
    public void resetDirBoleans() {
        left = false;
        right = false;
    }


    // jump
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    public void addHeart() {
        if (hearts.size() < 3) {
            this.hearts.add(LoadSave.GetSpriteAtlas(LoadSave.HEART));
        }
    }

    public void minusHeart(int value) {
        for (int i = 0; i < value; i++) {
//        	System.out.println(hearts.size());
            if (hearts.size() > 0) {
            	playing.getGame().getAudioPlayer().playEffect(AudioPlayer.HURT);
                hearts.remove(hearts.size() - 1);
                
            }	
        }
    }

    public void checkPlayerHit(ProjectileBoss b) {
        Rectangle attackBox = b.getHitbox().getBounds();
        if (hitbox.intersects(attackBox)) {
            minusHeart((int) playing.getEnemyManager().getFinalBoss().getDamage());
            b.setActive(false);
        }
    }

    public void checkLaserHit(Laser ls) {
        Rectangle attackBox = ls.getHitbox().getBounds();
        if (hitbox.intersects(attackBox)) {
            minusHeart(1);
        }
    }

    public void checkPlayerTrap(Trap t) {
        Rectangle attackBox = t.getHitbox().getBounds();
        if(hitbox.intersects(attackBox) && t.isActive()){
            minusHeart(1);
            t.setCanDamage(false);
        }
    }

    // Xóa khỏi mảng khi không cần
    public void destroy() {
        for (int i = 0; i < bullets.size(); i++) {
            if (!bullets.get(i).isActive()) {
                bullets.remove(i);
            }
        }
    }

    public void resetALl() {
        resetDirBoleans();
        inAir = false;
        attacking = false;
        moving = false;
        jump = false;
        airSpeed = 0f;
        hitbox.x = x;
        hitbox.y = y;
        if (!IsEntityOntheFloor(hitbox, lvlData))
            inAir = true;
        initHeart();
        bullets.clear();
    }

    public boolean IsDeath() {
        if (hitbox.y + hitbox.height + 1 > Game.GAME_HEIGHT)
        {
        	hearts.clear();
        }
        return hearts.isEmpty();
    }


    public void setSpawn(Point playerSpawn) {
//        this.x = playerSpawn.x;
//        this.y = playerSpawn.y;
//        hitbox.x = x;
//        hitbox.y = y;
    }

    public int getDamage() {
        return this.damage;
    }


}
