package utilz;

public class constants{
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        // Chỉ số của từng hành động
        public static final int IDLE = 0;


        // Số lượng ảnh của từng hành động
        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                default:
                    return 1;
            }
        }
    }
}
