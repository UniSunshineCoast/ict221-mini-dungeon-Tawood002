package enitity;

import Main.GamePanel;

import java.awt.*;

public class HealthPotions extends Objects {

    public HealthPotions(GamePanel gp, int x, int y) {
        super(gp, x, y);
    }

    @Override
    public void applyEffect(Player player) {
        player.health += 4;
        if (player.health > 10) {
            player.health = 10; // caps player's health at 10HP
        }
        System.out.println("Picked up Health Potion! Health: " + player.health);
        collected = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!collected) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 32));
            g2.drawString("H", x + gp.tileSize / 4, y + (3 * gp.tileSize) / 4);
        }
    }
}

