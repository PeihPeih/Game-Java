package utilz;


import entities.Demon;

import main.Game;
import objects.Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


import static utilz.constants.EnemyConstants.*;
import static utilz.constants.ObjectConstants.*;

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

    // Object
    public static final String HEART = "item/heart.png";

    public static final String[] BULLET = {
            "player/shot/shot-1.png",
            "player/shot/shot-2.png",
            "player/shot/shot-3.png"
    };
    public static final String[] BULLET_DISAPPEAR = {
            "player/shot-hit/shot-hit-1.png",
            "player/shot-hit/shot-hit-2.png",
            "player/shot-hit/shot-hit-3.png"
    };


    // Bomb
    public static final String BOMB = "item/meteorite.png";

    public static final String[] EXPLOSION = {
            "explosion/1.png",
            "explosion/2.png",
            "explosion/3.png",
            "explosion/4.png",
            "explosion/5.png",
            "explosion/6.png",
            "explosion/7.png",
            "explosion/8.png",
            "explosion/32.png",
            "explosion/33.png",
            "explosion/34.png"

    };


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

    // getDemons
        public static ArrayList<Demon> GetDemons(int level) {
            BufferedImage img = GetSpriteAtlas(LEVEL_DATA[level-1]);
            ArrayList<Demon> list = new ArrayList<>();
            for (int j = 0; j < img.getHeight(); j++) {
                for (int i = 0; i < img.getWidth(); i++) {
                    Color color = new Color(img.getRGB(i, j));
                    int value = color.getGreen();
                    if (value == FIRE_DEMON) list.add(new Demon(i*Game.TILES_SIZE  - DEMON_WIDTH,j*Game.TILES_SIZE+ Game.TILES_SIZE-DEMON_HEIGHT));
                }
            }
            return list;
        }

    // Xây ma trận level theo ảnh màu pixel
    public static int[][] GetLevelData(int level) {
        BufferedImage image = GetSpriteAtlas(LEVEL_DATA[level - 1]);
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

    // Load bullet
    public static BufferedImage[] GetBulletAnimation() {
        BufferedImage[] bullets = new BufferedImage[BULLET.length];
        for (int i = 0; i < bullets.length; i++) {
            bullets[i] = GetSpriteAtlas(BULLET[i]);
        }
        return bullets;
    }

    // Load buttet disappear
    public static BufferedImage[] GetBulletDisappearAnimation() {
        BufferedImage[] bulletsDisappear = new BufferedImage[BULLET_DISAPPEAR.length];
        for (int i = 0; i < bulletsDisappear.length; i++) {
            bulletsDisappear[i] = GetSpriteAtlas(BULLET_DISAPPEAR[i]);
        }
        return bulletsDisappear;
    }

    // Load explosion
    public static BufferedImage[] GetExplosion() {
        BufferedImage[] explosion = new BufferedImage[EXPLOSION.length];
        for (int i = 0; i < explosion.length; i++) {
            explosion[i] = GetSpriteAtlas(EXPLOSION[i]);
        }
        return explosion;
    }

    public static ArrayList<Heart> GetHearts(int level) {
        BufferedImage image = GetSpriteAtlas(LEVEL_DATA[level - 1]);
        ArrayList<Heart> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++) {
            for (int i = 0; i < image.getWidth(); i++) {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == 178)
                    list.add(new Heart(i * Game.TILES_SIZE + Game.TILES_SIZE - HEART_WIDTH, j * Game.TILES_SIZE + Game.TILES_SIZE - HEART_HEIGHT, value));
            }
        }
        return list;
    }
}
