package enitity;

import Main.GamePanel;
import java.awt.*;
import java.util.ArrayList;


public class MobManager {
    GamePanel gp;
    public ArrayList<Mob> mobs = new ArrayList<>();

    public MobManager(GamePanel gp) { //first floor mob spawns
        this.gp = gp;

        // Meelee Mob
        mobs.add(new Mob(gp, gp.tileSize * 3, gp.tileSize * 4));
        mobs.add(new Mob(gp, gp.tileSize * 3, gp.tileSize * 6));
        mobs.add(new Mob(gp, gp.tileSize * 7, gp.tileSize * 2));
        mobs.add(new Mob(gp, gp.tileSize * 9, gp.tileSize * 10));
        mobs.add(new Mob(gp, gp.tileSize * 10, gp.tileSize * 4));
        mobs.add(new Mob(gp, gp.tileSize * 9, gp.tileSize * 3));
        mobs.add(new Mob(gp, gp.tileSize * 6, gp.tileSize * 5));

        // ranged
        mobs.add(new RangedMob(gp, gp.tileSize * 8, gp.tileSize * 8));
        mobs.add(new RangedMob(gp, gp.tileSize * 1, gp.tileSize * 4));
        mobs.add(new RangedMob(gp, gp.tileSize * 10, gp.tileSize * 3));

        //Trap
        mobs.add(new Trap(gp, gp.tileSize * 4, gp.tileSize * 8));
    }

    public void draw(Graphics2D g2) {
        for (Mob mob : mobs) {
            if (mob.isAlive) {
                mob.draw(g2);
            }
        }
    }

    public void update(Player player, boolean playerMoved) {
        for (Mob mob : mobs) {
            if (mob instanceof RangedMob) {
                ((RangedMob) mob).shouldAttack = playerMoved;
                ((RangedMob) mob).tryAttack(player);
            }
        }
    }
}
