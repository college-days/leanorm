package org.jihui.leanorm;

/**
 * Created by zjh on 2015/6/2.
 */
public class Tuple<X, Y> {
    public final X left;
    public final Y right;

    public Tuple(X left, Y right) {
        this.left = left;
        this.right = right;
    }
}
