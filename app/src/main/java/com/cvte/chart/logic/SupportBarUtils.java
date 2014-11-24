package com.cvte.chart.logic;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.SupportBarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportBarSeriesRender;
import org.achartengine.renderer.support.SupportColorLevel;
import org.achartengine.renderer.support.SupportSelectedChartType;
import org.achartengine.renderer.support.SupportXAlign;
import org.achartengine.renderer.support.SupportYAlign;

import java.util.ArrayList;

/**
 * Created by wangjia on 30/06/14.
 */
public class SupportBarUtils extends BaseSupportUtils{
    private final  static int COLOR_UP_TARGET = Color.parseColor("#FF843D");
    private final  static int COLOR_LOW_TARGET = Color.parseColor("#FFC23E");
    private final  static int COLOR_OTHER= Color.parseColor("#8FD85A");

    public SupportBarUtils(Context context) {
        super(context);
    }

    public View initBarChartView(){
        mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        SupportBarSeriesRender barSeriesRender =new SupportBarSeriesRender();
        //设置柱状图的背景阴影是否可见
        barSeriesRender.setShowBarChartShadow(true);
//        barSeriesRender.setShowBarChartShadow(Color.DKGRAY);


        barSeriesRender.setSelectedChartType(SupportSelectedChartType.BOTH);

        //设置是否使用颜色分级功能
        barSeriesRender.setColorLevelValid(true);
        ArrayList<SupportColorLevel> list = new ArrayList<SupportColorLevel>();

        //如果仅仅以target作为颜色分级，可以使用这个用法
        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,mXYRenderer.getTargetValue(),COLOR_LOW_TARGET);
        SupportColorLevel supportColorLevel_b = new SupportColorLevel(mXYRenderer.getTargetValue(),mXYRenderer.getTargetValue()*10,COLOR_UP_TARGET);

        // 若有多个颜色等级可以使用这个用法
//        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,10,COLOR_LOW_TARGET);
//        SupportColorLevel supportColorLevel_b = new SupportColorLevel(10,15,COLOR_UP_TARGET);
//        SupportColorLevel supportColorLevel_c = new SupportColorLevel(15,20,COLOR_OTHER);


        list.add(supportColorLevel_a);
        list.add(supportColorLevel_b);
        barSeriesRender.setColorLevelList(list);

        String[] hours = new String[8];
        double[] allDataSets = new double[]{
                5,8,10,11,13,15,10,7
        };
        XYSeries sysSeries = new XYSeries("");
        for (int i = 0; i < allDataSets.length; i++) {
            sysSeries.add(i, allDataSets[i]);
            mXYRenderer.addXTextLabel(i, hours[i]);
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);
        View chartView =  ChartFactory.getSupportBarChartView(mContext, mXYMultipleSeriesDataSet,
                mXYRenderer, barSeriesRender, SupportBarChart.Type.STACKED);
        chartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphicalView graphicalView = (GraphicalView) v;
                graphicalView.handPointClickEvent("SupportBar");
            }
        });
        return  chartView;
    }

    @Override
    protected void setRespectiveRender(XYMultipleSeriesRenderer render) {
        mXYRenderer.setBarWidth(35);
        mXYRenderer.setBarSpacing(15);
        mXYRenderer.setXAxisMin(-0.5);
        mXYRenderer.setXAxisMax(8);
        mXYRenderer.setShowGrid(false);
        //设置XY轴Title的位置，默认是Center
        mXYRenderer.setSupportXAlign(SupportXAlign.LEFT);
        mXYRenderer.setSupportYAlign(SupportYAlign.TOP);
    }

    @Override
    protected XYSeriesRenderer getSimpleSeriesRender(int color) {
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(color);
        renderer.setDisplayChartValues(true);  // 设置是否在点上显示数据
        renderer.setPointStrokeWidth(4f);
        renderer.setChartValuesTextSize(14f);

//        renderer.setGradientStart(0,Color.GRAY);  //可以设置柱状图颜色的渐变
//        renderer.setGradientStop(10,Color.GREEN);
//        renderer.setGradientEnabled(false);

        return renderer;
    }
}
