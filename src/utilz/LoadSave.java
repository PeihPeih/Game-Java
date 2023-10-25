package utilz;


import entities.FireDemon;

import entities.FrostDemon;
import entities.ShadowDemon;
import main.Game;
import objects.Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


import static utilz.constants.EnemyConstants.*;
import static utilz.constants.ObjectConstants.*;

// Hỗ trợ load phần hình ảnh
public class LoadSave {
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



    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/level");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals("level_"+(i + 1) + ".png"))
                    filesSorted[i] = files[j];
            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return imgs;
    }
}
