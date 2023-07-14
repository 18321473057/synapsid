package com.line.common.test.image;

import java.awt.*;

/**
 * @Author: yangcs
 * @Date: 2020/12/22 17:00
 * @Description: 文字描述信息类
 */
public class DescribeInfo extends LocationInfo {
    //文字描述
    private String msg;
    //默认黑色
    private Color color = Color.BLACK;
    //默认样式
    //Font.PLAIN（普通）
    //Font.BOLD（加粗）
    //Font.ITALIC（斜体）
    //Font.BOLD+ Font.ITALIC（粗斜体）
    private Font font = new Font("微软雅黑" , Font.PLAIN, 12);


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
