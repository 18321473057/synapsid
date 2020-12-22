package com.line.common.test.image;

import java.util.Objects;

/**
 * @Author: yangcs
 * @Date: 2020/12/22 17:06
 * @Description:画布定位 信息类
 */
public class LocationInfo {

    //图片定位 画布X点
    private int x;
    //图片定位 画布Y点
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationInfo that = (LocationInfo) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
