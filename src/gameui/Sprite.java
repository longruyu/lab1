package gameui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dto on 9/8/2016.
 */
public abstract class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;

    public Sprite() {
        vis = true;
    }

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        vis = true;
    }

    protected Dimension getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
        return new Dimension(width,height);
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(Sprite.class.getResource(imageName));
        image = ii.getImage();
        getImageDimensions();
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setLocation(Point point) {
        x = (int) point.getX();
        y = (int) point.getY();

    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;

    }


}
