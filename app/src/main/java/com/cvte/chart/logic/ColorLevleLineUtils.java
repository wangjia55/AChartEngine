package com.cvte.chart.logic;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.support.SupportColorLevel;
import org.achartengine.renderer.support.SupportSeriesRender;

import java.util.ArrayList;

/**
 * Created by wangjia on 28/06/14.
 */
public class ColorLevleLineUtils extends  BaseSupportUtils{

    private static final String TAG = ColorLevleLineUtils.class.getSimpleName();
    private final  static int COLOR_UP_TARGET = Color.parseColor("#FF843D");
    private final  static int COLOR_LOW_TARGET = Color.parseColor("#FFC23E");
    private final  static int COLOR_OTHER= Color.parseColor("#8FD85A");

    public ColorLevleLineUtils(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void setRespectiveRender(XYMultipleSeriesRenderer render) {
        render.setPointSize(12f);
    }

    public View initLineGraphView() {
        mXYMultipleSeriesDataSet = new XYMultipleSeriesDataset();

        final SupportSeriesRender lineSeriesRender = new SupportSeriesRender();
        lineSeriesRender.setClickPointColor(Color.parseColor("#8F77AA"));
        lineSeriesRender.setColorLevelValid(false);
        ArrayList<SupportColorLevel> list = new ArrayList<SupportColorLevel>();

        //如果仅仅以target作为颜色分级，可以使用这个用法
//        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,mXYRenderer.getTargetValue(),COLOR_LOW_TARGET);
//        SupportColorLevel supportColorLevel_b = new SupportColorLevel(mXYRenderer.getTargetValue(),mXYRenderer.getTargetValue()*10,COLOR_UP_TARGET);

        // 若有多个颜色等级可以使用这个用法
        SupportColorLevel supportColorLevel_a = new SupportColorLevel(0,10,COLOR_LOW_TARGET);
        SupportColorLevel supportColorLevel_b = new SupportColorLevel(10,15,COLOR_UP_TARGET);
        SupportColorLevel supportColorLevel_c = new SupportColorLevel(15,20,COLOR_OTHER);


        list.add(supportColorLevel_a);
        list.add(supportColorLevel_b);
        list.add(supportColorLevel_c);
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

        mXYRenderer.addSupportRenderer(lineSeriesRender);

        mXYMultipleSeriesDataSet.addSeries(sysSeries);
        //如果不许要颜色分级功能，则直接用原始的lineChart既可
        View view =  ChartFactory.getLineChartView(mContext, mXYMultipleSeriesDataSet, mXYRenderer);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphicalView graphicalView = (GraphicalView) v;
                graphicalView.handPointClickEvent(lineSeriesRender,"SupportLine");
            }
        });
        return  view;
    }


    @Override
    protected XYSeriesRenderer getSimpleSeriesRender(int color) {
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setColor(color);
        renderer.setFillPoints(true);   //是否是实心的点
        renderer.setDisplayChartValues(false);  // 设置是否在点上显示数据
        renderer.setLineWidth(5f);    //设置曲线的宽度
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setInnerCircleColor(Color.parseColor("#CC9B61"));
        return renderer;
    }


}
