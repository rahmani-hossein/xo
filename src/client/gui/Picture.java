package client.gui;

import client.Controller;
import model.Piece;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Picture {

    private int x, y;
    private String name;
    private BufferedImage image;
    private int sizeX = Controller.getInstance().getResourceManager().getClientConstants().getSixeX();
    private int sizeY = Controller.getInstance().getResourceManager().getClientConstants().getSizeY();

    public Picture(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Picture(Piece piece) {
        this.x = piece.getLocation().getX();
        this.y = piece.getLocation().getY();
        this.name = piece.getName();
        initImage();
    }

    public Picture(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        initImage();
    }

    private void initImage() {
        image = Controller.getInstance().getResourceManager().getImage(name);
    }

    public void paint(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
}
