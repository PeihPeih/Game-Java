package entities;

import gamestate.Playing;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] Demon_arr;
    private ArrayList<Demon> demons = new ArrayList<>();

    public void update(int [][] lvlData,Player player){
        for (Demon d : demons){
            d.update(lvlData,player);
        }    
    }
    public void draw(Graphics g,int xLvOffset){
        drawDemons(g,xLvOffset);
    }

    private void drawDemons(Graphics g,int xLvOffset) {
        for (Demon d : demons){
            g.drawImage(Demon_arr[d.getEnemyState()][d.getAniIndex()],(int)d.getHitbox().x - xLvOffset,(int)d.getHitbox().y,DEMON_WIDTH,DEMON_HEIGHT,null);

        }
    }

    public EnemyManager(Playing playing ){
            this.playing = playing;
            loadEnemyImgs();
            addEnemies();
    }

    private void addEnemies() {
        demons = LoadSave.GetDemons(1);
    }

    private void loadEnemyImgs(){
        Demon_arr = new BufferedImage[5][22];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.DEMON_SPRITE);
        for (int j = 0;j<Demon_arr.length;j++){
            for (int i =0 ;i<Demon_arr[i].length;i++){
                Demon_arr[j][i] = temp.getSubimage(i * DEMON_WIDTH_DEFAULT,j*DEMON_HEIGHT_DEFAULT,DEMON_WIDTH_DEFAULT,DEMON_HEIGHT_DEFAULT);
            }
        }
    }
}
