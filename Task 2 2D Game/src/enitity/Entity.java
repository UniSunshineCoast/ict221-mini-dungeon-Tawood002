package enitity;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {

    public int x, y;
    public int speed;

    public BufferedImage up, down, left, right;
    public  String direction;
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
