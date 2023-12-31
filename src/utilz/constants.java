package utilz;

import main.Game;
import objects.Trap;

public class constants {
    public static int ANI_SPEED = 25;

    // Final boss
    public static class FinalBossConstants {
        public static final int IDLE = 0;
        public static final int BUFF_DAMAGE = 1;
        public static final int SHOOT = 2;
        public static final int HURT = 3;
        public static final int PUNCH = 4;
        public static final int LASER_CASTING = 5;
        public static final int BUFF_ARMOR = 6;
        public static final int DEAD = 7;

        public static final int FINAL_BOSS_WIDTH_DEFAULT = 300;
        public static final int FINAL_BOSS_HEIGHT_DEFAULT = 300;
        public static final int FINAL_BOSS_WIDTH = (int) (FINAL_BOSS_WIDTH_DEFAULT * Game.SCALE);
        public static final int FINAL_BOSS_HEIGHT = (int) (FINAL_BOSS_HEIGHT_DEFAULT * Game.SCALE);

        public static final int PROJECTILE_DEFAULT_WIDTH = 100 * 3;
        public static final int PROJECTILE_DEFAULT_HEIGHT = 100 * 3;
        public static final int PROJECTILE_WIDTH = (int) (Game.SCALE * PROJECTILE_DEFAULT_WIDTH);
        public static final int PROJECTILE_HEIGHT = (int) (Game.SCALE * PROJECTILE_DEFAULT_HEIGHT);
        public static final float SPEED = 1.7f * Game.SCALE;

        public static final int TRAP_WIDTH_DEFAULT = 16;
        public static final int TRAP_HEIGHT_DEFAULT = 32;
        public static final int TRAP_WIDTH = (int)(TRAP_WIDTH_DEFAULT*Game.SCALE);
        public static final int TRAP_HEIGHT = (int)(TRAP_HEIGHT_DEFAULT*Game.SCALE);


        public static int GetAniSpeedAction(int boss_action) {
            switch (boss_action) {
                case DEAD:
                    return 32;
            }
            return 25;
        }

        public static int GetSpriteAmount(int boss_action) {
            switch (boss_action) {
                case IDLE:
                    return 4;
                case BUFF_DAMAGE:
                    return 8;
                case SHOOT:
                    return 14;
                case PUNCH:
                    return 15;
                case LASER_CASTING:
                    return 26;
                case BUFF_ARMOR:
                    return 10;
                case DEAD:
                    return 14;
                case HURT:
                    return 21;
            }
            return 8;
        }
    }

    // Enemy
    public static class EnemyConstants {
        public static final int FIRE_DEMON = 29;
        public static final int FROST_DEMON = 39;
        public static final int SHADOW_DEMON = 49;

        public static final int IDLE = 2;
        public static final int WALK = 3;
        public static final int CLEAVE = 4;
        public static final int TAKE_HIT = 5;
        public static final int DEAD = 6;

        public static final int DEMON_WIDTH_DEFAULT = 120;
        public static final int DEMON_HEIGHT_DEFAULT = 80;

        public static final int DEMON_WIDTH = (int) (DEMON_WIDTH_DEFAULT * Game.SCALE);
        public static final int DEMON_HEIGHT = (int) (DEMON_HEIGHT_DEFAULT * Game.SCALE);

        public static final int FIRE_DEMON_DRAWOFFSET_X = (int) (50 * Game.SCALE);
        public static final int FIRE_DEMON_DRAWOFFSET_Y = (int) (38 * Game.SCALE);

        public static final int FROST_DEMON_DRAWOFFSET_X = (int) (30 * Game.SCALE);
        public static final int FROST_DEMON_DRAWOFFSET_Y = (int) (18 * Game.SCALE);

        public static final int SHADOW_DEMON_DRAWOFFSET_X = (int) (70 * Game.SCALE);
        public static final int SHADOW_DEMON_DRAWOFFSET_Y = (int) (38 * Game.SCALE);


        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case FIRE_DEMON:
                    switch (enemy_state) {
                        case IDLE:
                            return 6;
                        case WALK:
                            return 12;
                        case CLEAVE:
                            return 15;
                        case TAKE_HIT:
                            return 5;
                        case DEAD:
                            return 22;
                    }
                case FROST_DEMON:
                    switch (enemy_state) {
                        case IDLE:
                            return 6;
                        case WALK:
                            return 10;
                        case CLEAVE:
                            return 14;
                        case TAKE_HIT:
                            return 7;
                        case DEAD:
                            return 16;
                    }
                case SHADOW_DEMON:
                    switch (enemy_state) {
                        case IDLE:
                            return 8;
                        case WALK:
                            return 8;
                        case CLEAVE:
                            return 10;
                        case TAKE_HIT:
                            return 3;
                        case DEAD:
                            return 10;
                    }
            }
            return 0;
        }

        public static int GetMaxHeath(int enemy_type) {
            switch (enemy_type) {
                case FIRE_DEMON:
                    return 100;
                case FROST_DEMON:
                    return 130;
                case SHADOW_DEMON:
                    return 60;
            }
            return 0;
        }
    }

    // Bullet
    public static class Bullet {
        public static final int BULLET_DEFAULT_WIDTH = 20;
        public static final int BULLET_DEFAULT_HEIGHT = 16;

        public static final int BULLET_WIDTH = (int) (Game.SCALE * BULLET_DEFAULT_WIDTH);
        public static final int BULLET_HEIGHT = (int) (Game.SCALE * BULLET_DEFAULT_HEIGHT);
        public static final float SPEED = 1.2f * 2 * Game.SCALE;
    }

    // Projectile of Boss

    public static class Laser {
        public static final int LASER_DEFAULT_WIDTH = 1500;
        public static final int LASER_DEFAULT_HEIGHT = 150;

        public static final int LASER_WIDTH = (int) (Game.SCALE * LASER_DEFAULT_WIDTH);
        public static final int LASER_HEIGHT = (int) (Game.SCALE * LASER_DEFAULT_HEIGHT);
    }

    public static class ObjectConstants {
        public static final int BULLET = 0;
        public static final int BOMB = 1;
        public static final int PROJECTILES = 123;
        public static final int TRAP = 234;
        public static final int HEART = 178;

        public static final int HEART_DEFAULT_WIDTH = 20;
        public static final int HEART_DEFAULT_HEIGHT = 20;
        public static final int HEART_WIDTH = (int) (HEART_DEFAULT_WIDTH * Game.SCALE);
        public static final int HEART_HEIGHT = (int) (HEART_DEFAULT_HEIGHT * Game.SCALE);

        public static final int BOMB_WIDTH = 30;
        public static final int BOMB_HEIGHT = 50;

        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case BULLET:
                    return 3;
                case PROJECTILES:
                    return 6;
                case TRAP:
                    return 15;
            }
            return 1;
        }
    }

    public static class UI {// tham so cho phan nut

        public static class Buttons {//size Menu buttons
            public static final int B_WIDTH_DEFAULT = 180;
            public static final int B_HEIGHT_DEFAULT = 48;

            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons { //size sound buttons
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons {//size urm buttons
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;
            public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT * Game.SCALE);

        }
    }

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    // Player
    public static class PlayerConstants {
        // Chỉ số của từng hành động
        public static final int IDLE = 0;
        public static final int HURT = 1;
        public static final int BACK_JUMP = 3;
        public static final int JUMP = 2;
        public static final int RUN = 4;
        public static final int RUN_SHOOT = 5;
        public static final int SHOOT = 6;


        // Số lượng ảnh của từng hành động
        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case HURT:
                    return 1;
                case IDLE:
                    return 4;
                case JUMP:
                    return 4;
                case BACK_JUMP:
                    return 7;
                case RUN, RUN_SHOOT:
                    return 8;
                case SHOOT:
                    return 1;
                default:
                    return 1;
            }
        }
    }
}
