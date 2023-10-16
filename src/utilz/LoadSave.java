package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// Hỗ trợ load phần hình ảnh
public class LoadSave {
    // Level
    public static final String[] LEVEL_DATA = {
            "level/level_1.png",
    };
    // Background
    public static final String PLAYING_BG_IMG_1 = "layer/night_1.png";
    public static final String PLAYING_BG_IMG_2 = "layer/night_2.png";
    public static final String PLAYING_BG_IMG_3 = "layer/night_3.png";
    public static final String PLAYING_BG_IMG_4 = "layer/night_4.png";
    public static final String PLAYING_BG_IMG_5 = "layer/night_5.png";
    public static final String CLOUD = "layer/cloud.png";
    public static final String HEART = "item/heart.png";


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
    public static int[][] GetLevelData(int level) {
        BufferedImage image = GetSpriteAtlas(LEVEL_DATA[level-1]);
        int height = image.getHeight(), width = image.getWidth();
        int[][] lvlData = new int[height][width];
        // Nếu muốn xem ma trận level thì comment lại 2 cái system.out ở dưới
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
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
