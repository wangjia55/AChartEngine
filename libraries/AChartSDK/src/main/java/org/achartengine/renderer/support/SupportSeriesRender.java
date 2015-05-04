package org.achartengine.renderer.support;

import android.graphics.Color;

import org.achartengine.renderer.DefaultRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjia on 27/06/14.
 * 此类主要是为和Line相关的图表提供必要的扩展属性
 */
public class SupportSeriesRender extends DefaultRenderer {
    private static final int COLOR_START = Color.parseColor("#FF6D80");
    private static final int COLOR_CENTER = Color.parseColor("#FFB33C");
    private static final int COLOR_END = Color.parseColor("#FFEA00");

    /**
     * 为了扩展注状图功能而增加的属性,是否打开住装图的阴影背景颜色
     */
    private boolean showBarChartShadow = false;

    /**
     * 设置自定义的注状图的阴影颜色
     */
    private int barChartShadowColor = Color.parseColor("#7FD8D8D8");

    /**
     * 曲线的颜色 , 所有的值都是Color類型
     */
    private int lineColor = Color.parseColor("#FF8A82");
    /**
     * 是否打开颜色分级功能
     */
    private boolean colorLevelValid = false;
    /**
     * 设置自定义的颜色分级
     */
    private List<SupportColorLevel> colorLevelList = new ArrayList<SupportColorLevel>();


    /**
     * 设置曲线渐变的颜色 ，如果设置了lineColor，则此属性无效
     */
    private int[] shapeLineColor = new int[]{COLOR_START, COLOR_CENTER, COLOR_END};

    /**
     * 是否支持颜色渐变
     */
    private boolean isSupportShapeLineColor = false;

    /**
     * 当支持点的点击事件时，设置点中的点的颜色
     */
    private int clickPointColor = Color.parseColor("#FF8A82");

    /**
     * 点击后的效果设置
     */
    private SupportSelectedChartType selectedChartType = SupportSelectedChartType.SHOW_BOX;


    public int[] getShapeLineColor() {
        return shapeLineColor;
    }

    public void setShapeLineColor(int[] shapeLineColor) {
        isSupportShapeLineColor = true;
        this.shapeLineColor = shapeLineColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public boolean isSupportShapeLineColor() {
        return isSupportShapeLineColor;
    }

    public List<SupportColorLevel> getColorLevelList() {
        return colorLevelList;
    }

    public void setColorLevelList(List<SupportColorLevel> colorLevelList) {
        this.colorLevelList = colorLevelList;
    }

    public boolean isColorLevelValid() {
        return colorLevelValid;
    }

    public void setColorLevelValid(boolean colorLevelValid) {
        this.colorLevelValid = colorLevelValid;
    }

    public int getClickPointColor() {
        return clickPointColor;
    }

    public void setClickPointColor(int clickPointColor) {
        this.clickPointColor = clickPointColor;
    }

    public SupportSelectedChartType getSelectedChartType() {
        return selectedChartType;
    }

    public void setSelectedChartType(SupportSelectedChartType selectedChartType) {
        this.selectedChartType = selectedChartType;
    }

    public int getBarChartShadowColor() {
        return barChartShadowColor;
    }

    public void setBarChartShadowColor(int barChartShadowColor) {
        this.barChartShadowColor = barChartShadowColor;
    }


    public boolean isShowBarChartShadow() {
        return showBarChartShadow;
    }

    public void setShowBarChartShadow(boolean showBarChartShadow) {
        this.showBarChartShadow = showBarChartShadow;
    }


    /**
     * 根据设置的颜色分级，通过传入的数据得到分级后的颜色
     */
    public int getLevelColorByValue(double value) {
        SupportColorLevel maxLevel = new SupportColorLevel(0, 0, Color.GRAY);
        for (SupportColorLevel colorLevel : colorLevelList) {
            if (colorLevel.getEndValue() > maxLevel.getEndValue()) {
                maxLevel = colorLevel;
            }
            if (value >= colorLevel.getStartValue() && value < colorLevel.getEndValue()) {
                return colorLevel.getColor();
            }
        }
        if (value >= maxLevel.getEndValue()) {
            return maxLevel.getColor();
        }
        return Color.DKGRAY;
    }
}
