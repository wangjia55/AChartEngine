package org.achartengine.renderer.support;

/**
 * Created by wangjia on 01/07/14.
 */
public class SupportColorLevel {
    private double startValue;
    private double endValue;
    private int color;

    public SupportColorLevel(double startValue, double endValue, int color) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.color = color;
    }

    public double getStartValue() {
        return startValue;
    }

    public double getEndValue() {
        return endValue;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "SupportColorLevel{" +
                "startValue=" + startValue +
                ", endValue=" + endValue +
                ", color=" + color +
                '}';
    }
}
