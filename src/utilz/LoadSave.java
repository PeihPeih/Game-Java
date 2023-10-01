package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Hỗ trợ load phần hình ảnh
public class LoadSave {
    public static final String LEVEL_ONE_DATA = "level/level_one.png";

    // Background
    public static final String PLAYING_BG_IMG_1 = "layer/1.png";
    public static final String PLAYING_BG_IMG_2= "layer/2.png";
    public static final String PLAYING_BG_IMG_3 = "layer/3.png";
    public static final String PLAYING_BG_IMG_4 = "layer/4.png";
    public static final String PLAYING_BG_IMG_5 = "layer/5.png";



    // Load ảnh
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(LoadSave.class.getResourceAsStream("/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    // Xây ma trận level theo ảnh màu pixel
    public static int[][] GetLevelData() {
        int[][] lvlData = new int[Game.TILES_HEIGHT][Game.TILES_WIDTH];
        BufferedImage image = GetSpriteAtlas(LEVEL_ONE_DATA);
        // Nếu muốn xem ma trận level thì comment lại 2 cái system.out ở dưới
        for (int j = 0; j < Game.TILES_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_WIDTH; i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value > 98) value = 0;
                lvlData[j][i] = value;
//                System.out.print(value+" ");
            }
//            System.out.println();
        }
        return lvlData;
    }
}
