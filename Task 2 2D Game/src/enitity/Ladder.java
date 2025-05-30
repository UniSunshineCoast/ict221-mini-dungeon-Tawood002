package enitity;

import java.awt.*;
import Main.GamePanel;
import Main.ScoreManager;


public class Ladder extends Objects {
    private boolean isFinalLadder;

    public Ladder(GamePanel gp, int x, int y) {
        super(gp, x, y);
        this.isFinalLadder = isFinalLadder;
    }

    public boolean isFinalLadder() {
        return isFinalLadder;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("L", x + gp.tileSize / 4, y + (3 * gp.tileSize) / 4);
    }

    @Override
    public void applyEffect(Player player) {

        if (player.GP.currentFloor.equals("floor2")) {

            // Final ladder = game end
            System.out.println("\n");
            ScoreManager.printScores();
            ScoreManager.addScore(player.gold);
            System.out.println("Gold Collected: " + player.gold);

            System.out.println("\nCONGRATULATIONS!!! You've climbed your way out of the Dungeon.");
            System.out.println("Maybe… just maybe… there's more gold to be had?");
            System.out.println("Do you dare to go back?!?!");

            System.exit(0);
        }

        collected = true;

        player.moveCount = 0;
        System.out.println("You climbed the ladder to floor 2!");

        // Move player
        player.x = player.GP.tileSize * 10;
        player.y = player.GP.tileSize * 10;
        player.targetX = player.x;
        player.targetY = player.y;

        // Change floor 2 -----------------------------------------------------------------------------------------
        player.GP.tileM.setFloor("floor2");
        player.GP.currentFloor = "floor2";

        // Clear and add floor2 objects
        player.GP.objects.clear();

        //ladder
        player.GP.objects.add(new Ladder(player.GP, player.GP.tileSize * 10, player.GP.tileSize * 1));
        player.GP.objects.add(new Ladder(player.GP, player.GP.tileSize * 1, player.GP.tileSize * 1));
        player.GP.objects.add(new Ladder(player.GP, player.GP.tileSize * 7, player.GP.tileSize * 7));

        // coins
        player.GP.objects.add(new GoldCoins(player.GP, player.GP.tileSize * 3, player.GP.tileSize * 6));
        player.GP.objects.add(new GoldCoins(player.GP, player.GP.tileSize * 1, player.GP.tileSize * 3));
        player.GP.objects.add(new GoldCoins(player.GP, player.GP.tileSize * 7, player.GP.tileSize * 1));
        player.GP.objects.add(new GoldCoins(player.GP, player.GP.tileSize * 1, player.GP.tileSize * 6));
        //potions
        player.GP.objects.add(new HealthPotions(player.GP, player.GP.tileSize * 9, player.GP.tileSize * 9));
        player.GP.objects.add(new HealthPotions(player.GP, player.GP.tileSize * 9, player.GP.tileSize * 10));
        player.GP.objects.add(new HealthPotions(player.GP, player.GP.tileSize * 8, player.GP.tileSize * 10));
        player.GP.objects.add(new HealthPotions(player.GP, player.GP.tileSize * 8, player.GP.tileSize * 1));


        // Clear and add new mobs for floor2
        player.GP.mobManager.mobs.clear();
        //ranged
        player.GP.mobManager.mobs.add(new RangedMob(player.GP, player.GP.tileSize * 2, player.GP.tileSize * 3));
        player.GP.mobManager.mobs.add(new RangedMob(player.GP, player.GP.tileSize * 2, player.GP.tileSize * 6));
        player.GP.mobManager.mobs.add(new RangedMob(player.GP, player.GP.tileSize * 2, player.GP.tileSize * 8));
        player.GP.mobManager.mobs.add(new RangedMob(player.GP, player.GP.tileSize * 10, player.GP.tileSize * 8));
        player.GP.mobManager.mobs.add(new RangedMob(player.GP, player.GP.tileSize * 7, player.GP.tileSize * 4));
        //melee
        player.GP.mobManager.mobs.add(new Mob(player.GP, player.GP.tileSize * 8, player.GP.tileSize * 7));
        player.GP.mobManager.mobs.add(new Mob(player.GP, player.GP.tileSize * 8, player.GP.tileSize * 1));
    }
}

