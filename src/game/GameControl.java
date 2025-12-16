package game;

import game.weapon.Rocket;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// 注意：如果你的项目里GamePanel等类在其他包（比如game.gui），要调整import！
// 先补全必要的import（根据实际包路径修改）
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameControl {
    private GamePanel gamePanel;
    private GameEnemy gameEnemy;
    private Rocket rocket;
    private boolean isCharging = false; // 火箭充电状态
    private int velocity = 1; // 火箭发射速度（1-50）

    public GameControl(GamePanel panel) {
        this.gamePanel = panel;
        gameEnemy = new GameEnemy(panel);
        gamePanel.addCharacter(gameEnemy);
        gamePanel.setFocusable(true);
        playerSetup();
        rocketSetup(); // 修正：把rocketSetupPlayer改成rocketSetup（笔误！）
    }

    // 补全playerSetup空实现（避免找不到方法）
    private void playerSetup() {}

    // 火箭初始化（创建+键盘监听+自动旋转）
    private void rocketSetup() {
        rocket = new Rocket(gamePanel);
        rocket.setLocation(100, 400); // 初始位置
        gamePanel.addCharacter(rocket);

        // 启动自动旋转
        rocket.setAutoRotate(true);
        rocket.rotateAutomatically();

        // 添加键盘监听器
        gamePanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                onRocketKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                onRocketKeyReleased(e);
            }
        });
    }

    // 键盘按下事件处理
    private void onRocketKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            // 方向控制
            case KeyEvent.VK_UP:
                rocket.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                rocket.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                rocket.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                rocket.setDirection(Direction.RIGHT);
                break;
            case KeyEvent.VK_HOME: // 小键盘7 → 上左
                rocket.setDirection(Direction.UP_LEFT);
                break;
            case KeyEvent.VK_PAGE_UP: // 小键盘9 → 上右
                rocket.setDirection(Direction.UP_RIGHT);
                break;
            case KeyEvent.VK_END: // 小键盘1 → 下左
                rocket.setDirection(Direction.DOWN_LEFT);
                break;
            case KeyEvent.VK_PAGE_DOWN: // 小键盘3 → 下右
                rocket.setDirection(Direction.DOWN_RIGHT);
                break;

            // 空格键：暂停/恢复自动旋转
            case KeyEvent.VK_SPACE:
                rocket.setAutoRotate(!rocket.isAutoRotate());
                if (rocket.isAutoRotate()) {
                    rocket.rotateAutomatically(); // 恢复旋转时重启线程
                }
                break;

            // 回车键：开始充电
            case KeyEvent.VK_ENTER:
                chargeRocket();
                break;
        }
    }

    // 键盘释放事件处理
    private void onRocketKeyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // 释放回车键：结束充电并发射火箭
        if (keyCode == KeyEvent.VK_ENTER) {
            setCharging(false);
            // 发射方向：当前火箭朝向
            double launchRadian = directionToRadian(rocket.getDirection());
            rocket.launchRocket(new game.util.Vector(velocity, launchRadian));
        }
    }

    // 火箭充电线程（速度从1→50循环）
    public void chargeRocket() {
        new Thread(() -> {
            isCharging = true;
            velocity = 1;
            try {
                while (isCharging) {
                    if (velocity < 50) {
                        velocity++;
                    } else {
                        velocity = 1; // 速度上限50，循环重置
                    }
                    Thread.sleep(10); // 每10毫秒增加1点速度
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 辅助方法：将Direction转换为弧度
    private double directionToRadian(Direction direction) {
        switch (direction) {
            case UP: return -Math.PI/2;    // 270°
            case UP_RIGHT: return -Math.PI/4; // 315°
            case RIGHT: return 0;          // 0°
            case DOWN_RIGHT: return Math.PI/4; // 45°
            case DOWN: return Math.PI/2;   // 90°
            case DOWN_LEFT: return 3*Math.PI/4; // 135°
            case LEFT: return Math.PI;     // 180°
            case UP_LEFT: return 5*Math.PI/4; // 225°
            default: return -Math.PI/2;    // 默认向上
        }
    }

    // Getter和Setter
    public boolean isCharging() {
        return isCharging;
    }

    public void setCharging(boolean charging) {
        isCharging = charging;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}

// ======================== 补全缺失的框架类（关键！）========================
// 1. GamePanel类（游戏面板核心类）
class GamePanel extends Panel {
    public GamePanel() {
        super();
    }

    // 模拟添加游戏角色的方法（适配Rocket/GameEnemy）
    public void addCharacter(AbstractGameCharacter character) {}

    // 模拟获取父容器（适配draw方法）
    public Container getParent() {
        return this;
    }

    // 模拟获取图片（适配Rocket的精灵图加载，实际需替换为真实图片加载逻辑）
    public Image getImage(String path) {
        return new BufferedImage(800, 100, BufferedImage.TYPE_INT_ARGB);
    }
}

// 2. GameEnemy类（敌人角色，空实现先凑数）
class GameEnemy extends AbstractGameCharacter {
    public GameEnemy(GamePanel panel) {
        super(panel, "");
    }

    @Override
    public void draw(Graphics g) {}
}

// 3. AbstractGameCharacter抽象类（所有游戏角色的父类）
abstract class AbstractGameCharacter {
    private GamePanel gamePanel;
    private Image image;
    private double x;
    private double y;

    public AbstractGameCharacter(GamePanel panel, String imgPath) {
        this.gamePanel = panel;
        this.image = panel.getImage(imgPath);
    }

    // 抽象绘制方法（子类必须实现）
    public abstract void draw(Graphics g);

    // 位置设置
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Getter/Setter
    public GamePanel getGameField() {
        return gamePanel;
    }

    public Image getImage() {
        return image;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Container getParent() {
        return gamePanel.getParent();
    }
}