package enitity;


import Main.GamePanel;
import java.awt.*;

public class Mob extends Entity {

    public boolean isAlive = true;
    GamePanel gp;

    public Mob(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.solidArea = new Rectangle(8, 16, 32, 32);
        getMobImage();
    }

    public void getMobImage() { //getting for the mob/melee mutant
        try {
            down = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/mobs/monster_orc_masked.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if (isAlive && down != null) {
            g2.drawImage(down, x, y, gp.tileSize, gp.tileSize, null);
        }
    }

    public Rectangle getSolidArea() {
        Rectangle area = new Rectangle(solidArea);
        area.x += x;
        area.y += y;
        return area;
    }

    public void onTouchPlayer(Player player) {
        //  does nothing just a method called when the player touches the mob
    }

}
