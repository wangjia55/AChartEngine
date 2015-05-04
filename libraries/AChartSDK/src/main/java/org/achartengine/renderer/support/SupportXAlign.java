package org.achartengine.renderer.support;

/**
 * Created by wangjia on 04/07/14.
 */
public enum SupportXAlign {
    /**
     * The text is drawn to the left of the x origin
     */
    LEFT(0),
    /**
     * The text is drawn centered horizontally on the x origin
     */
    CENTER(1),
    /**
     * The text is drawn to the right of the x origin
     */
    RIGHT(2),

    RIGHT_UP(3);

    private SupportXAlign(int nativeInt) {
        this.nativeInt = nativeInt;
    }

    final int nativeInt;
}