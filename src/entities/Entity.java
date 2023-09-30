package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.constants.Direction.*;
import static utilz.constants.PlayerConstants.*;

public abstract class Entity {
    protected float x, y;
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
