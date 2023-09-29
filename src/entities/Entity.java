package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.constants.Direction.*;
import static utils.constants.Direction.DOWN;
import static utils.constants.PlayerConstants.*;

public abstract class Entity {
    protected float x, y;
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
