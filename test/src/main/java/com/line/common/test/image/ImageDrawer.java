package com.line.common.test.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author: yangcs
 * @Date: 2020/12/22 17:11
 * @Description: 图片绘画者
 */
public class ImageDrawer {

    public static void main(String[] args) {
        String backgroundPath = "D:\\11.png";
        String imagePath = "D:\\9.png";
        String message01 = "金点·韩e童社 卫衣三件套";
//        String message01 = "金点·韩e童社 卫衣三件套毛衣马甲外套";
        String message02 = "$250 $250";
        String outPutPath = "E:\\999.jpg";
        overlapImage(backgroundPath, imagePath, message01, message02, outPutPath);
    }

    public static String overlapImage(String backgroundPath, String imagePath, String message01, String message02, String outPutPath) {
        try {
            //背景图
            BufferedImage background = resizeImage(235, 440, ImageIO.read(new File(backgroundPath)));

            //多图图片预处理
            BufferedImage image1 = resizeImage(235, 235, ImageIO.read(new File(imagePath)));
            BufferedImage image2 = resizeImage(71, 71, ImageIO.read(new File(imagePath)));
            BufferedImage image3 = resizeImage(71, 71, ImageIO.read(new File(imagePath)));
            BufferedImage image4 = resizeImage(71, 71, ImageIO.read(new File(imagePath)));


            BufferedImage qrcode = resizeImage(71, 71, ImageIO.read(new File(imagePath)));

            //平铺图以及二维码
            Graphics2D g = background.createGraphics();
            g.drawImage(image1, 0, 0, image1.getWidth(), image1.getHeight(), null);
            g.drawImage(image2, 0, 245, image2.getWidth(), image2.getHeight(), null);
            g.drawImage(image3, 81, 245, image3.getWidth(), image3.getHeight(), null);
            g.drawImage(image4, 162, 245, image4.getWidth(), image4.getHeight(), null);
            g.drawImage(qrcode, 162, 235 + 81 + 10, qrcode.getWidth(), qrcode.getHeight(), null);

            //文字
            g.setColor(Color.BLACK);
            g.setFont(new Font("微软雅黑" , Font.PLAIN, 12));
            g.drawString(message01, 10, 235 + 81 + 20);
            g.drawString("毛衣马甲外套" , 10, 235 + 81 + 35);
            //金额红色醒目
            g.setColor(Color.red);
            g.drawString("$250" , 10, 235 + 81 + 20 + 60);
            g.setColor(Color.BLACK);
            g.drawString("$250" , 40, 235 + 81 + 20 + 60);

            g.dispose();
            ImageIO.write(background, "jpg" , new File(outPutPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage resizeImage(int x, int y, BufferedImage bfi) {
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(
                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }
}
