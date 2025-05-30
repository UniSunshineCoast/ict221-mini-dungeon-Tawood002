package enitity;

import java.awt.*;
import Main.GamePanel;

public abstract class Objects {
    protected int x, y; // location of objects
    protected boolean collected = false;
    protected GamePanel gp;

    public Objects(GamePanel gp, int x, int y) {
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    public abstract void applyEffect(Player player); //affects it causes on the player

    public abstract void draw(Graphics2D g2); //model

    public Rectangle getArea() {
        return new Rectangle(x, y, gp.tileSize, gp.tileSize);
    } //Hitbox

    public boolean isCollected() {
        return collected;
    } // collected check
}
