package com.xtxk.cn.dto.alarmInfo;

import java.io.Serializable;

/**
 * @Author daiqing(daiqing @ xingtu.com)
 * @Description 智能识别矩形区域
 * @Date create in 2021/1/25 15:16
 * @Modified by
 */
public class Rectangle implements Serializable {
    /**
     * 距离顶边的距离
     */
    private Double top;
    /**
     * 距离左边的距离
     */
    private Double left;
    /**
     * 区域的宽度
     */
    private Double width;
    /**
     * 区域的高度
     */
    private Double height;
    /**
     * 人脸框相对于竖直方向的顺时针旋转角，[-180,180]
     */
    private Double rotation;

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "top=" + top +
                ", left=" + left +
                ", width=" + width +
                ", height=" + height +
                ", rotation=" + rotation +
                '}';
    }
}
