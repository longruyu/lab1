package gameui;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by Dto on 9/8/2016.
 */
public interface GameField extends ImageObserver{
    public Rectangle getBounds();
}
