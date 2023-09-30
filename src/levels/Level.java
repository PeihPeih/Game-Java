package levels;

public class Level {
    private int[][] lvlData;

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
