package enitity;

import Main.GamePanel;
import Main.Movement;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import Main.ScoreManager;

public class Player extends Entity {

    GamePanel GP;
    Movement Move;
    boolean moving = false;
    int targetX, targetY;
    public int health = 10;
    public int gold = 0;
    public int moveCount = 0;
    public final int MAX_MOVES = 100;

    public Rectangle getSolidArea() {
        Rectangle area = new Rectangle(solidArea);
        area.x += x;
        area.y += y;
        return area;
    }

    public Player(GamePanel GP, Movement Move) {
        this.GP = GP;
        this.Move = Move;

        solidArea = new Rectangle(); //hitbox
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefulatValues();
        getPlayerImage();
    }

    public void setDefulatValues() {
        x = GP.tileSize * 1; // first floor spawn location
        y = GP.tileSize * 10;
        targetX = x;
        targetY = y;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() { // model/sprite images
        try {
            up = ImageIO.read(getClass().getResourceAsStream("/player/npc_paladin_back.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/player/npc_paladin_front.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/player/npc_paladin_left.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player/npc_paladin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        boolean didMove = false;

        if (!moving) { // movement keys
            int nextTileX = x;
            int nextTileY = y;

            if (Move.upPressed) {
                direction = "up";
                nextTileY = y - GP.tileSize;
            } else if (Move.downPressed) {
                direction = "down";
                nextTileY = y + GP.tileSize;
            } else if (Move.leftPressed) {
                direction = "left";
                nextTileX = x - GP.tileSize;
            } else if (Move.rightPressed) {
                direction = "right";
                nextTileX = x + GP.tileSize;
            }

            if (nextTileX != x || nextTileY != y) {
                int oldX = x;
                int oldY = y;
                x = nextTileX;
                y = nextTileY;

                collisionOn = false;
                GP.cChecker.checkTile(this);

                if (!collisionOn) {
                    targetX = nextTileX;
                    targetY = nextTileY;
                    moving = true;

                    didMove = true;

                    moveCount++;
                    if (moveCount >= MAX_MOVES) { // counts and adds score even if the player uses their step limit
                        ScoreManager.addScore(gold);
                        System.out.println("\n");
                        ScoreManager.printScores();
                        System.out.println("\nYou died from exertion!!? (You reached 100 steps) \n");
                        System.out.println("You Collected " +gold+ " Gold!!!");
                        System.exit(0);
                    }

                    for (enitity.Mob mob : GP.mobManager.mobs) {
                        if (mob.isAlive && this.getSolidArea().intersects(mob.getSolidArea())) {
                            if (mob instanceof Trap) {
                                mob.onTouchPlayer(this); //  trap stays armed after damage
                            } else {
                                this.health -= 2;
                                this.gold += 2;
                                mob.isAlive = false;

                                if (mob instanceof RangedMob) {
                                    System.out.println("Ranged Mutant killed!");
                                } else {
                                    System.out.println("Melee Mutant killed!");
                                }
                            }
                        }
                    }

                } else {
                    x = oldX;
                    y = oldY;
                }

                Move.upPressed = false;
                Move.downPressed = false;
                Move.leftPressed = false;
                Move.rightPressed = false;

                Iterator<Objects> it = GP.objects.iterator();
                while (it.hasNext()) {
                    Objects obj = it.next();
                    if (!obj.isCollected() && this.getSolidArea().intersects(obj.getArea())) {
                        obj.applyEffect(this);
                        break; // exits early if needed (e.g. ladder triggers map reset)
                    }
                }

                // creats a separate list to apply after iteration
                ArrayList<Objects> triggeredObjects = new ArrayList<>();

                for (Objects obj : GP.objects) {
                    if (!obj.isCollected() && this.getSolidArea().intersects(obj.getArea())) {
                        triggeredObjects.add(obj);
                    }
                }

                // applys effects AFTER iteration
                for (Objects obj : triggeredObjects) {
                    obj.applyEffect(this);
                }

                if (health <= 0) {
                    ScoreManager.addScore(gold); // saving score once player dies + death msg
                    System.out.println("\n");
                    ScoreManager.printScores();
                    System.out.println(" \nGame Over! You Died!");
                    System.out.println("You Collected " +gold+ " Gold!!!");
                    System.exit(0);
                }
            }
        }

        // tells mobManager if player moved
        GP.mobManager.update(this, didMove);

        // smooth transitions to each tile instead of free movement directional movement, as they could walkaround it don't track footsteps/moves well.
        if (moving) {
            if (direction.equals("up") && y > targetY) y -= speed;
            if (direction.equals("down") && y < targetY) y += speed;
            if (direction.equals("left") && x > targetX) x -= speed;
            if (direction.equals("right") && x < targetX) x += speed;

            if (Math.abs(x - targetX) <= speed && Math.abs(y - targetY) <= speed) {
                x = targetX;
                y = targetY;
                moving = false;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up": image = up; break;
            case "down": image = down; break;
            case "left": image = left; break;
            case "right": image = right; break;
        }

        g2.drawImage(image, x, y, GP.tileSize, GP.tileSize, null);
    }
}

