package game;

public enum Direction {
    UP(1), DOWN(5), LEFT(7), RIGHT(3),
    UP_LEFT(8), UP_RIGHT(2), DOWN_LEFT(6), DOWN_RIGHT(4);

    private final int value; // 加final，禁止修改

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    // 删掉setValue方法！枚举值不能动态改
}