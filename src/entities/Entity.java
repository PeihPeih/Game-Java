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
    protected int width, height;

    public Entity(float x, float y, int width, int height) {
        this.x = x; // vị tri x ban dau
        this.y = y; // vi tri y ban dau
        this.height = height;
        this.width = width;
    }
}
