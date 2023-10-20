package levels;

import objects.Heart;
import utilz.HelpMethods;
import utilz.LoadSave;

import java.util.ArrayList;

public class Level {
    private int[][] lvlData;
    private ArrayList<Heart> hearts;

    // Khởi tạo
    public Level(int[][] lvlData){
        this.lvlData = lvlData;
    }

    // Lấy value của 1 phần tử trong ma trận level
    public int getSpriteIndex(int x,int y){
        return lvlData[y][x];
    }

    // Lấy cả ma trận level
    public int[][] getLevelData(){
        return lvlData;
    }
}
