package enitity;

import Main.GamePanel;
import java.awt.*;

public class GoldCoins extends Objects {

    public GoldCoins (GamePanel gp, int x, int y) {
        super(gp, x, y);
    }

    @Override
    public void applyEffect(Player player) {
        player.gold += 5;
        collected = true;
        System.out.println("Collected a Gold Coin! +5 Gold");
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!collected) {
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 32));
            g2.drawString("G", x + gp.tileSize / 4, y + (3 * gp.tileSize) / 4);
            g2.drawString("G", x + gp.tileSize / 4, y + (3 * gp.tileSize) / 4);

        }
    }
}

