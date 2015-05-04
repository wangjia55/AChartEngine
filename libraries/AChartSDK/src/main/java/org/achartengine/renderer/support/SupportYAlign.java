package org.achartengine.renderer.support;

/**
 * Created by wangjia on 04/07/14.
 */

public enum SupportYAlign {
    /**
     * The text is drawn to the top of the y origin
     */
    TOP(0),
    /**
     * The text is drawn centered vertically on the y origin
     */
    CENTER(1),
    /**
     * The text is drawn to the bottom of the  y origin
     */
    BOTTOM(2);

    private SupportYAlign(int nativeInt) {
        this.nativeInt = nativeInt;
    }

    final int nativeInt;
}