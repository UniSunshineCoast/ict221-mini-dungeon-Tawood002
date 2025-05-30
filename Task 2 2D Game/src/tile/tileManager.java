package tile;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class tileManager {
    GamePanel gp;
    public Tile[] tile;

    // Public so other classes can reference map data
    public Map<String, int[][]> floors = new HashMap<>();
    public String currentFloor = "floor1"; // default // Start on floor1

    public tileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];

        getTileImage();

        // load floors
        loadFloor("floor1", "/Floors/floor1.txt");
        loadFloor("floor2", "/Floors/floor2.txt");

        setFloor("floor1"); // Set default
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/darkness_bottom.png"));
            tile[0].collision = true;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/darkness_top.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/darkness_right.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/darkness_left.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_missing_brick_1.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall_missing_brick_2.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/floor_mud_e.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFloor(String name, String filePath) {
        int[][] tileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            int col = 0;
            int row = 0;

            while (row < gp.maxScreenRow) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while (col < gp.maxScreenCol) {
                    tileNum[col][row] = Integer.parseInt(numbers[col]);
                    col++;
                }
                col = 0;
                row++;
            }

            floors.put(name, tileNum);

        } catch (Exception e) {
            System.out.println("Failed to load floor: " + name);
            e.printStackTrace();
        }
    }

    public void setFloor(String name) {
        if (floors.containsKey(name)) {
            currentFloor = name;
        } else {
            System.out.println("Floor not found: " + name);
        }
    }

    public void draw(Graphics2D g2) {
        int[][] tileNum = floors.get(currentFloor);

        if (tileNum == null) return;

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileIndex = tileNum[col][row];
            g2.drawImage(tile[tileIndex].image, x, y, gp.tileSize, gp.tileSize, null);

            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}

