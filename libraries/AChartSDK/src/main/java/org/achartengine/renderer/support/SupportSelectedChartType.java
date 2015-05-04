package org.achartengine.renderer.support;

/**
 * Created by wangjia on 01/07/14.
 */
public enum  SupportSelectedChartType {

    /**
     * The message is shown on the top of chart
     */
    SHOW_BOX(0),
    /**
     * show different color when selected
     */
    SHOW_DIFF_COLOR(1),
    /**
     * select both of up
     */
    BOTH(2);

    private SupportSelectedChartType(int nativeInt) {
        this.nativeInt = nativeInt;
    }

    final int nativeInt;
}
