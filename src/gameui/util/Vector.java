package game.util;

public class Vector {
    private double size;   // 向量大小（速度/加速度大小）
    private double radian; // 向量方向（弧度）

    public Vector() {}

    public Vector(double size, double radian) {
        this.size = size;
        this.radian = radian;
    }

    // 向量加法（用于速度与加速度叠加）
    public Vector add(Vector vector) {
        // 计算x轴分量总和
        double xAxis = this.getSize() * Math.cos(this.getRadian())
                + vector.getSize() * Math.cos(vector.getRadian());
        // 计算y轴分量总和
        double yAxis = this.getSize() * Math.sin(this.getRadian())
                + vector.getSize() * Math.sin(vector.getRadian());
        // 新向量的大小（勾股定理）
        double newSize = Math.sqrt(Math.pow(xAxis, 2) + Math.pow(yAxis, 2));
        // 新向量的方向（反正切函数）
        double newRadian = Math.atan2(yAxis, xAxis); // 注意使用atan2避免象限问题
        return new Vector(newSize, newRadian);
    }

    // Getter和Setter
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getRadian() {
        return radian;
    }

    public void setRadian(double radian) {
        this.radian = radian;
    }
}