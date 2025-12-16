package gameui;

import game.GameCharacter;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Dto on 8/30/2016.
 */
public class GamePanel extends JPanel implements GameField {
    Image backgroud;
    public GamePanel(){
        setLayout(null);
        setBackgroud();
        initGame();
    }

    private void initGame() {
        characterList = new ArrayList<GameCharacter>();
    }

    protected void setBackgroud(){
        ImageIcon icon = new ImageIcon(GamePanel.class.getResource("/bg.jpg"));
        backgroud = icon.getImage();
        setSize(backgroud.getWidth(null),backgroud.getHeight(null));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroud,0,0,getWidth(),getHeight(),this);
        for (GameCharacter gameCharacter :
                characterList) {
            gameCharacter.draw(g);

        }
    }

    List<GameCharacter> characterList ;

    public void addCharacter(GameCharacter character){
        characterList.add(character);
    }
}
