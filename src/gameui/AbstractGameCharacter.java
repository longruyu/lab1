package gameui;

import game.GameCharacter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dto on 9/8/2016.
 */
public class AbstractGameCharacter extends Sprite implements GameCharacter {

    public AbstractGameCharacter(GameField gameField ,String resourceName){
        parent = gameField;
        loadImage(resourceName);

    }
    int speed = 10;

    public GameField getParent() {
        return parent;
    }

    protected GameField parent;
    @Override
    public void moveLeft() {
        Point newPoint = new Point(getX()-speed,getY());
        if (isInOfBound(newPoint)) {
            setLocation(newPoint);
        };
    }

    @Override
    public void moveRight() {
        Point newPoint = new Point(getX()+speed,getY());
        if (isInOfBound(newPoint)) {
            setLocation(newPoint);
        };
    }

    @Override
    public void moveUp() {
        Point newPoint = new Point(getX(),getY()-speed);
        if (isInOfBound(newPoint)) {
            setLocation(newPoint);
        };
    }

    @Override
    public void moveDown() {
        Point newPoint = new Point(getX(),getY()+speed);
        if (isInOfBound(newPoint)) {
            setLocation(newPoint);
        };
    }
    private boolean isInOfBound(Point newPoint){
        Rectangle gameBounds = getParent().getBounds();
        Rectangle newPosition = new Rectangle(newPoint,getImageDimensions());
        return gameBounds.contains(newPosition);

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(),getX(),getY(),parent);
    }
}
