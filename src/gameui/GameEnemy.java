package gameui;

import game.GameCharacter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dto on 8/30/2016.
 */
public class GameEnemy extends AbstractGameCharacter  {
    public GameEnemy(GameField gameField){
        super(gameField,"/enemy1.gif");

    }



    public void randomMove(){
        int moveDirection = (int)(Math.random()*4);
        switch(moveDirection){
            case 0: moveUp();
                break;
            case 1: moveDown();
                break;
            case 2: moveLeft();
                break;
            case 3: moveRight();
                break;
            default: moveDown();
        }
    }
}
