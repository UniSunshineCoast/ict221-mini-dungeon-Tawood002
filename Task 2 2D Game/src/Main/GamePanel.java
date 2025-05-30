package Main;

import enitity.*;
import tile.CollisionChecker;
import tile.tileManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    final int originalTileSize = 16; // 16x16 = 1 tile
    final int scale = 4; // 16 x 4 = 64 pixels per tile

    public final int tileSize = originalTileSize * scale; // 64x64 tile
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    // FPS
    int FPS = 60;

    //calling all needed methods from differing classes
    public tileManager tileM = new tileManager(this);
    Movement Move = new Movement();
    Thread gameThread;
    Player player = new Player(this, Move);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public ArrayList<Objects> objects = new ArrayList<>();
    public String currentFloor = "floor1";
    public MobManager mobManager = new MobManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(Move);
        this.setFocusable(true);

        tileM.setFloor("floor1"); //setting first floor, as the first  floor to load
        currentFloor = "floor1";

        // second floor objects -----------------------------------------
        //Goldcoins
        objects.add(new GoldCoins(this, tileSize * 3, tileSize * 4));
        objects.add(new GoldCoins(this, tileSize * 1, tileSize * 4));
        objects.add(new GoldCoins(this, tileSize * 4, tileSize * 8));

        //Health Potions
        objects.add(new HealthPotions(this, tileSize * 6, tileSize * 8));
        objects.add(new HealthPotions(this, tileSize * 10, tileSize * 3));

        //ladder
        objects.add(new Ladder(this, tileSize * 10, tileSize * 1));
    }

    public void startGameThread() { //starting method
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { // core game loop

        double drawInterval = 1000000000.0 / FPS; //setting the games FPS  to 60 to ensure each functions,method and class works correctly, everytime
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();

    }

    @Override
    public void paintComponent(Graphics g) { //rendering all of the images and sysbols for the game
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        mobManager.draw(g2);

        for (Objects obj : objects) {
             if (!obj.isCollected()) {
                obj.draw(g2);
            }
        }

        // UI - Health and Gold
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.drawString("Health: " + player.health, 20, 40);

        g2.setColor(Color.ORANGE);
        g2.drawString("Gold: " + player.gold, 160, 40);

        g2.setColor(Color.CYAN);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        int movesLeft = player.MAX_MOVES - player.moveCount;
        g2.drawString("Moves Left: " + movesLeft, 270, 40);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("I", 145, 40);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("I", 260, 40);

        this.setBackground(Color.darkGray);

        g2.dispose();
    }
}
