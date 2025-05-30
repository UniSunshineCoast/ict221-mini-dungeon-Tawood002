package tile;

import Main.GamePanel;
import enitity.Player;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Player player) {
        // predict player's future position based on direction
        int futureLeftX = player.x + player.solidArea.x;
        int futureRightX = player.x + player.solidArea.x + player.solidArea.width;
        int futureTopY = player.y + player.solidArea.y;
        int futureBottomY = player.y + player.solidArea.y + player.solidArea.height;

        if (player.direction.equals("up")) {
            futureTopY -= player.speed;
        } else if (player.direction.equals("down")) {
            futureBottomY += player.speed;
        } else if (player.direction.equals("left")) {
            futureLeftX -= player.speed;
        } else if (player.direction.equals("right")) {
            futureRightX += player.speed;
        }

        // converts pixel positions to tile indices
        int leftCol = futureLeftX / gp.tileSize;
        int rightCol = futureRightX / gp.tileSize;
        int topRow = futureTopY / gp.tileSize;
        int bottomRow = futureBottomY / gp.tileSize;

        // prevents out-of-bounds errors, so the play can't get stuck or out of the map
        if (topRow < 0 || bottomRow >= gp.maxScreenRow || leftCol < 0 || rightCol >= gp.maxScreenCol) {
            player.collisionOn = true;
            return;
        }

        int[][] tileMap = gp.tileM.floors.get(gp.tileM.currentFloor);
        int tileNum1 = 0;
        int tileNum2 = 0;

        if (player.direction.equals("up")) {
            tileNum1 = tileMap[leftCol][topRow];
            tileNum2 = tileMap[rightCol][topRow];
        } else if (player.direction.equals("down")) {
            tileNum1 = tileMap[leftCol][bottomRow];
            tileNum2 = tileMap[rightCol][bottomRow];
        } else if (player.direction.equals("left")) {
            tileNum1 = tileMap[leftCol][topRow];
            tileNum2 = tileMap[leftCol][bottomRow];
        } else if (player.direction.equals("right")) {
            tileNum1 = tileMap[rightCol][topRow];
            tileNum2 = tileMap[rightCol][bottomRow];
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            player.collisionOn = true;
        }
    }
}



