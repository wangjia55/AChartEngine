package com.cvte.chart.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.cvte.chart.demo.R;
import com.cvte.chart.logic.SupportColorTapeUtils;


public class SupportColorTapeLineActivity extends Activity {
    private SupportColorTapeUtils mLineUtils;
    private LinearLayout mLinearLayoutLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_line);
        mLinearLayoutLine = (LinearLayout) findViewById(R.id.linearLayout_line);
        mLineUtils = new SupportColorTapeUtils(this.getApplicationContext());
        mLinearLayoutLine.addView(mLineUtils.initLineGraphView());

    }


}
