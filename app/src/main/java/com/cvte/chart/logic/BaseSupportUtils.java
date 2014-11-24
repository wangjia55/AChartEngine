package com.cvte.chart.logic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportTargetLineStyle;

/**
 * Created by wangjia on 28/06/14.
 */
public abstract class BaseSupportUtils {
    private static final String TAG = BaseSupportUtils.class.getSimpleName();
    protected Context mContext;
    protected XYMultipleSeriesRenderer mXYRenderer;
    protected XYMultipleSeriesDataset mXYMultipleSeriesDataSet;

    protected BaseSupportUtils(Context context) {
        this.mContext = context;
        mXYRenderer = new XYMultipleSeriesRenderer();
        initRender();
    }

    private void initRender() {
        LogUtils.LOGE(TAG,"initRender");
        // 1, 构造显示用渲染图
        float textSize = 20f;

        mXYRenderer.setChartTitle("Chart Title");
        mXYRenderer.setXTitle("X Title");
        mXYRenderer.setYTitle("Y Title");

        //设置字体的大小
        mXYRenderer.setAxisTitleTextSize(textSize);
        mXYRenderer.setChartTitleTextSize(textSize);
        mXYRenderer.setLabelsTextSize(textSize);
        mXYRenderer.setLegendTextSize(textSize);

        // 设置背景颜色
        mXYRenderer.setBackgroundColor(Color.WHITE);
        // 设置页边空白的颜色
        mXYRenderer.setMarginsColor(Color.WHITE);
        // 设置背景颜色生效
        mXYRenderer.setApplyBackgroundColor(true);

        // 设置坐标轴,轴的颜色
        mXYRenderer.setXLabelsColor(Color.parseColor("#797C7E"));
        mXYRenderer.setYLabelsColor(0, Color.parseColor("#797C7E"));
        //设置网格的颜色
        mXYRenderer.setShowGrid(true);
        mXYRenderer.setGridColor(Color.parseColor("#55797C7E"));
        mXYRenderer.addSeriesRenderer(getSimpleSeriesRender(Color.parseColor("#45b97c")));

        // 设置合适的刻度,在轴上显示的数量是 MAX / labels
        mXYRenderer.setYLabels(6);
        mXYRenderer.setXLabels(0); // 设置 X 轴不显示数字（改用我们手动添加的文字标签）

        mXYRenderer.setMargins(new int[]{30, 20, 20, 20});
        mXYRenderer.setXAxisMax(20);
        mXYRenderer.setXAxisMin(0);
        mXYRenderer.setYAxisMin(0);
        mXYRenderer.setYAxisMax(20);

        // 设置x,y轴显示的排列,默认是 Align.CENTER
        mXYRenderer.setXLabelsAlign(Paint.Align.CENTER);
        mXYRenderer.setYLabelsAlign(Paint.Align.RIGHT);

        mXYRenderer.setAntialiasing(true);
        mXYRenderer.setFitLegend(true);
        mXYRenderer.setShowAxes(true);  // 设置坐标轴是否可见
        mXYRenderer.setShowLegend(true);
        // 设置x,y轴上的刻度的颜色
        // 设置是否显示,坐标轴的轴,默认为 true
        // 是否支持图表移动
        mXYRenderer.setPanEnabled(false, false);// 表盘移动
        mXYRenderer.setZoomEnabled(false, false);
        mXYRenderer.setClickEnabled(true);// 是否可点击
        mXYRenderer.setSelectableBuffer(20);// 点击区域大小

        //设置目标线
        mXYRenderer.setTargetLineVisible(true);
        mXYRenderer.setTargetLineColor(Color.MAGENTA);
        mXYRenderer.setTargetValue(12.5f);
        mXYRenderer.setTargetLineStyle(SupportTargetLineStyle.Line_Dotted);
        mXYRenderer.setTargetDescription("GOAL");


        Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/Futura.ttc");
        mXYRenderer.setTextTypeface(typeFace);

        setRespectiveRender(mXYRenderer);
    }


    /**
     * 这个方法主要是让各自不同的图表设置自己需要的部分样式
     */
    protected  abstract void setRespectiveRender(XYMultipleSeriesRenderer render);

    /**
     * 这个方法主要是用来描述曲线中“点”的属性，如点的样式,颜色，大小等，
     * 如果不想显示出点，则在上面的方法中设置PointSize(0)即可
     * @param color
     * @return
     */
    protected abstract XYSeriesRenderer getSimpleSeriesRender(int color);
}
