package utilz;

import main.Game;
import objects.Bomb;
import objects.Bullet;
import objects.Heart;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.constants.ObjectConstants.HEART;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        // Check top-left
        if (!IsSolid(x, y, lvlData))
            // Check bottom-right
            if (!IsSolid(x + width, y + height, lvlData))
                // Check top-right
                if (!IsSolid(x + width, y, lvlData))
                    // Check bottom-left
                    if (!IsSolid(x, y + height, lvlData))
                        // Check middle-left
                        if (!IsSolid(x, y + height / 2, lvlData))
                            // Check middle-right
                            if (!IsSolid(x + width, y + height / 2, lvlData))
                                return true;
        return false;
    }

    // Kiểm tra xem vật thể có phải vật rắn hay không
    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        if (x <= 0 || x >= lvlData[0].length * Game.TILES_SIZE) {
            return true;
        }

        if (y <= 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        // Kiem tra vi tri cua nhan vat trong ma tran lvl
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;


        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);

    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        // Return false vi khong phai tile  (bo comment de check tile)
        if (value > 45 || value == 0 || value == 5 || value == 6 || value == 7 || value == 8 || value == 35) {
//			System.out.println(value);
            return false;
        }
//		System.out.println(value);
        return true;
    }

    // gravity
    public static float GetEntityXposNextToWAll(Rectangle2D.Float hitbox, float xSpeed) {
        // tính toán xem ta(player) đang ở ô nào ở X position
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // right
            // đổi vị trí ô chứa player hiện tại ra pixel
            int tileXPos = currentTile * Game.TILES_SIZE;
            // xOffset khoảng cách hiện tại của player với 1 ô chứa player đó
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return xOffset + tileXPos - 1; // trừ đi 1 vì cạnh của hitbox sex nằm trong ô
        } else {
            //left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float getEntityYPosUnderRootOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // falling - touching floor
            int tileYpos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYpos + yOffset + Game.TILES_SIZE - 1;
        } else {
            // jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    // check ont the floor
    public static boolean IsEntityOntheFloor(Rectangle2D.Float hitbox, int[][] lvData) {
        // check the pixel below bottomLeft ? bottomRight (chủ yếu xác định có on floor hay k)
        // In the air
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvData)) // bottom left check
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvData)) // bottom right check
                return false;
        return true;
    }


    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean IsAllTileWalkAble(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData)) return false;
        }
        return true;
    }

    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);
        if (firstXTile > secondXTile)
            return IsAllTileWalkAble(secondXTile,firstXTile,yTile,lvlData);
        else
            return IsAllTileWalkAble(firstXTile,secondXTile,yTile,lvlData);
    }
    // Check xem đạn có va chạm với tiles hay không
    public static boolean IsBulletsHittingLevel(Bullet b, int[][] lvlData) {
        return IsSolid(b.getHitbox().x + b.getHitbox().width / 2, b.getHitbox().y + b.getHitbox().height / 2, lvlData);
    }


    // Check xem đạn có va chạm với tiles hay không
    public static boolean IsBombsHittingLevel(Bomb b, int[][] lvlData) {
        return IsBombSolid(b.getHitbox().x + b.getHitbox().width / 2, b.getHitbox().y + b.getHitbox().height/4, lvlData);
    }

    public static boolean IsBombSolid(float x, float y, int[][] lvlData) {
        if (y >= Game.GAME_HEIGHT) {
            return true;
        }

        if (y <= 0) y = 0;
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        int value = lvlData[(int) yIndex][(int) xIndex];

        if (value > 45 || value == 0 || value == 5 || value == 6 || value == 7 || value == 8 || value == 35) {
            return false;
        }
        return true;
    }
}
