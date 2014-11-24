package com.cvte.chart.logic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportColorLevel;
import org.achartengine.renderer.support.SupportLineSeriesRender;
import org.achartengine.renderer.support.SupportYAlign;

import java.util.ArrayList;

/**
 * Created by wangjia on 28/06/14.
 */
public class SupportColorTapeUtils extends  BaseSupportUtils{

    private static final String TAG = SupportColorTapeUtils.class.getSimpleName();
    private final  static int COLOR_UP_TARGET = Color.parseColor("#FF843D");
    private final  static int COLOR_LOW_TARGET = Color.parseColor("#FFC23E");
    private final  static int COLOR_OTHER= Color.parseColor("#8FD85A");


    public final static int COLOR_GREEN = Color.parseColor("#A5CF38");
    public final static int COLOR_YELLOW = Color.parseColor("#FAA61A");
    public final static int COLOR_RED = Color.parseColor("#ED1D25");
    public final static int COLOR_RED_LIGHT = Color.parseColor("#FF6C60");


    public SupportColorTapeUtils(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void setRespectiveRender(XYMultipleSeriesRenderer render) {
        render.setPointSize(12f);
        render.setXAxisMin(-1);
        render.setSupportYAlign(SupportYAlign.TOP);
        render.setYLabelsAlign(Paint.Align.LEFT);
        render.setTargetValue(10f);

        //设置图表支持颜色渐变带，绘制渐变色的方向是从下向上画的
        render.setColorRampVisible(true);
        render.setColorRampWidth(10f);
        render.setColorRampGradient(new int[]{COLOR_RED_LIGHT,COLOR_YELLOW,COLOR_GREEN,COLOR_YELLOW,COLOR_RED_LIGHT});
        render.setColorRampGradientPosition(new float[]{0.25f,0.35f,0.5f,0.65f,0.95f});
    }

    public View initLineGraphView() {
        mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        SupportLineSeriesRender lineSeriesRender = new SupportLineSeriesRender();
        lineSeriesRender.setClickPointColor(Color.parseColor("#8F77AA"));


        lineSeriesRender.setColorLevelValid(true);
        ArrayList<SupportColorLevel> list = new ArrayList<SupportColorLevel>();

        //如果仅仅以target作为颜色分级，可以使用这个用法
//        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,mXYRenderer.getTargetValue(),COLOR_LOW_TARGET);
//        SupportColorLevel supportColorLevel_b = new SupportColorLevel(mXYRenderer.getTargetValue(),mXYRenderer.getTargetValue()*10,COLOR_UP_TARGET);

        // 若有多个颜色等级可以使用这个用法
        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,4.5,COLOR_RED_LIGHT);
        SupportColorLevel supportColorLevel_b = new SupportColorLevel(4.5,8.5,COLOR_YELLOW);
        SupportColorLevel supportColorLevel_c = new SupportColorLevel(8.5,11.5,COLOR_GREEN);
        SupportColorLevel supportColorLevel_d = new SupportColorLevel(11.5,16,COLOR_YELLOW);
        SupportColorLevel supportColorLevel_e = new SupportColorLevel(16,20,COLOR_RED_LIGHT);


        list.add(supportColorLevel_a);
        list.add(supportColorLevel_b);
        list.add(supportColorLevel_c);
        list.add(supportColorLevel_d);
        list.add(supportColorLevel_e);
        lineSeriesRender.setColorLevelList(list);


        String[] hours = new String[20];
        double[] allDataSets = new double[]{
                5,8,10,11,13,15,10,7,14,18,13,10, 5,8,10,11,15,10,7,14
        };
        XYSeries sysSeries = new XYSeries("");
        for (int i = 0; i < allDataSets.length; i++) {
            sysSeries.add(i, allDataSets[i]);
            mXYRenderer.addXTextLabel(i, hours[i]);
        }
        mXYMultipleSeriesDataSet.addSeries(sysSeries);
        //如果不许要颜色分级功能，则直接用原始的lineChart既可
        View view =  ChartFactory.getSupportLineChartView(mContext, mXYMultipleSeriesDataSet, mXYRenderer, lineSeriesRender);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphicalView graphicalView = (GraphicalView) v;
                graphicalView.handPointClickEvent("SupportLine");
            }
        });
        return  view;
    }


    @Override
    protected XYSeriesRenderer getSimpleSeriesRender(int color) {
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(color);
        renderer.setFillPoints(true);   //是否是实心的点
        renderer.setDisplayChartValues(true);  // 设置是否在点上显示数据
        renderer.setLineWidth(3f);    //设置曲线的宽度
        renderer.setPointStyle(PointStyle.CIRCLE_POINT);
        renderer.setInnerCircleColor(Color.parseColor("#CC9B61"));
        renderer.setChartValuesTextSize(20f);
        renderer.setChartValuesSpacing(10f);
        return renderer;
    }


}
