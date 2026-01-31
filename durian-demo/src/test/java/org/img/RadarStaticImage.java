package org.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class RadarStaticImage {
    public static void main(String[] args) throws Exception {
        int width = 800, height = 800;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = width / 2, centerY = height / 2;
        int radius = 280;

        // 背景
        g2.setColor(new Color(245, 245, 245));
        g2.fillRect(0, 0, width, height);

        // 网格：3圈 + 6条射线
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1.2f));
        for (int level = 1; level <= 3; level++) {
            int r = radius * level / 3;
            g2.drawOval(centerX - r, centerY - r, r * 2, r * 2);
        }
        double[] angles = new double[6];
        for (int i = 0; i < 6; i++) {
            angles[i] = Math.toRadians(60 * i - 90);
            int x = (int) (centerX + radius * Math.cos(angles[i]));
            int y = (int) (centerY + radius * Math.sin(angles[i]));
            g2.drawLine(centerX, centerY, x, y);
        }

        // 数据多边形
        double[] scores = {10, 180, 150, 100, 150, 50}; // 替换成你的6个值 (0~1)
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            double r = radius * scores[i];
            xPoints[i] = (int) (centerX + r * Math.cos(angles[i]));
            yPoints[i] = (int) (centerY + r * Math.sin(angles[i]));
        }

        g2.setColor(new Color(100, 149, 237, 90)); // 半透明填充
        g2.fillPolygon(xPoints, yPoints, 6);
        g2.setColor(new Color(0, 0, 139));
        g2.setStroke(new BasicStroke(3f));
        g2.drawPolygon(xPoints, yPoints, 6);

        // 标签（简化版）
        String[] labels = {"重点管控", "在线监控", "缺陷隐患", "停电试验", "带电检测", "运行监控"};
        g2.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        g2.setColor(Color.BLUE);
        for (int i = 0; i < 6; i++) {
            int lx = (int) (centerX + (radius + 40) * Math.cos(angles[i]));
            int ly = (int) (centerY + (radius + 40) * Math.sin(angles[i])) + 5;
            g2.drawString(labels[i], lx - 30, ly);
        }

        g2.dispose();
        ImageIO.write(img, "png", new File("radar_static.png"));
        System.out.println("已生成 radar_static.png");
    }
}
