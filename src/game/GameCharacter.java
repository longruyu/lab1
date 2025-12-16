package game;

import java.awt.*;

/**
 * Created by Dto on 8/30/2016.
 */
public interface GameCharacter {
    public void moveLeft();
    public void moveRight();
    public void moveUp();
    public void moveDown();
    public void draw(Graphics g);
}
