package game.weapon;

import game.Direction;
import game.AbstractGameCharacter;
import game.GamePanel; // 新增：引用补全的GamePanel
import game.util.Vector; // 确保Vector在game.util包下

import java.awt.*;
import java.awt.event.KeyEvent;

public class Rocket extends AbstractGameCharacter {
    private Direction direction;
    private Rectangle boundary;
    private boolean isAutoRotate = false;
    private Vector accelerator; // 加速度向量（包含重力）

    public Rocket(GamePanel gameField) {
        super(gameField, "/rocket4d.png");
        boundary = new Rectangle(0, 0, 100, 100);
        direction = Direction.UP;
        // 初始化加速度（重力：大小3，方向向上（-Math.PI/2弧度 = 270°））
        accelerator = new Vector(3, -Math.PI / 2);
    }

    // 绘制火箭（根据方向截取精灵图）
    @Override
    public void draw(Graphics g) {
        int x = (int) (getX() - boundary.getWidth() / 2);
        int y = (int) (getY() - boundary.getHeight() / 2);
        int width = (int) boundary.getWidth();
        int height = (int) boundary.getHeight();

        // 精灵图截取参数：100*8的雪碧图，按方向值截取对应帧
        int srcX = 100 * (direction.getValue() - 1);
        int srcY = 0;
        int srcWidth = srcX + 100; // 修正：原代码srcWidth=100*direction.getValue()会越界！
        int srcHeight = 100;

        g.drawImage(getImage(), x, y, x + width, y + height,
                srcX, srcY, srcWidth, srcHeight, getParent());
    }

    // 自动旋转线程（切换方向）
    public void rotateAutomatically() {
        new Thread(() -> {
            while (isAutoRotate) {
                try {
                    // 方向值循环：1→8，到达8后重置为1
                    int currentVal = direction.getValue();
                    if (currentVal < 8) {
                        direction = Direction.values()[currentVal]; // 按索引取方向
                    } else {
                        direction = Direction.UP; // 重置为第一个方向
                    }
                    Thread.sleep(500); // 每500毫秒切换一次方向
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 发射火箭（带物理运动：速度+加速度）
    public void launchRocket(Vector initialVelocity) {
        new Thread(() -> {
            Vector currentVelocity = new Vector(initialVelocity.getSize(), initialVelocity.getRadian());
            try {
                while (true) {
                    // 计算新位置（基于当前速度）
                    double newX = getX() + currentVelocity.getSize() * Math.cos(currentVelocity.getRadian());
                    double newY = getY() + currentVelocity.getSize() * Math.sin(currentVelocity.getRadian());

                    // 边界检测（防止火箭飞出屏幕）
                    Rectangle screenBounds = getGameField().getBounds();
                    boolean inXBound = newX >= boundary.getWidth()/2 && newX <= screenBounds.getWidth() - boundary.getWidth()/2;
                    boolean inYBound = newY >= boundary.getHeight()/2 && newY <= screenBounds.getHeight() - boundary.getHeight()/2;

                    if (inXBound && inYBound) {
                        setLocation(newX, newY);
                    } else {
                        break; // 飞出边界则停止运动
                    }

                    // 速度叠加加速度（模拟重力影响）
                    currentVelocity = currentVelocity.add(accelerator);

                    // 打印调试信息
                    System.out.printf("x=%.0f, y=%.0f | RightPos=%.0f, BottomPos=%.0f%n",
                            newX, newY, newX + boundary.getWidth(), newY + boundary.getHeight());
                    System.out.printf("ScreenSize: W=%.0f, H=%.0f%n",
                            screenBounds.getWidth(), screenBounds.getHeight());

                    Thread.sleep(40); // 每40毫秒更新一次位置
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("火箭停止运动（飞出边界或被中断）");
            }
        }).start();
    }

    // Getter和Setter
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isAutoRotate() {
        return isAutoRotate;
    }

    public void setAutoRotate(boolean autoRotate) {
        isAutoRotate = autoRotate;
    }

    public Vector getAccelerator() {
        return accelerator;
    }

    public void setAccelerator(Vector accelerator) {
        this.accelerator = accelerator;
    }
}