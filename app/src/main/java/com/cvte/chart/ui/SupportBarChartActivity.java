package com.cvte.chart.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.cvte.chart.demo.R;
import com.cvte.chart.logic.SupportBarUtils;

public class SupportBarChartActivity extends Activity {

    private LinearLayout mLinearLayoutBarChart;
    private SupportBarUtils mBarChartUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_bar_chart);
        mLinearLayoutBarChart= (LinearLayout) findViewById(R.id.linearLayout_bar_chart);
        mBarChartUtils = new SupportBarUtils(this.getApplicationContext());
        mLinearLayoutBarChart.addView(mBarChartUtils.initBarChartView());
    }


}
