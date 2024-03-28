package com.xtxk.cn.utils.image;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author daiqing(daiqing @ xingtu.com)
 * @Description Graphics2DUtil
 * @Date create in 2021/3/5 14:47
 * @Modified by
 */
public class Graphics2dUtil {

    private static Integer stroke = 1;

    /**
     * 获取Graphics2D对象
     * @param bufferedImage
     * @param color
     */
    public static Graphics2D getGraphics2D(BufferedImage bufferedImage, Color color){
        Graphics2D g2d = null;
        try {
            g2d = (Graphics2D) bufferedImage.getGraphics();
            //设置颜色和画笔粗细
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(stroke));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return g2d;
    }
}
