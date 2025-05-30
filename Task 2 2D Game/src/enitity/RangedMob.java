package enitity;

import Main.GamePanel;
import java.awt.*;

public class RangedMob extends Mob {

    boolean shouldAttack = false;

    public RangedMob(GamePanel gp, int x, int y) { //sprite for ranged mob
        super(gp, x, y);
        try {
            down = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/mobs/monster_necromancer.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tryAttack(Player player) { //method to attck the player
        if (!isAlive || !shouldAttack) return;

        int tileDistX = Math.abs(player.x - this.x) / gp.tileSize;
        int tileDistY = Math.abs(player.y - this.y) / gp.tileSize;

        if ((tileDistX <= 2 && tileDistY == 0) || (tileDistY <= 2 && tileDistX == 0)) {
            if (Math.random() < 0.5) { // 50% chance
                player.health -= 1;
                System.out.println("Ranged Mutant hit! Player health: " + player.health);
            } else {
                System.out.println("Ranged Mutant missed!");
            }
        }

        shouldAttack = false; // Reset flag after attack attempt, so it not a constant attack its per move/tile
    }

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);

        // red outline of the 2-tile attack range of the mob
        g2.setColor(Color.RED);
        int range = gp.tileSize * 2;
        g2.drawRect(x - range, y, range * 2 + gp.tileSize, gp.tileSize); // Horizontal range
        g2.drawRect(x, y - range, gp.tileSize, range * 2 + gp.tileSize); // Vertical range
    }
}