package levels;

import gamestate.Gamestate;
import main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex =1 ;

    // Khởi tạo
    public LevelManager(Game game){
        this.game = game;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
    }

    //  Load phần ảnh để xây level
    private void importOutsideSprites(){     
        levelSprite = new BufferedImage[98];
        // TILES
        // Tiles này cần chú ý giá trị value của nó, dùng để kiểm tra va chạm (collision)
        for (int i = 0 ;i <= 45;i++){
           try{
               levelSprite[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/Tiles/Tile_%02d.png",i)));
           }
           catch (IOException e){
               e.printStackTrace();
           }
        }

        // OBJECTS
        // Trees
        for (int i = 46 ;i <= 63;i++){
            try{
                levelSprite[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/Objects/Trees/%d.png",i-45)));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        // Stones
        for (int i = 64 ;i <= 69;i++){
            try{
                levelSprite[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/Objects/Stones/%d.png",i-63)));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        // Box
        for (int i = 70 ;i <= 73;i++){
            try{
                levelSprite[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/Objects/Other/Box%d.png",i-69)));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        // Grass
        for (int i = 74 ;i <= 97;i++){
            try{
                levelSprite[i] = ImageIO.read(getClass().getResourceAsStream(String.format("/tiles/Objects/Grass/%d.png",i-73)));
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    // In ra screen
    public void render(Graphics g, int xLvlOffset){
        for(int j=0;j < Game.TILES_HEIGHT;j++){
            for(int i=0;i<levels.get(lvlIndex).getLevelData()[0].length;i++){
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                // Nếu là tiles thì in ra tiles theo size là 48 x 48
                if(index <= 45){
                    g.drawImage(levelSprite[index],Game.TILES_SIZE*i - xLvlOffset,Game.TILES_SIZE*j,Game.TILES_SIZE, Game.TILES_SIZE,null);
                }
                // Objects thì giữ nguyên size
                else{
                    g.drawImage(levelSprite[index],Game.TILES_SIZE*i - xLvlOffset,Game.TILES_SIZE*j - levelSprite[index].getHeight()+Game.TILES_SIZE,null);
                }
            }
        }
    }

    // Update
    public void update(){

    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("No more levels! Game Completed!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    // Lấy level hiện tại
    public Level getCurrentLevel(){
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

}
