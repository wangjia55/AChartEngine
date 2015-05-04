/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.achartengine.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;

import org.achartengine.model.Point;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportSelectedChartType;
import org.achartengine.renderer.support.SupportSeriesRender;

import java.util.List;

/**
 * The bar chart rendering class.
 */
public class SupportBarChart extends XYChart {
    /**
     * The constant to identify this chart type.
     */
    public static final String TYPE = "Bar";
    /**
     * The legend shape width.
     */
    private static final int SHAPE_WIDTH = 12;
    /**
     * The chart type.
     */
    protected Type mType = Type.DEFAULT;

    /**
     * 为了扩展功能而增加的属性，added by wangjia
     */
    private float supportHalfDiffX = 0;
    private float supportYAxisValue = 0;

    /**
     * The bar chart type enum.
     */
    public enum Type {
        DEFAULT, STACKED;
    }


    SupportBarChart() {
    }

    SupportBarChart(Type type) {
        mType = type;
    }

    /**
     * Builds a new bar chart instance.
     *
     * @param dataset  the multiple series dataset
     * @param renderer the multiple series renderer
     * @param type     the bar chart type
     */
    public SupportBarChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer,
                           Type type) {
        super(dataset, renderer);
        mType = type;
    }

    @Override
    protected ClickableArea[] clickableAreasForPoints(List<Float> points, List<Double> values,
                                                      float yAxisValue, int seriesIndex, int startIndex) {
        int seriesNr = mDataset.getSeriesCount();
        int length = points.size();
        ClickableArea[] ret = new ClickableArea[length / 2];
        float halfDiffX = getHalfDiffX(points, length, seriesNr);
        for (int i = 0; i < length; i += 2) {
            float x = points.get(i);
            float y = points.get(i + 1);
            if (mType == Type.STACKED) {
                ret[i / 2] = new ClickableArea(new RectF(x - halfDiffX, Math.min(y, yAxisValue), x
                        + halfDiffX, Math.max(y, yAxisValue)), values.get(i), values.get(i + 1));
            } else {
                float startX = x - seriesNr * halfDiffX + seriesIndex * 2 * halfDiffX;
                ret[i / 2] = new ClickableArea(new RectF(startX, Math.min(y, yAxisValue), startX + 2
                        * halfDiffX, Math.max(y, yAxisValue)), values.get(i), values.get(i + 1));
            }
        }
        return ret;
    }

    /**
     * The graphical representation of a series.
     *
     * @param canvas         the canvas to paint to
     * @param paint          the paint to be used for drawing
     * @param points         the array of points to be used for drawing the series
     * @param seriesRenderer the series renderer
     * @param yAxisValue     the minimum value of the y axis
     * @param seriesIndex    the index of the series currently being drawn
     * @param startIndex     the start index of the rendering points
     */
    @Override
    public void drawSeries(Canvas canvas, Paint paint, List<Float> points, List<Double> values,
                           XYSeriesRenderer seriesRenderer, SupportSeriesRender supportSeriesRender,
                           float yAxisValue, int seriesIndex, int startIndex) {
        int seriesNr = mDataset.getSeriesCount();
        int length = points.size();
        paint.setColor(seriesRenderer.getColor());
        paint.setStyle(Style.FILL);
        float halfDiffX = getHalfDiffX(points, length, seriesNr);
        supportHalfDiffX = halfDiffX;
        supportYAxisValue = yAxisValue;
        for (int i = 0; i < length; i += 2) {
            float x = points.get(i);
            float y = points.get(i + 1);
            double value = values.get(i + 1);
            drawBar(supportSeriesRender, canvas, x, yAxisValue, x, y, halfDiffX, seriesNr, seriesIndex, value, paint);
        }
        paint.setColor(seriesRenderer.getColor());
    }

    /**
     * Draws a bar.
     *
     * @param canvas      the canvas
     * @param xMin        the X axis minimum
     * @param yMin        the Y axis minimum
     * @param xMax        the X axis maximum
     * @param yMax        the Y axis maximum
     * @param halfDiffX   half the size of a bar
     * @param seriesNr    the total number of series
     * @param seriesIndex the current series index
     * @param paint       the paint
     */
    protected void drawBar(SupportSeriesRender supportSeriesRender, Canvas canvas, float xMin, float yMin, float xMax, float yMax,
                           float halfDiffX, int seriesNr, int seriesIndex, double value, Paint paint) {
        int scale = mDataset.getSeriesAt(seriesIndex).getScaleNumber();
        if (mType == Type.STACKED) {
            drawBar(supportSeriesRender, canvas, xMin - halfDiffX, yMax, xMax + halfDiffX, yMin, scale, seriesIndex, value, paint);
        } else {
            float startX = xMin - seriesNr * halfDiffX + seriesIndex * 2 * halfDiffX;
            drawBar(supportSeriesRender, canvas, startX, yMax, startX + 2 * halfDiffX, yMin, scale, seriesIndex, value, paint);
        }
    }

    /**
     * Draws a bar.
     *
     * @param canvas      the canvas
     * @param xMin        the X axis minimum
     * @param yMin        the Y axis minimum
     * @param xMax        the X axis maximum
     * @param yMax        the Y axis maximum
     * @param scale       the scale index
     * @param seriesIndex the current series index
     * @param paint       the paint
     */
    private void drawBar(SupportSeriesRender supportSeriesRender, Canvas canvas, float xMin, float yMin, float xMax, float yMax, int scale,
                         int seriesIndex, double value, Paint paint) {
        // Fix negative bars issue in Android 4.2
        float temp;
        if (xMin > xMax) {
            temp = xMin;
            xMin = xMax;
            xMax = temp;
        }
        if (yMin > yMax) {
            temp = yMin;
            yMin = yMax;
            yMax = temp;
        }


        SimpleSeriesRenderer renderer = mRenderer.getSeriesRendererAt(seriesIndex);

        if (supportSeriesRender.isShowBarChartShadow()) {
            paint.setColor(supportSeriesRender.getBarChartShadowColor());
            canvas.drawRect(Math.round(xMin), 0, Math.round(xMax), canvas.getHeight(), paint);
            paint.setColor(renderer.getColor());
        }

        if (supportSeriesRender.isColorLevelValid() && supportSeriesRender.getColorLevelList().size() > 0) {
            paint.setColor(supportSeriesRender.getLevelColorByValue(value));
        }

        if (renderer.isGradientEnabled()) {
            float minY = (float) toScreenPoint(new double[]{0, renderer.getGradientStopValue()}, scale)[1];
            float maxY = (float) toScreenPoint(new double[]{0, renderer.getGradientStartValue()},
                    scale)[1];
            float gradientMinY = Math.max(minY, Math.min(yMin, yMax));
            float gradientMaxY = Math.min(maxY, Math.max(yMin, yMax));
            int gradientMinColor = renderer.getGradientStopColor();
            int gradientMaxColor = renderer.getGradientStartColor();
            int gradientStartColor = gradientMaxColor;
            int gradientStopColor = gradientMinColor;


            if (yMin < minY) {
                paint.setColor(gradientMinColor);
                canvas.drawRect(Math.round(xMin), Math.round(yMin), Math.round(xMax),
                        Math.round(gradientMinY), paint);
            } else {
                gradientStopColor = getGradientPartialColor(gradientMinColor, gradientMaxColor,
                        (maxY - gradientMinY) / (maxY - minY));
            }
            if (yMax > maxY) {
                paint.setColor(gradientMaxColor);
                canvas.drawRect(Math.round(xMin), Math.round(gradientMaxY), Math.round(xMax),
                        Math.round(yMax), paint);
            } else {
                gradientStartColor = getGradientPartialColor(gradientMaxColor, gradientMinColor,
                        (gradientMaxY - minY) / (maxY - minY));
            }
            GradientDrawable gradient = new GradientDrawable(Orientation.BOTTOM_TOP, new int[]{
                    gradientStartColor, gradientStopColor});
            gradient.setBounds(Math.round(xMin), Math.round(gradientMinY), Math.round(xMax),
                    Math.round(gradientMaxY));
            gradient.draw(canvas);
        } else {
            if (Math.abs(yMin - yMax) < 1) {
                if (yMin < yMax) {
                    yMax = yMin + 1;
                } else {
                    yMax = yMin - 1;
                }
            }
            canvas
                    .drawRect(Math.round(xMin), Math.round(yMin), Math.round(xMax), Math.round(yMax), paint);
        }
    }

    private int getGradientPartialColor(int minColor, int maxColor, float fraction) {
        int alpha = Math.round(fraction * Color.alpha(minColor) + (1 - fraction)
                * Color.alpha(maxColor));
        int r = Math.round(fraction * Color.red(minColor) + (1 - fraction) * Color.red(maxColor));
        int g = Math.round(fraction * Color.green(minColor) + (1 - fraction) * Color.green(maxColor));
        int b = Math.round(fraction * Color.blue(minColor) + (1 - fraction) * Color.blue((maxColor)));
        return Color.argb(alpha, r, g, b);
    }

    /**
     * The graphical representation of the series values as text.
     *
     * @param canvas      the canvas to paint to
     * @param series      the series to be painted
     * @param renderer    the series renderer
     * @param paint       the paint to be used for drawing
     * @param points      the array of points to be used for drawing the series
     * @param seriesIndex the index of the series currently being drawn
     * @param startIndex  the start index of the rendering points
     */
    protected void drawChartValuesText(Canvas canvas, XYSeries series, XYSeriesRenderer renderer,
                                       Paint paint, List<Float> points, int seriesIndex, int startIndex) {
        int seriesNr = mDataset.getSeriesCount();
        int length = points.size();
        float halfDiffX = getHalfDiffX(points, length, seriesNr);
        for (int i = 0; i < length; i += 2) {
            int index = startIndex + i / 2;
            double value = series.getY(index);
            if (!isNullValue(value)) {
                float x = points.get(i);
                if (mType == Type.DEFAULT) {
                    x += seriesIndex * 2 * halfDiffX - (seriesNr - 1.5f) * halfDiffX;
                }
                if (value >= 0) {
                    drawText(canvas, getLabel(renderer.getChartValuesFormat(), value), x, points.get(i + 1)
                            - renderer.getChartValuesSpacing(), paint, 0);
                } else {
                    drawText(canvas, getLabel(renderer.getChartValuesFormat(), value), x, points.get(i + 1)
                            + renderer.getChartValuesTextSize() + renderer.getChartValuesSpacing() - 3, paint, 0);
                }
            }
        }
    }

    /**
     * Returns the legend shape width.
     *
     * @param seriesIndex the series index
     *
     * @return the legend shape width
     */
    public int getLegendShapeWidth(int seriesIndex) {
        return SHAPE_WIDTH;
    }

    /**
     * The graphical representation of the legend shape.
     *
     * @param canvas      the canvas to paint to
     * @param renderer    the series renderer
     * @param x           the x value of the point the shape should be drawn at
     * @param y           the y value of the point the shape should be drawn at
     * @param seriesIndex the series index
     * @param paint       the paint to be used for drawing
     */
    public void drawLegendShape(Canvas canvas, SimpleSeriesRenderer renderer, float x, float y,
                                int seriesIndex, Paint paint) {
        float halfShapeWidth = SHAPE_WIDTH / 2;
        canvas.drawRect(x, y - halfShapeWidth, x + SHAPE_WIDTH, y + halfShapeWidth, paint);
    }

    @Override
    public void drawSpecifiedPoint(Canvas canvas, SupportSeriesRender supportSeriesRender, Point point, String desc) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(supportSeriesRender.getClickPointColor());

        SupportSelectedChartType selectedChartType = supportSeriesRender.getSelectedChartType();
        switch (selectedChartType) {
            case SHOW_BOX: {
                drawPopupBox(canvas, point, supportSeriesRender.getClickPointColor(), desc);
            }
            break;
            case SHOW_DIFF_COLOR: {
                drawDifferentColorBarChart(canvas, point, paint);
            }
            break;
            case BOTH: {
                drawDifferentColorBarChart(canvas, point, paint);
                drawPopupBox(canvas, point, supportSeriesRender.getClickPointColor(), desc);
            }
            break;
        }
    }

    private void drawDifferentColorBarChart(Canvas canvas, Point point, Paint paint) {
        canvas.drawRect(
                Math.round(point.getX()) - supportHalfDiffX,
                Math.round(point.getY()),
                Math.round(point.getX() + supportHalfDiffX),
                Math.round(supportYAxisValue),
                paint);
    }


    /**
     * Calculates and returns the half-distance in the graphical representation of
     * 2 consecutive points.
     *
     * @param points   the points
     * @param length   the points length
     * @param seriesNr the series number
     *
     * @return the calculated half-distance value
     */
    protected float getHalfDiffX(List<Float> points, int length, int seriesNr) {
        float barWidth = mRenderer.getBarWidth();
        if (barWidth > 0) {
            return barWidth / 2;
        }
        int div = length;
        if (length > 2) {
            div = length - 2;
        }
        float halfDiffX = (points.get(length - 2) - points.get(0)) / div;
        if (halfDiffX == 0) {
            halfDiffX = 10;
        }

        if (mType != Type.STACKED) {
            halfDiffX /= seriesNr;
        }
        return (float) (halfDiffX / (getCoeficient() * (1 + mRenderer.getBarSpacing())));
    }

    /**
     * Returns the value of a constant used to calculate the half-distance.
     *
     * @return the constant value
     */
    protected float getCoeficient() {
        return 1f;
    }

    /**
     * Returns if the chart should display the null values.
     *
     * @return if null values should be rendered
     */
    protected boolean isRenderNullValues() {
        return true;
    }

    /**
     * Returns the default axis minimum.
     *
     * @return the default axis minimum
     */
    public double getDefaultMinimum() {
        return 0;
    }

    /**
     * Returns the chart type identifier.
     *
     * @return the chart type
     */
    public String getChartType() {
        return TYPE;
    }
}
