package com.cvte.chart.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cvte.chart.ui.SupportBarChartActivity;
import com.cvte.chart.ui.SupportColorTapeLineActivity;
import com.cvte.chart.ui.SupportCubicLineActivity;
import com.cvte.chart.ui.SupportLineActivity;


public class MyActivity extends Activity {
    private final static int TYPE_CUBIC_LINE = 0;
    private final static int TYPE_NORMAL_LINE = 1;
    private final static int TYPE_NORMAL_BAR = 2;
    private final static int TYPE_SUPPROT_COLOR_TAPE = 3;

    private ListView mListViewChartDemo;
    private String[] mStringArrayChartTypes = new String[]{"扩展的曲线图", "新样式的图表", "扩展的柱状图","支持颜色带的图表"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListViewChartDemo = (ListView) findViewById(R.id.listView_chart_demo);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStringArrayChartTypes);
        mListViewChartDemo.setAdapter(arrayAdapter);
        mListViewChartDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case TYPE_CUBIC_LINE: {
                        Intent intent = new Intent(MyActivity.this, SupportCubicLineActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case TYPE_NORMAL_LINE: {
                        Intent intent = new Intent(MyActivity.this, SupportLineActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case TYPE_NORMAL_BAR: {
                        Intent intent = new Intent(MyActivity.this, SupportBarChartActivity.class);
                        startActivity(intent);
                    }
                    break;
                    case TYPE_SUPPROT_COLOR_TAPE: {
                        Intent intent = new Intent(MyActivity.this, SupportColorTapeLineActivity.class);
                        startActivity(intent);
                    }
                    break;
                }
            }
        });
    }
}
