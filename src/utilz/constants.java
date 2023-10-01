package utilz;

public class constants {
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

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
        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case HURT:
                    return 1;
                case IDLE:
                    return 4;
                case JUMP:
                    return 4;
                case BACK_JUMP:
                    return 7;
                case RUN:
                    return 8;
                case RUN_SHOOT:
                    return 8;
                case SHOOT:
                    return 1;
                default:
                    return 1;
            }
        }
    }
}
