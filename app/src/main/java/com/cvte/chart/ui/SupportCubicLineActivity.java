package com.cvte.chart.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.cvte.chart.demo.R;
import com.cvte.chart.logic.SupportCubicLineUtils;


public class SupportCubicLineActivity extends Activity {
    private LinearLayout mLinearLayoutCubicLine;
    private SupportCubicLineUtils mCubicLineUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_cubicline);

        mCubicLineUtils = new SupportCubicLineUtils(this.getApplicationContext());

        mLinearLayoutCubicLine = (LinearLayout) findViewById(R.id.linearLayout_cubic_line);
        mLinearLayoutCubicLine.addView(mCubicLineUtils.initCubicLineGraphView());
    }



}
