package com.line.common.test.image;

import java.awt.*;

/**
 * @Author: yangcs
 * @Date: 2020/12/22 16:54
 * @Description: 图片信息类
 */
public class PictureInfo extends LocationInfo {
    //图片对象
    private Image image;
    //图片希望宽度
    private int width;
    //图片希望长度
    private int height;


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
