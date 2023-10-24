package objects;

import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.constants.ObjectConstants.*;

public class Heart extends GameObject {

    public Heart(int x, int y, int objType) {
        super(x, y, objType);
        xDrawOffset = (int) (3 * Game.SCALE);
        yDrawOffset = (int) (2 * Game.SCALE);
        initHitbox(HEART_WIDTH, HEART_HEIGHT);
    }

}
