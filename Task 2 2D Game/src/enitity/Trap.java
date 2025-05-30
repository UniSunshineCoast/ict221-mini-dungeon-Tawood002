package enitity;

import Main.GamePanel;
import java.awt.*;


public class Trap extends Mob {

    public Trap(GamePanel gp, int x, int y) {
        super(gp, x, y);

    }

    public void draw(Graphics2D g2) { //image
        g2.setColor(Color.RED);
        g2.setFont(new Font("Monospaced", Font.BOLD, gp.tileSize));
        g2.drawString("T", x + 8, y + gp.tileSize - 8); // Adjust position slightly to center
    }


    public void onTouchPlayer(Player player) { //damage
        player.health -= 2;
        System.out.println("Player stepped on a trap! -2 HP. Current Health: " + player.health);
    }

}


